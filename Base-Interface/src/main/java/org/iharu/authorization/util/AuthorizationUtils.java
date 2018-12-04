/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.authorization.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author iTeamVEP
 */
public class AuthorizationUtils {
    private static final String CHARSET = "UTF8";
    private static final SecureRandom RAND = new SecureRandom();
    
    public static String uuidGen(boolean onlyAlphabet) {
        if(onlyAlphabet){
            return UUID.randomUUID().toString().replace("-", "").toLowerCase();
        } else {
            return UUID.randomUUID().toString().toLowerCase();
        }
    }
    
    public static String getTimestamp() {
        final long secondsFromEpoch = System.currentTimeMillis() / 1000;
        return Long.toString(secondsFromEpoch);
    }
    
    public static String tokenGen(){
        String baseStr = String.valueOf(System.nanoTime()) + String.valueOf(Math.abs(RAND.nextLong()));
        MessageDigest md;  
        try {
            md = MessageDigest.getInstance("md5");
            return Base64.getEncoder().encodeToString(md.digest(baseStr.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            return null;
        }
    }
    
    public static String secretTokenGen(){
        return dataEncrypt(String.valueOf(RAND.nextLong()), String.valueOf(System.nanoTime()), null);
    }
    
    public static String dataEncrypt(String signatureBase, String signingKey, String keySpec) {
        String _keySpec = StringUtils.isBlank(keySpec) ? "HmacSHA1" : keySpec;
        try {
            final String key = signingKey;
            // Calculate the signature by passing both the signature base and signing key to the
            // HMAC-SHA1 hashing algorithm
            final byte[] signatureBaseBytes = signatureBase.getBytes(CHARSET);
            final byte[] keyBytes = key.getBytes(CHARSET);
            final SecretKey secretKey = new SecretKeySpec(keyBytes, _keySpec);
            final Mac mac = Mac.getInstance(_keySpec);
            mac.init(secretKey);
            final byte[] signatureBytes = mac.doFinal(signatureBaseBytes);
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException e) {
            return "";
        }
    }
}
