/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.cache;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.iharu.cache.ehcache.EhCacheConfigs;
import org.iharu.cache.ehcache.EhCacheUtils;

/**
 *
 * @author iHaru
 */
public class SessionCache {
    public static Cache<String, String> sessionTokenCache;
    public static Cache<String, String> sessionVoucherCache;
    
    static{
        EhCacheConfigs ehCacheConfigs = new EhCacheConfigs();
        sessionTokenCache = EhCacheUtils.createCommonCache("sessionTokenCache", ehCacheConfigs);
        sessionVoucherCache = EhCacheUtils.createCommonCache("sessionVoucherCache", ehCacheConfigs);
        
    }
    
}
