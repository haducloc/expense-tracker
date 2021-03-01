package com.expensetracker.controllers;

import java.sql.Connection;
import java.sql.Statement;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;

import com.appslandia.common.base.Bind;
import com.appslandia.common.base.Out;
import com.appslandia.common.cdi.Json;
import com.appslandia.common.cdi.Json.Profile;
import com.appslandia.common.jdbc.JdbcUtils;
import com.appslandia.common.jdbc.ResultSetImpl;
import com.appslandia.common.json.JsonProcessor;
import com.appslandia.common.utils.ExceptionUtils;
import com.appslandia.common.utils.FileNameUtils;
import com.appslandia.common.utils.MimeTypes;
import com.appslandia.plum.base.ActionResult;
import com.appslandia.plum.base.Authorize;
import com.appslandia.plum.base.Controller;
import com.appslandia.plum.base.ExceptionHandler;
import com.appslandia.plum.base.HttpGet;
import com.appslandia.plum.base.HttpGetPost;
import com.appslandia.plum.base.ModelBinder;
import com.appslandia.plum.base.RequestAccessor;
import com.appslandia.plum.models.ManageSqlModel;
import com.appslandia.plum.results.JspResult;
import com.appslandia.plum.results.RedirectResult;
import com.appslandia.plum.results.TextFileResult;
import com.expensetracker.auth.AdminPolicy;
import com.expensetracker.utils.ResultUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Controller(value = "manage-sql")
@Authorize(policies = { AdminPolicy.ADMIN })
public class ManageSqlController {

	@Inject
	protected ModelBinder modelBinder;

	@Inject
	protected ExceptionHandler exceptionHandler;

	@Inject
	@Json(Profile.COMPACT_NULL)
	protected JsonProcessor jsonProcessor;

	@Inject
	protected DataSource ds;

	void prepareIndex(RequestAccessor request, ManageSqlModel model) {
		model.setSqlTypes(ManageSqlModel.sqlTypes(request.getResources()));
		model.setResultFormats(ManageSqlModel.resultFormats(request.getResources()));
	}

	@HttpGetPost
	public ActionResult index(RequestAccessor request, HttpServletResponse response) throws Exception {
		// GET
		if (request.isGetOrHead()) {
			ManageSqlModel model = new ManageSqlModel();

			model.setSqlType(ManageSqlModel.SQL_TYPE_QUERY);
			model.setResultFormat(ManageSqlModel.RESULT_FORMAT_JSON);

			request.storeModel(model);
			prepareIndex(request, model);
			return JspResult.DEFAULT;
		}

		// POST
		ManageSqlModel model = new ManageSqlModel();
		modelBinder.bindModel(request, model);

		if (!request.getModelState().isValid()) {
			request.storeModel(model);
			prepareIndex(request, model);
			return JspResult.DEFAULT;
		}

		try {
			try (Connection conn = ds.getConnection(); Statement stat = conn.createStatement()) {

				// Update
				if (!model.isQueryType()) {

					int updateResult = stat.executeUpdate(model.getSqlText());
					request.getMessages().addNotice(request.res("manage_sql_index.sql_executed_successfully", updateResult));

					request.storeModel(model);
					prepareIndex(request, model);
					return JspResult.DEFAULT;
				}

				// Query
				try (ResultSetImpl rs = new ResultSetImpl(stat.executeQuery(model.getSqlText()))) {

					Out<Integer> count = new Out<>();
					String content = model.isJsonResult() ? ResultUtils.executeJson(rs, this.jsonProcessor, count) : ResultUtils.executeCSV(rs, count);

					request.getMessages().addNotice(request.res("manage_sql_index.sql_executed_successfully", count.value));

					// INLINE
					if (!model.isResultFile()) {
						model.setResultText(content);

						request.storeModel(model);
						prepareIndex(request, model);
						return JspResult.DEFAULT;
					}

					// FILE
					if (model.isJsonResult()) {
						return new TextFileResult(content, FileNameUtils.insertNow("query.json"), MimeTypes.APP_JSON);
					} else {
						return new TextFileResult(content, FileNameUtils.insertNow("query.csv"), MimeTypes.APP_CSV);
					}
				}
			}
		} catch (Exception ex) {
			request.getMessages().addError(this.exceptionHandler.getProblem(request, ex).getTitle());
			model.setResultText(ExceptionUtils.toStackTrace(ex));

			request.storeModel(model);
			prepareIndex(request, model);
			return JspResult.DEFAULT;
		}
	}

	@HttpGet
	public ActionResult fixIdSeq(RequestAccessor request, @NotNull String tableName, @NotNull String idColumn, @Bind(defval = "false") boolean idInt,
			String idPkSeq, String idNotNull, String idPk, String idSeq) throws Exception {
		long latestSeq = 0;
		try (Connection conn = ds.getConnection()) {

			latestSeq = JdbcUtils.fixIdSeq(tableName, idColumn, idInt, idPkSeq, idNotNull, idPk, idSeq, conn);

			request.getMessages().addNotice("Done fixIdSeq, latestSeq=" + latestSeq);
			return new RedirectResult("index");
		}
	}
}
