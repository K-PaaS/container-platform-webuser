<%--
  Intro overview main

  @author kjhoon
  @version 1.0
  @since 2020.09.07
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel='stylesheet' type='text/css' href='<c:url value="/resources/css/jquery-ui-resourcequotas.min.css"/>'>
<div class="content">
    <jsp:include page="../common/contentsTab.jsp"/>
    <div class="cluster_tabs clearfix"></div>
    <!-- Intro 시작-->
    <div class="cluster_content01 row two_line two_view">
        <ul id="detailTab">
            <!-- Namespace 시작-->
            <li>
                <div class="sortable_wrap">
                    <div class="sortable_top">
                        <p>Namespace</p>
                    </div>
                    <div class="account_table view">
                        <table>
                            <colgroup>
                                <col style='width:20%'>
                                <col style=".">
                            </colgroup>
                            <tbody id="noResultAreaForNameSpaceDetails" style="display: none;"></tbody>
                            <tbody id="resultAreaForNameSpaceDetails" style="display: none;">
                            <tr>
                                <th><i class="cWrapDot"></i> Name</th>
                                <td id="nameSpaceName"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Creation Time</th>
                                <td id="nameSpaceCreationTime"></td>
                            </tr>
                            <tr>
                                <th><i class="cWrapDot"></i> Status</th>
                                <td id="nameSpaceStatus"></td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </li>
        </ul>
    </div>
    <!-- Intro 끝 -->
</div>
<!--ResourceQuota-->
<div id="quota-template" style="display:none;">
    <li class="cluster_second_box">
        <div class="sortable_wrap">
            <div class="sortable_top">
                <p>Resource Quotas</p>
            </div>
            <div class="view_table_wrap">
                <table class="table_event condition alignL">
                    <colgroup>
                        <col style='width:20%;'>
                        <col style='width:auto;'>
                        <col style='width:30%;'>
                    </colgroup>
                    <thead>
                    <tr id="noResultAreaForResourceQuotas" style="display: none"><td colspan='3'><p class='service_p'>생성한 Resource Quotas 가 없습니다.</p></td></tr>
                    <tr id="resultHeaderAreaForResourceQuotas" class="headerSortFalse">
                        <td>Name</td>
                        <td>Status</td>
                        <td>Created time</td>
                    </tr>
                    </thead>
                    <tbody id="resultAreaForResourceQuotas">
                    </tbody>
                </table>
            </div>
        </div>
    </li>
</div>
<!--LimitRange-->
<div id="range-template" style="display:none;">
    <li class="cluster_third_box">
        <div class="sortable_wrap">
            <div class="sortable_top">
                <p>Limit Range</p>
            </div>
            <div class="view_table_wrap">
                <table class="table_event condition alignL">
                    <p class="p30" id="nameForLimitRanges">- <strong>Name</strong> : {{items.name}} </p>
                    <colgroup>
                        <col style='width:auto;'>
                        <col style='width:auto;'>
                        <col style='width:auto;'>
                        <col style='width:auto;'>
                    </colgroup>
                    <thead>
                    <tr id="noResultAreaForLimitRanges" style="display: none"><td colspan='4'><p class='service_p'>생성한 Limit Ranges 가 없습니다.</p></td></tr>
                    <tr id="resultHeaderAreaForLimitRanges">
                        <td>Resource Name</td>
                        <td>Resource Type</td>
                        <td>Default Limit</td>
                        <td>Default Request</td>
                    </tr>
                    </thead>
                    <tbody id="resultAreaForLimitRanges"></tbody>
                </table>
            </div>
        </div>
    </li>
</div>

