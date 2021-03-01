package com.expensetracker.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.appslandia.common.base.Bind;
import com.appslandia.common.jpa.EntityBase;
import com.expensetracker.formatters.Formatters;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@Entity
public class Vendors extends EntityBase {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer accountId;

	@Bind(fmt = Formatters.VENDORS)
	@Column(length = 2500)
	private String vendors;

	@Override
	public Serializable getPk() {
		return this.accountId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getVendors() {
		return vendors;
	}

	public void setVendors(String vendors) {
		this.vendors = vendors;
	}
}
