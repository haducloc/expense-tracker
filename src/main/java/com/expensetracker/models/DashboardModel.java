package com.expensetracker.models;

import java.time.YearMonth;
import java.util.List;
import java.util.Map;

import com.appslandia.common.base.NotBind;
import com.appslandia.common.models.SelectItem;
import com.expensetracker.entities.ExpenseCat;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class DashboardModel {

	private int dashboardType;

	private YearMonth currentYm;

	private int currentYear;

	@NotBind
	private List<YearMonth> yearMonths;

	@NotBind
	private List<Integer> years;

	@NotBind
	private List<ExpenseCat> expenseCats;

	@NotBind
	private Map<ExpCatMonth, Double> expCatMonths;

	@NotBind
	private Map<ExpCatYear, Double> expCatYears;

	@NotBind
	private Map<YearMonth, Double> yearMonthTotals;

	@NotBind
	private Map<Integer, Double> yearTotals;

	@NotBind
	private List<SelectItem> dashboardTypes;

	public Double getMonthAmt(ExpenseCat cat, YearMonth ym) {
		return expCatMonths.get(new ExpCatMonth(cat, ym));
	}

	public Double getYearAmt(ExpenseCat cat, int year) {
		return expCatYears.get(new ExpCatYear(cat, year));
	}

	public int getDashboardType() {
		return dashboardType;
	}

	public void setDashboardType(int dashboardType) {
		this.dashboardType = dashboardType;
	}

	public YearMonth getCurrentYm() {
		return currentYm;
	}

	public void setCurrentYm(YearMonth currentYm) {
		this.currentYm = currentYm;
	}

	public int getCurrentYear() {
		return currentYear;
	}

	public void setCurrentYear(int currentYear) {
		this.currentYear = currentYear;
	}

	public List<YearMonth> getYearMonths() {
		return yearMonths;
	}

	public void setYearMonths(List<YearMonth> yearMonths) {
		this.yearMonths = yearMonths;
	}

	public List<Integer> getYears() {
		return years;
	}

	public void setYears(List<Integer> years) {
		this.years = years;
	}

	public List<ExpenseCat> getExpenseCats() {
		return expenseCats;
	}

	public void setExpenseCats(List<ExpenseCat> expenseCats) {
		this.expenseCats = expenseCats;
	}

	public Map<ExpCatMonth, Double> getExpCatMonths() {
		return expCatMonths;
	}

	public void setExpCatMonths(Map<ExpCatMonth, Double> expCatMonths) {
		this.expCatMonths = expCatMonths;
	}

	public Map<ExpCatYear, Double> getExpCatYears() {
		return expCatYears;
	}

	public void setExpCatYears(Map<ExpCatYear, Double> expCatYears) {
		this.expCatYears = expCatYears;
	}

	public Map<YearMonth, Double> getYearMonthTotals() {
		return yearMonthTotals;
	}

	public void setYearMonthTotals(Map<YearMonth, Double> yearMonthTotals) {
		this.yearMonthTotals = yearMonthTotals;
	}

	public Map<Integer, Double> getYearTotals() {
		return yearTotals;
	}

	public void setYearTotals(Map<Integer, Double> yearTotals) {
		this.yearTotals = yearTotals;
	}

	public List<SelectItem> getDashboardTypes() {
		return dashboardTypes;
	}

	public void setDashboardTypes(List<SelectItem> dashboardTypes) {
		this.dashboardTypes = dashboardTypes;
	}
}
