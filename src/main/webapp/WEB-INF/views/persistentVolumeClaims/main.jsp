<%--
  PersistentVolumeClaims main

  @author: jjy
  @version: 1.0
  @since: 2020.09.17
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<style>
    #createBtn {margin-top : -10px;}
</style>
<div class="content">
    <div class="cluster_tabs clearfix"></div>
    <div class="cluster_content01 row two_line two_view">
        <ul>
            <li>
                <jsp:include page="../common/commonCreateBtn.jsp">
                    <jsp:param name="kind" value="persistentVolumeClaims" />
                </jsp:include>
                <div class="sortable_wrap">
                    <div class="sortable_top">
                        <p>Persistent Volume Claims</p>
                        <ul class="colright_btn">
                            <li>
                                <input type="text" id="table-search-01" name="" class="table-search" placeholder="search" onkeypress="if(event.keyCode===13) {searchPvcList(this.value);}" maxlength="100" />
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
                                <col style='width:35%;'>
                                <col style='width:15%;'>
                                <col style='width:20%;'>
                            </colgroup>
                            <thead>
                            <tr id="noResultArea"><td colspan='5'><p class='service_p'>생성한 Persistent Volume Claims 이 없습니다.</p></td></tr>
                            <tr id="resultHeaderArea" class="headerSortFalse" style="display: none;">
                                <td>Name</td>
                                <td>Labels</td>
                                <td>Spec</td>
                                <td>Status</td>
                                <td>Created on</td>
                            </tr>
                            </thead>
                            <tbody id="resultArea">
                            <tr>
                                <td colspan="5"> - </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </li>
            <div><button id="pvcMoreDetailBtn" class="resourceMoreDetailBtn">더보기(More)</button></div>
        </ul>
    </div>
</div>


