<%--
  Created by IntelliJ IDEA.
  author: hrjin
  version: 1.0
  Date: 2020.09.15
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="content">
    <div class="cluster_tabs clearfix"></div>
    <div class="cluster_content01 row two_line two_view">
        <ul>
            <li>
                <div class="sortable_wrap">
                    <div class="sortable_top colors10">
                        <p style="color: white">생성</p>
                    </div>
                    <div class="view_table_wrap">
                        <table class="table_event condition">
                            <tbody><tr><td>
                                <textarea id="yamlText" style="width: 100%; height: 400px;"></textarea>
                            </td>
                            </tr></tbody></table>
                    </div>
                    <div class="common-cu center">
                        <button id="createBtn" type="button" class="colors8 common-btn">저장</button>
                        <button id="cancel" type="button" class="colors5 common-btn" onclick="cancelBtn();">취소</button>
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
        if(yamlTextVal.length < 1) {
            procAlertMessage("<%=Constants.NO_VALUE_INPUT%>");}
        else {
            var code = "<p class='account_modal_p'>Resource를 등록하시겠습니까?</p>";
            procSetLayerPopup('Resource 등록', code, '확인', '취소', 'x', 'createCommonResource()', null, null);
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
        var resultCode = 'Resource가 정상적으로 등록되었습니다.';

        if (!procCheckValidData(data)) {
            resultCode = 'Resource 등록을 실패하였습니다.';
            procAlertMessage(resultCode);
            return false;
        } else {
            var nextActionUrl = data.nextActionUrl;
            procSetLayerPopup('Resource 등록', resultCode, '확인', null, 'x', 'procMovePage("' + nextActionUrl + '")', null, null);
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