<script type="text/javascript">

    var G_PVC_LIST_GET_FIRST = true;

    var getDetail = function() {
        procViewLoading('show');

        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_NAME_SPACES_DETAIL %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", NAME_SPACE);

        procCallAjax(reqUrl, "GET", null, null, callbackGetDetail);
    };


    var callbackGetDetail = function(data) {
        var noResultAreaForNameSpaceDetails = $("#noResultAreaForNameSpaceDetails");
        var resultAreaForNameSpaceDetails = $("#resultAreaForNameSpaceDetails");

        if (!procCheckValidData(data)) {
            noResultAreaForNameSpaceDetails.show();
            procViewLoading('hide');
            procAlertMessage('NameSpaces 조회에 실패하였습니다.', false);
            return false;
        }

        $("#title").html(NAME_SPACE);

        var namespaceNameHtml = "<a href='javascript:void(0);' onclick='procMovePage(\"<%= Constants.URI_CLUSTER_NAMESPACES %>/" + NAME_SPACE + "\");'>" + NAME_SPACE + "</a>";

        $("#nameSpaceName").html(namespaceNameHtml);
        $("#nameSpaceCreationTime").html(data.metadata.creationTimestamp);
        $("#nameSpaceStatus").html(data.status.phase);

        resultAreaForNameSpaceDetails.show();

        procViewLoading('hide');
    };


    var getResourceQuotaList = function(namespace) {
        procViewLoading('show');

        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_NAME_SPACES_RESOURCE_QUOTAS %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", namespace);

        procCallAjax(reqUrl, "GET", null, null, callbackGetResourceQuotaList);
    };

    var callbackGetResourceQuotaList = function(data) {
        var html = $("#quota-template").html();

        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage('ResourceQuotas 목록 조회에 실패하였습니다.', false);
            return false;
        }

        var trHtml;
        if (data.items.length >= 1) {
            for (var i = 0; i < data.items.length; i++) {
                var htmlRe = "";

                trHtml = "";
                trHtml += "<tr>"
                    + "<td>" + data.items[i].metadata.name + "</td>"
                    + "<td><p>" + JSON.stringify(data.items[i].resourceQuotasStatus) + "</p></td>"
                    + "<td>" + data.items[i].metadata.creationTimestamp + "</td>"
                    + "</tr>";

                htmlRe = html.replace("<tbody id=\"resultAreaForResourceQuotas\">", "<tbody id=\"resultAreaForResourceQuotas\">" + trHtml);

                $("#detailTab").append(htmlRe);

            }
        } else {
            $("#detailTab").append(html);

            $("#resultHeaderAreaForResourceQuotas").hide();
            $("#resultAreaForResourceQuotas").hide();
            $("#noResultAreaForResourceQuotas").show();
        }

        procSetToolTipForTableTd('resultAreaForResourceQuotas');
        procViewLoading('hide');
    };


    var getLimitRangeList = function(namespace) {
        procViewLoading('show');

        var reqUrl =  "<%= Constants.API_URL %><%= Constants.URI_API_NAME_SPACES_LIMIT_RANGES %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", namespace);

        procCallAjax(reqUrl, "GET", null, null, callbackGetLimitRangeList);
    };

    var callbackGetLimitRangeList = function(data) {
        var html = $("#range-template").html();

        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage('LimitRanges 목록 조회에 실패하였습니다.', false);
            return false;
        }

        var trHtml;

        if (data.items.length >= 1) {
            var countY = 0;
            for (var key = 0; key < data.items.length; key++) {

                var htmlRe = "";
                trHtml = "";
                if (data.items[key].checkYn == "Y") {
                    trHtml += "<tr>"
                        + "<td>" + data.items[key].resource + "</td>"
                        + "<td>" + data.items[key].type + "</td>"
                        + "<td>" + data.items[key].defaultLimit + "</td>"
                        + "<td>" + data.items[key].defaultRequest + "</td>"
                        + "</tr>";

                    htmlRe = html.replace("<tbody id=\"resultAreaForLimitRanges\">", "<tbody id=\"resultAreaForLimitRanges\">" + trHtml);
                    htmlRe = htmlRe.replace("{{items.name}}", data.items[key].name);

                    $("#detailTab").append(htmlRe);
                    countY = countY + 1;

                } else if (countY == 0) {
                    $("#detailTab").append(html);

                    $("#nameForLimitRanges").hide();
                    $("#resultHeaderAreaForLimitRanges").hide();
                    $("#resultAreaForLimitRanges").hide();
                    $("#noResultAreaForLimitRanges").show();

                    break;
                }
            }
        } else {
            $("#detailTab").append(html);

            $("#nameForLimitRanges").hide();
            $("#resultHeaderAreaForLimitRanges").hide();
            $("#resultAreaForLimitRanges").hide();
            $("#noResultAreaForLimitRanges").show();
        }

        procViewLoading('hide');

    };

    $(document.body).ready(function () {
        getDetail();
        getResourceQuotaList(NAME_SPACE);
        getLimitRangeList(NAME_SPACE);
    });

</script>
