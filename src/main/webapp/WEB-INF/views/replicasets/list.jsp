<%--
  ReplicaSets list
  @author kjhoon
  @version 1.0
  @since 2020.08.25
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="sortable_wrap">
    <div class="sortable_top">
        <p>Replica Sets</p>
        <ul class="colright_btn">
            <li>
                <input type="text" id="table-search-rs" name="" class="table-search" placeholder="search"
                       onkeypress="if(event.keyCode===13) {searchReplicaSetsList(this.value);}" maxlength="100"/>
                <button name="button" class="btn table-search-on" type="button">
                    <i class="fas fa-search"></i>
                </button>
            </li>
        </ul>
    </div>
    <div class="view_table_wrap">
        <table class="table_event condition alignL" id="resultTableForReplicaSets">
            <colgroup>
                <col style='width:auto;'>
                <col style='width:10%;'>
                <col style='width:15%;'>
                <col style='width:5%;'>
                <col style='width:15%;'>
                <col style='width:25%;'>
            </colgroup>
            <thead>
            <tr id="resultHeaderAreaForReplicaSets" class="headerSortFalse" style="display: none;">
                <td>Name</td>
                <td>Namespace</td>
                <td>Labels</td>
                <td>Pods</td>
                <td>Created on</td>
                <td>Images</td>
            </tr>
            <tr id="noResultAreaForReplicaSets" style="display: none;">
                <td colspan='6'><p class='service_p'>실행 중인 ReplicaSet이 없습니다.</p></td>
            </tr>
            </thead>
            <tbody id="resultAreaForReplicaSets">
            </tbody>
        </table>
    </div>
    <div>
        <button id="replicaSetsMoreDetailBtn" class="resourceMoreDetailBtn">더보기(More)</button>
    </div>
</div>

