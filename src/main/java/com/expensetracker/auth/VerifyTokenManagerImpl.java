package com.expensetracker.auth;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.transaction.Transactional;

import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.logging.AppLogger;
import com.appslandia.plum.base.VerifyToken;
import com.appslandia.plum.base.VerifyTokenManager;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Alternative
@Priority(Interceptor.Priority.APPLICATION)
public class VerifyTokenManagerImpl implements VerifyTokenManager {

	@Inject
	protected EntityManagerAccessor em;

	@Inject
	protected AppLogger logger;

	@Override
	@Transactional
	public void save(VerifyToken token) {
		this.em.persist(token);
	}

	@Override
	public VerifyToken load(String series) {
		return this.em.find(VerifyToken.class, series);
	}

	@Override
	@Transactional
	public void remove(String series) {
		this.logger.error(() -> this.em.removeByPk(VerifyToken.class, series));
	}
}
