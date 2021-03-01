package com.expensetracker.controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.appslandia.common.logging.AppLogger;
import com.appslandia.plum.base.ActionResult;
import com.appslandia.plum.base.Authorize;
import com.appslandia.plum.base.Controller;
import com.appslandia.plum.base.ExceptionHandler;
import com.appslandia.plum.base.HttpGet;
import com.appslandia.plum.base.HttpGetPost;
import com.appslandia.plum.base.ModelBinder;
import com.appslandia.plum.base.RequestAccessor;
import com.appslandia.plum.results.JspResult;
import com.appslandia.plum.results.RedirectResult;
import com.expensetracker.entities.RecurringExpense;
import com.expensetracker.services.ExpenseCatService;
import com.expensetracker.services.RecurringExpenseService;
import com.expensetracker.services.VendorService;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Controller(value = "manage-recurringexpense")
@Authorize
public class ManageRecurringExpenseController {

	@Inject
	protected AppLogger logger;

	@Inject
	protected ModelBinder modelBinder;

	@Inject
	protected ExceptionHandler exceptionHandler;

	@Inject
	protected RecurringExpenseService recurringExpenseService;

	@Inject
	protected ExpenseCatService expenseCatService;

	@Inject
	protected VendorService vendorService;

	@HttpGet
	public ActionResult index(RequestAccessor request, HttpServletResponse response) throws Exception {
		request.store("recurringExpenses", recurringExpenseService.queryByUser(request.getUserId()));
		return JspResult.DEFAULT;
	}

	void prepareEdit(RequestAccessor request) {
		request.store("expenseCats", expenseCatService.getByUser(request.getUserId()));
		request.store("vendors", vendorService.getVendors(request.getUserId()));
	}

	@HttpGetPost
	public ActionResult edit(RequestAccessor request, HttpServletResponse response, Integer recurringExpenseId) throws Exception {
		// GET
		if (request.isGetOrHead()) {
			RecurringExpense model = null;

			if (recurringExpenseId == null) {
				model = new RecurringExpense();
				model.setTxDate("{}-{}-01");
			} else {
				model = this.recurringExpenseService.findByPk(recurringExpenseId);
				request.assertNotNull(model);
				request.assertForbidden(model.getAccountId() == request.getUserId());
			}

			request.storeModel(model);
			prepareEdit(request);
			return JspResult.DEFAULT;
		}

		// POST
		RecurringExpense model = new RecurringExpense();
		modelBinder.bindModel(request, model);

		request.getModelState().remove("accountId");
		model.setAccountId(request.getUserId());

		if (!request.getModelState().isValid()) {
			request.storeModel(model);
			prepareEdit(request);
			return JspResult.DEFAULT;
		}

		try {
			// Remove?
			if (request.isRemoveAction()) {
				request.assertNotNull(model.getRecurringExpenseId());
				recurringExpenseService.remove(request.getUserId(), model.getRecurringExpenseId());

				request.getMessages().addNotice(request.res("record.deleted_successfully", request.res("recurringExpense")));
				return new RedirectResult("index");
			}

			// Save
			this.recurringExpenseService.save(model);
			request.getMessages().addNotice(request.res("record.saved_successfully", request.res("recurringExpense")));

			return new RedirectResult("index");

		} catch (Exception ex) {
			logger.error(ex);
			request.getMessages().addError(this.exceptionHandler.getProblem(request, ex).getTitle());

			request.storeModel(model);
			prepareEdit(request);
			return JspResult.DEFAULT;
		}
	}
}
