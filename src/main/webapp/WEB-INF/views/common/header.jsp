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
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

    #r_user{
        margin: 0px !important;
        padding: 0px !important;
        background-color: #f9f9f9 !important;
        top: 76px !important;
        box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2) !important;
        border : 1px solid rgba(220,220,220,0.8); !important;
    }

    #r_user_ul{
        margin: 0px !important;
        padding: 0px !important;

    }
    #r_user_ul li:hover {
        background-color: #f1f1f1 !important;
    }
    #r_user_ul li a {
        color: black !important;
        margin: 0 20px 0 0  !important;
        padding: 10px 15px !important;
        border-bottom: none;
    }
    #r_user_ul li a:hover {
        font-weight: normal !important;
    }
    #header-menu-logout{
        border-top : 1px solid rgba(220,220,220,0.9);
    }
    #u_locale_lang{
        height: 43px;
        width: 125px;
        line-height: initial;
        font-size: 14px;
        padding: 0 0 0 10px;
    }
    #localeLangDiv {
        margin: 0 9px;
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
        <li style="width: auto;">
            <div id="localeLangDiv">
                <fieldset>
                    <select name="u_locale_lang" id="u_locale_lang" onchange="changeLocaleLanguage()">
                        <option value="ko">Korean</option>
                        <option value="en">English</option>
                    </select>
                </fieldset>
            </div>
        </li>
        <li style="width: auto;"><div id="usernameDiv"></div></li>
        <li>
            <div class="btn-group">
                <button href="#" class="dropdown-toggle user" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                </button>
                <div id="r_user" class="dropdown-menu">
                    <ul id="r_user_ul" class="cp-user" style="width: 150px;">
                        <!-- <li id="header-menu-info"><a href="javascript:void(0);" onclick="procMovePage('<%= Constants.URI_USERS_INFO %>');">My info</a></li> -->
                        <li id="header-menu-users" class="r_user_li"><a href="javascript:void(0);" onclick="procMovePage('<%= Constants.URI_USERS %>');">Users</a></li>
                        <li id="header-menu-roles" class="r_user_li"><a href="javascript:void(0);" onclick="procMovePage('<%= Constants.URI_ROLES %>');">Roles</a></li>
                        <li id="header-menu-logout" class="r_user_li"><a href="javascript:void(0);" onclick="logoutHandler();">Sign Out</a></li>
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
                                <li><a class="cont-parent-link" href="javascript:void(0);" onclick="procMovePage('/${pathArray[0]}/${pathArray[1]}/${pathArray[2]}');">${cfn:camelCaseParser('private Repository')}</a></li>
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

        var logout = '로그아웃 하시겠습니까?';
        var sso_logout = 'SSO(Single Sign On) 통합 로그아웃이 진행됩니다.';
        var cfm = '확인';
        var cxl ='취소';

        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';
        var s_msg_cxl = '<spring:message code="M0029" arguments='arg_cxl' javaScriptEscape="true" text="취소"/>';
        var s_msg_logout = '<spring:message code="M0015" arguments='arg_logout' javaScriptEscape="true" text="로그아웃 하시겠습니까?"/>';
        var s_msg_sso_logout = '<spring:message code="M0016" arguments='arg_sso_logout' javaScriptEscape="true" text="SSO(Single Sign On) 통합 로그아웃이 진행됩니다."/>';

        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);
        s_msg_cxl_lang = s_msg_cxl.replace('arg_cxl', cxl);
        s_msg_logout_lang = s_msg_logout.replace('arg_logout', logout);
        s_msg_sso_logout_lang = s_msg_sso_logout.replace('arg_sso', sso_logout);

        var code = "<p class='account_modal_p'>" + s_msg_logout_lang + "</p>" +
                   "<p class='account_modal_p'>" + s_msg_sso_logout_lang + "</p>";

        procSetLayerPopup('Sign Out', code, s_msg_cfm_lang, s_msg_cxl_lang, 'x', 'logout()', null, null);

    }

    // 로그아웃 처리 (Logout Process)
    var logout = function() {
        procMovePage('/logout');
    };

    // Locale URL 세팅
    function changeLocaleLanguage() {
        var u_locale_lang = document.getElementById("u_locale_lang").value;
        var reqUrl = "<%= Constants.URL_API_LOCALE_LANGUAGE %>" +"?"+ "<%= Constants.URL_API_CHANGE_LOCALE_PARAM %>" + "=" + u_locale_lang; //=> /localeLanguage?language=ko 또는 en
        setLocaleLang(reqUrl);
        setLanguage(u_locale_lang);
    }

    function setSelectValue(id, val) {
        document.getElementById(id).value = val;
    }

    function reloadPage() {
        location.reload();
    }

    // Locale Language 조회
    function getLocaleLang(){
        var request = new XMLHttpRequest();
        request.open('GET', "/localeLanguage", false);
        request.setRequestHeader('Content-type', 'application/json');

        request.onreadystatechange = () => {
            if (request.readyState === XMLHttpRequest.DONE){
                if(request.status === 200){
                    setSelectValue('u_locale_lang',request.responseText);
                } else {
                    setSelectValue('u_locale_lang',"en");
                };
            };
        };
        request.send();
    };

    // Locale Language 설정
    function setLocaleLang(reqUrl){
        var request = new XMLHttpRequest();
        request.open('PUT', reqUrl, false);
        request.setRequestHeader('Content-type', 'application/json');

        request.onreadystatechange = () => {
            if (request.readyState === XMLHttpRequest.DONE){
                if(request.status === 200){
                    reloadPage();
                }
            };
        };
        request.send();
    };

</script>
