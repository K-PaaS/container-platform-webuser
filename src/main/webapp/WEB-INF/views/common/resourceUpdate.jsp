<%--
  Resource Update

  @author kjhoon
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
                    <div class="sortable_top colors5">
                        <p style="color: white"><spring:message code="M0025" text="수정"/></p>
                    </div>
                    <div class="view_table_wrap">
                        <table class="table_event condition">
                            <tbody>
                            <tr>
                                <td>
                                    <textarea spellcheck="false" id="common_update_textarea"
                                              style="width: 100%; height: 400px;"></textarea>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="common-cu center">
                        <button id="updateBtn" type="button" class="colors8 common-btn"><spring:message code="M0025" text="수정"/></button>
                        <button id="cancelBtn" type="button" class="colors5 common-btn" onclick="cancelBtn();"><spring:message code="M0029" text="취소"/></button>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>


<input type="hidden" id="hiddenNamespace" name="hiddenNamespace" value="<c:out value='${namespace}' default='' />"/>
<input type="hidden" id="hiddenResourceKind" name="hiddenResourceKind"
       value="<c:out value='${resourceKind}' default='' />"/>
<input type="hidden" id="hiddenResourceName" name="hiddenResourceName"
       value="<c:out value='${resourceName}' default='' />"/>


<script type="text/javascript">

    $(document.body).ready(function () {
        getResourceYaml();
    });

    // Get Resource Yaml Func
    var getResourceYaml = function () {

        var namespace = $("#hiddenNamespace").val();
        var resourceKind = $("#hiddenResourceKind").val();
        var resourceName = $("#hiddenResourceName").val();


        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_COMMON_RESOURCE_YAML %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", namespace)
            .replace("{resourceKind:.+}", resourceKind)
            .replace("{resourceName:.+}", resourceName);

        procCallAjax(reqUrl, "GET", null, null, callbackProcGetCommonResourceYaml);
    };


    // Yaml Callback func
    var callbackProcGetCommonResourceYaml = function (data) {
        var sourceTypeYaml = data.sourceTypeYaml.trim();
        $('#common_update_textarea').html(sourceTypeYaml);
        procViewLoading('hide');
    };

</script>


<script type="text/javascript">

    $("#updateBtn").on('click', function (event) {

        var yamlTextVal = $("#common_update_textarea").val().trim();

        var edit_rsc = 'Resource 수정';
        var cfm = '확인';
        var cxl ='취소';
        var q_edit_rsc = 'Resource를 수정하시겠습니까?';

        var s_msg_edit_rsc = '<spring:message code="M0049" arguments='arg_edit_rsc' javaScriptEscape="true" text="Resource 수정"/>';
        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';
        var s_msg_cxl = '<spring:message code="M0029" arguments='arg_cxl' javaScriptEscape="true" text="취소"/>';
        var s_msg_q_edit_rsc= '<spring:message code="M0048" arguments='arg_q_edit_rsc' javaScriptEscape="true" text="Resource를 수정하시겠습니까?"/>';

        s_msg_edit_rsc_lang = s_msg_edit_rsc.replace('arg_edit_rsc', edit_rsc);
        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);
        s_msg_cxl_lang = s_msg_cxl.replace('arg_cxl', cxl);
        s_msg_q_edit_rsc_lang = s_msg_q_edit_rsc.replace('arg_q_edit_rsc', q_edit_rsc);

        if(yamlTextVal.length < 1) {
            procAlertMessage("<%=Constants.NO_VALUE_INPUT%>");}
        else {
            var code = "<p class='account_modal_p'>"+ s_msg_q_edit_rsc_lang +"</p>";
            procSetLayerPopup(s_msg_edit_rsc_lang, code, s_msg_cfm_lang, s_msg_cxl_lang, 'x', 'updateCommonResource()', null, null);
        }
    });

    //Update Resource Func
    var updateCommonResource = function () {

        $("#commonLayerPopup").modal("hide");

        var updateYaml = $("#common_update_textarea").val();
        updateYaml = updateYaml.trim();

        var namespace = $("#hiddenNamespace").val();
        var resourceKind = $("#hiddenResourceKind").val();
        var resourceName = $("#hiddenResourceName").val();

        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_COMMON_RESOURCE_UPDATE %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", namespace)
            .replace("{resourceKind:.+}", resourceKind)
            .replace("{resourceName:.+}", resourceName);

        procCallAjax(reqUrl, "PUT", updateYaml, true, resourceUpdateCallback);


    };

    // Update Resource Callback Func
    var resourceUpdateCallback = function (data) {
        procViewLoading('hide');

        setTimeout(() => resourceUpdate(data) ,500);
    }

    var resourceUpdate = function(data) {

        var edit_rsc = 'Resource 수정';
        var cfm = '확인';
        var resultCode = 'Resource가 정상적으로 수정되었습니다.';
        var failResultCode = 'Resource 수정을 실패하였습니다.';

        var s_msg_edit_rsc = '<spring:message code="M0049" arguments='arg_edit_rsc' javaScriptEscape="true" text="Resource 수정"/>';
        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';
        var s_msg_resultCode = '<spring:message code="M0050" arguments='arg_resultCode' javaScriptEscape="true" text="Resource가 정상적으로 수정되었습니다."/>';
        var s_msg_failResultCode = '<spring:message code="M0051" arguments='arg_failResultCode' javaScriptEscape="true" text="Resource 수정을 실패하였습니다."/>';

        s_msg_edit_rsc_lang = s_msg_edit_rsc.replace('arg_edit_rsc', edit_rsc);
        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);
        s_msg_resultCode_lang = s_msg_resultCode.replace('arg_resultCode', resultCode);
        s_msg_failResultCode_lang = s_msg_failResultCode.replace('arg_failResultCode', failResultCode);

        if (!procCheckValidData(data)) {
            procAlertMessage(s_msg_failResultCode_lang);
            return false;

        } else {
            var nextActionUrl = data.nextActionUrl;
            procSetLayerPopup(s_msg_edit_rsc_lang, s_msg_resultCode_lang, s_msg_cfm_lang, null, 'x', 'procMovePage("' + nextActionUrl + '")', null, null);
        }
    };


    //Cancel Btn Func
    var cancelBtn = function () {
        procMovePage(-1);
    };

    // ON LOAD
    $(document.body).ready(function () {
        $("#namespacesList").hide();
    });

</script>



