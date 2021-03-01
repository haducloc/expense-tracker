package com.expensetracker.models;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import com.appslandia.common.base.NotBind;
import com.appslandia.common.models.SelectItem;
import com.expensetracker.entities.Expense;
import com.expensetracker.entities.ExpenseCat;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ExpenseIndexModel {

	private LocalDate dateFrom;

	private LocalDate dateTo;

	private Integer expenseCatId;

	private String vendor;

	private Boolean recurringType;

	private YearMonth yearMonth;

	@NotBind
	private List<ExpenseCat> expenseCats;

	@NotBind
	private List<String> vendors;

	@NotBind
	private List<SelectItem> recurringTypes;

	@NotBind
	private List<Expense> expenses;

	@NotBind
	private List<SelectItem> yearMonths;

	@NotBind
	private double totalAmt;

	public LocalDate getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(LocalDate dateFrom) {
		this.dateFrom = dateFrom;
	}

	public LocalDate getDateTo() {
		return dateTo;
	}

	public void setDateTo(LocalDate dateTo) {
		this.dateTo = dateTo;
	}

	public Integer getExpenseCatId() {
		return expenseCatId;
	}

	public void setExpenseCatId(Integer expenseCatId) {
		this.expenseCatId = expenseCatId;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Boolean getRecurringType() {
		return recurringType;
	}

	public void setRecurringType(Boolean recurringType) {
		this.recurringType = recurringType;
	}

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
	}

	public List<ExpenseCat> getExpenseCats() {
		return expenseCats;
	}

	public void setExpenseCats(List<ExpenseCat> expenseCats) {
		this.expenseCats = expenseCats;
	}

	public List<String> getVendors() {
		return vendors;
	}

	public void setVendors(List<String> vendors) {
		this.vendors = vendors;
	}

	public List<SelectItem> getRecurringTypes() {
		return recurringTypes;
	}

	public void setRecurringTypes(List<SelectItem> recurringTypes) {
		this.recurringTypes = recurringTypes;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public void setExpenses(List<Expense> expenses) {
		this.expenses = expenses;
	}

	public List<SelectItem> getYearMonths() {
		return yearMonths;
	}

	public void setYearMonths(List<SelectItem> yearMonths) {
		this.yearMonths = yearMonths;
	}

	public double getTotalAmt() {
		return totalAmt;
	}

	public void setTotalAmt(double totalAmt) {
		this.totalAmt = totalAmt;
	}
}
