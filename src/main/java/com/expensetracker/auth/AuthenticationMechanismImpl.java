package com.expensetracker.auth;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;

import com.appslandia.plum.base.HttpAuthenticationMechanismBase;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Alternative
@Priority(Interceptor.Priority.APPLICATION)
@AutoApplySession

//@formatter:off
@RememberMe(
	isRememberMeExpression = "#{self.rememberMe(httpMessageContext)}",
	cookieName = "#{self.rememberMeCookieName()}",
	cookieMaxAgeSecondsExpression = "#{self.rememberMeCookieAge()}",
	cookieSecureOnlyExpression="#{self.rememberMeCookieSecure()}",
	cookieHttpOnlyExpression="#{self.rememberMeCookieHttpOnly()}"
)
//@formatter:on
public class AuthenticationMechanismImpl extends HttpAuthenticationMechanismBase {
}
