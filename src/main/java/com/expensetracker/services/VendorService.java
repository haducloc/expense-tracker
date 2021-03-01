package com.expensetracker.services;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.utils.AssertUtils;
import com.appslandia.common.utils.CollectionUtils;
import com.appslandia.plum.caching.CacheChangeEvent;
import com.appslandia.plum.caching.CacheResult;
import com.expensetracker.caching.Caches;
import com.expensetracker.entities.Vendors;
import com.expensetracker.utils.VendorUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class VendorService {

	static final String CACHE_KEY_VENDORS = "vendors-${0}";

	@Inject
	protected EntityManagerAccessor em;

	@Inject
	protected CacheChangeEvent cacheChangeEvent;

	public Vendors findByPk(int accountId) {
		return em.find(Vendors.class, accountId);
	}

	@Transactional
	public void save(Vendors vendors) throws Exception {
		Vendors managed = em.find(Vendors.class, vendors.getAccountId());
		if (managed == null) {

			Vendors obj = new Vendors();
			obj.setAccountId(vendors.getAccountId());
			obj.setVendors(vendors.getVendors());

			em.insert(obj);
		} else {
			managed.setVendors(vendors.getVendors());
			AssertUtils.assertTrue(managed.getAccountId() == vendors.getAccountId());
		}

		// Notify change
		cacheChangeEvent.fireAsync(Caches.DEFAULT, CACHE_KEY_VENDORS, vendors.getAccountId());
	}

	@CacheResult(cacheName = Caches.DEFAULT, key = CACHE_KEY_VENDORS)
	public List<String> getVendors(int accountId) {
		Vendors managed = em.find(Vendors.class, accountId);
		if (managed == null)
			return Collections.emptyList();

		String[] vendors = VendorUtils.toVendorArray(managed.getVendors());
		return Collections.unmodifiableList(CollectionUtils.toList(vendors));
	}
}
