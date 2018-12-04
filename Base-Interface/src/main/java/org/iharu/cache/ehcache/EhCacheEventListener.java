/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.iharu.cache.ehcache;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iTeamVEP
 */
@SuppressWarnings("rawtypes")
public class EhCacheEventListener implements CacheEventListener {
	static final org.slf4j.Logger LOG = LoggerFactory.getLogger(EhCacheEventListener.class);
	
	@Override
	public void onEvent(CacheEvent event) {
		LOG.info("timeout, key:"+event.getKey());			
	}
}
