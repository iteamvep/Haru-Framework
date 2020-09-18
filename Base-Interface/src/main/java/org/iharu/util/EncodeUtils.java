/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.BitSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author VEP
 */
public class EncodeUtils {
    
    private static Logger LOG = LoggerFactory.getLogger(EncodeUtils.class);
 
    private static int BYTE_SIZE = 8;
    public static String CODE_UTF8 = "UTF-8";
    public static String CODE_UTF8_BOM = "UTF-8_BOM";
    public static String CODE_GBK = "GBK";
 
    /**
     * 通过文件全名称获取编码集名称
     *
     * @param fullFileName absolute filepath
     * @param ignoreBom ignore utf-8 bom
     * @return Encode charset
     * @throws Exception IOException
     */
    public static String getEncode(String fullFileName, boolean ignoreBom) throws Exception {
    	LOG.debug("fullFileName ; {}", fullFileName);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fullFileName));
        return getEncode(bis, ignoreBom);
    }
 
    /**
     * 通过文件缓存流获取编码集名称，文件流必须为未曾
     *
     * @param bis input buffered input stream
     * @param ignoreBom ignore utf-8 bom
     * @return Encode charset
     * @throws Exception IOException
     */
    public static String getEncode(BufferedInputStream bis, boolean ignoreBom) throws Exception {
        bis.mark(0);
 
        String encodeType = "未识别";
        byte[] head = new byte[3];
        bis.read(head);
        if (head[0] == -1 && head[1] == -2) {
            encodeType = "UTF-16";
        } else if (head[0] == -2 && head[1] == -1) {
            encodeType = "Unicode";
        } else if (head[0] == -17 && head[1] == -69 && head[2] == -65) { //带BOM
            if (ignoreBom) {
                encodeType = CODE_UTF8;
            } else {
                encodeType = CODE_UTF8_BOM;
            }
        } else if ("Unicode".equals(encodeType)) {
            encodeType = "UTF-16";
        } else if (isUTF8(bis)) {
            encodeType = CODE_UTF8;
        } else {
            encodeType = CODE_GBK;
        }
        LOG.info("result encode type : " + encodeType);
        return encodeType;
    }
 
    /**
     * 是否是无BOM的UTF8格式，不判断常规场景，只区分无BOM UTF8和GBK
     *
     * @param bis input buffered input stream
     * @return
     */
    private static boolean isUTF8( BufferedInputStream bis) throws Exception {
        bis.reset();
 
        //读取第一个字节
        int code = bis.read();
        do {
            BitSet bitSet = convert2BitSet(code);
            //判断是否为单字节
            if (bitSet.get(0)) {//多字节时，再读取N个字节
                if (!checkMultiByte(bis, bitSet)) {//未检测通过,直接返回
                    return false;
                }
            } else {
                //单字节时什么都不用做，再次读取字节
            }
            code = bis.read();
        } while (code != -1);
        return true;
    }
 
    /**
     * 检测多字节，判断是否为utf8，已经读取了一个字节
     *
     * @param bis input buffered input stream
     * @param bitSet bitSet
     * @return
     */
    private static boolean checkMultiByte(BufferedInputStream bis, BitSet bitSet) throws Exception {
        int count = getCountOfSequential(bitSet);
        byte[] bytes = new byte[count - 1];//已经读取了一个字节，不能再读取
        bis.read(bytes);
        for (byte b : bytes) {
            if (!checkUtf8Byte(b)) {
                return false;
            }
        }
        return true;
    }
 
    /**
     * 检测单字节，判断是否为utf8
     *
     * @param b byte
     * @return
     */
    private static boolean checkUtf8Byte(byte b) throws Exception {
        BitSet bitSet = convert2BitSet(b);
        return bitSet.get(0) && !bitSet.get(1);
    }
 
    /**
     * 检测bitSet中从开始有多少个连续的1
     *
     * @param bitSet bitSet
     * @return
     */
    private static int getCountOfSequential( BitSet bitSet) {
        int count = 0;
        for (int i = 0; i < BYTE_SIZE; i++) {
            if (bitSet.get(i)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
 
 
    /**
     * 将整形转为BitSet
     *
     * @param code int
     * @return
     */
    private static BitSet convert2BitSet(int code) {
        BitSet bitSet = new BitSet(BYTE_SIZE);
 
        for (int i = 0; i < BYTE_SIZE; i++) {
            int tmp3 = code >> (BYTE_SIZE - i - 1);
            int tmp2 = 0x1 & tmp3;
            if (tmp2 == 1) {
                bitSet.set(i);
            }
        }
        return bitSet;
    }
 
    /**
     * 将一指定编码的文件转换为另一编码的文件
     *
     * @param oldFullFileName source file absolute filepath
     * @param oldCharsetName source file charset
     * @param newFullFileName output file absolute filepath
     * @param newCharsetName output file charset
     * @throws java.lang.Exception IOException
     */
    public static void convert(String oldFullFileName, String oldCharsetName, String newFullFileName, String newCharsetName) throws Exception {
    	LOG.info("the old file name is : {}, The oldCharsetName is : {}", oldFullFileName, oldCharsetName);
    	LOG.info("the new file name is : {}, The newCharsetName is : {}", newFullFileName, newCharsetName);
 
        StringBuffer content = new StringBuffer();
 
        BufferedReader bin = new BufferedReader(new InputStreamReader(new FileInputStream(oldFullFileName), oldCharsetName));
        String line;
        while ((line = bin.readLine()) != null) {
            content.append(line);
            content.append(System.getProperty("line.separator"));
        }
        newFullFileName = newFullFileName.replace("\\", "/");
        File dir = new File(newFullFileName.substring(0, newFullFileName.lastIndexOf("/")));
        if (!dir.exists()) {
            dir.mkdirs();
        }
        Writer out = new OutputStreamWriter(new FileOutputStream(newFullFileName), newCharsetName);
        out.write(content.toString());
    }

/** 
 * 判断文件的编码格式 
 * @param fileName :file 
 * @return 文件编码格式 
 * @throws java.lang.Exception IOException
 */  
    public static String codeString(String fileName) throws Exception{  
          
        //String charset = "GBK"; // 默认编码
        String charset = "UTF-8"; 
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileName))) {
                bis.mark(0);
                int read = bis.read(first3Bytes, 0, 3);
                if (read == -1)
                    return charset;
                if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
                    charset = "UTF-16LE";
                    checked = true;
                } else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1]
                        == (byte) 0xFF) {
                    charset = "UTF-16BE";
                    checked = true;
                } else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1]
                        == (byte) 0xBB
                        && first3Bytes[2] == (byte) 0xBF) {
                    charset = "UTF-8";
                    checked = true;
                }
                bis.reset();
                if (!checked) {
                    int loc = 0;
                    while ((read = bis.read()) != -1) {
                        loc++;
                        if (read >= 0xF0)
                            break;
                        //单独出现BF以下的，也算是GBK
                        if (0x80 <= read && read <= 0xBF)
                            break;
                        if (0xC0 <= read && read <= 0xDF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) { // (0x80 -
                            // 0xBF),也可能在GB编码内
                            } else
                                break;
                            // 也有可能出错，但是几率较小
                        } else if (0xE0 <= read && read <= 0xEF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                read = bis.read();
                                if (0x80 <= read && read <= 0xBF) {
                                    charset = "UTF-8";
                                    break;
                                } else
                                    break;
                            } else
                                break;
                        }
                    }
                    //System.out.println(loc + " " + Integer.toHexString(read));
                }
            }
        } catch (IOException e) {
        }
        return charset;
    }
}
