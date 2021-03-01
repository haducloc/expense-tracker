package com.expensetracker.beans;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.appslandia.plum.base.DynServletRegister;
import com.appslandia.plum.base.Startup;
import com.appslandia.plum.base.StartupConfig;
import com.appslandia.plum.utils.ResourceServlets;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@StartupConfig(priority = StartupConfig.PRIORITY_LIBRARY_BEFORE + 250)
public class ResourceServletInitializer implements Startup {

	@Override
	public void onStartup(ServletContext sc, List<Class<? extends Startup>> startupClasses) throws ServletException {

		String[] urlMappings = { "*.css", "*.js", "*.map", "*.png", "*.jpg", "*.pdf", "*.ico", "*.txt", "*.pdf" };
		final String servletName = "ResourceServlet";

		new DynServletRegister().servletName(servletName).servletClassName(ResourceServlets.RESOURCE_SERVLET_UNDERTOW).urlPatterns(urlMappings).registerTo(sc);
	}
}
