package com.expensetracker.controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.appslandia.common.base.Out;
import com.appslandia.common.logging.AppLogger;
import com.appslandia.common.utils.AssertUtils;
import com.appslandia.plum.base.ActionResult;
import com.appslandia.plum.base.AuthContext;
import com.appslandia.plum.base.AuthFailureResult;
import com.appslandia.plum.base.AuthParameters;
import com.appslandia.plum.base.Authorize;
import com.appslandia.plum.base.Controller;
import com.appslandia.plum.base.ExceptionHandler;
import com.appslandia.plum.base.FormLogin;
import com.appslandia.plum.base.HttpGet;
import com.appslandia.plum.base.HttpGetPost;
import com.appslandia.plum.base.Model;
import com.appslandia.plum.base.ModelBinder;
import com.appslandia.plum.base.RequestAccessor;
import com.appslandia.plum.models.EmailLoginModel;
import com.appslandia.plum.models.NewPasswordModel;
import com.appslandia.plum.results.JspResult;
import com.appslandia.plum.results.RedirectResult;
import com.appslandia.plum.utils.ServletUtils;
import com.expensetracker.auth.AccountCredential;
import com.expensetracker.entities.Account;
import com.expensetracker.services.AccountService;
import com.expensetracker.utils.AccountUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Controller
public class AccountController {

	@Inject
	protected AppLogger logger;

	@Inject
	protected ModelBinder modelBinder;

	@Inject
	protected ExceptionHandler exceptionHandler;

	@Inject
	protected AuthContext authContext;

	@Inject
	protected AccountService accountService;

	@HttpGetPost
	@FormLogin
	public ActionResult login(RequestAccessor request, HttpServletResponse response) throws Exception {
		EmailLoginModel model = new EmailLoginModel();

		// GET
		if (request.isGetOrHead()) {

			request.storeModel(model);
			return JspResult.DEFAULT;
		}

		// POST
		modelBinder.bindModel(request, model);

		// Module Authenticated?
		if (request.isModuleAuthenticated()) {
			model.setEmail(request.getRemoteUser());
			model.setRememberMe(request.getUserPrincipal().isRememberMe());

			request.getModelState().remove("email", "rememberMe");
		}

		if (AccountUtils.isDemoUser(model.getEmail())) {
			model.setPassword("Demo@111");
			request.getModelState().clearErrors();
		}

		if (!request.getModelState().isValid()) {
			request.storeModel(model);
			return JspResult.DEFAULT;
		}

		try {
			// AuthParameters
			AuthParameters authParameters = new AuthParameters().credential(new AccountCredential(model.getEmail(), model.getPassword()))
					.rememberMe(model.isRememberMe()).reauthentication(request.isModuleAuthenticated());

			// Logout
			if (request.getUserPrincipal() != null) {
				Account account = accountService.findByEmail(model.getEmail());
				AssertUtils.assertNotNull(account);

				// Verify password
				if (!AccountUtils.verifyPassword(model.getPassword(), account.getPassword())) {
					request.getModelState().addError(request.res(getMsgKey(AuthFailureResult.PASSWORD_INVALID.getFailureCode())));

					request.storeModel(model);
					return JspResult.DEFAULT;
				}

				// LOGOUT
				request.logout();
			}

			// Authenticate
			Out<String> failureCode = new Out<>();
			if (!this.authContext.authenticate(request, response, authParameters, failureCode)) {
				request.getModelState().addError(request.res(getMsgKey(failureCode.val())));

				request.storeModel(model);
				return JspResult.DEFAULT;
			}

		} catch (Exception ex) {
			logger.error(ex);
			request.getModelState().addError(this.exceptionHandler.getProblem(request, ex).getTitle());

			request.storeModel(model);
			return JspResult.DEFAULT;
		}

		// returnUrl
		String returnUrl = request.getParamOrNull(ServletUtils.PARAM_RETURN_URL);
		if (returnUrl != null) {
			return new RedirectResult().location(returnUrl);
		} else {
			return new RedirectResult("index", "dashboard");
		}
	}

	@HttpGet
	public ActionResult logout(RequestAccessor request, HttpServletResponse response) throws Exception {
		if (request.getUserPrincipal() != null) {
			request.logout();
		}
		return new RedirectResult("index", "dashboard");
	}

	static String getMsgKey(String failureCode) {
		return "account." + failureCode;
	}

	@HttpGetPost
	@Authorize
	public ActionResult changepwd(RequestAccessor request, HttpServletResponse response, @Model NewPasswordModel model) throws Exception {
		// GET
		if (request.isGetOrHead()) {
			request.getModelState().clearErrors();

			request.storeModel(model);
			return JspResult.DEFAULT;
		}
		// POST
		if (!request.getModelState().isValid()) {
			request.storeModel(model);
			return JspResult.DEFAULT;
		}
		try {
			// Demo user has read permissions only
			AccountUtils.assertNotDemoUser(request);

			accountService.changePassword(request.getUserId(), AccountUtils.hashPassword(model.getNewPassword()));

			request.getMessages().addNotice(request.res("account_changepwd.password_changed_successfully"));
			return new RedirectResult("changepwd");

		} catch (Exception ex) {
			logger.error(ex);
			request.getMessages().addError(this.exceptionHandler.getProblem(request, ex).getTitle());

			request.storeModel(model);
			return JspResult.DEFAULT;
		}
	}
}
