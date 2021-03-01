<!-- @variables
  page.title=${ctx.esc('error_page.page_title')}
    __layout=layout_admin
 -->

<div class="container mb-4">
  <ul class="mb-0 px-4 py-2 rounded messages-error">
    <li>${requestScope['com.appslandia.plum.base.Problem'].title}</li>
  </ul>
</div>

<!-- @jsSection begin -->
<script type="text/javascript">
  $(document).ready(function() {
  });
</script>
<!-- @jsSection end -->