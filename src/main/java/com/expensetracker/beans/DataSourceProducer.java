package com.expensetracker.beans;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

import com.expensetracker.utils.DBUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class DataSourceProducer {

	@Produces
	@Dependent
	@Resource(mappedName = DBUtils.DS_NAME)
	protected DataSource ds;
}
