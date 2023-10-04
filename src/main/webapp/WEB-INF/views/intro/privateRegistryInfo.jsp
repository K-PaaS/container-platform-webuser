<%--
  Private Registry Info

  @author kjhoon
  @version 1.0
  @since 2020.09.07
--%>
<%@ page import="org.container.platform.web.user.common.Constants" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="content">
    <jsp:include page="../common/contentsTab.jsp"/>
    <div class="cluster_tabs clearfix"></div>
    <div class="cluster_content01 row two_line two_view">
        <div class="sortable_wrap custom-sortable_wrap">
            <div class="sortable_top">
                <p>Private Repository Info</p>
            </div>
        </div>
        <div class="account_table access">
            <p><spring:message code="M0079" text="컨테이너 플랫폼 포털은 Private Repository로 Harbor를 사용하고 있으며, Harbor를 통해 컨테이너 플랫폼 관련 이미지 및 Helm Chart를 관리하고 있습니다."/></p>
             <br>
             <h4>What is Harbor?</h4>
              <p>Harbor is an open source registry that secures artifacts with policies and role-based access control, ensures images are scanned and free from vulnerabilities, and signs images as trusted.</p>
              <p>Harbor, a CNCF Graduated project, delivers compliance, performance, and interoperability to help you consistently and securely manage artifacts across cloud native compute platforms like Kubernetes and Docker.</p>
              <br>
            <p>
                <a href='javascript:void(0);' onclick="window.open('https://goharbor.io/')">Harbor docs</a>
            </p>
        </div>
    </div>
    <div class="cluster_tabs clearfix"></div>
    <%--How to access :: BEGIN--%>
    <div class="cluster_content01 row two_line two_view paB40">
        <div class="sortable_wrap custom-sortable_wrap">
            <div class="sortable_top">
                <p>How to access</p>
            </div>
        </div>
        <div class="custom-access-wrap" style="height: 110px;">
            <div class="custom-access-title">
                <p style="color: #292d37"><i class="fas fa-info-circle"></i>&nbsp;&nbsp;<spring:message code="M0080" text="Private Repository 접속정보는 운영자에게 문의하시기 바랍니다."/></p>
                <button  id="repoAccessBtn" style="width:160px; margin-top:15px;" class="colors5 common-btn" title="Private Repository 접속" onclick="moveToPrivateRepository();"><spring:message code="M0081" text="Private Repository 접속"/></button>
             </div>
        </div>
    </div>
    <%--How to access :: END--%>
</div>
<style>
    #repoAccessBtn {
        width: 180px !important;
        height: 40px;
        font-size: 13px;
    }
</style>

<script type="text/javascript">
    var G_PRIVATE_REPOSITORY_URL;

    var getRegistryInfo = function () {
        procViewLoading('show');

        $('.nameSpace').html(NAME_SPACE);

        procViewLoading('hide');
    };

    // BIND
    $(".custom-access-copy-button").on("click", function () {

        var cp_scr = '스크립트를 복사했습니다.';
        var f_cp_scr = '스크립트 복사에 실패했습니다.';
        var alert ='알림';
        var cfm = '확인';

        var s_msg_cp_scr = '<spring:message code="M0071" arguments='arg_cp_scr' javaScriptEscape="true" text="스크립트를 복사했습니다."/>';
        var s_msg_f_cp_scr = '<spring:message code="M0072" arguments='arg_f_cp_scr' javaScriptEscape="true" text="스크립트 복사에 실패했습니다."/>';
        var s_msg_alert = '<spring:message code="M0073" arguments='arg_alert' javaScriptEscape="true" text="알림"/>';
        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';

        s_msg_cp_scr_lang = s_msg_cp_scr.replace('arg_cp_scr', cp_scr);
        s_msg_f_cp_scr_lang = s_msg_f_cp_scr.replace('arg_f_cp_scr', f_cp_scr);
        s_msg_alert_lang = s_msg_alert.replace('arg_alert', alert);
        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);

        var item = document.getElementById($(this).attr('about'));
        var reqValue = item.innerText || item.textContent;
        var resultString = (procSetExecuteCommandCopy(reqValue)) ? s_msg_cp_scr_lang : s_msg_f_cp_scr_lang;
        procSetLayerPopup(s_msg_alert_lang, resultString, s_msg_cfm_lang, null, 'x', null, null, null);
    });

    var getPrivateRegistryDetail = function () {
        procViewLoading('show');
        var imageName= '<c:out value="${privateRegistryImageName}" />';
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_PRIVATE_REGISTRY_DETAIL %>"
            .replace("{imageName:.+}", imageName);

        procCallAjax(reqUrl, "GET", null, null, callbackGetPrivateRegistryDetail);
    };

    var callbackGetPrivateRegistryDetail = function (data) {

        var repositoryUrl = data.repositoryUrl;
        var imageName = data.imageName;
        var imageVersion = data.imageVersion;
        G_PRIVATE_REPOSITORY_URL = repositoryUrl;
    };

    // ON LOAD
    $(document.body).ready(function () {
        getPrivateRegistryDetail();
        getRegistryInfo();
    });

    var moveToPrivateRepository = function () {
        window.open(G_PRIVATE_REPOSITORY_URL);
    };
</script>