package com.expensetracker.controllers;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import org.mariuszgromada.math.mxparser.Expression;

import com.appslandia.common.base.TextBuilder;
import com.appslandia.common.logging.AppLogger;
import com.appslandia.common.models.SelectItem;
import com.appslandia.common.models.SelectItemImpl;
import com.appslandia.common.utils.DateUtils;
import com.appslandia.common.utils.DecimalUtils;
import com.appslandia.common.utils.SplitUtils;
import com.appslandia.plum.base.ActionResult;
import com.appslandia.plum.base.Authorize;
import com.appslandia.plum.base.Controller;
import com.appslandia.plum.base.ExceptionHandler;
import com.appslandia.plum.base.HttpGet;
import com.appslandia.plum.base.HttpGetPost;
import com.appslandia.plum.base.Model;
import com.appslandia.plum.base.ModelBinder;
import com.appslandia.plum.base.RequestAccessor;
import com.appslandia.plum.base.Result;
import com.appslandia.plum.results.JspResult;
import com.appslandia.plum.results.RedirectResult;
import com.appslandia.plum.utils.SelectItemUtils;
import com.expensetracker.entities.Expense;
import com.expensetracker.models.CalculatorModel;
import com.expensetracker.models.ExpenseIndexModel;
import com.expensetracker.services.ExpenseCatService;
import com.expensetracker.services.ExpenseService;
import com.expensetracker.services.VendorService;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Controller(value = "manage-expense")
@Authorize
public class ManageExpenseController {

	@Inject
	protected AppLogger logger;

	@Inject
	protected ModelBinder modelBinder;

	@Inject
	protected ExceptionHandler exceptionHandler;

	@Inject
	protected ExpenseService expenseService;

	@Inject
	protected ExpenseCatService expenseCatService;

	@Inject
	protected VendorService vendorService;

	void prepareIndex(RequestAccessor request, ExpenseIndexModel model) {
		model.setVendors(vendorService.getVendors(request.getUserId()));
		model.setRecurringTypes(SelectItemUtils.yesNoItems(request.getResources(), true));
		model.setExpenseCats(expenseCatService.getByUser(request.getUserId()));

		// YearMonths
		List<SelectItem> yearMonths = new ArrayList<>();
		yearMonths.add(SelectItemImpl.EMPTY);

		LocalDate today = LocalDate.now(request.getClientZone(null));
		YearMonth ym = YearMonth.from(today);
		YearMonth fromYm = YearMonth.from(today.minusYears(1).withMonth(1));

		do {
			yearMonths.add(new SelectItemImpl(ym, ym.toString()));
			ym = ym.plusMonths(-1);
		} while (ym.compareTo(fromYm) >= 0);

		model.setYearMonths(yearMonths);
	}

	@HttpGet
	public ActionResult index(RequestAccessor request, HttpServletResponse response, @Model ExpenseIndexModel model) throws Exception {

		LocalDate from = null;
		LocalDate to = null;
		ZoneId clientZone = request.getClientZone(null);

		// Compute model.dateFrom/dateTo
		if ((model.getYearMonth() != null) || (model.getDateFrom() == null && model.getDateTo() == null)) {

			if (model.getYearMonth() != null) {

				YearMonth ym = model.getYearMonth();
				from = ym.atDay(1);
				to = ym.atEndOfMonth();

				model.setDateFrom(null);
				model.setDateTo(null);

			} else {
				YearMonth ym = YearMonth.now(clientZone);
				from = ym.atDay(1);
				to = ym.atEndOfMonth();

				model.setDateFrom(from);
				model.setDateTo(to);
			}

		} else {
			LocalDate today = LocalDate.now(clientZone);

			if (model.getDateFrom() == null) {
				model.setDateFrom(DateUtils.firstDayOfMonth(today));
			}
			if (model.getDateTo() == null) {
				model.setDateTo(DateUtils.lastDayOfMonth(today));
			}

			from = model.getDateFrom();
			to = model.getDateTo();
		}

		request.getModelState().clearErrors();

		// Query Expenses
		if (from.compareTo(to) <= 1) {
			model.setExpenses(expenseService.query(request.getUserId(), from, to, model.getExpenseCatId(), model.getVendor(), model.getRecurringType()));
		} else {
			model.setExpenses(Collections.emptyList());
		}
		model.setTotalAmt(model.getExpenses().stream().mapToDouble(e -> e.getTxAmt()).sum());

		request.storeModel(model);
		prepareIndex(request, model);
		return JspResult.DEFAULT;
	}

	void prepareEdit(RequestAccessor request) {
		request.store("expenseCats", expenseCatService.getByUser(request.getUserId()));
		request.store("vendors", vendorService.getVendors(request.getUserId()));
	}

	@HttpGetPost
	public ActionResult edit(RequestAccessor request, HttpServletResponse response, Integer expenseId) throws Exception {
		// GET
		if (request.isGetOrHead()) {
			Expense model = null;

			if (expenseId == null) {
				model = new Expense();
				model.setTxDate(LocalDate.now(request.getClientZone(null)));

			} else {
				model = this.expenseService.findByPk(expenseId);

				request.assertNotNull(model);
				request.assertForbidden(model.getAccountId() == request.getUserId());
			}

			request.storeModel(model);
			prepareEdit(request);
			return JspResult.DEFAULT;
		}

		// POST
		Expense model = new Expense();
		modelBinder.bindModel(request, model);

		request.getModelState().remove("accountId", "timeCreated");
		model.setAccountId(request.getUserId());

		if (!request.getModelState().isValid()) {
			request.storeModel(model);
			prepareEdit(request);
			return JspResult.DEFAULT;
		}

		try {
			// Remove?
			if (request.isRemoveAction()) {
				request.assertNotNull(model.getExpenseId());
				expenseService.remove(request.getUserId(), model.getExpenseId());

				request.getMessages().addNotice(request.res("record.deleted_successfully", request.res("expense")));
				return new RedirectResult("index");
			}

			// Save
			this.expenseService.save(model);
			request.getMessages().addNotice(request.res("record.saved_successfully", request.res("expense")));

			// Recurring Expense
			if (model.getRecurringExpenseId() == null) {
				return new RedirectResult("index");
			} else {
				return new RedirectResult("index").query("recurringType", true);
			}

		} catch (Exception ex) {
			logger.error(ex);
			request.getMessages().addError(this.exceptionHandler.getProblem(request, ex).getTitle());

			request.storeModel(model);
			prepareEdit(request);
			return JspResult.DEFAULT;
		}
	}

	@HttpGetPost
	public Result cal(RequestAccessor request, @Model CalculatorModel model) {
		if (model.getExpressions() == null) {
			return new Result().setData(model);
		}
		TextBuilder res = new TextBuilder();

		for (String expr : SplitUtils.splitByLine(model.getExpressions())) {
			double result = new Expression(expr).calculate();

			if (Double.isNaN(result)) {

				String[] numbers = SplitUtils.split(expr, ',');
				result = Arrays.stream(numbers).mapToDouble(price -> new Expression(price).calculate()).sum();
			}

			if (!Double.isNaN(result)) {
				result = DecimalUtils.round(result, 2);
			}
			res.appendln(String.format("%s = %.2f", expr, result));
		}
		model.setResult(res.toString());
		return new Result().setData(model);
	}
}
