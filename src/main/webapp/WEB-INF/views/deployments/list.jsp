<%--
  Deployments list

  @author kjhoon
  @version 1.0
  @since 2020.09.03
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div class="sortable_wrap">
    <div class="sortable_top">
        <p>Deployments</p>
        <ul class="colright_btn">
            <li>
                <input type="text" id="table-search-01" name="" class="table-search" placeholder="search"
                       onkeypress="if(event.keyCode===13) {searchDeploymentsList(this.value);}" maxlength="100"/>
                <button name="button" class="btn table-search-on" type="button">
                    <i class="fas fa-search"></i>
                </button>
            </li>
        </ul>
    </div>
    <div class="view_table_wrap">
        <table class="table_event condition alignL" id="resultDeploymentsTable">
            <colgroup>
                <col style="width:auto;">
                <col style="width:20%;">
                <col style="width:15%;">
                <col style="width:5%;">
                <col style="width:15%;">
                <col style="width:25%;">
            </colgroup>
            <thead>
            <tr id="noResultDeploymentsArea" style="display: none;">
                <td colspan='6'><p class='service_p'><spring:message code="M0055" text="실행 중인 Deployments가 없습니다."/></p></td>
            </tr>
            <tr id="resultDeploymentsHeaderArea" class="headerSortFalse">
                <td>Name</td>
                <td>Namespace</td>
                <td>Labels</td>
                <td>Pods</td>
                <td>Created on</td>
                <td>Images</td>
            </tr>
            </thead>
            <tbody id="deploymentsListArea"></tbody>
        </table>
    </div>
    <div class="resourceMoreDetailWrapDiv">
        <button id="deploymentsMoreDetailBtn" class="resourceMoreDetailBtn"><spring:message code="M0056" text="더보기(More)"/></button>
    </div>
</div>


