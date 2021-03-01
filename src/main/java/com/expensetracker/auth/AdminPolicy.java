package com.expensetracker.auth;

import javax.enterprise.context.ApplicationScoped;

import com.appslandia.common.base.MappedID;
import com.appslandia.plum.base.AuthorizePolicy;
import com.appslandia.plum.base.UserPrincipal;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@MappedID(AdminPolicy.ADMIN)
public class AdminPolicy implements AuthorizePolicy {

	public static final String ADMIN = "admin";

	@Override
	public boolean authorize(UserPrincipal principal) {
		return principal.getUserId() == 1;
	}
}
