package com.expensetracker.models;

import java.time.YearMonth;
import java.util.Objects;

import com.expensetracker.entities.Expense;
import com.expensetracker.entities.ExpenseCat;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ExpCatMonth {

	final ExpenseCat expenseCat;
	final YearMonth yearMonth;

	public ExpCatMonth(Expense exp) {
		this.expenseCat = exp.getExpenseCat();
		this.yearMonth = YearMonth.from(exp.getTxDate());
	}

	public ExpCatMonth(ExpenseCat expenseCat, YearMonth yearMonth) {
		this.expenseCat = expenseCat;
		this.yearMonth = yearMonth;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.expenseCat, this.yearMonth);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof ExpCatMonth)) {
			return false;
		}
		ExpCatMonth another = (ExpCatMonth) obj;
		return this.expenseCat.equals(another.expenseCat) && this.yearMonth.equals(another.yearMonth);
	}
}
