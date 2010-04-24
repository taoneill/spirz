package com.team3.socialnews.server.persistence;

import java.util.HashMap;
import java.util.Map;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.memcache.stdimpl.GCacheFactory;

public class SpirzCache {
	private Cache cache;
	
	public SpirzCache() {
        Map<Integer, Integer> config = new HashMap<Integer, Integer>();
        config.put(GCacheFactory.EXPIRATION_DELTA, 3600);

        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache = cacheFactory.createCache(config);
        } catch (CacheException e) {
    		//
        }
	}
	
	public Cache getInstance() {
		// singleton through module config
		return cache;
	}
}
