/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.io.UnsupportedEncodingException;
import org.iharu.constant.ConstantValue;
import static org.iharu.util.CommontUtils.LOG;

/**
 *
 * @author iHaru
 */
public class StringUtils {
    private StringUtils() { }   

    public static boolean isNullOrEmpty(String s){
        return s==null || s.isEmpty();
    }
    
    public static boolean isAnyNullOrWhiteSpace(String ...strings){
        for(String string:strings){
            if(string == null || string.trim().isEmpty())
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
