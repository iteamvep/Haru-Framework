/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.rsa;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 * https://www.jianshu.com/p/d56a72013392
 * https://blog.csdn.net/igoqhb/article/details/19832259
 * 
 */
public class RSAUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RSAUtils.class);
    
    /**
     * 定义加密方式
     */
    private final static String KEY_RSA = "RSA";
    /**
     * 定义签名算法
     */
    private final static String KEY_RSA_SIGNATURE = "SHA512withRSA";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static PublicKey GetPublicKey(String base64Key){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(DecryptBase64(base64Key));
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            publicKey = keyFactory.generatePublic(keySpec);
//            writePemFile(publicKey, "", "C:\\Users\\iTeamVEP\\Desktop\\rs\\pub");
            return publicKey;
        } catch (NoSuchAlgorithmException ex) {
            LOG.error("getPrivateKey error", ex);
        } catch (InvalidKeySpecException ex) {
            LOG.error("getPrivateKey error", ex);
        }
        return publicKey;
    }
    
    public static PrivateKey GetPrivateKey(byte[] key){
        PrivateKey privateKey = null;
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            privateKey = keyFactory.generatePrivate(keySpec);
//            writePemFile(privateKey, "", "C:\\Users\\iTeamVEP\\Desktop\\rs\\priv");
            return privateKey;
        } catch (NoSuchAlgorithmException ex) {
            LOG.error("getPrivateKey error", ex);
        } catch (InvalidKeySpecException ex) {
            LOG.error("getPrivateKey error", ex);
        }
        return privateKey;
    }
    
    public static String GetBase64Key(Key key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
    
    public static void WritePemFile(Key key, String description, String filename) throws FileNotFoundException, IOException {
	PemFile pemFile = new PemFile(key, description);
	pemFile.write(filename);
	LOG.info(String.format("%s successfully writen in file %s.", description, filename));
    }

    public static byte[] Encrypt(byte[] data, Key key) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        if(data == null || key == null)
            return null;
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    public static byte[] Decrypt(byte[] data, Key key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        if(data == null || key == null)
            return null;
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }
    
    public static byte[] EncryptWithPublicKey(String data, String key) {
        return EncryptWithPublicKey(data.getBytes(), key);
    }
    
    public static byte[] EncryptWithPublicKey(byte[] data, String key) {
        if(data == null || key == null)
            return null;
        try {
            return EncryptBase64(Encrypt(data, GetPublicKey(key)));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            LOG.error("encryptWithPublicKey error.", ex);
        }
        return null;
    }
    
    public static byte[] DecryptWithPublicKey(String data, String key) {
        return DecryptWithPublicKey(DecryptBase64(data.getBytes()), key);
    }
    
    public static byte[] DecryptWithPublicKey(byte[] data, String key) {
        if(data == null || key == null)
            return null;
        try {
            return Decrypt(data, GetPublicKey(key));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            LOG.error("decryptWithPublicKey error.", ex);
        }
        return null;
    }
    
    /**
     * 
     * @param data
     * @param base64Key
     * @return Base64-Encoded Bytes
     */
    public static byte[] EncryptWithPrivateKey(String data, String base64Key) {
        return EncryptWithPrivateKey(data.getBytes(), DecryptBase64(base64Key));
    }
    
    /**
     * 
     * @param data
     * @param key
     * @return Base64-Encoded Bytes
     */
    public static byte[] EncryptWithPrivateKey(String data, byte[] key) {
        return EncryptWithPrivateKey(data.getBytes(), key);
    }
    
    /**
     * 
     * @param data
     * @param base64Key
     * @return Base64-Encoded Bytes
     */
    public static byte[] EncryptWithPrivateKey(byte[] data, String base64Key) {
        return EncryptWithPrivateKey(data, DecryptBase64(base64Key));
    }
    
    /**
     * 
     * @param data
     * @param key
     * @return Base64-Encoded Bytes
     */
    public static byte[] EncryptWithPrivateKey(byte[] data, byte[] key) {
        if(data == null || key == null)
            return null;
        try {
            return EncryptBase64(Encrypt(data, GetPrivateKey(key)));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            LOG.error("encryptWithPrivateKey error.", ex);
        }
        return null;
    }

    public static byte[] DecryptWithPrivateKey(String data, String base64Key) {
        return DecryptWithPrivateKey(DecryptBase64(data.getBytes()), DecryptBase64(base64Key));
    }
    
    public static byte[] DecryptWithPrivateKey(String data, byte[] key) {
        return DecryptWithPrivateKey(DecryptBase64(data.getBytes()), key);
    }
    
    public static byte[] DecryptWithPrivateKey(byte[] data, String base64Key) {
        return DecryptWithPrivateKey(data, DecryptBase64(base64Key));
    }
    
    public static byte[] DecryptWithPrivateKey(byte[] data, byte[] key) {
        if(data == null || key == null)
            return null;
        try {
            return Decrypt(data, GetPrivateKey(key));
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException ex) {
            LOG.error("decryptWithPrivateKey error.", ex);
        }
        return null;
    }
    
    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     */
    public static String Sign(byte[] data, String privateKey) {
        String str = "";
        try {
            // 解密由base64编码的私钥
            byte[] bytes = DecryptBase64(privateKey);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);
            // 指定的加密算法
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            // 取私钥对象
            PrivateKey key = factory.generatePrivate(pkcs);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initSign(key);
            signature.update(data);
            str = new String(EncryptBase64(signature.sign()));
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException ex) {
            LOG.error("sign error", ex);
        }
        return str;
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true，失败返回false
     */
    public static boolean Verify(byte[] data, String publicKey, String sign) {
        boolean flag = false;
        try {
            // 解密由base64编码的公钥
            byte[] bytes = DecryptBase64(publicKey);
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            // 指定的加密算法
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            // 取公钥对象
            PublicKey key = factory.generatePublic(keySpec);
            // 用公钥验证数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initVerify(key);
            signature.update(data);
            flag = signature.verify(DecryptBase64(sign));
        } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException | InvalidKeySpecException ex) {
            LOG.error("verify error", ex);
        }
        return flag;
    }
    
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
            return null;
        }
    }
    
    public static byte[] DecryptBase64(byte[] data) {
        if(data == null)
            return null;
        try{
            return Base64.getDecoder().decode(data);
        } catch (Exception ex) {
            LOG.error("decryptBase64 error, data: {}", data, ex);
            return null;
        }
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
     
//    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
//        try {
//            String pubk = new String(Base64.getDecoder().decode(privateKey.getBytes()));
//            String encryptedString = new String(RSAUtil.encryptWithPrivateKey("Dhiraj is the author", privateKey));
//            System.out.println(encryptedString);
//            byte[] data = RSAUtil.decryptWithPublicKey(encryptedString, publicKey);
//            String decryptedString = new String(data);
//            System.out.println(decryptedString);
//        } catch (NoSuchAlgorithmException ex) {
//            System.out.println(ex.getMessage());
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//
//    }
    
}
