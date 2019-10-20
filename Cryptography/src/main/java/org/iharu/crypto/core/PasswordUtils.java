/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.crypto.core;

import org.iharu.crypto.hmac.HmacUtils;
import org.iharu.crypto.util.BytesUtils;
import org.iharu.util.StringUtils;
import static org.passay.AllowedCharacterRule.ERROR_CODE;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;

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
        if(password == null)
            return null;
        return BytesUtils.encodeHexString(HmacUtils.SafeHashPasswordWithSalt(salt, password+salt));
    }
    
    public static boolean isPassordMatch(String encryptedPwd, String password, String salt){
        if(StringUtils.isNullOrWhiteSpace(encryptedPwd))
            return false;
        return encryptedPwd.equals(HashPasswordWithSalt(password, salt));
    }
    
    public static String GeneratePassayPassword(int len, int digit, int lowerCase, int upperCase) {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(lowerCase);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(upperCase);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(digit);

        String password = gen.generatePassword(len, 
                digitRule, lowerCaseRule, upperCaseRule);
        return password;
    }
    
    public static String GeneratePassayPassword(int len, int digit, int lowerCase, int upperCase, int special) {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(lowerCase);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(upperCase);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(digit);

        CharacterData specialChars = new CharacterData() {
            @Override
            public String getErrorCode() {
                return ERROR_CODE;
            }

            @Override
            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(special);

        String password = gen.generatePassword(len, splCharRule, lowerCaseRule, 
          upperCaseRule, digitRule);
        return password;
    }
    
}
