package com.expensetracker.auth;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appslandia.plum.base.PostRememberMe;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Alternative
@Priority(Interceptor.Priority.APPLICATION)
public class PostRememberMeImpl implements PostRememberMe {

	@Override
	public void apply(HttpServletRequest request, HttpServletResponse response, String tokenIdentity) throws Exception {
	}
}
