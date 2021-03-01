package com.expensetracker.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;

import com.appslandia.common.base.Out;
import com.appslandia.plum.base.AuthFailureResult;
import com.appslandia.plum.base.IdentityStoreBase;
import com.appslandia.plum.base.PrincipalGroups;
import com.expensetracker.entities.Account;
import com.expensetracker.services.AccountService;
import com.expensetracker.utils.AccountUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class AccountIdentityStore extends IdentityStoreBase {

	@Inject
	protected AccountService accountService;

	@Override
	public Class<? extends Credential> getCredentialType() {
		return AccountCredential.class;
	}

	@Override
	protected PrincipalGroups doValidate(Credential credential, Out<String> failureCode) {
		AccountCredential accountCredential = (AccountCredential) credential;
		Account account = null;

		try {
			account = accountService.findByEmail(accountCredential.getCaller());
		} catch (Exception ex) {
			failureCode.value = AuthFailureResult.ID_STORE_EXCEPTION.getFailureCode();
			return null;
		}

		if (account == null) {
			failureCode.value = AuthFailureResult.CREDENTIAL_INVALID.getFailureCode();
			return null;
		}

		// Password
		if (!AccountUtils.verifyPassword(accountCredential.getPasswordAsString(), account.getPassword())) {
			failureCode.value = AuthFailureResult.CREDENTIAL_INVALID.getFailureCode();
			return null;
		}

		// Status
		if (account.getStatus() == AccountUtils.ACCOUNT_PENDING) {
			failureCode.value = AuthFailureResult.CREDENTIAL_NOT_ACTIVATED.getFailureCode();
			return null;
		}

		if (account.getStatus() == AccountUtils.ACCOUNT_BANNED) {
			failureCode.value = AuthFailureResult.CREDENTIAL_SUSPENDED.getFailureCode();
			return null;
		}
		return new PrincipalGroups(new AccountPrincipal(account.getAccountId(), account.getEmail(), account.getDisplayName()), null);
	}
}
