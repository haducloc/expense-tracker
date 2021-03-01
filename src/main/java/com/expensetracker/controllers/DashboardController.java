package com.expensetracker.controllers;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.appslandia.common.logging.AppLogger;
import com.appslandia.common.utils.DateUtils;
import com.appslandia.common.utils.ValueUtils;
import com.appslandia.plum.base.ActionResult;
import com.appslandia.plum.base.Authorize;
import com.appslandia.plum.base.Controller;
import com.appslandia.plum.base.Home;
import com.appslandia.plum.base.HttpGet;
import com.appslandia.plum.base.Model;
import com.appslandia.plum.base.RequestAccessor;
import com.appslandia.plum.results.JspResult;
import com.expensetracker.beans.RecurringExpenseScheduler;
import com.expensetracker.entities.Expense;
import com.expensetracker.models.DashboardModel;
import com.expensetracker.models.DashboardTypes;
import com.expensetracker.models.ExpCatMonth;
import com.expensetracker.models.ExpCatYear;
import com.expensetracker.services.ExpenseCatService;
import com.expensetracker.services.ExpenseService;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Controller
@Authorize
@Home
public class DashboardController {

	@Inject
	protected AppLogger logger;

	@Inject
	protected ExpenseService expenseService;

	@Inject
	protected ExpenseCatService expenseCatService;

	@Inject
	protected RecurringExpenseScheduler expenseScheduler;

	@PostConstruct
	protected void initialized() {
		expenseScheduler.toString();
	}

	void prepareIndex(RequestAccessor request, @Model DashboardModel model) {
		model.setDashboardType(ValueUtils.inRange(model.getDashboardType(), DashboardTypes.TYPE_MONTHLY, DashboardTypes.TYPE_YEARLY));
		model.setDashboardTypes(DashboardTypes.createList(request.getResources()));
	}

	@HttpGet
	public ActionResult index(RequestAccessor request, HttpServletResponse response, @Model DashboardModel model) throws Exception {
		request.getModelState().clearErrors();

		if (model.getDashboardType() == DashboardTypes.TYPE_YEARLY) {
			// years
			List<Integer> years = new ArrayList<>();
			LocalDate today = LocalDate.now(request.getClientZone(null));

			// 10 Years
			int yearFrom = today.getYear() - 9;
			int yearTo = today.getYear();

			LocalDate dateFrom = LocalDate.of(yearFrom, 1, 1);
			LocalDate dateTo = today.with(TemporalAdjusters.lastDayOfYear());

			int year = yearTo;
			do {
				years.add(year);
				year -= 1;
			} while (year >= yearFrom);

			model.setYears(years);
			model.setCurrentYear(today.getYear());

			// ExpenseCats
			model.setExpenseCats(expenseCatService.getByUser(request.getUserId()));

			// ExpCatYears
			List<Expense> list = expenseService.query(request.getUserId(), dateFrom, dateTo, null, null, null);

			model.setExpCatYears(list.stream().collect(Collectors.groupingBy(e -> new ExpCatYear(e), Collectors.summingDouble(e -> e.getTxAmt()))));
			model.setYearTotals(list.stream().collect(Collectors.groupingBy(e -> e.getTxDate().getYear(), Collectors.summingDouble(e -> e.getTxAmt()))));

			request.storeModel(model);

			prepareIndex(request, model);
			return new JspResult().path("/dashboard/index_yearly.jsp");
		} else {
			// yearMonths
			List<YearMonth> yearMonths = new ArrayList<>();
			LocalDate today = LocalDate.now(request.getClientZone(null));

			// 12 Months
			LocalDate dateFrom = today.minusMonths(11).withDayOfMonth(1);
			LocalDate dateTo = DateUtils.lastDayOfMonth(today);

			YearMonth ym = YearMonth.from(dateTo);
			YearMonth fromYm = YearMonth.from(dateFrom);
			do {
				yearMonths.add(ym);
				ym = ym.plusMonths(-1);
			} while (ym.compareTo(fromYm) >= 0);

			model.setYearMonths(yearMonths);
			model.setCurrentYm(YearMonth.from(today));

			// ExpenseCats
			model.setExpenseCats(expenseCatService.getByUser(request.getUserId()));

			// ExpCatMonths
			List<Expense> list = expenseService.query(request.getUserId(), dateFrom, dateTo, null, null, null);

			model.setExpCatMonths(list.stream().collect(Collectors.groupingBy(e -> new ExpCatMonth(e), Collectors.summingDouble(e -> e.getTxAmt()))));
			model.setYearMonthTotals(
					list.stream().collect(Collectors.groupingBy(e -> YearMonth.from(e.getTxDate()), Collectors.summingDouble(e -> e.getTxAmt()))));

			request.storeModel(model);
			prepareIndex(request, model);
			return new JspResult().path("/dashboard/index_monthly.jsp");
		}
	}
}
