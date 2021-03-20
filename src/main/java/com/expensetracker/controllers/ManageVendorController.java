package com.expensetracker.controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.appslandia.common.logging.AppLogger;
import com.appslandia.plum.base.ActionResult;
import com.appslandia.plum.base.Authorize;
import com.appslandia.plum.base.Controller;
import com.appslandia.plum.base.ExceptionHandler;
import com.appslandia.plum.base.HttpGetPost;
import com.appslandia.plum.base.ModelBinder;
import com.appslandia.plum.base.RequestAccessor;
import com.appslandia.plum.results.JspResult;
import com.appslandia.plum.results.RedirectResult;
import com.expensetracker.entities.Vendors;
import com.expensetracker.services.VendorService;
import com.expensetracker.utils.AccountUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Controller(value = "manage-vendor")
@Authorize
public class ManageVendorController {

	@Inject
	protected AppLogger logger;

	@Inject
	protected ModelBinder modelBinder;

	@Inject
	protected ExceptionHandler exceptionHandler;

	@Inject
	protected VendorService vendorService;

	@HttpGetPost
	public ActionResult index(RequestAccessor request, HttpServletResponse response) throws Exception {
		// GET
		if (request.isGetOrHead()) {
			Vendors model = this.vendorService.findByPk(request.getUserId());
			if (model == null)
				model = new Vendors();

			request.storeModel(model);
			return JspResult.DEFAULT;
		}

		// POST
		Vendors model = new Vendors();
		modelBinder.bindModel(request, model);

		request.getModelState().remove("accountId");
		model.setAccountId(request.getUserId());

		if (!request.getModelState().isValid()) {
			request.storeModel(model);
			return JspResult.DEFAULT;
		}

		try {
			// Demo user has read permissions only
			AccountUtils.assertNotDemoUser(request);

			// Save
			this.vendorService.save(model);
			request.getMessages().addNotice(request.res("record.saved_successfully", request.res("vendors")));

			return new RedirectResult("index");

		} catch (Exception ex) {
			logger.error(ex);
			request.getMessages().addError(this.exceptionHandler.getProblem(request, ex).getTitle());

			request.storeModel(model);
			return JspResult.DEFAULT;
		}
	}
}
