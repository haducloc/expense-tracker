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
import com.expensetracker.entities.ExpenseCat;
import com.expensetracker.services.ExpenseCatService;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Controller(value = "manage-expensecat")
@Authorize
public class ManageExpenseCatController {

	@Inject
	protected AppLogger logger;

	@Inject
	protected ModelBinder modelBinder;

	@Inject
	protected ExceptionHandler exceptionHandler;

	@Inject
	protected ExpenseCatService expenseCatService;

	@HttpGet
	public ActionResult index(RequestAccessor request, HttpServletResponse response) throws Exception {
		request.store("expenseCats", expenseCatService.queryByUser(request.getUserId()));
		return JspResult.DEFAULT;
	}

	@HttpGetPost
	public ActionResult edit(RequestAccessor request, HttpServletResponse response, Integer expenseCatId) throws Exception {
		// GET
		if (request.isGetOrHead()) {
			ExpenseCat model = null;

			if (expenseCatId == null) {
				model = new ExpenseCat();
			} else {
				model = this.expenseCatService.findByPk(expenseCatId);

				request.assertNotNull(model);
				request.assertForbidden(model.getAccountId() == request.getUserId());
			}

			request.storeModel(model);
			return JspResult.DEFAULT;
		}

		// POST
		ExpenseCat model = new ExpenseCat();
		modelBinder.bindModel(request, model);

		request.getModelState().remove("accountId");
		model.setAccountId(request.getUserId());

		if (!request.getModelState().isValid()) {
			request.storeModel(model);
			return JspResult.DEFAULT;
		}

		try {
			// Remove?
			if (request.isRemoveAction()) {
				request.assertNotNull(model.getExpenseCatId());
				expenseCatService.remove(request.getUserId(), model.getExpenseCatId());

				request.getMessages().addNotice(request.res("record.deleted_successfully", request.res("expenseCat")));
				return new RedirectResult("index");
			}

			// Save
			this.expenseCatService.save(model);
			request.getMessages().addNotice(request.res("record.saved_successfully", request.res("expenseCat")));

			return new RedirectResult("index");

		} catch (Exception ex) {
			logger.error(ex);
			request.getMessages().addError(this.exceptionHandler.getProblem(request, ex).getTitle());

			request.storeModel(model);
			return JspResult.DEFAULT;
		}
	}
}
