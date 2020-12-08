<%--
  Users role config page

  @author hrjin
  @version 1.0
  @since 2020.10.05
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<div class="content">
    <div class="cluster_tabs clearfix"></div>
    <div class="cluster_content01 row two_line two_view">
        <ul>
            <li>
                <div class="sortable_wrap">
                    <div class="sortable_top user">
                        <p>User Role Config</p>
                    </div>
                    <div class="view_table_wrap">
                        <table class="table_event condition alignL user">
                            <colgroup>
                                <col style='width:5%;'>
                                <col style='width:20%;'>
                                <col style='width:20%;'>
                                <col style='width:auto;'>
                                <col style='width:18%;'>
                            </colgroup>
                            <thead>
                            <tr id="noResultArea" style="display: none;"><td colspan='4'><p class='user_p'>사용자가 존재하지 않습니다.</p></td></tr>
                            <tr id="resultHeaderArea">
                                <td></td>
                                <td>User ID</td>
                                <td>Service Account</td>
                                <td>Role</td>
                                <td>Created Time</td>
                            </tr>
                            </thead>
                            <tbody id="resultArea">
                            </tbody>
                        </table>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
<div class="common-cu center">
    <button id="saveBtn" type="button" class="colors8 common-btn" style="margin-right: 20px;">저장</button>
    <button id="cancel" type="button" class="colors5 common-btn" onclick="cancelBtn();">이전</button>
</div>

