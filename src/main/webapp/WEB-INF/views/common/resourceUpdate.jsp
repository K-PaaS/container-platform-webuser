<%--
  Created by IntelliJ IDEA.
  author: kjhoon
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
                    <div class="sortable_top colors5">
                        <p style="color: white">수정</p>
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
                        <button id="updateBtn" type="button" class="colors8 common-btn">수정</button>
                        <button id="cancelBtn" type="button" class="colors5 common-btn" onclick="cancelBtn();">취소
                        </button>
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

        if(yamlTextVal.length < 1) {
            procAlertMessage("<%=Constants.NO_VALUE_INPUT%>");}
        else {
            var code = "<p class='account_modal_p'>Resource를 수정하시겠습니까?</p>";
            procSetLayerPopup('Resource 수정', code, '확인', '취소', 'x', 'updateCommonResource()', null, null);
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

        var resultCode = 'Resource가 정상적으로 수정되었습니다.';

        if (!procCheckValidData(data)) {
            resultCode = 'Resource 수정을 실패하였습니다.';
            procAlertMessage(resultCode);
            return false;

        } else {
            var nextActionUrl = data.nextActionUrl;
            procSetLayerPopup('Resource 수정', resultCode, '확인', null, 'x', 'procMovePage("' + nextActionUrl + '")', null, null);
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



