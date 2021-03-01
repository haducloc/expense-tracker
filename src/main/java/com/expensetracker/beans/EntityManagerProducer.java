package com.expensetracker.beans;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.jpa.HintMapper;
import com.appslandia.common.jpa.JpaHints;
import com.expensetracker.utils.DBUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@Dependent
public class EntityManagerProducer {

	static {
		try {
			final HintMapper mapper = new HintMapper();
			mapper.addHint(JpaHints.HINT_QUERY_READONLY, "org.hibernate.readOnly");

			JpaHints.setHintMapper(mapper);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@PersistenceContext(unitName = DBUtils.PU_NAME)
	private EntityManager em;

	@Produces
	public EntityManagerAccessor produce() {
		return new EntityManagerAccessor(this.em);
	}
}
