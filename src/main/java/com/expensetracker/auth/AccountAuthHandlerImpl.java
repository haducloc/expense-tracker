package com.expensetracker.auth;

import javax.enterprise.context.ApplicationScoped;

import com.appslandia.common.base.MappedID;
import com.appslandia.plum.base.FormAuthHandler;
import com.appslandia.plum.base.Modules;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@MappedID(Modules.DEFAULT)
public class AccountAuthHandlerImpl extends FormAuthHandler {
}
