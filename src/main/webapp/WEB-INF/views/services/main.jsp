<%--
  Services main
  @author kjhoon
  @version 1.0
  @since 2020.09.10
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    #createBtn {
        margin-top: -10px;
    }
</style>
<div class="content">
    <div class="cluster_tabs clearfix"></div>
    <div class="cluster_content01 row two_line two_view">
        <ul>
            <li>
                <jsp:include page="../common/commonCreateBtn.jsp">
                    <jsp:param name="kind" value="services"/>
                </jsp:include>
                <div class="sortable_wrap">
                    <div class="sortable_top">
                        <p>Services</p>
                        <ul class="colright_btn">
                            <li>
                                <input type="text" id="table-search-01" name="" class="table-search"
                                       placeholder="search"
                                       onkeypress="if(event.keyCode===13) {searchServicesList(this.value);}"
                                       maxlength="100"/>
                                <button name="button" class="btn table-search-on" type="button">
                                    <i class="fas fa-search"></i>
                                </button>
                            </li>
                        </ul>
                    </div>
                    <div class="view_table_wrap">
                        <table class="table_event condition alignL service-lh" id="resultTable">
                            <colgroup>
                                <col style='width:auto;'>
                                <col style='width:10%;'>
                                <col style='width:10%;'>
                                <col style='width:20%;'>
                                <col style='width:5%;'>
                                <col style='width:20%;'>
                            </colgroup>
                            <thead>
                            <tr id="noResultArea">
                                <td colspan='6'><p class='service_p'>실행 중인 Service가 없습니다.</p></td>
                            </tr>
                            <tr id="resultHeaderArea" class="headerSortFalse" style="display: none;">
                                <td>Name</td>
                                <td>Service Type</td>
                                <td>Cluster IP</td>
                                <td>Endpoints</td>
                                <td>Pods</td>
                                <td>Created on</td>
                            </tr>
                            </thead>
                            <tbody id="resultArea">
                            <tr>
                                <td colspan="6"> -</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </li>
            <div>
                <button id="servicesMoreDetailBtn" class="resourceMoreDetailBtn">더보기(More)</button>
            </div>
        </ul>
    </div>
</div>


