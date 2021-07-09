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
<style>
    #namespacesList {
        text-overflow: ellipsis;
        padding : 0 40px 0 10px ;
        width : 250px;
    }
    #usernameDiv{
        color : #fff;
        padding : 0 10px;
        font-size: 17px;
        margin-top: 3px;
    }
    #usernameDiv:hover{
        opacity: 0.8;
    }

    .r_user_li a{
        border : 1px solid transparent;
    }
</style>
<header class="header">
    <div class="logo">
        <a href="javascript:void(0);" onclick="procMovePage('<%= Constants.CP_INIT_URI %>');" class="custom_border_none"><h1><img src="<c:url value="/resources/images/main/logo.png"/>" alt=""/></h1></a>
    </div>
    <select id="namespacesList"  onchange="changeSelectedNamespace(this.value)">
    </select>
    <div class="gnb search">
    </div>

    <ul class="right_nav">
        <li style="width: auto;"><div id="usernameDiv"></div></li>
        <li>
            <div class="btn-group">
                <button href="#" class="dropdown-toggle user" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                </button>
                <div id="r_user" class="dropdown-menu">
                    <ul class="cp-user" style="width: 150px;">
                        <!-- <li id="header-menu-info"><a href="javascript:void(0);" onclick="procMovePage('<%= Constants.URI_USERS_INFO %>');">My info</a></li> -->
                        <li id="header-menu-users" class="r_user_li"><a href="javascript:void(0);" onclick="procMovePage('<%= Constants.URI_USERS %>');">Users</a></li>
                        <li id="header-menu-roles" class="r_user_li"><a href="javascript:void(0);" onclick="procMovePage('<%= Constants.URI_ROLES %>');">Roles</a></li>
                        <!-- <li id="header-menu-logout"><a href="javascript:void(0);" onclick="logoutHandler();">Logout</a></li> -->
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
                            <c:when test="${(path eq 'info')}" >
                               <%-- <li><a class="cont-parent-link" href="javascript:void(0);" onclick="procMovePage('/${pathArray[0]}/${pathArray[1]}');">My Info</a></li>--%>
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
                                <%-- service는 탭메뉴 없음.(service does not have tabmenu) 1 depth만 표시 (Show only 1 dept) --%>
                            </c:when>
                            <c:when test="${pathArray[1] eq 'storages'}" >
                                <%-- storages 는 탭메뉴 없음.(storages does not have tabmenu) 1 depth만 표시 (Show only 1 dept--%>
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

    var getNamespacesList = function() {
        var html = "";

        if(namespace === null || namespace === "" || namespace === undefined) {
            console.log("Namespace list does not exist...token expired...");
            return procMovePage('/logout');
        }

        for (var i = 0; i < namespacesList.length; i++) {
            html += "<option value='" + namespacesList[i] + "'" + "id='ns" + i + "'>" + namespacesList[i] + "</option>";
        };

        $("#namespacesList").html(html);
    };


    // 초기 네임스페이스 값은 namespace 목록의 첫 번째 값으로 셋팅 (Set namespace init value)
    var initSelectedNamespace = function() {

        var selectedNamespace = current_select_ns;

        // 선택된 네임스페이스가 없을 때 (When selected namespace is not exist)
        if(selectedNamespace == null || selectedNamespace === "" || selectedNamespace === "undefined" || selectedNamespace === "null") {

            var reqUrl = "<%= Constants.URI_UPDATE_SELECTED_NAMESPACE%>".replace("{namespace:.+}", namespacesList[0]);
            procCallAjax(reqUrl, "GET", null, null, callbackUpdateSelectedNamespace);
            selectedNamespace =  namespacesList[0];
        }

        // 페이지 이동 시에도 selected 고정 (Fixed selected)
        $("#namespacesList option[value='" + selectedNamespace + "']").attr('selected', 'selected');
        NAME_SPACE = selectedNamespace;

    };

    // 네임스페이스 변경 시 변경된 값 반영 (Update selected namespace value after namespace change)
    var changeSelectedNamespace = function(value) {

        var reqUrl = "<%= Constants.URI_UPDATE_SELECTED_NAMESPACE%>".replace("{namespace:.+}", value);
        procCallAjax(reqUrl, "GET", null, null, callbackUpdateSelectedNamespace);
        NAME_SPACE = value;

        procMovePage('<%=Constants.URI_INTRO_OVERVIEW%>');
    };


    var callbackUpdateSelectedNamespace = function(data) {

    }


    var setUserIdHeader = function(){
        $("#usernameDiv").text(CP_USER_ID);
    }

    $(document.body).ready(function () {
        getNamespacesList();
        initSelectedNamespace();
        setUserIdHeader();
    });


    var logoutHandler =function(){
        var code = "<p class='account_modal_p'>로그아웃 하시겠습니까?</p>";
        procSetLayerPopup('로그아웃', code, '확인', '취소', 'x', 'logout()', null, null);

    }

    // 로그아웃 처리 (Logout Process)
    var logout = function() {
        procMovePage('/logout');
    };



</script>
