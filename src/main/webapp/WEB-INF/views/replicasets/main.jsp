<%--
  ReplicaSets main
  @author kjhoon
  @version 1.0
  @since 2020.08.25
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<div class="content">
    <jsp:include page="../common/contentsTab.jsp"/>
    <div class="cluster_content01 row two_line two_view">
        <ul>
            <!-- Replica Sets 시작 -->
            <li class="cluster_fourth_box maB50">
                <jsp:include page="./list.jsp"/>
            </li>
            <!-- Replica Sets 끝 -->
        </ul>
    </div>
</div>
<script type="text/javascript">
    $(document.body).ready(function () {
        getReplicaSetsList(null,0, <%= Constants.OVERVIEW_LIMIT_COUNT %>, null);
    });
</script>
