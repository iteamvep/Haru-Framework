/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.auth2.authentication;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.iharu.crypto.aes.AesUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class Handshake {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Handshake.class);
    
    private static final String DEFAULT_ENCRYPTION = "AES";
    
    public static byte[] GenEncryptedToken(byte[] token, byte[] key){
        return GenEncryptedToken(token, AesUtils.ParseSecretKey(key));
    }
    
    public static byte[] GenEncryptedToken(byte[] token, SecretKey key){
        try {
            return AesUtils.Encrypt(token, key);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException ex) {
            LOG.warn("GenEncryptedToken failed", ex);
        }
        return null;
    }
    
    
    
}
