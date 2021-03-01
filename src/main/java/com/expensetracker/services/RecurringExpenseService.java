package com.expensetracker.services;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.utils.AssertUtils;
import com.appslandia.common.utils.ModelUtils;
import com.expensetracker.entities.RecurringExpense;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class RecurringExpenseService {

	@Inject
	protected EntityManagerAccessor em;

	public RecurringExpense findByPk(int pk) {
		return em.find(RecurringExpense.class, pk);
	}

	@Transactional
	public void remove(int accountId, int recurringExpenseId) throws Exception {
		RecurringExpense managed = em.find(RecurringExpense.class, recurringExpenseId);

		AssertUtils.assertNotNull(managed);
		AssertUtils.assertTrue(managed.getAccountId() == accountId);

		em.remove(managed);
	}

	@Transactional
	public void save(RecurringExpense obj) throws Exception {
		if (obj.getPk() == null) {
			em.insert(obj);

		} else {
			RecurringExpense managed = em.find(RecurringExpense.class, obj.getPk());

			AssertUtils.assertNotNull(managed);
			AssertUtils.assertTrue(managed.getAccountId() == obj.getAccountId());

			ModelUtils.copy(managed, obj, "expenseCatId", "txDate", "txAmt", "vendor", "notes");
		}
	}

	public List<RecurringExpense> queryByUser(int accountId) {
		return em.createNamedQuery("RecurringExpense.queryByUser", RecurringExpense.class).setParameter("accountId", accountId).asReadonly().getResultList();
	}
}
