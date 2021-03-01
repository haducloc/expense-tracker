<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('manage_recurringexpense_index.page_title')}
  __layout=layout_admin
 -->

<div class="container mb-4">

  <div class="row">
    <div class="col">

      <div class="table-responsive">
        <table class="table table-sm nowrap-head table-bordered table-hover">
          <thead>
            <tr>
              <th scope="col" colspan="4">
                <t:actionLink action="edit" class="btn btn-sm btn-secondary font-w4">&plus;</t:actionLink>
              </th>
              <th scope="col" class="text-right">
                <span class="font-w4">${ctx.escCt('record.record_count', recurringExpenses.size())}</span>
              </th>
            </tr>
            <tr>
              <th scope="col">${ctx.escCt('recurringExpense.vendor')}</th>
              <th scope="col" class="text-right">${ctx.escCt('recurringExpense.txAmt')}</th>
              <th scope="col" class="text-center">${ctx.escCt('recurringExpense.txDate')}</th>
              <th scope="col">${ctx.escCt('recurringExpense.notes')}</th>
              <th scope="col">${ctx.escCt('recurringExpense.expenseCatId')}</th>
            </tr>
          </thead>
          <tbody>
            <t:iterate items="${recurringExpenses}" var="item">
              <tr>
                <td>
                  <t:actionLink action="edit" __recurringExpenseId="${item.recurringExpenseId}">
                     ${fx:escCt(item.vendor)}
                  </t:actionLink>
                </td>
                <td class="text-right">
                  <t2:dollarAmt value="${item.txAmt}" />
                </td>
                <td class="text-center">${item.txDate}</td>
                <td>${fx:escCt(item.notes)}</td>
                <td>${fx:escCt(item.expenseCat.name)}</td>
              </tr>
            </t:iterate>
            <t:c t="tr" render="${empty recurringExpenses}">
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