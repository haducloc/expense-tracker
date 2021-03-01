<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('manage_expensecat_edit.page_title')}
  __layout=layout_admin
 -->

<div class="container mb-4">
  <div class="row">
    <div class="col">

      <div class="mb-4 p-4 border">

        <t:form id="form1" action="edit" __expenseCatId="${model.expenseCatId}" autocomplete="off" actionType="true">
          <t:formErrors clazz="px-4 py-2" modelLevelOnly="true" />

          <div class="form-group">
            <t:fieldLabel field="name" labelKey="expenseCat.name" required="true" />
            <t:textBox path="model.name" clazz="form-control" />
          </div>

          <div class="form-group">
            <t:fieldLabel field="monthlyBudget" labelKey="expenseCat.monthlyBudget" />
            <div class="input-group">
              <div class="input-group-prepend">
                <div class="input-group-text">$</div>
              </div>
              <t:textBox type="number" step="1" path="model.monthlyBudget" clazz="form-control" />
            </div>
          </div>

          <div class="form-group">
            <t:fieldLabel field="yearlyBudget" labelKey="expenseCat.yearlyBudget" />
            <div class="input-group">
              <div class="input-group-prepend">
                <div class="input-group-text">$</div>
              </div>
              <t:textBox type="number" step="1" path="model.yearlyBudget" clazz="form-control" />
            </div>
          </div>

          <div class="form-group">
            <t:fieldLabel field="dispPos" labelKey="expenseCat.dispPos" required="true" />
            <t:textBox type="text" path="model.dispPos" clazz="form-control" />
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

    initButtons("#btnSave,#btnDel");
  });
</script>
<!-- @jsSection end -->