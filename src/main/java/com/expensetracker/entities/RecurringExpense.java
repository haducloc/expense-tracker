package com.expensetracker.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import com.appslandia.common.base.Bind;
import com.appslandia.common.jpa.EntityBase;
import com.appslandia.common.utils.StringFormat;
import com.expensetracker.formatters.Formatters;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@Entity
@NamedQuery(name = "RecurringExpense.queryAll", query = "SELECT e FROM RecurringExpense e")
@NamedQuery(name = "RecurringExpense.queryByUser", query = "SELECT e FROM RecurringExpense e WHERE e.accountId=:accountId")
public class RecurringExpense extends EntityBase {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer recurringExpenseId;

	@NotNull
	private Integer expenseCatId;

	// {}-{}-dd
	@NotNull
	private String txDate;

	@Bind(fmt = Formatters.MONEY_AMT)
	@Column(precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) NOT NULL")
	@NotNull
	private Double txAmt;

	@NotNull
	@Column(length = 255)
	private String vendor;

	@Column(length = 255)
	private String notes;

	@NotNull
	@Column(updatable = false)
	private Integer accountId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "expenseCatId", insertable = false, updatable = false)
	private ExpenseCat expenseCat;

	@Override
	public Serializable getPk() {
		return recurringExpenseId;
	}

	public String getNotesDesc() {
		return StringFormat.fmt("{} #{}", this.notes, this.recurringExpenseId);
	}

	public Integer getRecurringExpenseId() {
		return recurringExpenseId;
	}

	public void setRecurringExpenseId(Integer recurringExpenseId) {
		this.recurringExpenseId = recurringExpenseId;
	}

	public Integer getExpenseCatId() {
		return expenseCatId;
	}

	public void setExpenseCatId(Integer expenseCatId) {
		this.expenseCatId = expenseCatId;
	}

	public String getTxDate() {
		return txDate;
	}

	public void setTxDate(String txDate) {
		this.txDate = txDate;
	}

	public Double getTxAmt() {
		return txAmt;
	}

	public void setTxAmt(Double txAmt) {
		this.txAmt = txAmt;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public ExpenseCat getExpenseCat() {
		return expenseCat;
	}

	public void setExpenseCat(ExpenseCat expenseCat) {
		this.expenseCat = expenseCat;
	}
}
