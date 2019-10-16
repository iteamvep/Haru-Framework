/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.ecc;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
 
import javax.crypto.Cipher;
 
import org.bouncycastle.jce.interfaces.ECPrivateKey;
import org.bouncycastle.jce.interfaces.ECPublicKey;
import org.iharu.util.Base64Utils;

/**
 *
 * @author iHaru
 */
public class ECCUtils {
    
    static {
	Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }
	
	//生成秘钥对
	public static KeyPair genKeyPair() throws Exception {
            return org.iharu.crypto.KeyPairGenerator.getInstance("EC", 256).getKeyPair();
	}
        
        public static org.iharu.crypto.KeyPairGenerator genKeyPairGenerator() throws Exception {
            return org.iharu.crypto.KeyPairGenerator.getInstance("EC", 256);
	}
	
	//获取公钥(Base64编码)
	public static String getPublicKey(KeyPair keyPair){
		ECPublicKey publicKey = (ECPublicKey) keyPair.getPublic();
		byte[] bytes = publicKey.getEncoded();
		return Base64Utils.EncryptBase64ToString(bytes);
	}
	
	//获取私钥(Base64编码)
	public static String GetPrivateKey(KeyPair keyPair){
		ECPrivateKey privateKey = (ECPrivateKey) keyPair.getPrivate();
		byte[] bytes = privateKey.getEncoded();
		return Base64Utils.EncryptBase64ToString(bytes);
	}
	
	//将Base64编码后的公钥转换成PublicKey对象
	public static ECPublicKey GetPublicKey(String pubStr) throws Exception{
		byte[] keyBytes = Base64Utils.DecryptBase64(pubStr);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
		ECPublicKey publicKey = (ECPublicKey) keyFactory.generatePublic(keySpec);
		return publicKey;
	}
	
	//将Base64编码后的私钥转换成PrivateKey对象
	public static ECPrivateKey string2PrivateKey(String priStr) throws Exception{
		byte[] keyBytes = Base64Utils.DecryptBase64(priStr);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("EC", "BC");
		ECPrivateKey privateKey = (ECPrivateKey) keyFactory.generatePrivate(keySpec);
		return privateKey;
	}
	
	//公钥加密
	public static byte[] EncryptWithPublicKey(byte[] content, PublicKey publicKey) throws Exception{
		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}
	
	//私钥解密
	public static byte[] DecryptWithPrivateKey(byte[] content, PrivateKey privateKey) throws Exception{
		Cipher cipher = Cipher.getInstance("ECIES", "BC");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] bytes = cipher.doFinal(content);
		return bytes;
	}

}
