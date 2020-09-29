<%--
  Header

  @author kjhoon
  @version 1.0
  @since 2020.08.21
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="cfn" uri="common/customTag.tld" %>
<header class="header">
    <div class="logo">
        <a href="javascript:void(0);" onclick="procMovePage('<%= Constants.CP_INIT_URI %>');" class="custom_border_none"><h1><img src="<c:url value="/resources/images/main/logo.png"/>" alt=""/></h1></a>
    </div>
    <select id="namespacesList" style="width: 200px;">
    </select>
    <div class="gnb search">
    </div>

    <ul class="right_nav">
        <li>
            <div class="btn-group">
                <button href="#" class="dropdown-toggle user" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                </button>
                <div id="r_user" class="dropdown-menu">
                    <ul class="cp-user">
                        <li id="header-menu-users"><a href="javascript:void(0);" onclick="procMovePage('<%= Constants.URI_USERS %>');">Users</a></li>
                        <li id="header-menu-roles"><a href="javascript:void(0);" onclick="procMovePage('<%= Constants.URI_ROLES %>');">Roles</a></li>
                    </ul>
                </div>
            </div>
        </li>
    </ul>
    <div class="header_bottom">
        <p class="tit">Container Platform</p>
        <span class="nav_toggle">
                <input type="checkbox" id="checkbox-1" name="" class="navcheck" value="1" >
                <label for="checkbox-1"></label>
        </span>
        <c:set var="servletPath" value="${requestScope['javax.servlet.forward.servlet_path']}" />
        <c:set var="pathArray" value="${fn:split(servletPath,'/')}" />
        <ul class="content_dev">
            <c:forEach var="path" items="${pathArray}" varStatus="g">
                <c:choose>
                    <c:when test="${g.index eq 1}" >
                        <c:choose>
                            <c:when test="${(path eq 'services') || (path eq 'users') || (path eq 'roles') || (path eq 'storages')}" >
                                <li><a class="cont-parent-link" href="javascript:void(0);" onclick="procMovePage('/${pathArray[0]}/${pathArray[1]}');">${cfn:camelCaseParser(path)}</a></li>
                            </c:when>
                            <c:when test="${(path eq 'common')}" >
                                <li><a class="cont-parent-link">${cfn:camelCaseParser(path)}</a></li>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${path eq 'clusters'}" >
                                        <li><a class="cont-parent-link" href="javascript:void(0);" onclick="procMovePage('/${pathArray[0]}/intro/overview');">${cfn:camelCaseParser('intro')}</a></li>
                                    </c:when>
                                    <c:otherwise>
                                        <li><a class="cont-parent-link" href="javascript:void(0);" onclick="procMovePage('/${pathArray[0]}/${pathArray[1]}/overview');">${cfn:camelCaseParser(path)}</a></li>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                    <c:when test="${g.index eq 2}">
                        <c:choose>
                            <c:when test="${pathArray[2] eq 'accessInfo'}" >
                                <li><a class="cont-parent-link" href="javascript:void(0);" onclick="procMovePage('/${pathArray[0]}/${pathArray[1]}/${pathArray[2]}');">${cfn:camelCaseParser('access')}</a></li>
                            </c:when>
                            <c:when test="${pathArray[2] eq 'privateRegistryInfo'}" >
                                <li><a class="cont-parent-link" href="javascript:void(0);" onclick="procMovePage('/${pathArray[0]}/${pathArray[1]}/${pathArray[2]}');">${cfn:camelCaseParser('private registry')}</a></li>
                            </c:when>
                            <c:when test="${pathArray[1] eq 'services'}" >
                                <%-- service는 탭메뉴 없음. 1 depth만 표시 --%>
                            </c:when>
                            <c:when test="${pathArray[1] eq 'storages'}" >
                                <%-- storages 는 탭메뉴 없음. 1 depth만 표시 --%>
                            </c:when>
                            <c:when test="${pathArray[2] eq 'namespaces'}" >
                                <%-- namespaces main : Detail --%>
                                <li><a class="cont-parent-link" href="javascript:void(0);" onclick="procMovePage('/${pathArray[0]}/${pathArray[1]}/${pathArray[2]}/${pathArray[3]}');"> ${cfn:camelCaseParser(path)}</a></li>
                            </c:when>
                            <c:when test="${pathArray[2] eq 'nodes'}" >
                                <%-- nodes main : summary --%>
                                <li><a class="cont-parent-link" href="javascript:void(0);" onclick="procMovePage('/${pathArray[0]}/${pathArray[1]}/${pathArray[2]}/${pathArray[3]}/summary');"> ${cfn:camelCaseParser(path)}</a></li>
                            </c:when>
                            <c:when test="${(path eq 'resource')}" >
                            </c:when>
                            <c:otherwise>
                                <li><a class="cont-parent-link" href="javascript:void(0);" onclick="procMovePage('/${pathArray[0]}/${pathArray[1]}/${pathArray[2]}');">${cfn:camelCaseParser(path)}</a></li>
                            </c:otherwise>
                        </c:choose>
                    </c:when>
                </c:choose>
            </c:forEach>
        </ul>
        <div class="btn-kuber">
            <c:forEach var="path" items="${pathArray}" varStatus="g">
                <c:if test="${path eq 'clusters' || path eq 'workloads' || path eq 'services' || path eq 'users' || path eq 'roles' || path eq 'storages'}">
                </c:if>
            </c:forEach>
        </div>
    </div>
</header>

<script type="text/javascript">

    var getNamespacesList = function() {
        var html = '';

        for (var i=0; i < namespacesList.length; i++) {
            html += "<option value='" + namespacesList[i] + "'>" + namespacesList[i] + "</option>";
        };

        $("#namespacesList").html(html);
    };

    $("#namespacesList option:selected").val();

    $(document.body).ready(function () {
        getNamespacesList();

    });


</script>