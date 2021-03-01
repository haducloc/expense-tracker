package com.expensetracker.models;

import com.appslandia.common.base.NotBind;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class CalculatorModel {

	private String expressions;

	@NotBind
	private String result;

	public String getExpressions() {
		return expressions;
	}

	public void setExpressions(String expressions) {
		this.expressions = expressions;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
