/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.hmac;

import org.iharu.crypto.hmac.type.HMACEncryptType;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.iharu.crypto.util.BytesUtils;
import org.iharu.util.StringUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class HmacUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HmacUtils.class);
    
    private final static HMACEncryptType DEFAULT_ENCRYPTION = HMACEncryptType.HMAC_SHA256;
    
    public static byte[] SafeHashPasswordWithSalt(String text, String key, HMACEncryptType encryptType){
        try{
            return DigestWithHmacSHA512(text, key, encryptType);
        } catch(Exception ex) {
            LOG.error("SafeHashPasswordWithSalt failed", ex);
        }
        return null;
    }
    
    public static byte[] SafeHashPasswordWithSalt(String text, String key){
        return SafeHashPasswordWithSalt(text, key, DEFAULT_ENCRYPTION);
    }
    
    public static byte[] DigestWithHmacSHA512(String text, String key) throws NoSuchAlgorithmException, InvalidKeyException
    {
        return DigestWithHmacSHA512(text, key, DEFAULT_ENCRYPTION);
    }
    
    public static byte[] DigestWithHmacSHA512(String text, String key, HMACEncryptType encryptType) throws NoSuchAlgorithmException, InvalidKeyException
    {
        if(StringUtils.isNullOrWhiteSpace(key))
            return null;
        Mac hmacSHA512;
        hmacSHA512 = Mac.getInstance(encryptType.getName());
        SecretKeySpec secretKeySpec = new SecretKeySpec(BytesUtils.StringToUnicodeByteArray(key), encryptType.getName());
        hmacSHA512.init(secretKeySpec);
        return hmacSHA512.doFinal(BytesUtils.StringToUnicodeByteArray(text));
    }
    
}
