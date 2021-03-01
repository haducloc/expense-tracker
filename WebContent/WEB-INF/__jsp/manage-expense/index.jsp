<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('manage_expense_index.page_title')}
  __layout=layout_admin
 -->

<div class="container mb-4">
  <div class="row">
    <div class="col">

      <div class="mb-4 p-4 border">
        <t:form action="index" method="GET">

          <div class="row">
            <div class="col-md-6">
              <div class="form-group row">
                <t:fieldLabel field="dateFrom" labelKey="expenseIndexModel.dateFrom" clazz="col-md-4 col-form-label" />
                <div class="col-md-8">
                  <t:textBox type="date" path="model.dateFrom" clazz="form-control" />
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group row">
                <t:fieldLabel field="dateTo" labelKey="expenseIndexModel.dateTo" clazz="col-md-4 col-form-label" />
                <div class="col-md-8">
                  <t:textBox type="date" path="model.dateTo" clazz="form-control" />
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col-md-6">
              <div class="form-group row">
                <t:fieldLabel field="yearMonth" labelKey="expenseIndexModel.yearMonth" clazz="col-md-4 col-form-label" />
                <div class="col-md-8">
                  <t:select items="${model.yearMonths}" path="model.yearMonth" clazz="form-control" triggerSubmit="true" />
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group row">
                <t:fieldLabel field="expenseCatId" labelKey="expenseIndexModel.expenseCatId" clazz="col-md-4 col-form-label" />
                <div class="col-md-8">
                  <t:select items="${model.expenseCats}" path="model.expenseCatId" clazz="form-control" triggerSubmit="true">
                    <t:selectItem name="" />
                  </t:select>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
            <div class="col-md-6">
              <div class="form-group row">
                <t:fieldLabel field="vendor" labelKey="expenseIndexModel.vendor" clazz="col-md-4 col-form-label" />
                <div class="col-md-8">
                  <t:textBox type="text" path="model.vendor" list="vendors" clazz="form-control" />

                  <t:dataList id="vendors" items="${model.vendors}">
                    <t:dataItem value="" />
                  </t:dataList>
                </div>
              </div>
            </div>
            <div class="col-md-6">
              <div class="form-group row">
                <t:fieldLabel field="recurringType" labelKey="expenseIndexModel.recurringType" clazz="col-md-4 col-form-label" />
                <div class="col-md-8">
                  <t:select items="${model.recurringTypes}" path="model.recurringType" clazz="form-control" triggerSubmit="true" />
                </div>
              </div>
            </div>
          </div>

          <div class="row mb-0">
            <div class="col">
              <t:submitButton id="btnSearch" clazz="btn btn-primary px-4" labelKey="label.search" handleWait="false"></t:submitButton>
            </div>
          </div>
        </t:form>
      </div>
    </div>
  </div>

  <div class="row">
    <div class="col">

      <div class="table-responsive mb-4">
        <table class="table table-sm nowrap-head table-bordered table-hover">
          <thead>
            <tr>
              <th scope="col">
                <t:actionLink action="edit" class="btn btn-sm btn-secondary font-w4">&plus;</t:actionLink>
              </th>
              <th scope="col" class="text-right">
                <t2:dollarAmt value="${model.totalAmt}" clazz="font-weight-bold" />
              </th>
              <th scope="col" colspan="3" class="text-right">
                <span class="font-weight-normal">${ctx.escCt('record.record_count', model.expenses.size())}</span>
              </th>
            </tr>
            <tr>
              <th scope="col">${ctx.escCt('expense.vendor')}</th>
              <th scope="col" class="text-right">${ctx.escCt('expense.txAmt')}</th>
              <th scope="col" class="text-center">${ctx.escCt('expense.txDate')}</th>
              <th scope="col">${ctx.escCt('expense.expenseCatId')}</th>
              <th scope="col">${ctx.escCt('expense.notes')}</th>
            </tr>
          </thead>
          <tbody>
            <t:iterate items="${model.expenses}" var="item">
              <tr>
                <td>
                  <t:actionLink action="edit" __expenseId="${item.expenseId}">
                     ${fx:escCt(item.vendor)}
                  </t:actionLink>
                </td>
                <td class="text-right">
                  <t2:dollarAmt value="${item.txAmt}" />
                </td>
                <td class="text-center">${ctx.fmtVal(item.txDate)}</td>
                <td>${fx:escCt(item.expenseCat.name)}</td>
                <td>${fx:escCt(item.notes)}</td>
              </tr>
            </t:iterate>

            <t:c t="tr" render="${empty model.expenses}">
              <td colspan="5">${ctx.escCt('record.no_records_found')}</td>
            </t:c>
          </tbody>
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