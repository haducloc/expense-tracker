package com.expensetracker.controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.appslandia.plum.base.ActionResult;
import com.appslandia.plum.base.Authorize;
import com.appslandia.plum.base.Controller;
import com.appslandia.plum.base.HttpGet;
import com.appslandia.plum.base.RequestAccessor;
import com.appslandia.plum.results.JspResult;
import com.appslandia.plum.utils.DebugUtils;
import com.expensetracker.auth.AdminPolicy;
import com.expensetracker.utils.AccountUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Controller
@Authorize(policies = { AdminPolicy.ADMIN })
public class AdminController {

	@HttpGet
	public ActionResult index(RequestAccessor request) throws Exception {
		return JspResult.DEFAULT;
	}

	@HttpGet
	public String hashpwd(RequestAccessor request, String password) throws Exception {
		if (password != null) {
			return AccountUtils.hashPassword(password);
		} else {
			return "No password provided.";
		}
	}

	@Inject
	protected BeanManager beanManager;

	@HttpGet
	public void tcpInfo(RequestAccessor request, HttpServletResponse response) throws Exception {
		DebugUtils.writeTcpInfo(request, response);
	}

	@HttpGet
	public void beanInfo(RequestAccessor request, HttpServletResponse response) throws Exception {
		DebugUtils.writeBeanInfo(this.beanManager, response);
	}

	@HttpGet
	public void servletInfo(RequestAccessor request, HttpServletResponse response) throws Exception {
		DebugUtils.writeServletInfo(request, response);
	}

	@HttpGet
	public void envsProps(RequestAccessor request, HttpServletResponse response) throws Exception {
		DebugUtils.writeEnvsProps(request, response);
	}

	@HttpGet
	public void zoneInfo(RequestAccessor request, HttpServletResponse response) throws Exception {
		DebugUtils.writeZoneInfo(request, response);
	}
}
