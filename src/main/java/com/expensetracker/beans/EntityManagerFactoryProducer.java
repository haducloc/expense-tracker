package com.expensetracker.beans;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import com.appslandia.common.jpa.EntityManagerFactoryAccessor;
import com.expensetracker.utils.DBUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@Dependent
public class EntityManagerFactoryProducer {

	@PersistenceUnit(unitName = DBUtils.PU_NAME)
	private EntityManagerFactory emf;

	@Produces
	public EntityManagerFactoryAccessor produce() {
		return new EntityManagerFactoryAccessor(this.emf);
	}
}
