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
            return DigestWithHmac(text, key, encryptType);
        } catch(Exception ex) {
            LOG.error("SafeHashPasswordWithSalt failed", ex);
        }
        return null;
    }
    
    public static byte[] SafeHashPasswordWithSalt(String text, String key){
        return SafeHashPasswordWithSalt(text, key, HMACEncryptType.HMAC_SHA256);
    }
    
    public static byte[] DigestWithHmacSHA512(String text, String key) throws NoSuchAlgorithmException, InvalidKeyException
    {
        return DigestWithHmac(text, key, HMACEncryptType.HMAC_SHA512);
    }
    
    public static byte[] DigestWithHmac(String text, String key) throws NoSuchAlgorithmException, InvalidKeyException
    {
        if(StringUtils.isNullOrWhiteSpace(key))
            return null;
        Mac hmac;
        hmac = Mac.getInstance(DEFAULT_ENCRYPTION.getName());
        SecretKeySpec secretKeySpec = new SecretKeySpec(BytesUtils.StringToUnicodeByteArray(key), DEFAULT_ENCRYPTION.getName());
        hmac.init(secretKeySpec);
        return hmac.doFinal(BytesUtils.StringToUnicodeByteArray(text));
    }
    
    public static byte[] DigestWithHmac(String text, String key, HMACEncryptType encryptType) throws NoSuchAlgorithmException, InvalidKeyException
    {
        if(StringUtils.isNullOrWhiteSpace(key))
            return null;
        Mac hmac;
        hmac = Mac.getInstance(encryptType.getName());
        SecretKeySpec secretKeySpec = new SecretKeySpec(BytesUtils.StringToUnicodeByteArray(key), encryptType.getName());
        hmac.init(secretKeySpec);
        return hmac.doFinal(BytesUtils.StringToUnicodeByteArray(text));
    }
    
}
