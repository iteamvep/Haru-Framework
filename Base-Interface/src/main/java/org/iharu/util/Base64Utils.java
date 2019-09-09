/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iharu.constant.ConstantValue;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class Base64Utils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Base64Utils.class);
    
    /**
     * BASE64 解密
     *
     * @param data 需要解密的字符串
     * @return 字节数组
     */
    public static byte[] DecryptBase64(String data) {
        if(data == null)
            return null;
        try{
            return Base64.getDecoder().decode(data);
        } catch (Exception ex) {
            LOG.error("decryptBase64 error, data: {}", data, ex);
        }
        return null;
    }
    
    public static byte[] DecryptBase64(byte[] data) {
        if(data == null)
            return null;
        try{
            return Base64.getDecoder().decode(data);
        } catch (Exception ex) {
            LOG.error("decryptBase64 error, data: {}", data, ex);
        }
        return null;
    }

    /**
     * BASE64 加密
     *
     * @param data 需要加密的字节数组
     * @return 字节数组
     */
    public static byte[] EncryptBase64(byte[] data) {
        if(data == null)
            return null;
        return Base64.getEncoder().encode(data);
    }
    
    public static String EncryptBase64ToString(byte[] data) {
        if(data == null)
            return null;
        try {
            return new String(Base64.getEncoder().encode(data), ConstantValue.CHARSET);
        } catch (UnsupportedEncodingException ex) {
            LOG.error("could not encode: {}", data);
        }
        return null;
    }
    
}
