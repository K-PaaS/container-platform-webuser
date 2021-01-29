<%--
  Pods main

  @author kjhoon
  @version 1.0
  @since 2020.09.01
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<div class="content">
    <jsp:include page="../common/contentsTab.jsp"/>

    <div class="cluster_content02 row two_line two_view harf_view" style="display: block;">
        <ul class="maT30">
            <li class="cluster_first_box">
                <jsp:include page="./list.jsp"/>
            </li>
        </ul>
    </div>
</div>
<script type="text/javascript">
    // ON LOAD
    $(document.body).ready(function() {
        procViewLoading('show');
        getPodsList(0, <%= Constants.DEFAULT_LIMIT_COUNT %>, null);
        procViewLoading('hide');
    });
</script>
