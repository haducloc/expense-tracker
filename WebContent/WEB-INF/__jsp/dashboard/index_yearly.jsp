<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('dashboard_index.page_title')}
  __layout=layout_admin
 -->

<div class="container-fluid mb-4">

  <div class="row">
    <div class="col">

      <div class="mb-4 p-4 border">
        <t:form action="index" method="GET" clazz="form-inline">

          <t:fieldLabel field="dashboardType" clazz="my-2 mr-3">Type</t:fieldLabel>
          <t:select path="model.dashboardType" items="${model.dashboardTypes}" clazz="form-control" triggerSubmit="true" />
        </t:form>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col">

      <div class="table-responsive mb-4">
        <table class="table table-sm table-bordered table-striped table-hover">
          <thead>
            <tr>
              <th scope="col"></th>
              <th scope="col"></th>
              <t:iterate items="${model.years}" var="year">
                <th class="text-right${model.currentYear eq year ? ' current-year' : ''}">
                  <t2:dollarAmt value="${model.yearTotals.get(year)}" clazz="font-weight-normal" />
                </th>
              </t:iterate>
            </tr>
            <tr>
              <th scope="col" class="bg-dark text-white">Category</th>
              <th scope="col" class="text-right bg-dark text-white">Max</th>

              <t:iterate items="${model.years}" var="year">
                <th scope="col" class="text-right bg-dark text-white">${year}</th>
              </t:iterate>
            </tr>
          </thead>
          <tbody>
            <t:iterate items="${model.expenseCats}" var="cat">
              <tr>
                <th scope="row">${fx:escCt(cat.name)}</th>
                <td class="text-right">
                  <t2:dollarAmt value="${cat.yearlyBudget}" />
                </td>

                <t:iterate items="${model.years}" var="year">
                  <td class="text-right${model.currentYear eq year ? ' current-year' : ''}">
                    <t2:totalAmt amt="${model.getYearAmt(cat, year)}" max="${cat.yearlyBudget}" />
                  </td>
                </t:iterate>
              </tr>
            </t:iterate>
          </tbody>
          <tfoot>
            <tr>
              <td></td>
              <td></td>
              <t:iterate items="${model.years}" var="year">
                <td class="text-right${model.currentYear eq year ? ' current-year' : ''}">
                  <t2:dollarAmt value="${model.yearTotals.get(year)}" clazz="font-weight-normal" />
                </td>
              </t:iterate>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>
  </div>
</div>

<!-- @jsSection begin -->
<script type="text/javascript">
  $(document).ready(function() {
  });
</script>
<!-- @jsSection end -->