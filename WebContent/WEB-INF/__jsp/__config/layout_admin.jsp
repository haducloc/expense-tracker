<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="t" uri="http://www.appslandia.com/jstl/tags"%>
<%@ taglib prefix="fx" uri="http://www.appslandia.com/jstl/functions"%>
<%@ taglib prefix="t2" uri="http://expensetracker.appslandia.com/jstl/tags"%>
<!doctype html>
<html lang="${ctx.languageId}">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<meta name="description" content="@(page.desc)">
<meta name="author" content="@(page.author)" />
<meta name="robots" content="noindex, nofollow" />

<link rel="shortcut icon" href="@(contextPathExpr)/favicon.ico">
<link rel="apple-touch-icon" sizes="180x180" href="@(contextPathExpr)/apple-touch-icon.png">

<link rel="icon" type="image/png" sizes="512x512" href="@(contextPathExpr)/android-icon-512x512.png">
<link rel="icon" type="image/png" sizes="192x192" href="@(contextPathExpr)/android-icon-192x192.png">
<link rel="icon" type="image/png" sizes="32x32" href="@(contextPathExpr)/favicon-32x32.png">
<link rel="icon" type="image/png" sizes="16x16" href="@(contextPathExpr)/favicon-16x16.png">

<link rel="stylesheet" href="@(contextPathExpr)/static/css/bootstrap.min.css">
<link rel="stylesheet" href="@(contextPathExpr)/static/css/app.min.css">

<title>@{page.title}</title>
</head>
<!-- @variables: shared.properties -->

<body>
  <header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm fixed-top">
      <div class="container">
        <t:actionLink clazz="navbar-brand" action="index" controller="dashboard">${ctx.escCt('app.name')}</t:actionLink>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false"
          aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarCollapse">
          <t:c t="ul" clazz="navbar-nav ml-auto" render="${not empty @(principalPath)}">

            <t2:navItem clazz="nav-item mr-3" action="index" controller="manage-expense" linkKey="manage_expense_index.label" />
            <t2:navItem clazz="nav-item mr-3" action="edit" controller="manage-expense" linkKey="manage_expense_edit.label" />
            <t2:navItem clazz="nav-item mr-3" action="index" controller="manage-vendor" linkKey="manage_vendor_index.label" />

            <li class="nav-item dropdown mr-3">
              <a class="nav-link dropdown-toggle" href="#" id="adminDrp" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">${ctx.escCt('label.admin')}</a>

              <div class="dropdown-menu" aria-labelledby="adminDrp">
                <t:actionLink clazz="dropdown-item" action="index" controller="manage-sql">${ctx.escCt('manage_sql_index.label')}</t:actionLink>

                <div class="dropdown-divider"></div>
                <t:actionLink clazz="dropdown-item" action="index" controller="manage-recurringexpense">${ctx.escCt('manage_recurringexpense_index.label')}</t:actionLink>

                <div class="dropdown-divider"></div>
                <t:actionLink clazz="dropdown-item" action="index" controller="manage-expensecat">${ctx.escCt('manage_expensecat_index.label')}</t:actionLink>
              </div>
            </li>
          </t:c>

          <ul class="navbar-nav ml-auto">
            <c:if test="${empty @(principalPath)}">
              <li class="nav-item">
                <t:actionLink clazz="nav-link" action="login" controller="account">${ctx.escCt('label.login')}</t:actionLink>
              </li>
            </c:if>
            <c:if test="${not empty @(principalPath)}">
              <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="authDrp" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                  <t:displayName />
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="authDrp">
                  <t:actionLink clazz="dropdown-item" action="logout" controller="account">${ctx.escCt('label.logout')}</t:actionLink>
                </div>
              </li>
            </c:if>
          </ul>
        </div>
      </div>
    </nav>
  </header>

  <main role="main">
    <div class="container my-5">
      <div class="row">
        <div class="col text-center">
          <h1 class="font-sl10 font-w4">@{page.title}</h1>
        </div>
      </div>
    </div>
    <c:if test="${empty requestScope['javax.servlet.error.servlet_name'] and not empty messages}">
      <div class="container mb-4">
        <div class="messages-wrapper">
          <t:messages clazz="mb-0 px-4 py-2 rounded" />
        </div>
      </div>
    </c:if>
    <!-- @doBody -->
  </main>

  <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
  <script src="@(contextPathExpr)/static/js/bootstrap.bundle.min.js"></script>
  <script src="@(contextPathExpr)/static/js/app.min.js"></script>

  <script type="text/javascript">
      var LABEL_WAIT = "${ctx.esc('label.please_wait')}";
      setClientZoneCookie("clientZoneId", 3600, "/");
    </script>
  <!-- @jsSection? -->
</body>
</html>