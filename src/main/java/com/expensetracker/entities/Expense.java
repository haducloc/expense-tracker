package com.expensetracker.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import com.appslandia.common.base.Bind;
import com.appslandia.common.jpa.EntityBase;
import com.expensetracker.formatters.Formatters;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@Entity
@NamedQuery(name = "Expense.query", query = "SELECT e FROM Expense e WHERE e.accountId=:accountId AND (:expenseCatId IS NULL OR e.expenseCatId=:expenseCatId) AND (e.txDate BETWEEN :dateFrom AND :dateTo) AND (:vendor IS NULL OR e.vendor LIKE :vendor) AND ((:recurringType=-1) OR (:recurringType=0 AND e.recurringExpenseId IS NULL) OR (:recurringType=1 AND e.recurringExpenseId IS NOT NULL)) ORDER BY e.recurringExpenseId ASC, e.txDate DESC")
@NamedNativeQuery(name = "Expense.checkExpense", query = "SELECT 1 FROM Expense e WHERE e.accountId=:accountId AND e.recurringExpenseId=:recurringExpenseId AND DATE_FORMAT(e.txDate, '%Y-%m')=:txYearMonth")
public class Expense extends EntityBase {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer expenseId;

	@NotNull
	private Integer expenseCatId;

	@NotNull
	private LocalDate txDate;

	@Bind(fmt = Formatters.MONEY_AMT)
	@Column(precision = 10, scale = 2, columnDefinition = "DECIMAL(10,2) NOT NULL")
	@NotNull
	private Double txAmt;

	@NotNull
	@Column(length = 255)
	private String vendor;

	@Column(length = 255)
	private String notes;

	@Column(updatable = false)
	private Integer recurringExpenseId;

	@NotNull
	@Column(updatable = false)
	private Integer accountId;

	@ManyToOne
	@JoinColumn(name = "expenseCatId", insertable = false, updatable = false)
	private ExpenseCat expenseCat;

	@ManyToOne
	@JoinColumn(name = "recurringExpenseId", insertable = false, updatable = false)
	private RecurringExpense recurringExpense;

	@Override
	public Serializable getPk() {
		return expenseId;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public Integer getExpenseCatId() {
		return expenseCatId;
	}

	public void setExpenseCatId(Integer expenseCatId) {
		this.expenseCatId = expenseCatId;
	}

	public LocalDate getTxDate() {
		return txDate;
	}

	public void setTxDate(LocalDate txDate) {
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

	public Integer getRecurringExpenseId() {
		return recurringExpenseId;
	}

	public void setRecurringExpenseId(Integer recurringExpenseId) {
		this.recurringExpenseId = recurringExpenseId;
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

	public RecurringExpense getRecurringExpense() {
		return recurringExpense;
	}

	public void setRecurringExpense(RecurringExpense recurringExpense) {
		this.recurringExpense = recurringExpense;
	}
}
