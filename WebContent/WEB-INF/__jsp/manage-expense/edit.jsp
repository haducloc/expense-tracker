<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('manage_expense_edit.page_title')}
  __layout=layout_admin
 -->

<div class="container mb-4">
  <div class="row">

    <div class="mb-4 col-md-8">
      <div class="p-4 border">

        <t:form id="form1" action="edit" __expenseId="${model.expenseId}" autocomplete="off" actionType="true">
          <t:formErrors clazz="px-4 py-2" modelLevelOnly="true" />

          <t:hidden path="model.recurringExpenseId" />

          <div class="form-group">
            <t:fieldLabel field="expenseCatId" labelKey="expense.expenseCatId" required="true" />
            <t:select items="${expenseCats}" path="model.expenseCatId" clazz="form-control form-control-lg" />
          </div>

          <div class="form-group">
            <t:fieldLabel field="vendor" labelKey="expense.vendor" required="true" />
            <t:textBox type="text" path="model.vendor" list="vendors" clazz="form-control form-control-lg" enterBtn="btnSave" />

            <t:dataList id="vendors" items="${vendors}">
              <t:dataItem value="" />
            </t:dataList>
          </div>

          <div class="form-group">
            <t:fieldLabel field="txAmt" labelKey="expense.txAmt" required="true" />
            <div class="input-group">
              <div class="input-group-prepend">
                <div class="input-group-text">$</div>
              </div>
              <t:textBox type="number" step="0.01" path="model.txAmt" clazz="form-control form-control-lg" enterBtn="btnSave" />
            </div>
          </div>

          <div class="form-group">
            <t:fieldLabel field="txDate" labelKey="expense.txDate" required="true" />
            <t:textBox type="date" path="model.txDate" clazz="form-control form-control-lg" enterBtn="btnSave" />
          </div>

          <div class="form-group">
            <t:fieldLabel field="notes" labelKey="expense.notes" />
            <t:textBox type="text" path="model.notes" clazz="form-control form-control-lg" enterBtn="btnSave" />
          </div>

          <t:c t="div" clazz="form-group form-check" render="${not empty model.recurringExpense}">
            <input type="checkbox" class="form-check-input" id="recurringExpenseId_chk" checked="checked" disabled="disabled">
            <label class="form-check-label" for="recurringExpenseId_chk"> ${fx:escCt(model.recurringExpense.notesDesc)} </label>
          </t:c>

          <div class="form-row">
            <div class="col-auto mr-auto">
              <t:submitButton id="btnSave" clazz="btn btn-primary px-4" labelKey="label.save"></t:submitButton>
            </div>
            <div class="col-auto">
              <t:submitButton id="btnDel" actionType="remove" clazz="btn btn-danger px-3" labelKey="label.remove" render="${not empty model.pk}"></t:submitButton>
            </div>
          </div>
        </t:form>
      </div>
    </div>

    <div class="col-md-4">
      <div class="mb-4 p-4 border">
        <h2 class="font-sl2 font-w6 mb-3">${ctx.escCt('calculatorModel.calculator')}</h2>

        <form class="mb-0">
          <div class="form-group">
            <label>${ctx.escCt('calculatorModel.expressions')}</label>
            <textarea id="expressions" name="expressions" rows="6" class="form-control" placeholder="${ctx.esc('calculatorModel.expressions_hint')}"></textarea>
          </div>

          <div class="form-group">
            <t:button id="btnCal" clazz="btn btn-secondary px-3" labelKey="label.calculate"></t:button>
          </div>

          <div class="form-group">
            <label>${ctx.escCt('calculatorModel.result')}</label>
            <textarea id="result" name="result" rows="6" class="form-control" readonly="readonly" placeholder="${ctx.esc('calculatorModel.result_hint')}"></textarea>
          </div>
        </form>

      </div>
    </div>

  </div>
</div>

<!-- @jsSection begin -->
<script type="text/javascript">
  $(document).ready(function() {

    initDropdowns("#expenseCatId");
    initButtons("#btnSave,#btnDel");

    $("#btnCal").click(function() {

      var CAL_URL = '<t:action action="cal" />';
      var params = "expressions=" + encodeURIComponent($("#expressions").val());

      ajax("POST", CAL_URL, params, null, true, function(res) {
        $("#result").val(res.data.result);
      });
    });

  });
</script>
<!-- @jsSection end -->