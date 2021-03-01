package com.expensetracker.caching;

import org.ehcache.Cache;
import org.ehcache.CacheManager;

import com.appslandia.common.caching.AppCache;
import com.appslandia.common.caching.AppCacheManager;
import com.appslandia.common.utils.ObjectUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class EhAppCacheManager implements AppCacheManager {

	final CacheManager cacheManager;

	public EhAppCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public <K, V> AppCache<K, V> getCache(String cacheName) {
		Cache<Object, Object> cache = this.cacheManager.getCache(cacheName, Object.class, Object.class);
		return (cache != null) ? new EhAppCache<>(ObjectUtils.cast(cache)) : null;
	}

	@Override
	public boolean clearCache(String cacheName) {
		Cache<Object, Object> cache = this.cacheManager.getCache(cacheName, Object.class, Object.class);
		if (cache != null) {
			cache.clear();
			return true;
		}
		return false;
	}

	@Override
	public boolean destroyCache(String cacheName) {
		Cache<Object, Object> cache = this.cacheManager.getCache(cacheName, Object.class, Object.class);
		if (cache != null) {
			this.cacheManager.removeCache(cacheName);
			return true;
		}
		return false;
	}

	@Override
	public Iterable<String> getCacheNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void close() {
		this.cacheManager.close();
	}
}
