package com.expensetracker.auth;

import javax.annotation.Priority;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.logging.AppLogger;
import com.appslandia.common.utils.AssertUtils;
import com.appslandia.plum.base.RemMeToken;
import com.appslandia.plum.base.RemMeTokenManager;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Alternative
@Priority(Interceptor.Priority.APPLICATION)
public class RemMeTokenServiceImpl implements RemMeTokenManager {

	@Inject
	protected EntityManagerAccessor em;

	@Inject
	protected AppLogger logger;

	@Override
	@Transactional
	public void save(RemMeToken token) {
		this.em.persist(token);
	}

	@Override
	public RemMeToken load(String series) {
		return this.em.find(RemMeToken.class, series);
	}

	@Override
	@Transactional
	public void reissue(String series, String token, long expiresAt, long issuedAt) {
		RemMeToken obj = this.em.find(RemMeToken.class, series);
		AssertUtils.assertNotNull(obj);

		obj.setToken(token);
		obj.setExpiresAt(expiresAt);
		obj.setIssuedAt(issuedAt);
	}

	@Override
	@Transactional
	public void remove(String series) {
		this.logger.error(() -> this.em.removeByPk(RemMeToken.class, series));
	}

	@Override
	@Transactional
	public void removeAll(String hashIdentity) {
		Query nq = this.em.createNativeQuery("DELETE FROM RemMeToken WHERE hashIdentity=:hashIdentity");
		nq.setParameter("hashIdentity", hashIdentity).executeUpdate();
	}
}
