/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.iharu.crypto.argon2.Argon2Utils;
import org.iharu.crypto.hmac.HmacUtils;
import org.iharu.util.StringUtils;

/**
 *
 * @author iHaru
 */
public class BytesUtils {
    
    /**
     * 
     * https://stackoverflow.com/questions/11525186/convert-char-to-byte-without-losing-bits
     * https://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.1.3
     * https://docs.oracle.com/javase/specs/jls/se7/html/jls-5.html#jls-5.1.4
     * char: 16-bit unsigned - always unicode encode
     * byte: 8-bit signed
     * 
     * @param str
     * @return 
     */
    public static byte[] StringToUnicodeByteArray(String str){
        byte[] bytes = new byte[str.length()];
        for (int i = 0; i != bytes.length; i++)
        {
            char ch = str.charAt(i);
            bytes[i] = (byte)ch;
        }
        return bytes;
    }
    
    public static String encodeHexString(byte[] byteArray) {
        StringBuilder hexStringBuffer = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            hexStringBuffer.append(byteToHex(byteArray[i]));
        }
        return hexStringBuffer.toString();
    }
    
    public static byte[] decodeHexString(String hexString) {
        if (hexString.length() % 2 == 1) {
            throw new IllegalArgumentException(
              "Invalid hexadecimal String supplied.");
        }

        byte[] bytes = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i += 2) {
            bytes[i / 2] = hexToByte(hexString.substring(i, i + 2));
        }
        return bytes;
    }
    
    public static String byteToHex(byte num) {
        char[] hexDigits = new char[2];
        hexDigits[0] = Character.forDigit((num >> 4) & 0xF, 16);
        hexDigits[1] = Character.forDigit((num & 0xF), 16);
        return new String(hexDigits);
    }

    public static byte hexToByte(String hexString) {
        int firstDigit = toDigit(hexString.charAt(0));
        int secondDigit = toDigit(hexString.charAt(1));
        return (byte) ((firstDigit << 4) + secondDigit);
    }

    private static int toDigit(char hexChar) {
        int digit = Character.digit(hexChar, 16);
        if(digit == -1) {
            throw new IllegalArgumentException(
              "Invalid Hexadecimal Character: "+ hexChar);
        }
        return digit;
    }
    
    
}