<script type="text/javascript">

    var G_PVC_LIST;
    var G_PVC_LIST_LENGTH;
    var G_PVC_LIST_GET_FIRST = true;
    var G_PVC_LIST_OFFSET = 0;
    var G_PVC_LIST_SEARCH_KEYWORD = null;
    var G_PVC_MORE_BTN_ID = 'pvcMoreDetailBtn';


    // GET LIST
    var getPersistentVolumeClaimsList = function(offset, limit, searchName) {
        procViewLoading('show');

        var param = makeResourceListParamQuery(offset, limit, searchName);
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_STORAGES_LIST %>"  + param;
        reqUrl = reqUrl.replace("{cluster:.+}", CLUSTER_NAME).replace("{namespace:.+}", NAME_SPACE);

        procCallAjax(reqUrl, "GET", null, null, callbackPvcGetList);
    };

    // CALLBACK
    var callbackPvcGetList = function (data) {
        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage('PersistentVolumeClaims 목록 조회에 실패하였습니다.', false);
            return false;
        }

        G_PVC_LIST = data;
        G_PVC_LIST_LENGTH = data.items.length;


        resourceListMoreBtnDisplay('<%= Constants.REMAIN_ITEM_COUNT_KEY %>', data, G_PVC_MORE_BTN_ID);
        setPvcList("");
        procViewLoading('hide');
    };

    // SET LIST
    var setPvcList = function (searchKeyword) {
        procViewLoading('show');

        var resultArea = $('#resultArea');
        var resultHeaderArea = $('#resultHeaderArea');
        var noResultArea = $('#noResultArea');
        var resultTable = $('#resultTable');

        var itemsMetadata,
            itemsSpec,
            persistentVolumeClaimName,
            selector,
            namespace,
            specResources,
            itemStatus;

        var items = G_PVC_LIST.items;
        var listLength = items.length;
        var checkListCount = 0;
        var htmlString = [];

        for(var i = 0; i < listLength; i++) {
            itemsMetadata = items[i].metadata;
            itemsSpec = items[i].spec;
            persistentVolumeClaimName = itemsMetadata.name;
            itemStatus = items[i].status;

            if ((nvl(searchKeyword) === "") || persistentVolumeClaimName.indexOf(searchKeyword) > -1) {
                selector = procSetSelector(itemsSpec.selector);
                namespace = itemsMetadata.namespace;
                specResources = itemsSpec.resources;

                var capacity = "";

                if(itemStatus.capacity != null) {
                    capacity = itemStatus.capacity.storage;
                } else {
                    capacity = '-';
                }

                var specCollection = [];

                var accessModesList = [];
                for(var j = 0; j < itemsSpec.accessModes.length; j++) {
                    accessModesList.push(itemsSpec.accessModes[j]);
                }

                specCollection.push(
                    '<p> accessMode : ' + accessModesList + '</p>'
                    + '<p> capacity : ' + capacity + '</p>'
                    + '<p> volumeName : ' + nvl(itemsSpec.volumeName, '-') + '</p>'
                    + '<p> volumeMode : ' + nvl(itemsSpec.volumeMode, '-') + '</p>'
                    + '<p> storageClassName : ' + nvl(itemsSpec.storageClassName, '-') + '</p>'
                    + '<p> claimRef : ' + nvl(persistentVolumeClaimName, '-') + "(" + nvl(itemsSpec.resources.requests.storage, '-') + ")" + '</p>'
                );

                var statusIconHtml = "";

                if(itemStatus.phase === 'Bound') {
                    statusIconHtml = "<td><span class='green2 tableTdToolTipFalse'><i class='fas fa-check-circle'></i></span> ";
                } else {
                    statusIconHtml = "<td><span class='red2 tableTdToolTipFalse'><i class='fas fa-exclamation-circle'></i></span> ";
                }


                var labels = procSetSelector(itemsMetadata.labels);

                htmlString.push(
                    '<tr>'
                    + statusIconHtml
                    + '<a href="javascript:void(0);" onclick="procMovePage(\'<%= Constants.URI_STORAGES %>/' + persistentVolumeClaimName + '\');">' + persistentVolumeClaimName + '</a></td>'
                    + '<td>' + procCreateSpans(labels) + '</td>'
                    + '<td><p>' + nvl(specCollection, '-') + '</p></td>'
                    + '<td>' + nvl(itemStatus.phase, '-') + "</td>"
                    + '<td>' + itemsMetadata.creationTimestamp + '</td>'
                    + '</tr>');

                checkListCount++;
            }
        }


        if (listLength < 1 || checkListCount < 1) {
            resultHeaderArea.hide();
            resultArea.hide();
            noResultArea.show();
        }
        else if(G_PVC_LIST_GET_FIRST == true){
            noResultArea.hide();
            resultHeaderArea.show();
            resultArea.html(htmlString);
            resultArea.show();

            $('.headerSortFalse > td').unbind();
        }
        else if(G_PVC_LIST_GET_FIRST == false) {
            $('#resultArea tr:last').after(htmlString);
        }

        procSetToolTipForTableTd('resultArea');
        procViewLoading('hide');

    };


    // ON LOAD
    $(document.body).ready(function () {
        getPersistentVolumeClaimsList(0, <%= Constants.DEFAULT_LIMIT_COUNT %>, null);
    });

</script>
<script>


    $(document).on("click", "#"+ G_PVC_MORE_BTN_ID, function(){
        G_PVC_LIST_GET_FIRST = false;
        G_PVC_LIST_OFFSET++;
        getPersistentVolumeClaimsList(G_PVC_LIST_OFFSET, <%= Constants.DEFAULT_LIMIT_COUNT %>, G_PVC_LIST_SEARCH_KEYWORD);
    });

    var searchPvcList = function (searchName) {

        searchName = searchName.trim();
        if (searchName == null || searchName.length == 0) {
            searchName = null;
        }
        G_PVC_LIST_GET_FIRST = true;
        G_PVC_LIST_SEARCH_KEYWORD = searchName;
        G_PVC_LIST_OFFSET = 0;

        $('#' + G_PVC_MORE_BTN_ID).css("display", "block");
        getPersistentVolumeClaimsList(0, <%= Constants.DEFAULT_LIMIT_COUNT %>, G_PVC_LIST_SEARCH_KEYWORD);

    };
</script>

