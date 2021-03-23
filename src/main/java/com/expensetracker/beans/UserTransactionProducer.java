package com.expensetracker.beans;

import javax.annotation.Priority;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.interceptor.Interceptor;
import javax.transaction.UserTransaction;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Priority(Interceptor.Priority.APPLICATION)
public class UserTransactionProducer {

	@Produces
	@Alternative
	@Dependent
	@Resource(mappedName = "java:jboss/UserTransaction")
	protected UserTransaction tx;
}
