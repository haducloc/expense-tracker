package com.expensetracker.caching;

import java.time.Duration;
import java.util.List;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class EhCacheConfig {

	private String cacheName;
	private Duration expiration;
	private int poolSize = 32;

	public String getCacheName() {
		return cacheName;
	}

	public EhCacheConfig setCacheName(String cacheName) {
		this.cacheName = cacheName;
		return this;
	}

	public Duration getExpiration() {
		return expiration;
	}

	public EhCacheConfig setExpiration(Duration expiration) {
		this.expiration = expiration;
		return this;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public EhCacheConfig setPoolSize(int poolSize) {
		this.poolSize = poolSize;
		return this;
	}

	public CacheManagerBuilder<CacheManager> toBuilder(CacheManagerBuilder<CacheManager> builder) {
		return builder.withCache(this.cacheName,
				CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.heap(this.poolSize))
						.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(this.expiration)));
	}

	public static CacheManagerBuilder<CacheManager> toBuilder(List<EhCacheConfig> configs) {
		CacheManagerBuilder<CacheManager> builder = CacheManagerBuilder.newCacheManagerBuilder();

		for (EhCacheConfig config : configs) {
			builder = config.toBuilder(builder);
		}
		return builder;
	}
}
