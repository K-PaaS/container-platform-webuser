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
    <select id="namespacesList" style="width: 200px;" onchange="changeNamespace(this.value)">
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
                        <%--<li id="header-menu-logout"><a href="javascript:void(0);" onclick="procMovePage('/logout');">Logout</a></li>--%>
                        <li id="header-menu-logout"><a href="javascript:void(0);" onclick="logout();">Logout</a></li>
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
<input type="hidden" id="chkValue" name="chkValue">
<script type="text/javascript" src='<c:url value="/resources/js/jquery.cookie.js"/>'></script>
<script type="text/javascript">

    var NAME_SPACE;
    var cookieName = "selectedNs";

    var getNamespacesList = function() {
        var html = "";
        for (var i = 0; i < namespacesList.length; i++) {
            html += "<option value='" + namespacesList[i] + "'" + "id='ns" + i + "'>" + namespacesList[i] + "</option>";
        };

        $("#namespacesList").html(html);
    };


    // 로그아웃 시 쿠키 제거
    var logout = function() {
        deleteCookie(cookieName);
        procMovePage('/logout');
    };


    // cookie 삭제
    var deleteCookie = function (cookieName) {
        $.removeCookie(cookieName, { path: '/' });
    };


    // cookie 처음 값은 namespace 목록의 첫 번째 값으로 1시간 셋팅
    var checkChkCookie = function(cookieName) {
        var cookieValue = $.cookie(cookieName);

        var hour = new Date();
        hour.setTime(hour.getTime() + (3600 * 1000)); // 1시간

        // 쿠키 없을 때
        if(cookieValue == null || cookieValue === "" || cookieValue === "undefined") {
            $.cookie(cookieName, namespacesList[0], { expires: hour, path: '/' });
        }
        cookieValue = $.cookie(cookieName);

        // 페이지 이동 시에도 selected 고정
        $("#namespacesList option[value='" + cookieValue + "']").attr('selected', 'selected');
        NAME_SPACE = cookieValue;

    };

    // namespace change 후에 cookie 값 갱신
    var changeNamespace = function(value) {
        deleteCookie(cookieName);
        var hour = new Date();
        hour.setTime(hour.getTime() + (3600 * 1000)); // 1시간
        var changedCookie = $.cookie(cookieName, value, { expires: hour, path : '/' });

        NAME_SPACE = value;

        location.reload();
    };

    $(document.body).ready(function () {
        getNamespacesList();
        checkChkCookie(cookieName);
    });


</script>