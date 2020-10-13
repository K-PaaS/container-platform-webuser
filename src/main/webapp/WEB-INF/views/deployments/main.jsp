<%--
  Deployments main
  @author kjhoon
  @version 1.0
  @since 2020.09.03
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<div class="content">
    <jsp:include page="../common/contentsTab.jsp"/>
    <!-- Deployments 시작 -->
    <div class="cluster_content02 row two_line two_view harf_view" style="display: block;">
        <ul class="maT30">
            <li class="cluster_first_box maB50">
                <jsp:include page="./list.jsp"/>
            </li>
        </ul>
    </div>
    <!-- Deployments 끝 -->
</div>

<script type="text/javascript">
    $(document.body).ready(function () {
        getDeploymentsList(<%= Constants.DEFAULT_LIMIT_COUNT %>,"");
    });
</script>