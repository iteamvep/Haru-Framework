/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.auth2.cache.inmem;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.crypto.SecretKey;

/**
 *
 * @author iHaru
 */
public class AppDataCache {
    
    public static final Map<String, SecretKey> USER_SSO_DATASET = new ConcurrentHashMap();
    
}
