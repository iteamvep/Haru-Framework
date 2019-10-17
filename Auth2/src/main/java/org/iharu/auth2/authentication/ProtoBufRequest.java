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
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import org.iharu.auth2.authentication.traffic.LoginWrapper;
import org.iharu.auth2.utils.AuthenticationUtils;
import org.iharu.crypto.aes.AesUtils;
import org.slf4j.LoggerFactory;
import protobuf.proto.iharu.C2S_LoginProto;

/**
 *
 * @author iHaru
 */
public class ProtoBufRequest<T> {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(Handshake.class);
    
    private static final String DEFAULT_ENCRYPTION = "AES";
    
    public static LoginWrapper HandleLogin(byte[] body, SecretKey secretKey){
        try {
            C2S_LoginProto req = C2S_LoginProto.parseFrom(AesUtils.Decrypt(body, secretKey));
            if(AuthenticationUtils.isAuthRequestTimeout(req.getTimestamp())){
                return new LoginWrapper("request timeout");
            }
            return new LoginWrapper(req);
        } catch (InvalidProtocolBufferException ex) {
            LOG.warn("Parse login body failed", ex);
            return new LoginWrapper("Parse login body failed");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException ex) {
            LOG.warn("Decode login body failed", ex);
            return new LoginWrapper("Decode login body failed");
        }
    }
    
    public static byte[] DecryptRequest(byte[] body, SecretKey secretKey){
        try {
            return AesUtils.Decrypt(body, secretKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException | InvalidKeySpecException ex) {
            LOG.warn("DecryptRequest failed", ex);
        }
        return null;
    }
}
