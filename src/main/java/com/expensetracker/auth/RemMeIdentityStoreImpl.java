package com.expensetracker.auth;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.appslandia.common.base.Out;
import com.appslandia.common.base.TextGenerator;
import com.appslandia.common.base.TokenGenerator;
import com.appslandia.common.base.UUIDGenerator;
import com.appslandia.common.crypto.DigesterImpl;
import com.appslandia.common.crypto.PasswordDigester;
import com.appslandia.common.crypto.TextDigester;
import com.appslandia.plum.base.AuthFailureResult;
import com.appslandia.plum.base.Modules;
import com.appslandia.plum.base.PrincipalGroups;
import com.appslandia.plum.base.RemMeIdentityStore;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class RemMeIdentityStoreImpl extends RemMeIdentityStore {

	final TextGenerator tokenGenerator = new TokenGenerator(64);
	final TextDigester tokenDigester = new PasswordDigester();
	final TextDigester identityDigester = new TextDigester(new DigesterImpl("SHA-256"));

	@Inject
	protected AccountIdentityValidator identityValidator;

	@Override
	protected TextGenerator getSeriesGenerator() {
		return UUIDGenerator.INSTANCE;
	}

	@Override
	protected TextGenerator getTokenGenerator() {
		return this.tokenGenerator;
	}

	@Override
	protected TextDigester getTokenDigester() {
		return this.tokenDigester;
	}

	@Override
	protected TextDigester getIdentityDigester() {
		return this.identityDigester;
	}

	@Override
	protected PrincipalGroups doValidate(String module, String identity, Out<String> failureCode) {
		if (Modules.DEFAULT.equalsIgnoreCase(module)) {
			return this.identityValidator.validate(identity, failureCode);
		}
		failureCode.value = AuthFailureResult.CREDENTIAL_INVALID.getFailureCode();
		return null;
	}
}
