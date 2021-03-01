package com.expensetracker.beans;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.appslandia.plum.base.ErrorServlet;
import com.appslandia.plum.base.RequestContext;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@WebServlet(name = "ErrorServlet", urlPatterns = "/error")
public class ErrorServletImpl extends ErrorServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected String getErrorJsp(HttpServletRequest request, RequestContext requestContext) {
		return "/error_admin.jsp";
	}

	@Override
	protected void writeErrorDev(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		writeErrorProd(request, response);
	}
}
