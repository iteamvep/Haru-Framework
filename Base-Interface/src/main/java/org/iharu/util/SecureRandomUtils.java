/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iHaru
 */
public class SecureRandomUtils {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SecureRandomUtils.class);
    
    private SecureRandomUtils(){}
    
    public static SecureRandom getInstance() throws NoSuchAlgorithmException {
        return SecureRandom.getInstance("SHA1PRNG");
    }
    
    public static SecureRandom getInstanceStrong() throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong();
    }
    
}
