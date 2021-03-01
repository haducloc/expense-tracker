package com.expensetracker.base;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.extension.ExtensionContext;

import com.appslandia.common.junit.cdi_transaction.SharedEmfTestEntityManagerExtension;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class SharedEmfTestEmExtension extends SharedEmfTestEntityManagerExtension {

	@Override
	protected String getPUName() {
		return "ExpenseTracker";
	}

	@Override
	protected void initEach(ExtensionContext context, EntityManager txEm) {
	}
}
