package com.expensetracker.base;

import com.appslandia.common.junit.cdi_transaction.TestEntityManagerExtension;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class NotSharedEmfTestEmExtension extends TestEntityManagerExtension {

	@Override
	protected String getPUName() {
		return "ExpenseTracker";
	}
}
