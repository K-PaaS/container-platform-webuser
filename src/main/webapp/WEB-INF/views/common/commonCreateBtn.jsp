<%--
  common Create Resource Button

  author: hrjin
  version: 1.0
  since: 2020.09.15
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:include page="header.jsp" flush="true"/>
<div class="common-cu">
    <button id="createBtn" type="button" class="colors4 common-btn pull-right" onclick="createResource();">생성</button>
</div>

<input type="hidden" id="hiddenNamespace" value="" />
<input type="hidden" id="hiddenResourceKind" value="<c:out value='${param.kind}' default='' />" />
<script type="text/javascript">

    var createResource = function () {
        procMovePage("<%=Constants.CP_BASE_URL%><%=Constants.URI_API_COMMON_RESOURCE_CREATE_VIEW%>"
            .replace("{namespace:.+}", $("#hiddenNamespace").val())
            .replace("{resourceKind:.+}", $("#hiddenResourceKind").val()));
    };

    $(document.body).ready(function () {
        $("#hiddenNamespace").val(NAME_SPACE);
    });

</script>