/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.auth2.authentication;

import com.google.protobuf.InvalidProtocolBufferException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.iharu.auth2.authentication.traffic.LoginWrapper;
import org.iharu.auth2.utils.AuthenticationUtils;
import org.iharu.crypto.aes.AesUtils;
import org.iharu.crypto.rsa.RSAUtils;
import org.slf4j.LoggerFactory;
import protobuf.proto.iharu.C2S_LoginProto;

/**
 *
 * @author iHaru
 */
public class ProtoBufResponse<T> {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Handshake.class);
    
    private static final String DEFAULT_ENCRYPTION = "AES";
    
    public static byte[] EncryptResponse(byte[] body, SecretKey secretKey){
        try {
            return AesUtils.Encrypt(body, secretKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException ex) {
            LOG.warn("EncryptResponse failed", ex);
        }
        return null;
    }
    
    public static byte[] DecryptResponseWithRSA(byte[] body, PrivateKey privateKey){
        try {
            return RSAUtils.Decrypt(body, privateKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            LOG.warn("DecryptResponseWithRSA failed", ex);
        }
        return null;
    }
    
    public static byte[] EncryptResponseWithRSA(byte[] body, PrivateKey privateKey){
        try {
            return RSAUtils.Encrypt(body, privateKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            LOG.warn("EncryptResponseWithRSA failed", ex);
        }
        return null;
    }
    
}
