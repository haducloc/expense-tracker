package com.expensetracker.beans;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.interceptor.Interceptor;

import com.appslandia.common.cdi.CDIFactory;
import com.appslandia.common.logging.AppLoggerManager;
import com.appslandia.common.logging.Log4JAppLoggerManager;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Priority(Interceptor.Priority.APPLICATION)
public class AppLoggerManagerImpl implements CDIFactory<AppLoggerManager> {

	@Produces
	@Alternative
	@ApplicationScoped
	@Override
	public AppLoggerManager produce() {
		return new Log4JAppLoggerManager();
	}

	@Override
	public void dispose(@Disposes AppLoggerManager impl) {
		impl.close();
	}
}
