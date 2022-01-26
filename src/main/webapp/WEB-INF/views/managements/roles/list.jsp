<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Roles List View

  @author kjhoon
  @version 1.0
  @since 2020.10.13
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<div class="sortable_wrap">
    <div class="sortable_top">
        <p>Roles</p>
        <ul class="colright_btn">
            <li>
                <input type="text" id="table-search-01" name="" class="table-search" placeholder="search"
                       onkeypress="if(event.keyCode===13) {searchRolesList(this.value);}" maxlength="100"/>
                <button name="button" class="btn table-search-on" type="button">
                    <i class="fas fa-search"></i>
                </button>
            </li>
        </ul>
    </div>
    <div class="view_table_wrap">
        <table class="table_event condition alignL" id="resultRolesTable">
            <colgroup>
                <col style="width:auto;">
                <col style="width:25%;">
                <col style="width:25%;">
                <col style="width:25%;">
            </colgroup>
            <thead>
            <tr id="noResultRolesArea" style="display: none;">
                <td colspan='4'><p class='service_p'><spring:message code="M0082" text="실행 중인 Roles가 없습니다."/></p></td>
            </tr>
            <tr id="resultRolesHeaderArea" class="headerSortFalse">
                <td>Name</td>
                <td>Labels</td>
                <td>Annotations</td>
                <td>Created on</td>
            </tr>
            </thead>
            <tbody id="rolesListArea"></tbody>
        </table>
    </div>
    <div class="resourceMoreDetailWrapDiv">
        <button id="rolesMoreDetailBtn" class="resourceMoreDetailBtn"><spring:message code="M0056" text="더보기(More)"/></button></div>
</div>


<script type="text/javascript">

    var G_ROLES_LIST;
    var G_ROLES_LIST_LENGTH;
    var G_DEV_CAHRT_RUNNING_CNT = 0;
    var G_DEV_CHART_FAILED_CNT = 0;
    var G_DEV_CHART_SUCCEEDEDCNT = 0;
    var G_DEV_CHART_PENDDING_CNT = 0;
    

    var G_ROLES_LIST_GET_FIRST = true;
    var G_ROLES_LIST_OFFSET = 0;
    var G_ROLES_LIST_SEARCH_KEYWORD = null;
    var G_ROLES_MORE_BTN_ID = 'rolesMoreDetailBtn';
    
    
    var getRolesList = function (offset, limit, searchName) {
        procViewLoading('show');
        var param = makeResourceListParamQuery(offset, limit, searchName);
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_ROLES_LIST %>" + param;
        reqUrl = reqUrl.replace("{cluster:.+}", CLUSTER_NAME).replace("{namespace:.+}", NAME_SPACE);

        procCallAjax(reqUrl, "GET", null, null, callbackgetRolesList);

    };

    // CALLBACK
    var callbackgetRolesList = function (data) {
        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage('Roles 목록 조회에 실패하였습니다.', false);
            return false;
        }

        G_ROLES_LIST = data;
        G_ROLES_LIST_LENGTH = data.items.length;

        resourceListMoreBtnDisplay('<%= Constants.REMAIN_ITEM_COUNT_KEY %>', data, G_ROLES_MORE_BTN_ID);
        setRolesList("");
        procViewLoading('hide');

    };

    var setRolesList = function (searchKeyword) {
        procViewLoading('show');

        var resultArea = $('#rolesListArea');
        var resultHeaderArea = $('#resultRolesHeaderArea');
        var noResultArea = $('#noResultRolesArea');
        var resultTable = $('#resultRolesTable');

        var checkListCount = 0;
        var htmlString = [];

        $.each(G_ROLES_LIST.items, function (index, itemList) {
            var metadata = itemList.metadata;
            var rules = itemList.rules;
            var roleName = metadata.name;

            if ((nvl(searchKeyword) === "") || roleName.indexOf(searchKeyword) > -1) {
                var namespace = metadata.namespace;

                // 라벨이 없는 경우도 있음 (Without labels)
                var labels = procSetSelector(metadata.labels);
                var annotations = procSetAnnotations(metadata.annotations);
                var creationTimestamp = metadata.creationTimestamp;

                htmlString.push('<tr>' +
                    '<td>' + "<a href='javascript:void(0);' onclick='procMovePage(\"<%= Constants.URI_ROLES %>/" + roleName + "\");'>" + roleName + '</a>' + '</td>' +
                    '<td>' + procCreateSpans(labels, "LB") + '</td>' +
                    '<td style="white-space: normal !important;">' + annotations + '</td>' +
                    '<td>' + creationTimestamp + '</td>' +
                    '</tr>');

                checkListCount++;
            }
        });

        if (G_ROLES_LIST_LENGTH < 1 || checkListCount < 1) {
            resultHeaderArea.hide();
            resultArea.hide();
            noResultArea.show();
        }
        else if(G_ROLES_LIST_GET_FIRST == true) {
            noResultArea.hide();
            resultHeaderArea.show();
            resultArea.html(htmlString);
            resultArea.show();

            $('.headerSortFalse > td').unbind();
        }

        else if(G_ROLES_LIST_GET_FIRST == false) {
            $('#rolesListArea tr:last').after(htmlString);
        }
        procSetToolTipForTableTd('resultRolesTable');
        procViewLoading('hide');

    };

</script>

<script>


    $(document).on("click", "#"+ G_ROLES_MORE_BTN_ID, function(){
        G_ROLES_LIST_GET_FIRST = false;
        G_ROLES_LIST_OFFSET++;
        getRolesList(G_ROLES_LIST_OFFSET, <%= Constants.DEFAULT_LIMIT_COUNT %>, G_ROLES_LIST_SEARCH_KEYWORD);
    });


    var searchRolesList = function (searchName) {

        searchName = searchName.trim();
        if (searchName == null || searchName.length == 0) {
            searchName = null;
        }
        G_ROLES_LIST_GET_FIRST = true;
        G_ROLES_LIST_SEARCH_KEYWORD = searchName;
        G_ROLES_LIST_OFFSET = 0;
        $("#" + G_ROLES_MORE_BTN_ID).css("display", "block");
        getRolesList(0, <%= Constants.DEFAULT_LIMIT_COUNT %>, G_ROLES_LIST_SEARCH_KEYWORD);

    };
</script>