<%--
  Roles Detail View
  @author kjhoon
  @version 1.0
  @since 2020.10.13
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<style>

#rulesListResultArea tr td {
    word-break:break-all !important;
    white-space: pre-wrap;
    font-size: 13px;
}

#beforeBtn{
    margin-top : -50px;
}

</style>
<div class="content">
    <h1 class="view-title"><span class="detail_icon"><i class="fas fa-file-alt"></i></span> <c:out value="${roleName}"/></h1>
    <jsp:include page="../common/contentsTab.jsp"/>
    <!-- Details 시작-->
    <div class="cluster_content01 row two_line two_view harf_view">
        <ul class="maT30">
            <li class="cluster_first_box">
                <div class="sortable_wrap">
                    <div class="sortable_top">
                        <p>Details</p>
                    </div>
                    <div class="account_table view">
                        <table>
                            <colgroup>
                                <col style="width:20%">
                                <col style=".">
                            </colgroup>
                            <tbody>
                            <tr>
                                <th><i class="cWrapDot"></i> Name</th>
                                <td id="name"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> UID</th>
                                <td id="uid"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Labels</th>
                                <td id="labels" class="labels_wrap"></td>
                            </tr>

                            <tr>
                                <th><i class="cWrapDot"></i> Annotations</th>
                                <td id="annotations" class="labels_wrap">
                                </td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Creation Time</th>
                                <td id="creationTime"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </li>
            <!-- Details 끝 -->
            <!-- Rules 시작 -->
            <li class="cluster_third_box maB50">
                <div class="sortable_wrap">
                    <div class="sortable_top">
                        <p>Rules</p>
                    </div>
                    <div class="view_table_wrap">
                        <table class="table_event condition alignL service-lh" id="resultTableForRules">
                            <colgroup>
                                <col style='width:auto;'>
                                <col style='width:25%;'>
                                <col style='width:25%;'>
                                <col style='width:15%;'>
                                <col style='width:15%;'>
                            </colgroup>
                            <thead>
                            <tr id="noRuleListResultArea" style="display: none;">
                                <td colspan='5'><p class='service_p'>실행 중인 Rules이 없습니다.</p></td>
                            </tr>
                            <tr id="ruleListResultHeaderArea" class="headerSortFalse">
                                <td>Resource</td>
                                <td>Non-resource URL</td>
                                <td>Resource name</td>
                                <td>Verb</td>
                                <td>API group</td>
                            </tr>
                            </thead>
                            <tbody id="rulesListResultArea">
                            </tbody>
                        </table>
                    </div>
                </div>
            </li>
            <!-- Rules 끝 -->
            <li class="cluster_fifth_box maB50">
                    <button id="beforeBtn" class="colors4 common-btn pull-right" title="beforeBtn" onclick="beforeBtn();">이전</button>
            </li>
        </ul>
    </div>
    <!-- Details  끝 -->
</div>
<input type="hidden" id="hiddenNamespace" name="hiddenNamespace" value="" />
<input type="hidden" id="hiddenResourceKind" name="hiddenResourceKind" value="roles" />
<input type="hidden" id="hiddenResourceName" name="hiddenResourceName" value="" />

<script type="text/javascript">

    var getRolesDetail = function() {
        procViewLoading('show');

        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_ROLES_DETAIL %>"
            .replace("{namespace:.+}", NAME_SPACE)
            .replace("{roleName:.+}", "<c:out value='${roleName}'/>");
        
        procCallAjax(reqUrl, "GET", null, null, callbackGetRoles);
    };

    var callbackGetRoles = function (data) {

        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage();
            return false;
        }

        var metadata = data.metadata;
        var rules = data.rules;

        // detail
        var namespace = metadata.namespace;
        var roleName = metadata.name;
        var uid = metadata.uid;
        var labels = procSetSelector(metadata.labels);
        var annotations = metadata.annotations;
        var creationTimestamp = metadata.creationTimestamp;


        $('#name').html(roleName);
        $('#uid').html(uid);
        $('#labels').html(procCreateSpans(labels));
        $('#annotations').html(procSetAnnotations(annotations));
        $('#creationTime').html(creationTimestamp);

        $('#hiddenNamespace').val(namespace);
        $('#hiddenResourceName').val(roleName);


        //ruls table data set
        setRulesDetail(rules);
    };


    var setRulesDetail = function (data) {

        var resultArea = $('#rulesListResultArea');//.hide();
        var resultHeaderArea = $('#ruleListResultHeaderArea');//.hide();
        var noResultArea = $('#noRuleListResultArea');//.show();
        var resultTable = $('#resultTableForRules');

        var checkListCount = 0;
        var htmlString = [];


        $.each(data, function(index, item) {

            if ((item != null)) {

                var resources =  (typeof item.resources !== 'undefined' && item.resources.length > 0) ? item.resources.join("\n") :  "-";
                var nonResourceURLs = (typeof item.nonResourceURLs !== 'undefined' && item.nonResourceURLs.length > 0) ? item.nonResourceURLs.join(", ") :  "-";
                var resourceNames =  (typeof item.resourceNames !== 'undefined' && item.resourceNames.length > 0) ? item.resourceNames.join(", ") :  "-";
                var verbs =  (typeof item.verbs !== 'undefined' && item.verbs.length > 0)? item.verbs.join(", ") :  "-";
                var apiGroups = (typeof item.apiGroups !== 'undefined' && item.apiGroups.length > 0) ? item.apiGroups.join(", ") :  "-";

                htmlString.push(
                     '<tr name="rulesRow"' + '>'
                    + '<td>' + resources + '</td>'
                    + '<td>' + nonResourceURLs+ '</td>'
                    + '<td>' + resourceNames + '</td>'
                    + '<td>' + verbs + '</td>'
                    + '<td>' + apiGroups + '</td>'
                    + '</tr>');

                checkListCount++;
           }
        });

        if (data.length < 1 || checkListCount < 1) {
            resultHeaderArea.hide();
            resultArea.hide();
            noResultArea.show();
        }
        else{
            noResultArea.hide();
            resultHeaderArea.show();
            resultArea.html(htmlString);
            resultArea.show();
        }

        procSetToolTipForTableTd('podListResultArea');
        $('[data-toggle="tooltip"]').tooltip();
        procViewLoading('hide');
    };

    $(document.body).ready(function () {
        getRolesDetail();
        procViewLoading('hide');
    });

</script>
<script>
    //Cancel Btn Func
    var beforeBtn = function () {
        procMovePage(-1);
    };
</script>