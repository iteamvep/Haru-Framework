/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.cache.ehcache;

import java.io.File;
import java.time.Duration;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheEventListenerConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.event.CacheEventListener;
import org.ehcache.event.EventType;
import org.iharu.exception.BaseException;
import org.iharu.type.error.ErrorType;


/**
 *
 * @author iHaru
 */
public class EhCacheUtils {

    private static CacheConfiguration<String, String> commonCacheConfig;
    private static CacheManager commonCacheManager;
    
        public static <K extends Object, V extends Object> CacheManager createCustomCache(String cahceName, Class<K> keyType, Class<V> valueType, 
                CacheEventListener cacheEventListener, EventType eventType,
                EhCacheConfigs ehcacheConfigs){
            CacheEventListenerConfigurationBuilder cacheEventListenerConfiguration = null;
            if(cacheEventListener != null && eventType != null)
                cacheEventListenerConfiguration = CacheEventListenerConfigurationBuilder
		    .newEventListenerConfiguration(cacheEventListener, eventType) 
		    .unordered().asynchronous();
            CacheConfiguration<K, V> cacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(keyType, valueType,
			  ResourcePoolsBuilder.newResourcePoolsBuilder()
			  .heap(ehcacheConfigs.getHEAP_CACHE_SIZE(), MemoryUnit.KB)    //堆内缓存大小
			  .offheap(ehcacheConfigs.getOFF_HEAP_CACHE_SIZE(), MemoryUnit.MB)  //堆外缓存大小
//			  .disk(ehcacheConfigs.getDISK_CACHE_SIZE(), MemoryUnit.MB)                     //文件缓存大小
			  ) 
			  .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcacheConfigs.getEHCACHE_TTL()))) //缓存超时时间
			  .withSizeOfMaxObjectGraph(ehcacheConfigs.getHEAP_MAX_OBJECT_GRAPH())  //统计对象大小时对象图遍历深度
			  .withSizeOfMaxObjectSize(ehcacheConfigs.getHEAP_MAX_OBJECT_SIZE(), MemoryUnit.KB) //可缓存的最大对象大小
			  .add(cacheEventListenerConfiguration)
			  .build();
            return CacheManagerBuilder.newCacheManagerBuilder()
//        		  .with(CacheManagerBuilder.persistence(new File(ehcacheConfigs.getDISK_CACHE_DIR(), cahceName)))
				  .withCache(cahceName, cacheConfig)
                    .build(true);
        }
	
	private static void initCommonCache(EhCacheConfigs ehcacheConfigs){
            commonCacheConfig = CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
			  ResourcePoolsBuilder.newResourcePoolsBuilder()
			  .heap(ehcacheConfigs.getHEAP_CACHE_SIZE(), MemoryUnit.KB)
			  .offheap(ehcacheConfigs.getOFF_HEAP_CACHE_SIZE(), MemoryUnit.MB)
			  ) 
			  .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcacheConfigs.getEHCACHE_TTL())))
			  .withSizeOfMaxObjectGraph(ehcacheConfigs.getHEAP_MAX_OBJECT_GRAPH())
			  .withSizeOfMaxObjectSize(ehcacheConfigs.getHEAP_MAX_OBJECT_SIZE(), MemoryUnit.KB)
			  .build();
            commonCacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);
        }
	
        public static Cache<String, String> createCommonCache(String cahceName, EhCacheConfigs ehcacheConfigs){
            if(commonCacheManager == null)
                initCommonCache(ehcacheConfigs);
            return commonCacheManager.createCache(cahceName, commonCacheConfig);
        }
        
        private static Cache<String, String> getEhCache(String cahceName, boolean create) throws BaseException {
            Cache<String, String> _cache = commonCacheManager.getCache(cahceName, String.class, String.class);
            if(_cache == null && create)
                return commonCacheManager.createCache(cahceName, commonCacheConfig);
            if(_cache == null)
                throw new BaseException(ErrorType.INTERNAL_MODULE_ERROR, "ehcache", "cache name: "+cahceName+"not exist.");
            return _cache;
        }	
        
	//缓存存值
	public static  void put(String cahceName, String key, String value, boolean create) throws BaseException {		
            getEhCache(cahceName, create).put(key, value);		
	}
	
	//获取缓存值
	public static String get(String cahceName, String key) throws BaseException {
            return getEhCache(cahceName, false).get(key);
	}
	
	//清除缓存值
	public static void remove(String cahceName, String key) throws BaseException {
            getEhCache(cahceName, false).remove(key);	
	}
	
	
	//清除缓存值
	public static void removeCache(String cahceName) {
		commonCacheManager.removeCache(cahceName);
	}
        
        public static void removeCache(CacheManager cacheManager, String cahceName) {
		cacheManager.removeCache(cahceName);
	}
}
