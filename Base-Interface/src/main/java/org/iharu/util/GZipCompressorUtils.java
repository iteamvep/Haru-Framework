/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import com.google.common.primitives.Bytes;
import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.exception.ExceptionUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iTeam_VEP
 */
public class GZipCompressorUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(GZipCompressorUtils.class);
  
    private GZipCompressorUtils(){};  
   
    public static void compressGZIP(File input, File output) throws IOException {
        try (GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(output))){
            try (FileInputStream in = new FileInputStream(input)){
                byte[] buffer = new byte[1024];
                int len;
                while((len=in.read(buffer)) != -1){
                    out.write(buffer, 0, len);
                }
            }
        }
    }

    public static void decompressGzip(File input, File output) throws IOException {
        try (GZIPInputStream in = new GZIPInputStream(new FileInputStream(input))){
            try (FileOutputStream out = new FileOutputStream(output)){
                byte[] buffer = new byte[1024];
                int len;
                while((len = in.read(buffer)) != -1){
                    out.write(buffer, 0, len);
                }
            }
        }
    }
    
    public static void compressGZIP(byte[] input, File output) throws IOException {
        try (GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(output))){
            out.write(input, 0, input.length);
        }
    }
    
    public static byte[] decompressGzip(File input) throws IOException {
        List<Byte> data = new ArrayList();
        try (GZIPInputStream in = new GZIPInputStream(new FileInputStream(input))){
            byte[] buffer = new byte[1024];
            int len;
            while((len = in.read(buffer)) != -1){
                for(byte b:buffer){
                    data.add(b);
                }
            }
        }
        return Bytes.toArray(data);
    }
    
    public static byte[] decompressGzip(byte[] input) throws IOException {
        List<Byte> data = new ArrayList();
        try (GZIPInputStream in = new GZIPInputStream(new ByteArrayInputStream(input))){
            byte[] buffer = new byte[1024];
            int len;
            while((len = in.read(buffer)) != -1){
                for(byte b:buffer){
                    data.add(b);
                }
            }
        }
        return Bytes.toArray(data);
    }
    
    public static void compressGZIP(File input) throws IOException {
        compressGZIP(input, new File(input.getAbsolutePath()+".gz"));
    }
}
