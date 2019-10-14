/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.aes;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.iharu.crypto.aes.type.AESEncryptType;
import org.iharu.constant.ConstantValue;
import org.iharu.util.StringUtils;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class AesUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(AesUtils.class);
    
    private final static String KEY = "AES";
    private final static AESEncryptType DEFAULT_ENCRYPTION = AESEncryptType.GCM;
    public static final int GCM_TAG_LENGTH = 16;
    
    private static byte[] genRandomIV(AESEncryptType encryptType){
        SecureRandom randomSecureRandom = new SecureRandom();
        byte[] iv = new byte[encryptType.getIvLength()];
        randomSecureRandom.nextBytes(iv);
        return iv;
    }
    
    public static byte[] GenKey(String password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException, UnsupportedEncodingException{
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(ConstantValue.CHARSET), 65536, 256);
        return factory.generateSecret(spec).getEncoded();
    }
    
    private static byte[] encrypt(byte[] data, byte[] key, AESEncryptType encryptType) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        if(data == null || key == null)
            return null;
        Cipher cipher = Cipher.getInstance(encryptType.getName());
        SecretKey aesSecret = new SecretKeySpec(key, KEY);
        byte[] iv = genRandomIV(encryptType);
        
        if(AESEncryptType.GCM == encryptType){
            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, iv);
            // Initialize Cipher for ENCRYPT_MODE
            cipher.init(Cipher.ENCRYPT_MODE, aesSecret, gcmParameterSpec);
        } else {
            IvParameterSpec ivps = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, aesSecret, ivps);
        }
        
        byte [] cipherText = cipher.doFinal(data);
        byte[] cipherData = new byte[encryptType.getIvLength() + cipherText.length];
        System.arraycopy(iv, 0, cipherData, 0, encryptType.getIvLength());
        System.arraycopy(cipherText, 0, cipherData, encryptType.getIvLength(), cipherText.length);
        return cipherData;
    }
    
    public static byte[] EncryptWithoutException(byte[] data, byte[] key) {
        try {
            return encrypt(data, key, DEFAULT_ENCRYPTION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException ex) {
            LOG.error("Encrypt error", ex);
        }
        return null;
    }
    
    public static byte[] EncryptWithoutException(String data, byte[] key) {
        try {
            return encrypt(StringUtils.StringToByteArray(data), key, DEFAULT_ENCRYPTION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException ex) {
            LOG.error("Encrypt error", ex);
        }
        return null;
    }
    
    public static byte[] Encrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        return encrypt(data, key, DEFAULT_ENCRYPTION);
    }
    
    public static byte[] Encrypt(byte[] data, byte[] key, AESEncryptType encryptType) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        return encrypt(data, key, encryptType);
    }
    
    public static byte[] Encrypt(byte[] data, String password, String salt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException {
        return Encrypt(data, GenKey(password, salt));
    }
    
    private static byte[] decrypt(byte[] data, byte[] key, AESEncryptType encryptType) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        if(data == null || key == null)
            return null;
        Cipher cipher = Cipher.getInstance(encryptType.getName());
        SecretKey aesSecret = new SecretKeySpec(key, KEY);
        if(AESEncryptType.GCM == encryptType){
            // Create GCMParameterSpec
            GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, Arrays.copyOfRange(data, 0, encryptType.getIvLength()));
            // Initialize Cipher for DECRYPT_MODE
            cipher.init(Cipher.DECRYPT_MODE, aesSecret, gcmParameterSpec);
        } else {
            IvParameterSpec ivps = new IvParameterSpec(Arrays.copyOfRange(data, 0, encryptType.getIvLength()));
            cipher.init(Cipher.DECRYPT_MODE, aesSecret, ivps);
        }
        
        return cipher.doFinal(Arrays.copyOfRange(data, encryptType.getIvLength(), data.length));
    }
    
    public static byte[] DecryptWithoutException(byte[] data, byte[] key) {
        try {
            return decrypt(data, key, DEFAULT_ENCRYPTION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException ex) {
            LOG.error("Decrypt error", ex);
        }
        return null;
    }
    
    public static byte[] DecryptWithoutException(String data, byte[] key) {
        try {
            return decrypt(StringUtils.StringToByteArray(data), key, DEFAULT_ENCRYPTION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException ex) {
            LOG.error("Decrypt error", ex);
        }
        return null;
    }
    
    public static byte[] Decrypt(byte[] data, byte[] key, AESEncryptType encryptType) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        return decrypt(data, key, encryptType);
    }
    
    public static byte[] Decrypt(byte[] data, byte[] key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
        return decrypt(data, key, DEFAULT_ENCRYPTION);
    }
    
    public static byte[] Decrypt(byte[] data, String password, String salt) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, UnsupportedEncodingException {
        return Decrypt(data, GenKey(password, salt));
    }
    
}
