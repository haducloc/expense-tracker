package com.expensetracker.services;

import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.utils.AssertUtils;
import com.appslandia.common.utils.ModelUtils;
import com.appslandia.plum.caching.CacheChangeEvent;
import com.appslandia.plum.caching.CacheResult;
import com.expensetracker.caching.Caches;
import com.expensetracker.entities.ExpenseCat;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class ExpenseCatService {

	static final String CACHE_KEY_EXPENSE_CATS = "expense-cats-${0}";

	@Inject
	protected EntityManagerAccessor em;

	@Inject
	protected CacheChangeEvent cacheChangeEvent;

	public ExpenseCat findByPk(int pk) {
		return em.find(ExpenseCat.class, pk);
	}

	@Transactional
	public void remove(int accountId, int expenseCatId) throws Exception {
		ExpenseCat managed = em.find(ExpenseCat.class, expenseCatId);

		AssertUtils.assertNotNull(managed);
		AssertUtils.assertTrue(managed.getAccountId() == accountId);

		em.remove(managed);

		// Notify change
		cacheChangeEvent.fireAsync(Caches.DEFAULT, CACHE_KEY_EXPENSE_CATS, accountId);
	}

	@Transactional
	public void save(ExpenseCat obj) throws Exception {
		if (obj.getPk() == null) {
			em.insert(obj);

		} else {
			ExpenseCat managed = em.find(ExpenseCat.class, obj.getPk());

			AssertUtils.assertNotNull(managed);
			AssertUtils.assertTrue(managed.getAccountId() == obj.getAccountId());

			ModelUtils.copy(managed, obj, "name", "monthlyBudget", "yearlyBudget", "dispPos");
		}

		// Notify change
		cacheChangeEvent.fireAsync(Caches.DEFAULT, CACHE_KEY_EXPENSE_CATS, obj.getAccountId());
	}

	public List<ExpenseCat> queryByUser(int accountId) {
		return em.createNamedQuery("ExpenseCat.queryByUser", ExpenseCat.class).setParameter("accountId", accountId).asReadonly().getResultList();
	}

	@CacheResult(cacheName = Caches.DEFAULT, key = CACHE_KEY_EXPENSE_CATS)
	public List<ExpenseCat> getByUser(int accountId) {
		List<ExpenseCat> list = em.createNamedQuery("ExpenseCat.queryByUser", ExpenseCat.class).setParameter("accountId", accountId).asReadonly()
				.getResultList();
		return Collections.unmodifiableList(list);
	}
}
