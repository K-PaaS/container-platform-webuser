<%--
  Resource Create

  @author hrjin
  @version 1.0
  @since 2020.09.15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="content">
    <div class="cluster_tabs clearfix"></div>
    <div class="cluster_content01 row two_line two_view">
        <ul>
            <li>
                <div class="sortable_wrap">
                    <div class="sortable_top colors10">
                        <p style="color: white"><spring:message code="M0024" text="생성"/></p>
                    </div>
                    <div class="view_table_wrap">
                        <table class="table_event condition">
                            <tbody><tr><td>
                                <textarea id="yamlText" style="width: 100%; height: 400px;"></textarea>
                            </td>
                            </tr></tbody></table>
                    </div>
                    <div class="common-cu center">
                        <button id="createBtn" type="button" class="colors8 common-btn"><spring:message code="M0030" text="저장"/></button>
                        <button id="cancel" type="button" class="colors5 common-btn" onclick="cancelBtn();"><spring:message code="M0029" text="취소"/></button>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<input type="hidden" id="hiddenNamespace" name="hiddenNamespace" value="<c:out value='${namespace}' default='' />"/>
<input type="hidden" id="hiddenResourceKind" value="<c:out value='${resourceKind}' default='' />" />
<script type="text/javascript">

    // ON LOAD
    $(document.body).ready(function () {
        procViewLoading('hide');
    });


    $("#createBtn").on('click', function () {

        var yamlTextVal = $("#yamlText").val().trim();

        var reg_rsc = 'Resource 등록';
        var cfm = '확인';
        var cxl ='취소';
        var q_reg_rsc = 'Resource를 등록하시겠습니까?';
        var no_input = '입력된 값이 없습니다.';

        var s_msg_reg_rsc = '<spring:message code="M0045" arguments='arg_reg_rsc' javaScriptEscape="true" text="Resource 등록"/>';
        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';
        var s_msg_cxl = '<spring:message code="M0029" arguments='arg_cxl' javaScriptEscape="true" text="취소"/>';
        var s_msg_q_reg_rsc= '<spring:message code="M0044" arguments='arg_q_reg_rsc' javaScriptEscape="true" text="Resource를 등록하시겠습니까?"/>';
        var s_msg_no_input= '<spring:message code="M0457" arguments='arg_no_input' javaScriptEscape="true" text="입력된 값이 없습니다."/>';

        s_msg_reg_rsc_lang = s_msg_reg_rsc.replace('arg_reg_rsc', reg_rsc);
        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);
        s_msg_cxl_lang = s_msg_cxl.replace('arg_cxl', cxl);
        s_msg_q_reg_rsc_lang = s_msg_q_reg_rsc.replace('arg_q_reg_rsc', q_reg_rsc);
        s_msg_no_input_lang = s_msg_no_input.replace('arg_no_input', no_input);

        if(yamlTextVal.length < 1) {
            procAlertMessage(s_msg_no_input_lang);}
        else {
            var code = "<p class='account_modal_p'>" + s_msg_q_reg_rsc_lang + "</p>";
            procSetLayerPopup(s_msg_reg_rsc_lang, code, s_msg_cfm_lang, s_msg_cxl_lang, 'x', 'createCommonResource()', null, null);
        }
    });

    var createCommonResource = function () {
        $("#commonLayerPopup").modal("hide");

        var resourceKind = $("#hiddenResourceKind").val();

        var reqUrl = "<%=Constants.API_URL%><%=Constants.URI_API_COMMON_RESOURCE_CREATE%>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", $("#hiddenNamespace").val())
            .replace("{resourceKind:.+}", resourceKind);
        var yaml = $("#yamlText").val();

        procCallAjax(reqUrl, "POST", yaml, true, callbackCreateResource);
    };

    var callbackCreateResource = function (data) {
        procViewLoading('hide');

        setTimeout(() => resourceCreate(data) ,500);
    }

    var resourceCreate = function(data) {

        var reg_rsc = 'Resource 등록';
        var cfm = '확인';
        var resultCode = 'Resource가 정상적으로 등록되었습니다.';
        var failResultCode = 'Resource 등록을 실패하였습니다.';

        var s_msg_reg_rsc = '<spring:message code="M0045" arguments='arg_reg_rsc' javaScriptEscape="true" text="Resource 등록"/>';
        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';
        var s_msg_resultCode = '<spring:message code="M0046" arguments='arg_resultCode' javaScriptEscape="true" text="Resource가 정상적으로 등록되었습니다."/>';
        var s_msg_failResultCode = '<spring:message code="M0047" arguments='arg_failResultCode' javaScriptEscape="true" text="Resource 등록을 실패하였습니다."/>';

        s_msg_reg_rsc_lang = s_msg_reg_rsc.replace('arg_reg_rsc', reg_rsc);
        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);
        s_msg_resultCode_lang = s_msg_resultCode.replace('arg_resultCode', resultCode);
        s_msg_failResultCode_lang = s_msg_failResultCode.replace('arg_failResultCode', failResultCode);

        if (!procCheckValidData(data)) {
            procAlertMessage(s_msg_failResultCode_lang);
            return false;
        } else {
            var nextActionUrl = data.nextActionUrl;
            procSetLayerPopup(s_msg_reg_rsc_lang, s_msg_resultCode_lang, s_msg_cfm_lang, null, 'x', 'procMovePage("' + nextActionUrl + '")', null, null);
        }
    };

    var cancelBtn = function () {
        procMovePage(-1);
    };


    // ON LOAD
    $(document.body).ready(function () {
        $("#namespacesList").hide();
    });

</script>
