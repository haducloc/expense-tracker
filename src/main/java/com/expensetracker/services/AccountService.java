package com.expensetracker.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.utils.AssertUtils;
import com.appslandia.common.utils.ModelUtils;
import com.expensetracker.entities.Account;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class AccountService {

	@Inject
	protected EntityManagerAccessor em;

	public Account findByPk(int accountId) throws Exception {
		return em.find(Account.class, accountId);
	}

	public Account findByEmail(String email) throws Exception {
		return em.createNamedQuery("Account.findByEmail", Account.class).setParameter("email", email).getSingleOrNull();
	}

	public Account findBySid(String sid) throws Exception {
		return em.createNamedQuery("Account.findBySid", Account.class).setParameter("sid", sid).getSingleOrNull();
	}

	@Transactional
	public void save(Account account) throws Exception {
		if (account.getPk() == null) {
			em.insert(account);
		} else {

			Account managed = em.find(Account.class, account.getPk());
			AssertUtils.assertNotNull(managed);

			ModelUtils.copy(managed, account, "firstName", "lastName");
		}
	}

	@Transactional
	public void changePassword(int accountId, String newHashPassword) throws Exception {
		Account account = em.find(Account.class, accountId);
		AssertUtils.assertNotNull(account);

		account.setPassword(newHashPassword);
	}
}
