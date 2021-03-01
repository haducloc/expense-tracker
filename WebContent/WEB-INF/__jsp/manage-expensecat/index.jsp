<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('manage_expensecat_index.page_title')}
  __layout=layout_admin
 -->

<div class="container mb-4">
  <div class="row">
    <div class="col">

      <div class="table-responsive mb-4">
        <table class="table table-sm nowrap-head table-bordered table-hover">
          <thead>
            <tr>
              <th scope="col" colspan="3">
                <t:actionLink action="edit" class="btn btn-sm btn-secondary font-w4">&plus;</t:actionLink>
              </th>
              <th scope="col" class="text-right">
                <span class="font-w4">${ctx.escCt('record.record_count', expenseCats.size())}</span>
              </th>
            </tr>
            <tr>
              <th scope="col">${ctx.escCt('expenseCat.name')}</th>
              <th scope="col" class="text-right">${ctx.escCt('expenseCat.monthlyBudget')}</th>
              <th scope="col" class="text-right">${ctx.escCt('expenseCat.yearlyBudget')}</th>
              <th scope="col">${ctx.escCt('expenseCat.dispPos')}</th>
            </tr>
          </thead>
          <tbody>
            <t:iterate items="${expenseCats}" var="item">
              <tr>
                <td>
                  <t:actionLink action="edit" __expenseCatId="${item.expenseCatId}">
                     ${fx:escCt(item.name)}
                  </t:actionLink>
                </td>
                <td class="text-right">
                  <t2:dollarAmt value="${item.monthlyBudget}" />
                </td>
                <td class="text-right">
                  <t2:dollarAmt value="${item.yearlyBudget}" />
                </td>
                <td>${fx:escCt(item.dispPos)}</td>
              </tr>
            </t:iterate>
            <t:c t="tr" render="${empty expenseCats}">
              <td colspan="4">${ctx.escCt('record.no_records_found')}</td>
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