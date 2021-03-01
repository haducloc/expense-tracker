<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('manage_sql_index.page_title')}
  __layout=layout_admin
 -->

<div class="container mb-4">
  <div class="row">
    <div class="col">

      <div class="mb-4 p-4 border">
        <t:form id="form1" action="index" autocomplete="off">

          <div class="form-row">
            <div class="form-group col-md-6">
              <t:fieldLabel field="sqlType" labelKey="manageSqlModel.sqlType" />
              <t:select items="${model.sqlTypes}" path="model.sqlType" clazz="form-control" />
            </div>
            <div class="form-group col-md-6">
              <t:fieldLabel field="resultFormat" labelKey="manageSqlModel.resultFormat" />
              <t:select items="${model.resultFormats}" path="model.resultFormat" clazz="form-control" />
            </div>
          </div>

          <div class="form-group">
            <div class="form-check">
              <t:checkbox submitValue="true" path="model.resultFile" clazz="form-check-input" />
              <t:fieldLabel field="resultFormat" labelKey="manageSqlModel.resultFile" clazz="form-check-label" />
            </div>
          </div>

          <div class="form-group">
            <t:fieldLabel field="sqlText" labelKey="manageSqlModel.sqlText" />
            <t:textArea path="model.sqlText" clazz="form-control" rows="8" />
          </div>

          <div class="form-group">
            <t:submitButton id="btnRun" clazz="btn btn-primary px-4" labelKey="label.execute" handleWait="false"></t:submitButton>
          </div>

          <div class="form-group mb-0">
            <t:fieldLabel field="resultText" labelKey="manageSqlModel.resultText" />
            <t:textArea path="model.resultText" clazz="form-control" rows="8" />
          </div>
        </t:form>
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