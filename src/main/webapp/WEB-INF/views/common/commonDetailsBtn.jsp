<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  common Detail Resource Button

  @author hrjin
  @version 1.0
  @since 2020.09.15
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.container.platform.web.user.common.Constants" %>

<div class="common-cu">
    <button id="deleteBtn" class="colors2 common-btn pull-left" title="deleteBtn"><spring:message code="M0026" text="삭제"/></button>
    <button id="beforeBtn" class="colors4 common-btn pull-right" title="beforeBtn" onclick="beforeBtn();"><spring:message code="M0028" text="이전"/></button>
    <button id="updateBtn" class="colors5 common-btn pull-right" title="updateBtn"><spring:message code="M0025" text="수정"/></button>
</div>

<script type="text/javascript">

    $(document).on("click", "#deleteBtn", function(){

        var del_rsc = 'Resource 삭제';
        var cfm = '확인';
        var cxl ='취소';
        var q_del_rsc = 'Resource를 삭제하시겠습니까?';

        var s_msg_del_rsc = '<spring:message code="M0038" arguments='arg_del_rsc' javaScriptEscape="true" text="Resource 삭제"/>';
        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';
        var s_msg_cxl = '<spring:message code="M0029" arguments='arg_cxl' javaScriptEscape="true" text="취소"/>';
        var s_msg_q_del_rsc= '<spring:message code="M0037" arguments='arg_q_del_rsc' javaScriptEscape="true" text="Resource를 삭제하시겠습니까?"/>';

        s_msg_del_rsc_lang = s_msg_del_rsc.replace('arg_del_rsc', del_rsc);
        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);
        s_msg_cxl_lang = s_msg_cxl.replace('arg_cxl', cxl);
        s_msg_q_del_rsc_lang = s_msg_q_del_rsc.replace('arg_q_del_rsc', q_del_rsc);

        var code = "<p class='account_modal_p'>" + s_msg_q_del_rsc_lang + "</p>";

        procSetLayerPopup(s_msg_del_rsc_lang, code, s_msg_cfm_lang, s_msg_cxl_lang, 'x', 'deleteCommonResource();', null, null);

    });

    //Delete Resource Func
    var deleteCommonResource = function() {

        $("#commonLayerPopup").modal("hide");


        var namespace = $("#hiddenNamespace").val();
        var resourceKind = $("#hiddenResourceKind").val();
        var resourceName = $("#hiddenResourceName").val();


        var reqUrl = "<%= Constants.API_URL%><%=Constants.URI_API_COMMON_RESOURCE_DELETE %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", namespace)
            .replace("{resourceKind:.+}", resourceKind)
            .replace("{resourceName:.+}", resourceName);

        procCallAjax(reqUrl, "DELETE", null, null, resourceDeleteCallback);
    };


    // Delete Resource Callback Func
    var resourceDeleteCallback = function (data) {
        procViewLoading('hide');

        setTimeout(() => resourceDelete(data) ,500);
    }

    var resourceDelete = function(data) {

        var del_rsc = 'Resource 삭제';
        var cfm = '확인';
        var resultCode = 'Resource가 정상적으로 삭제되었습니다.';
        var failResultCode = 'Resource 삭제를 실패하였습니다.';

        var s_msg_del_rsc = '<spring:message code="M0038" arguments='arg_del_rsc' javaScriptEscape="true" text="Resource 삭제"/>';
        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';
        var s_msg_resultCode = '<spring:message code="M0039" arguments='arg_resultCode' javaScriptEscape="true" text="Resource가 정상적으로 삭제되었습니다."/>';
        var s_msg_failResultCode = '<spring:message code="M0040" arguments='arg_failResultCode' javaScriptEscape="true" text="Resource 삭제를 실패하였습니다."/>';

        s_msg_del_rsc_lang = s_msg_del_rsc.replace('arg_del_rsc', del_rsc);
        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);
        s_msg_resultCode_lang = s_msg_resultCode.replace('arg_resultCode', resultCode);
        s_msg_failResultCode_lang = s_msg_failResultCode.replace('arg_failResultCode', failResultCode);

        if (!procCheckValidData(data)) {
            procAlertMessage(s_msg_failResultCode_lang);
            return false;

        } else {
            var nextActionUrl = data.nextActionUrl;
            procSetLayerPopup(s_msg_del_rsc_lang, s_msg_resultCode_lang, s_msg_cfm_lang, null, 'x', 'procMovePage("' + nextActionUrl + '")', null, null);
        }
    };


</script>

<script type="text/javascript">

    $("#updateBtn").on('click', function(event){

        var param_namespace =  $("#hiddenNamespace").val();
        var param_resourceKind = $("#hiddenResourceKind").val();
        var param_resourceName = $("#hiddenResourceName").val();

        var reqUrl = "<%= Constants.CP_BASE_URL %><%= Constants.URI_API_COMMON_RESOURCE_UPDATE_VIEW %>"
            .replace("{namespace:.+}", param_namespace)
            .replace("{resourceKind:.+}", param_resourceKind)
            .replace("{resourceName:.+}", param_resourceName);

        procMovePage(reqUrl);

    });

</script>

<script>

    //Cancel Btn Func
    var beforeBtn = function () {
        procMovePage(-1);
    };
</script>




<style>
    .common-cu {
        width: 100%;
        height: 35px;
    }

    #updateBtn {
        margin-right: 15px;
    }
</style>