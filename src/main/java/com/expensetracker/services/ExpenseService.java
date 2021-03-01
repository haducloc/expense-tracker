package com.expensetracker.services;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.utils.AssertUtils;
import com.appslandia.common.utils.BitBool;
import com.appslandia.common.utils.ModelUtils;
import com.expensetracker.entities.Expense;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class ExpenseService {

	@Inject
	protected EntityManagerAccessor em;

	public Expense findByPk(int pk) {
		return em.find(Expense.class, pk);
	}

	@Transactional
	public void remove(int accountId, int expenseId) throws Exception {
		Expense managed = em.find(Expense.class, expenseId);

		AssertUtils.assertNotNull(managed);
		AssertUtils.assertTrue(managed.getAccountId() == accountId);

		em.remove(managed);
	}

	@Transactional
	public void save(Expense obj) throws Exception {
		if (obj.getPk() == null) {
			em.insert(obj);

		} else {
			Expense managed = em.find(Expense.class, obj.getPk());

			AssertUtils.assertNotNull(managed);
			AssertUtils.assertTrue(managed.getAccountId() == obj.getAccountId());

			ModelUtils.copy(managed, obj, "expenseCatId", "txDate", "txAmt", "vendor", "notes");
		}
	}

	public List<Expense> query(int accountId, LocalDate dateFrom, LocalDate dateTo, Integer expenseCatId, String vendor, Boolean recurringType) {
		int type = (recurringType == null) ? -1 : BitBool.valueOf(recurringType);

		return em.createNamedQuery("Expense.query", Expense.class).setParameter("accountId", accountId).setParameter("dateFrom", dateFrom)
				.setParameter("dateTo", dateTo).setParameter("expenseCatId", expenseCatId).setLikePattern("vendor", vendor).setParameter("recurringType", type)
				.asReadonly().getResultList();
	}
}
