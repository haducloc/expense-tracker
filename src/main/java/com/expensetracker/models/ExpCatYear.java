package com.expensetracker.models;

import java.util.Objects;

import com.expensetracker.entities.Expense;
import com.expensetracker.entities.ExpenseCat;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ExpCatYear {

	final ExpenseCat expenseCat;
	final int year;

	public ExpCatYear(Expense exp) {
		this.expenseCat = exp.getExpenseCat();
		this.year = exp.getTxDate().getYear();
	}

	public ExpCatYear(ExpenseCat expenseCat, int year) {
		this.expenseCat = expenseCat;
		this.year = year;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.expenseCat, this.year);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ExpCatYear)) {
			return false;
		}
		ExpCatYear another = (ExpCatYear) obj;
		return this.expenseCat.equals(another.expenseCat) && this.year == another.year;
	}
}