<script type="text/javascript">

    var G_DEPLOYMENTS_LIST;
    var G_DEPLOYMENTS_LIST_LENGTH;
    var G_DEV_CAHRT_RUNNING_CNT = 0;
    var G_DEV_CHART_FAILED_CNT = 0;
    var G_DEV_CHART_SUCCEEDEDCNT = 0;
    var G_DEV_CHART_PENDDING_CNT = 0;
    var G_DEPLOYMENTS_LIST_GET_FIRST = true;
    var G_DEPLOYMENTS_LIST_LIMIT_COUNT= 0;
    var G_DEPLOYMENTS_LIST_OFFSET = 0;
    var G_DEPLOYMENTS_LIST_SEARCH_KEYWORD = null;
    var G_DEPLOYMENTS_MORE_BTN_ID = 'deploymentsMoreDetailBtn';

    //GET LIST
    var getDeploymentsList = function (offset, limit, searchName) {
        procViewLoading('show');

        var param = makeResourceListParamQuery(offset, limit, searchName);
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_DEPLOYMENTS_LIST %>" + param;
        reqUrl = reqUrl.replace("{cluster:.+}", CLUSTER_NAME).replace("{namespace:.+}", NAME_SPACE);

        procCallAjax(reqUrl, "GET", null, null, callbackGetDeploymentsList);

    };

    // CALLBACK
    var callbackGetDeploymentsList = function (data) {

        var f_srch_deployments = 'Deployments 목록 조회에 실패하였습니다.';
        var s_msg_f_srch_deployments = '<spring:message code="M0057" arguments='arg_f_srch_deployments' javaScriptEscape="true" text="Deployments 목록 조회에 실패하였습니다."/>';
        s_msg_f_srch_deployments_lang = s_msg_f_srch_deployments.replace('arg_f_srch_deployments', f_srch_deployments);

        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage(s_msg_f_srch_deployments_lang, false);
            return false;
        }

        G_DEPLOYMENTS_LIST = data;
        G_DEPLOYMENTS_LIST_LENGTH = data.items.length;

        resourceListMoreBtnDisplay('<%= Constants.REMAIN_ITEM_COUNT_KEY %>', data, G_DEPLOYMENTS_MORE_BTN_ID);
        setDeploymentsList("");
        procViewLoading('hide');

    };

    var setDeploymentsList = function (searchKeyword) {
        procViewLoading('show');

        var resultArea = $('#deploymentsListArea');
        var resultHeaderArea = $('#resultDeploymentsHeaderArea');
        var noResultArea = $('#noResultDeploymentsArea');
        var resultTable = $('#resultDeploymentsTable');

        var checkListCount = 0;
        var htmlString = [];

        $.each(G_DEPLOYMENTS_LIST.items, function (index, itemList) {
            var metadata = itemList.metadata;
            var spec = itemList.spec;
            var status = itemList.status;
            var deployName = metadata.name;

            if ((nvl(searchKeyword) === "") || deployName.indexOf(searchKeyword) > -1) {
                var namespace = metadata.namespace;
                // 라벨이 없는 경우도 있음(Without labels)
                var labels = procSetSelector(metadata.labels);
                var creationTimestamp = metadata.creationTimestamp;
                // Set replicas and total Pods are same
                var totalPods = spec.replicas;
                var runningPods = totalPods - status.unavailableReplicas;
                if(runningPods < 0) {runningPods = 0;}

                var containers = itemList.spec.template.spec.containers;
                var imageTags = "";

                for (var i = 0; i < containers.length; i++) {
                    imageTags += '<p>' + containers[i].image + '</p>';
                }

                procAddPodsEvent(itemList, itemList.spec.selector.matchLabels); // 이벤트 추가 (add Event)

                var statusIconHtml;
                var statusMessageHtml = [];

                if (itemList.type == 'Warning') { // [Warning]과 [Warning] 외 두 가지 상태로 분류 (Classifying into two states)
                    statusIconHtml = "<span class='red2 tableTdToolTipFalse'><i class='fas fa-exclamation-circle'></i> </span>";
                    $.each(itemList.message, function (index, eventMessage) {
                        statusMessageHtml += "<p class='red2 custom-content-overflow'>" + eventMessage + "</p>";
                    });
                } else {
                    statusIconHtml = "<span class='green2 tableTdToolTipFalse'><i class='fas fa-check-circle'></i> </span>";
                }

                if (itemList.type == "normal") {
                    G_DEV_CAHRT_RUNNING_CNT += 1;
                } else if (itemList.type == "Warning") {
                    G_DEV_CHART_FAILED_CNT += 1;
                } else {
                    G_DEV_CHART_FAILED_CNT += 1;
                }

                htmlString.push('<tr>' +
                    '<td>' +
                    statusIconHtml +
                    "<a href='javascript:void(0);' onclick='procMovePage(\"<%= Constants.URI_WORKLOAD_DEPLOYMENTS %>/" + deployName + "\");'>" + deployName + '</a>' +
                    statusMessageHtml +
                    '</td>' +
                    "<td><a href='javascript:void(0);' onclick='procMovePage(\"<%= Constants.URI_CLUSTER_NAMESPACES %>/" + namespace + "\");'>" + namespace + "</td>" +
                    '<td>' + procCreateSpans(labels, "LB") + '</td>' +
                    '<td>' + runningPods + " / " + totalPods + '</td>' +
                    '<td>' + creationTimestamp + '</td>' +
                    "<td>" + imageTags + "</td>" +
                    '</tr>');

                checkListCount++;
            }
        });

        if (G_DEPLOYMENTS_LIST_LENGTH < 1 || checkListCount < 1) {
            resultHeaderArea.hide();
            resultArea.hide();
            noResultArea.show();
        } else if (G_DEPLOYMENTS_LIST_GET_FIRST == true) {
            noResultArea.hide();
            resultHeaderArea.show();
            resultArea.html(htmlString);
            resultArea.show();
            resultTable.tablesorter();
            resultTable.trigger("update");
            $('.headerSortFalse > td').unbind();
        } else if (G_DEPLOYMENTS_LIST_GET_FIRST == false) {
            $('#deploymentsListArea tr:last').after(htmlString);
        }

        procSetToolTipForTableTd('resultDeploymentsTable');
        procViewLoading('hide');

    };

</script>

<script>

    $(document).on("click", "#"+ G_DEPLOYMENTS_MORE_BTN_ID, function () {
        G_DEPLOYMENTS_LIST_GET_FIRST = false;
        G_DEPLOYMENTS_LIST_OFFSET++;
        G_DEPLOYMENTS_LIST_LIMIT_COUNT = setResourceListLimitCount();

        getDeploymentsList(G_DEPLOYMENTS_LIST_OFFSET,G_DEPLOYMENTS_LIST_LIMIT_COUNT, G_DEPLOYMENTS_LIST_SEARCH_KEYWORD);

    });

    var searchDeploymentsList = function (searchName) {

        searchName = searchName.trim();
        if (searchName == null || searchName.length == 0) {
            searchName = null;
        }
        G_DEPLOYMENTS_LIST_GET_FIRST = true;
        G_DEPLOYMENTS_LIST_SEARCH_KEYWORD = searchName;
        G_DEPLOYMENTS_LIST_OFFSET = 0;
        G_DEPLOYMENTS_LIST_LIMIT_COUNT = setResourceListLimitCount();
        $("#"+ G_DEPLOYMENTS_MORE_BTN_ID).css("display", "block");

        getDeploymentsList(0, G_DEPLOYMENTS_LIST_LIMIT_COUNT, G_DEPLOYMENTS_LIST_SEARCH_KEYWORD);

    };

</script>