<script type="text/javascript">
    var G_USERS_LIST;
    var G_ROLES_LIST;
    var ROLE_SELECT_BOX_HTML;
    var NOT_ASSIGNED_ROLE = "<%= Constants.NOT_ASSIGNED_ROLE%>";
    var NAMESPACE_ADMIN = "<%= Constants.NAMESPACE_ADMIN%>";
    var DEFAULT_ADMIN_ROLE = "<%= Constants.DEFAULT_ADMIN_ROLE%>";

    // GET LIST
    var getUsersList = function() {
        procViewLoading('show');
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_USERS_LIST %>" + "?namespace=" + NAME_SPACE;
        reqUrl = reqUrl.replace("{cluster:.+}", CLUSTER_NAME);

        procCallAjax(reqUrl, "GET", null, null, callbackGetUsersList);
    };


    // CALLBACK
    var callbackGetUsersList = function(data) {
        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage('사용자 목록 조회에 실패하였습니다.', false);
            return false;
        }

        G_USERS_LIST = data.items;

        procViewLoading('hide');
        setUsersList();
    };

    // SET LIST
    var setUsersList = function() {

        var resultArea = $('#resultArea');
        var resultHeaderArea = $('#resultHeaderArea');
        var noResultArea = $('#noResultArea');

        var listLength = G_USERS_LIST.length;

        var checkListCount = 0;
        var htmlString = [];

        var checkBox = '';

        for (var i = 0; i < listLength; i++) {
            if((NAME_SPACE === G_USERS_LIST[i].cpNamespace) && (NAMESPACE_ADMIN === G_USERS_LIST[i].userType)) {
                checkBox = "<input type='checkbox' name='checkbox_name' style='opacity: 1; position: static' checked disabled>"
            } else if(NAME_SPACE === G_USERS_LIST[i].cpNamespace) {
                checkBox = "<input type='checkbox' name='checkbox_name' style='opacity: 1; position: static' checked>"
            } else {
                checkBox = "<input type='checkbox' name='checkbox_name' style='opacity: 1; position: static'>"
            }

            var selectRole = "";

            if((NAME_SPACE === G_USERS_LIST[i].cpNamespace) && (NAMESPACE_ADMIN === G_USERS_LIST[i].userType) && (DEFAULT_ADMIN_ROLE === G_USERS_LIST[i].roleSetCode)) {
                selectRole = "<select disabled='disabled' class='defaultNsRole'><option selected>" + G_USERS_LIST[i].roleSetCode + "</option>";
            } else {
                selectRole = "<select class='roleList'><option>" + NOT_ASSIGNED_ROLE + "</option>";

                for(var j = 0; j < G_ROLES_LIST.length; j++) {
                    var defaultAdminRole = "<%= Constants.DEFAULT_ADMIN_ROLE %>";
                    var roleName = G_ROLES_LIST[j].metadata.name;

                    if(defaultAdminRole !== roleName) {
                        if(roleName === G_USERS_LIST[i].roleSetCode) {
                            selectRole  += "<option selected name='roleSelect' value='" + roleName + "'" + "id='role" + j + "'>" + roleName + "</option>";
                        } else {
                            selectRole  += "<option name='roleSelect' value='" + roleName + "'" + "id='role" + j + "'>" + roleName + "</option>";
                        }
                    }
                }
            }

            htmlString.push(
                "<tr>"
                + "<td>" + checkBox + "</td>"
                + "<td class='userId'>" + G_USERS_LIST[i].userId + "</td>"
                + "<td>" + G_USERS_LIST[i].serviceAccountName + "</td>"
                + "<td>" + selectRole + "</select></td>"
                + "<td>" + G_USERS_LIST[i].created + "</td>"
                + "</td>"
                + "</tr>");

            checkListCount++;

        }

        if (listLength < 1 || checkListCount < 1) {
            resultHeaderArea.hide();
            resultArea.hide();
            noResultArea.show();
        } else {
            noResultArea.hide();
            resultHeaderArea.show();
            resultArea.show();
            resultArea.html(htmlString);
        }

    };

    var getRolesList = function() {
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_ROLES_LIST %>".replace("{cluster:.+}", CLUSTER_NAME).replace("{namespace:.+}", NAME_SPACE);
        procCallAjax(reqUrl, "GET", null, null, callbackGetRolesList);
    };

    var callbackGetRolesList = function (data) {
        G_ROLES_LIST = data.items;
        procViewLoading('hide');
    };


    // User namespace include/exclude
    $("#saveBtn").on('click', function () {
        var code = "<p class='account_modal_p'>사용자 권한 변경을 저장하시겠습니까?</p>";
        procSetLayerPopup('사용자 권한 변경', code, '확인', '취소', 'x', 'updateUserConfig()', null, null);
    });


    var updateUserConfig = function () {
        $("#commonLayerPopup").modal("hide");

        var checkbox = $('input[name="checkbox_name"]:checked');

        var sa = "";
        var role = "";
        var array = [];
        var map = new Map();

        checkbox.each(function(i) {
            var tr = checkbox.parent().parent().eq(i);
            var td = tr.children();

            sa = td.eq(2).text();
            role = td.eq(3).find('select :selected').text();

            map = makeMap(sa, role);
            array.push(map);
        });

        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_USERS_CONFIG %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", NAME_SPACE);

        procCallAjax(reqUrl, "PUT", JSON.stringify(array), false, callbackUpdateUserConfig);
    };


    var callbackUpdateUserConfig = function (data) {
        var resultCode = '권한이 정상적으로 변경되었습니다.';

        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            resultCode = '권한 변경을 실패하였습니다.';
            procAlertMessage(resultCode);
            return false;
        } else {
            var nextActionUrl = data.nextActionUrl;
            procViewLoading('hide');
            procSetLayerPopup('권한 변경', resultCode, '확인', null, 'x', 'procMovePage("' + nextActionUrl + '")', null, null);

        }
    };

    var makeMap = function (sa, roleName) {
        var param = {
            "serviceAccountName": sa,
            "roleSetCode": roleName
        };

        return param;
    };

    var cancelBtn = function () {
        procMovePage(-1);
    };

    // ON LOAD
    $(document.body).ready(function () {
        // k8s에서 롤 목록 조회 (Get Roles List from k8s)
        getRolesList();

        // DB에 있는 전체 user 목록 조회 (Get all user List from DB)
        getUsersList();

    });

</script>
