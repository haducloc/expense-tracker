package com.expensetracker.beans;

import java.sql.Connection;

import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
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
//@formatter:off
@DataSourceDefinition(
		name = DBUtils.DS_NAME, 
		className = "com.mysql.cj.jdbc.MysqlDataSource", 
		portNumber = 3306, 
		serverName = "localhost", 
		databaseName = DBUtils.DB_NAME, 
		user = "${dpusername}", 
		password = "${dpuserpwd}", 
		isolationLevel = Connection.TRANSACTION_READ_COMMITTED, 
		properties = {"serverTimezone=UTC"})
//@formatter:on

@ApplicationScoped
public class DataSourceProducer {

	@Produces
	@Dependent
	@Resource(mappedName = DBUtils.DS_NAME)
	protected DataSource ds;
}
