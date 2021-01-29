<%--
  PersistentVolumeClaims events

  @author jjy
  @version 1.0
  @since 2020.09.17
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="../common/commonEvents.jsp"/>

<script type="text/javascript">

    // GET LIST
    var getList = function () {
        var resourceName = "<c:out value='${persistentVolumeClaimName}' default='' />";

        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_EVENTS_LIST %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", NAME_SPACE)
            .replace("{resourceUid:.+}", resourceName + '?type=persistentVolumeClaim');

        procGetCommonEventsList(reqUrl, resourceName);
    };


    // ON LOAD
    $(document.body).ready(function () {
        getList();
    });

</script>
