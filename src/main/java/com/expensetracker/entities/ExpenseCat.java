package com.expensetracker.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import com.appslandia.common.jpa.EntityBase;
import com.appslandia.common.models.SelectItem;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@Entity
@NamedQuery(name = "ExpenseCat.queryByUser", query = "SELECT e FROM ExpenseCat e WHERE e.accountId=:accountId ORDER BY e.dispPos")
public class ExpenseCat extends EntityBase implements SelectItem {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer expenseCatId;

	@NotNull
	@Column(length = 255)
	private String name;

	private Integer monthlyBudget;

	private Integer yearlyBudget;

	private int dispPos;

	@NotNull
	@Column(updatable = false)
	private Integer accountId;

	@Override
	public Serializable getPk() {
		return expenseCatId;
	}

	@Override
	public Object getValue() {
		return expenseCatId;
	}

	@Override
	public String getDisplayName() {
		return name;
	}

	public Integer getExpenseCatId() {
		return expenseCatId;
	}

	public void setExpenseCatId(Integer expenseCatId) {
		this.expenseCatId = expenseCatId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMonthlyBudget() {
		return monthlyBudget;
	}

	public void setMonthlyBudget(Integer monthlyBudget) {
		this.monthlyBudget = monthlyBudget;
	}

	public Integer getYearlyBudget() {
		return yearlyBudget;
	}

	public void setYearlyBudget(Integer yearlyBudget) {
		this.yearlyBudget = yearlyBudget;
	}

	public int getDispPos() {
		return dispPos;
	}

	public void setDispPos(int dispPos) {
		this.dispPos = dispPos;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
}
