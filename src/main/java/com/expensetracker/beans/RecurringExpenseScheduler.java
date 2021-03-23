package com.expensetracker.beans;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.LastExecution;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.concurrent.Trigger;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.UserTransaction;

import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.jpa.EntityManagerFactoryAccessor;
import com.appslandia.common.logging.AppLogger;
import com.appslandia.common.utils.DateUtils;
import com.appslandia.common.utils.StringFormat;
import com.expensetracker.entities.Expense;
import com.expensetracker.entities.RecurringExpense;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class RecurringExpenseScheduler {

	@Inject
	protected AppLogger logger;

	@Inject
	protected UserTransaction tx;

	@Inject
	protected EntityManagerFactoryAccessor emf;

	@Resource
	protected ManagedScheduledExecutorService executorService;

	@PostConstruct
	protected void initialize() {
		logger.info("Initializing RecurringExpenseScheduler...");

		executorService.schedule(new Runnable() {

			String fmtMonth(int month) {
				if (month >= 10)
					return Integer.toString(month);
				return "0" + month;
			}

			@Override
			public void run() {
				logger.info("Executing RecurringExpenseScheduler...{}", OffsetDateTime.now());

				EntityManagerAccessor em = null;
				try {
					em = emf.createEntityManager();
					tx.begin();

					List<RecurringExpense> recurExpenses = em.createNamedQuery("RecurringExpense.queryAll", RecurringExpense.class).asReadonly()
							.getResultList();
					LocalDate today = LocalDate.now();

					for (RecurringExpense re : recurExpenses) {

						LocalDate txDate = DateUtils.iso8601LocalDate(StringFormat.fmt(re.getTxDate(), today.getYear(), fmtMonth(today.getMonthValue())));
						String txYearMonth = YearMonth.from(txDate).toString();

						if (em.createNamedQuery("Expense.checkExpense").setParameter("accountId", re.getAccountId())
								.setParameter("recurringExpenseId", re.getRecurringExpenseId()).setParameter("txYearMonth", txYearMonth)
								.getFirstOrNull() == null) {

							Expense expense = new Expense();
							expense.setExpenseCatId(re.getExpenseCatId());
							expense.setTxDate(txDate);
							expense.setTxAmt(re.getTxAmt());

							expense.setVendor(re.getVendor());
							expense.setRecurringExpenseId(re.getRecurringExpenseId());
							expense.setNotes(re.getNotesDesc());

							expense.setAccountId(re.getAccountId());
							em.persist(expense);
						}
					}

					tx.commit();

				} catch (Exception ex) {
					logger.error(ex);
					logger.error(() -> tx.rollback());

				} finally {
					if (em != null) {
						em.close();
					}
				}
			}

		}, new Trigger() {

			@Override
			public Date getNextRunTime(LastExecution lastExecutionInfo, Date taskScheduledTime) {
				if (lastExecutionInfo == null) {
					return getRunTime(false);
				}
				return getRunTime(true);
			}

			@Override
			public boolean skipRun(LastExecution lastExecutionInfo, Date scheduledRunTime) {
				return false;
			}
		});

		logger.info("Finished initializing RecurringExpenseScheduler.");
	}

	static Date getRunTime(boolean nextMonth) {
		OffsetDateTime now = OffsetDateTime.now();
		OffsetDateTime odt = OffsetDateTime.of(now.toLocalDate().plusMonths(nextMonth ? 1 : 0).withDayOfMonth(1), LocalTime.of(13, 0, 0, 0), now.getOffset());
		return new Date(odt.toInstant().toEpochMilli());
	}
}
