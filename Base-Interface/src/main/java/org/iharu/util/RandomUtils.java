/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class RandomUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(RandomUtils.class);
    
    public static String GenRandomString(int targetStringLength) {
        int leftLimit = 33;
        int rightLimit = 126;
        Random random = new SecureRandom();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) 
              (random.nextFloat() * (rightLimit - leftLimit + 1));
            if(randomLimitedInt == 34 || randomLimitedInt == 39 || randomLimitedInt == 94 || randomLimitedInt == 96){
                i--;
                continue;
            }
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
    
    public static byte[] GenRandomBytes(int targetLength) {
        byte[] bytes = new byte[targetLength];
        try {
            SecureRandomUtils.getInstance().nextBytes(bytes);
            return bytes;
        } catch (NoSuchAlgorithmException ex) {
            LOG.error("GenRandomBytes failed", ex);
        }
        return null;
    }
    
    public static String GenTokenString(int targetStringLength) {
        return Base64Utils.EncryptBase64ToString(GenRandomBytes(targetStringLength));
    }
    
    public Stream<Character> getRandomSpecialChars(int count) {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(count, 33, 45);
        return specialChars.mapToObj(data -> (char) data);
    }
    
    public Stream<Character> getRandomChars(int count, int randomNumberOrigin, int randomNumberBound) {
        Random random = new SecureRandom();
        IntStream specialChars = random.ints(count, randomNumberOrigin, randomNumberBound);
        return specialChars.mapToObj(data -> (char) data);
    }
    
}
