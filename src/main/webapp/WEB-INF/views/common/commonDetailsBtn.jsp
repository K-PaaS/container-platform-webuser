<%--
  Footer

  author: hrjin
  version: 1.0
  since: 2020.09.15
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>

<div class="common-cu">
    <button id="deleteBtn" class="colors2 common-btn pull-left" title="deleteBtn">삭제</button>
    <button id="beforeBtn" class="colors4 common-btn pull-right" title="beforeBtn" onclick="beforeBtn();">이전</button>
    <button id="updateBtn" class="colors5 common-btn pull-right" title="updateBtn">수정</button>
</div>

<script type="text/javascript">

    $(document).on("click", "#deleteBtn", function(){
        var code = "<p class='account_modal_p'>Resource를 삭제하시겠습니까?</p>";
        procSetLayerPopup('Resource 삭제', code, '확인', '취소', 'x', 'deleteCommonResource();', null, null);

    });

    //Delete Resource Func
    var deleteCommonResource = function() {

        $("#commonLayerPopup").modal("hide");


        var namespace = $("#hiddenNamespace").val();
        var resourceKind = $("#hiddenResourceKind").val();
        var resourceName = $("#hiddenResourceName").val();


        var reqUrl = "<%= Constants.API_URL%><%=Constants.URI_API_COMMON_RESOURCE_DELETE %>"
            .replace("{namespace:.+}", namespace)
            .replace("{resourceKind:.+}", resourceKind)
            .replace("{resourceName:.+}", resourceName);

        procCallAjax(reqUrl, "DELETE", null, null, resourceDeleteCallback);
    };


    // Delete Resource Callback Func
    var resourceDeleteCallback = function (data) {

        var resultCode = 'Resource가 정상적으로 삭제되었습니다.';

        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            resultCode = 'Resource 삭제를 실패하였습니다.';
            procAlertMessage(resultCode);
            return false;

        } else {

            var nextActionUrl = data.nextActionUrl;
            procViewLoading('hide');
            procSetLayerPopup('Resource 삭제', resultCode, '확인', null, 'x', 'procMovePage("' + nextActionUrl + '")', null, null);

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