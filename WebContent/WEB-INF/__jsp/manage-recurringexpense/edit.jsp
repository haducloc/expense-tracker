<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('manage_recurringexpense_edit.page_title')}
  __layout=layout_admin
 -->

<div class="container mb-4">
  <div class="row">
    <div class="col">

      <div class="mb-4 p-4 border">

        <t:form id="form1" action="edit" __recurringExpenseId="${model.recurringExpenseId}" autocomplete="off" actionType="true">
          <t:formErrors clazz="px-4 py-2" modelLevelOnly="true" />

          <div class="form-group">
            <t:fieldLabel field="expenseCatId" labelKey="recurringExpense.expenseCatId" required="true" />
            <t:select items="${expenseCats}" path="model.expenseCatId" clazz="form-control" />
          </div>

          <div class="form-group">
            <t:fieldLabel field="vendor" labelKey="expense.vendor" required="true" />
            <t:textBox type="text" path="model.vendor" list="vendors" clazz="form-control" />

            <t:dataList id="vendors" items="${vendors}">
              <t:dataItem value="" />
            </t:dataList>
          </div>

          <div class="form-group">
            <t:fieldLabel field="txAmt" labelKey="recurringExpense.txAmt" required="true" />
            <div class="input-group">
              <div class="input-group-prepend">
                <div class="input-group-text">$</div>
              </div>
              <t:textBox type="number" step="0.01" path="model.txAmt" clazz="form-control" />
            </div>
          </div>

          <div class="form-group">
            <t:fieldLabel field="txDate" labelKey="recurringExpense.txDate" required="true" />
            <t:textBox type="text" path="model.txDate" placeholder="{}-{}-dd" maxlength="8" clazz="form-control" />
          </div>

          <div class="form-group">
            <t:fieldLabel field="notes" labelKey="recurringExpense.notes" />
            <t:textBox type="text" path="model.notes" clazz="form-control" />
          </div>

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
  </div>
</div>

<!-- @jsSection begin -->
<script type="text/javascript">
  $(document).ready(function() {

   	initDropdowns("#expenseCatId");
    initButtons("#btnSave,#btnDel");
  });
</script>
<!-- @jsSection end -->