<script type="text/javascript">

    var G_REPLICA_SETS_LIST;

    // Overview 통계용 데이터
    var G_REPLICA_SETS_LIST_LENGTH = 0;
    var G_REPLICA_SETS_TOTAL_CNT = 0;
    var G_REPLICA_SETS_CHART_RUNNING_CNT = 0;
    var G_REPLICA_SETS_CHART_FAILED_CNT = 0;
    var G_REPLICA_SETS_CHART_PENDDING_CNT = 0;
    var G_REPLICA_SETS_CHART_SUCCEEDED_CNT = 0;
    var G_REPLICA_SETS_LIST_GET_FIRST = true;
    var G_REPLICA_SETS_LIST_OFFSET = 0;
    var G_REPLICA_SETS_LIST_LIMIT_COUNT = 0;
    var G_REPLICA_SETS_LIST_SEARCH_KEYWORD = null;
    var G_REPLICA_SETS_MORE_BTN_ID = 'replicaSetsMoreDetailBtn';

    var ownerParamForPodsByReplicaSets ='';

    // GET LIST
    var getReplicaSetsList = function (selector, offset, limit, searchName) {
        procViewLoading('show');

        var reqUrl;

        if (selector != null) {
            var param = "?selector=" + selector;
            reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_REPLICA_SETS_RESOURCES %>"
                .replace("{cluster:.+}", CLUSTER_NAME)
                .replace("{namespace:.+}", NAME_SPACE) + param;

            if (searchName != null) {
                reqUrl += "&searchName=" + searchName;
            }

        } else {
            var param = makeResourceListParamQuery(offset, limit, searchName);
            reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_REPLICA_SETS_LIST %>" + param;
            reqUrl = reqUrl.replace("{cluster:.+}", CLUSTER_NAME)
                .replace("{namespace:.+}", NAME_SPACE);

        }

        procCallAjax(reqUrl, "GET", null, null, callbackGetReplicaSetsList);
    };

    // CALLBACK
    var callbackGetReplicaSetsList = function (data) {
        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage();
            return false;
        }

        G_REPLICA_SETS_LIST = data;
        resourceListMoreBtnDisplay('<%= Constants.REMAIN_ITEM_COUNT_KEY %>', data, G_REPLICA_SETS_MORE_BTN_ID);
        setReplicaSetsList("");
        procViewLoading('hide');


    };

    // SET LIST
    var setReplicaSetsList = function (searchKeyword) {
        procViewLoading('show');

        var resultArea = $('#resultAreaForReplicaSets');
        var resultHeaderArea = $('#resultHeaderAreaForReplicaSets');
        var noResultArea = $('#noResultAreaForReplicaSets');
        var resultTable = $('#resultTableForReplicaSets');
        var htmlString = [];


        var items = G_REPLICA_SETS_LIST.items;
        var checkListCount = 0;
        G_REPLICA_SETS_LIST_LENGTH = items.length;

        $.each(items, function (index, itemList) {

            var replicaSetName = itemList.metadata.name;

            if ((nvl(searchKeyword) === "") || replicaSetName.indexOf(searchKeyword) > -1) {
                var namespace = itemList.metadata.namespace;
                var labels = procSetSelector(itemList.metadata.labels);
                var replicaSetUid = itemList.metadata.uid;

                //set Labels, UID by Deployment details view
                ownerParamForPodsByReplicaSets = replaceLabels(labels) + "&type=replicaSets&ownerReferencesUid=" + replicaSetUid;


                var creationTimestamp = itemList.metadata.creationTimestamp;
                var pods = itemList.status.availableReplicas + " / " + itemList.spec.replicas;  // current / desired

                var imageTags = "";
                var containers = itemList.spec.template.spec.containers;
                for (var i = 0; i < containers.length; i++) {
                    imageTags += '<p>' + containers[i].image + '</p>';
                }

                //이벤트 관련 추가 START
                procAddPodsEvent(itemList, itemList.spec.selector.matchLabels);

                // Overview 통계용 전역 데이터 셋팅
                if (itemList.type == "normal") {
                    G_REPLICA_SETS_CHART_RUNNING_CNT += 1;
                } else if (itemList.type == "Warning") {
                    G_REPLICA_SETS_CHART_FAILED_CNT += 1;
                } else {
                    G_REPLICA_SETS_CHART_FAILED_CNT += 1;
                }
                G_REPLICA_SETS_TOTAL_CNT += itemList.spec.replicas;

                var statusIconHtml;
                var statusMessageHtml = [];

                if (itemList.type == 'Warning') { // [Warning]과 [Warning] 외 두 가지 상태로 분류
                    statusIconHtml = "<span class='red2 tableTdToolTipFalse'><i class='fas fa-exclamation-circle'></i> </span>";
                    $.each(itemList.message, function (index, eventMessage) {
                        statusMessageHtml += "<p class='red2 custom-content-overflow'>" + eventMessage + "</p>";
                    });

                } else {
                    statusIconHtml = "<span class='green2 tableTdToolTipFalse'><i class='fas fa-check-circle'></i> </span>";
                }
                //이벤트 관련 추가 END

                htmlString.push(
                    "<tr>"
                    + "<td>" + statusIconHtml
                    + "<a href='javascript:void(0);' onclick='procMovePage(\"<%= Constants.URI_WORKLOAD_REPLICA_SETS %>/" + replicaSetName + "\");'>" + replicaSetName + "</a>"
                    + statusMessageHtml
                    + "</td>"
                    + "<td><a href='javascript:void(0);' onclick='procMovePage(\"<%= Constants.URI_CLUSTER_NAMESPACES %>/" + namespace + "\");'>" + namespace + "</td>"
                    + "<td>" + procCreateSpans(labels, "LB") + "</td>"
                    + "<td>" + pods + "</td>"
                    + "<td>" + creationTimestamp + "</td>"
                    + "<td>" + imageTags + "</td>"
                    + "</tr>");

                checkListCount++;

            }  // if End
        });

        if (G_REPLICA_SETS_LIST_LENGTH < 1 || checkListCount < 1) {
            resultHeaderArea.hide();
            resultArea.hide();
            noResultArea.show();
        } else if (G_REPLICA_SETS_LIST_GET_FIRST == true) {
            noResultArea.hide();
            resultHeaderArea.show();
            resultArea.html(htmlString);
            resultArea.show();

            $('.headerSortFalse > td').unbind();
        } else if (G_REPLICA_SETS_LIST_GET_FIRST == false) {
            $('#resultAreaForReplicaSets tr:last').after(htmlString);
        }

        procSetToolTipForTableTd('resultAreaForReplicaSets');
        procViewLoading('hide');

    };

</script>
<script>

    $(document).on("click", "#" + G_REPLICA_SETS_MORE_BTN_ID, function () {

        G_REPLICA_SETS_LIST_GET_FIRST = false;
        G_REPLICA_SETS_LIST_OFFSET++;
        G_REPLICA_SETS_LIST_LIMIT_COUNT = setResourceListLimitCount();
        getReplicaSetsList(null, G_REPLICA_SETS_LIST_OFFSET, G_REPLICA_SETS_LIST_LIMIT_COUNT, G_REPLICA_SETS_LIST_SEARCH_KEYWORD);

    });

    var searchReplicaSetsList = function (searchName) {

        searchName = searchName.trim();
        if (searchName == null || searchName.length == 0) {
            searchName = null;
        }
        G_REPLICA_SETS_LIST_GET_FIRST = true;
        G_REPLICA_SETS_LIST_SEARCH_KEYWORD = searchName;
        G_REPLICA_SETS_LIST_OFFSET = 0;
        G_REPLICA_SETS_LIST_LIMIT_COUNT = setResourceListLimitCount();


        if (typeof ownerParamForReplicaSetsByDeployments !== 'undefined') {
            getReplicaSetsList(ownerParamForReplicaSetsByDeployments, 0, 0, G_REPLICA_SETS_LIST_SEARCH_KEYWORD);
        } else {
            $("#" + G_REPLICA_SETS_MORE_BTN_ID).css("display", "block");
            getReplicaSetsList(null, 0, G_REPLICA_SETS_LIST_LIMIT_COUNT, G_REPLICA_SETS_LIST_SEARCH_KEYWORD);
        }
    };


</script>