<script type="text/javascript">

    var G_SERVICE_LIST;
    var G_SERVICE_LIST_LENGTH;
    var G_SERVICE_LIST_GET_FIRST = true;
    var G_SERVICE_LIST_OFFSET = 0;
    var G_SERVICE_LIST_SEARCH_KEYWORD = null;
    var G_SERVICE_MORE_BTN_ID = 'servicesMoreDetailBtn';

    // GET LIST
    var getServiceList = function(offset, limit, searchName) {
        procViewLoading('show');

        var param = makeResourceListParamQuery(offset, limit, searchName);
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_SERVICES_LIST %>" + param;
        reqUrl = reqUrl.replace("{cluster:.+}", CLUSTER_NAME).replace("{namespace:.+}", NAME_SPACE);

        procCallAjax(reqUrl, "GET", null, null, callbackGetServiceList);
    };


    // CALLBACK
    var callbackGetServiceList = function (data) {
        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage('Services 목록 조회에 실패하였습니다.', false);
            return false;
        }

        G_SERVICE_LIST = data;
        G_SERVICE_LIST_LENGTH = data.items.length;

        resourceListMoreBtnDisplay('<%= Constants.REMAIN_ITEM_COUNT_KEY %>', data, G_SERVICE_MORE_BTN_ID);
        setServiceList("");
        procViewLoading('hide');
    };


    // SET LIST
    var setServiceList = function (searchKeyword) {
        procViewLoading('show');

        var resultArea = $('#resultArea');
        var resultHeaderArea = $('#resultHeaderArea');
        var noResultArea = $('#noResultArea');
        var resultTable = $('#resultTable');

        var itemsMetadata,
            itemsSpec,
            serviceName,
            selector,
            namespace,
            endpointsPreString,
            nodePort,
            specPortsList,
            specPortsListLength,
            endpointWithSpecPort,
            endpointWithNodePort,
            endpointProtocol;

        var items = G_SERVICE_LIST.items;
        var listLength = items.length;
        var endpoints = "";
        var checkListCount = 0;
        var selectorList = [];
        var htmlString = [];

        for (var i = 0; i < listLength; i++) {
            itemsMetadata = items[i].metadata;
            itemsSpec = items[i].spec;
            serviceName = itemsMetadata.name;

            if ((nvl(searchKeyword) === "") || serviceName.indexOf(searchKeyword) > -1) {
                selector = procSetSelector(itemsSpec.selector);
                namespace = itemsMetadata.namespace;
                endpointsPreString = (namespace === 'default') ? serviceName : serviceName + "." + namespace;
                endpointsPreString += ":";
                specPortsList = itemsSpec.ports;

                if (nvl(specPortsList) !== '') {
                    specPortsListLength = specPortsList.length;

                    for (var j = 0; j < specPortsListLength; j++) {
                        nodePort = nvl(specPortsList[j].nodePort, '0');
                        endpointProtocol = specPortsList[j].protocol;
                        endpointWithSpecPort = endpointsPreString + specPortsList[j].port + " " + endpointProtocol;
                        endpointWithNodePort = endpointsPreString + nodePort + " " + endpointProtocol;

                        endpoints += '<p>' + endpointWithSpecPort + '</p>' + '<p>' + endpointWithNodePort + '</p>';
                    }
                }

                htmlString.push(
                    "<tr>"
                    + "<td><span class='green2'><i class='fas fa-check-circle'></i></span> "
                    + "<a href='javascript:void(0);' onclick='procMovePage(\"<%= Constants.CP_BASE_URL %>/services/"
                    + serviceName + "\");'>" + serviceName + "</a>"
                    + "</td>"
                    + "<td><p>" + nvl(itemsSpec.type, '-') + "</p></td>"
                    + "<td><p>" + nvl(itemsSpec.clusterIP, '-') + "</p></td>"
                    + "<td>" + nvl(endpoints, '-') + "</td>"
                    + "<td><p id='" + serviceName + "' class='tableTdToolTipFalse'>0 / 0</p></td>"
                    + "<td>" + itemsMetadata.creationTimestamp + "</td>"
                    + "</tr>");

                if (selector !== 'false') {
                    selectorList.push(selector + "||" + serviceName);
                }

                endpoints = "";
                checkListCount++;
            }
        }

        if (listLength < 1 || checkListCount < 1) {
            resultHeaderArea.hide();
            resultArea.hide();
            noResultArea.show();
        } else if (G_SERVICE_LIST_GET_FIRST == true) {
            noResultArea.hide();
            resultHeaderArea.show();
            resultArea.html(htmlString);
            resultArea.show();

            $('.headerSortFalse > td').unbind();
        } else if (G_SERVICE_LIST_GET_FIRST == false) {
            $('#resultArea tr:last').after(htmlString);
        }

        procSetToolTipForTableTd('resultArea');
        procViewLoading('hide');
        getDetailForPods(selectorList);
    };


    // GET DETAIL FOR PODS
    var getDetailForPods = function (selectorList) {
        var listLength = selectorList.length;
        var tempSelectorList,
            reqUrl;

        for (var i = 0; i < listLength; i++) {
            procViewLoading('show');
            tempSelectorList = selectorList[i].split("||");

            var param = "?selector=" +  tempSelectorList[0] ;

            reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_PODS_LIST_BY_SELECTOR_WITH_SERVICE %>"
                .replace("{cluster:.+}", CLUSTER_NAME)
                .replace("{namespace:.+}", NAME_SPACE)
                .replace("{serviceName:.+}", tempSelectorList[tempSelectorList.length - 1]) + param;

            procCallAjax(reqUrl, "GET", null, null, callbackGetDetailForPods);
        }
    };


    // CALLBACK
    var callbackGetDetailForPods = function (data) {
        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            return false;
        }

        var items = data.items;
        var listLength = items.length;
        var runningSum = 0;
        var totalSum = 0;

        for (var i = 0; i < listLength; i++) {
            if (items[i].status.phase.toLowerCase() === "running") {
                runningSum++;
            }
            totalSum++;
        }

        $('#' + data.serviceName).html(runningSum + " / " + totalSum);

        procSetToolTipForTableTd('resultArea');
        procViewLoading('hide');
    };

    // ON LOAD
    $(document.body).ready(function () {
        getServiceList(0, <%= Constants.DEFAULT_LIMIT_COUNT %>, null);
    });
</script>
<script>
    $(document).on('click', '#' + G_SERVICE_MORE_BTN_ID, function () {
        G_SERVICE_LIST_GET_FIRST = false;
        G_SERVICE_LIST_OFFSET++;
        getServiceList(G_SERVICE_LIST_OFFSET, <%= Constants.DEFAULT_LIMIT_COUNT %>, G_SERVICE_LIST_SEARCH_KEYWORD);
    });


    var searchServicesList = function (searchName) {

        searchName = searchName.trim();
        if (searchName == null || searchName.length == 0) {
            searchName = null;
        }
        G_SERVICE_LIST_GET_FIRST = true;
        G_SERVICE_LIST_SEARCH_KEYWORD = searchName;
        G_SERVICE_LIST_OFFSET = 0;
        $('#' + G_SERVICE_MORE_BTN_ID).css("display", "block");
        getServiceList(0, <%= Constants.DEFAULT_LIMIT_COUNT %>, G_SERVICE_LIST_SEARCH_KEYWORD);

    };
</script>





