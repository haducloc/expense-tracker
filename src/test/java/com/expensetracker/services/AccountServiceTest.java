package com.expensetracker.services;

import javax.inject.Inject;

import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddEnabledInterceptors;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;

import com.appslandia.common.base.UUIDGenerator;
import com.appslandia.common.junit.cdi_transaction.RequiredTestTxInterceptor;
import com.appslandia.common.junit.cdi_transaction.TestEmfControl;
import com.appslandia.common.junit.cdi_transaction.TestEntityManagerProducer;
import com.expensetracker.base.SharedEmfTestEmExtension;
import com.expensetracker.entities.Account;
import com.expensetracker.utils.AccountUtils;
import com.expensetracker.utils.TimeUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ExtendWith(SharedEmfTestEmExtension.class)
@EnableAutoWeld
@AddBeanClasses({ TestEntityManagerProducer.class, TestEmfControl.SharedEmf.class, Account.class })
@AddEnabledInterceptors({ RequiredTestTxInterceptor.class })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountServiceTest {

	@Inject
	protected AccountService accountService;

	@Test
	@Order(100)
	public void test_save() {
		Account obj = new Account();

		obj.setEmail("user1@gmail.com");
		obj.setPassword("hashedPassword");

		obj.setFirstName("Loc");
		obj.setLastName("Ha");

		obj.setSid(UUIDGenerator.INSTANCE.generate());
		obj.setStatus(AccountUtils.ACCOUNT_ACTIVE);
		obj.setTimeCreated(TimeUtils.nowAtGMT7());

		try {
			accountService.save(obj);

		} catch (Exception ex) {
			Assertions.fail(ex);
		}
	}

	@Test
	@Order(200)
	public void test_findByPk() {
		try {
			Account obj = accountService.findByPk(1);
			Assertions.assertNotNull(obj);

		} catch (Exception ex) {
			Assertions.fail(ex);
		}
	}

	@Test
	@Order(300)
	public void test_findByEmail() {
		try {
			Account obj = accountService.findByEmail("user1@gmail.com");
			Assertions.assertNotNull(obj);

		} catch (Exception ex) {
			Assertions.fail(ex);
		}
	}

	@Test
	@Order(400)
	public void test_update() {
		try {
			Account obj = new Account();

			obj.setAccountId(1);
			obj.setFirstName("Luke");

			accountService.save(obj);

			obj = accountService.findByPk(1);
			Assertions.assertEquals("Luke", obj.getFirstName());

		} catch (Exception ex) {
			Assertions.fail(ex);
		}
	}

	@Test
	@Order(400)
	public void test_changePassword() {
		try {
			accountService.changePassword(1, "newHashedPassword");

			Account obj = accountService.findByPk(1);
			Assertions.assertEquals("newHashedPassword", obj.getPassword());

		} catch (Exception ex) {
			Assertions.fail(ex);
		}
	}
}
