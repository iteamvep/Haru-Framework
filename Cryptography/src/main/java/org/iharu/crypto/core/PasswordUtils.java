/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.core;

import org.iharu.crypto.hmac.HmacUtils;
import org.iharu.crypto.util.BytesUtils;
import org.iharu.util.StringUtils;

/**
 *
 * @author iHaru
 */
public class PasswordUtils {
    
    /**
     * Use @param password to sign the @param salt
     * @param password
     * @param salt
     * @return 
     */
    public static String HashPasswordWithSalt(String password, String salt){
        return BytesUtils.encodeHexString(HmacUtils.SafeHashPasswordWithSalt(salt, password));
    }
    
    public static boolean isPassordMatch(String encryptedPwd, String password, String salt){
        if(StringUtils.isNullOrWhiteSpace(encryptedPwd))
            return false;
        return encryptedPwd.equals(HashPasswordWithSalt(password, salt));
    }
    
}
