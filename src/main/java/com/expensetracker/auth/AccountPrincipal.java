package com.expensetracker.auth;

import com.appslandia.plum.base.UserPrincipal;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class AccountPrincipal extends UserPrincipal {

	final int accountId;
	final String dispName;

	public AccountPrincipal(int accountId, String email, String displayName) {
		super(email);

		this.accountId = accountId;
		this.dispName = displayName;
	}

	@Override
	public int getUserId() {
		return this.accountId;
	}

	@Override
	public String getDisplayName() {
		return this.dispName;
	}
}
