<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('manage_vendor_index.page_title')}
  __layout=layout_admin
 -->

<div class="container mb-4">
  <div class="row">
    <div class="col">

      <div class="mb-4 p-4 border">

        <t:form id="form1" action="index" autocomplete="off">
          <t:formErrors clazz="px-4 py-2" modelLevelOnly="true" />

          <div class="form-group">
            <t:fieldLabel field="vendors" labelKey="vendors.vendors" />
            <t:textArea path="model.vendors" clazz="form-control" fmt="Vendors" rows="16" style="line-height: 2rem;" />
          </div>

          <t:submitButton id="btnSave" clazz="btn btn-primary px-4" labelKey="label.save"></t:submitButton>
        </t:form>
      </div>
    </div>
  </div>
</div>

<!-- @jsSection begin -->
<script type="text/javascript">
  $(document).ready(function() {

    initButtons("#btnSave");
  });
</script>
<!-- @jsSection end -->