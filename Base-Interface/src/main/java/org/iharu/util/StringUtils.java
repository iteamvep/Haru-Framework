/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.iharu.constant.ConstantValue;
import static org.iharu.util.CommontUtils.LOG;

/**
 *
 * @author iHaru
 */
public class StringUtils {
    private StringUtils() { }   

    public static boolean isNullOrEmpty(String s){
        if(s==null || s.isEmpty()){
            return true;
        }
        return false;
    }

    public static boolean isNullOrWhiteSpace(String s){
        return s == null || s.trim().isEmpty();
    }
    
    public static byte[] StringToByteArray(String str){
        try {
            return str.getBytes(ConstantValue.CHARSET);
        } catch (UnsupportedEncodingException ex) {
            LOG.error("could not encode: {}", str);
        }
        return null;
    }
    
    public static String ByteArrayToString(byte[] bytes){
        try {
            return new String(bytes, ConstantValue.CHARSET);
        } catch (UnsupportedEncodingException ex) {
            LOG.error("could not encode: {}", bytes);
        }
        return null;
    }
    
}
