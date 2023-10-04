<%--
  Users role config page

  @author hrjin
  @version 1.0
  @since 2020.10.05
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="org.container.platform.web.user.common.Constants" %>
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
                            <tr id="noResultArea" style="display: none;"><td colspan='4'><p class='user_p'><spring:message code="M0086" text="사용자가 존재하지 않습니다."/></p></td></tr>
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
    <button id="saveBtn" type="button" class="colors8 common-btn" style="margin-right: 20px; display: none;"><spring:message code="M0030" text="저장"/></button>
    <button id="cancel" type="button" class="colors5 common-btn" style="display: none;" onclick="cancelBtn();"><spring:message code="M0028" text="이전"/></button>
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

        var f_srch_users = '사용자 목록 조회에 실패하였습니다.';
        var s_msg_f_srch_users= '<spring:message code="M0087" arguments='arg_f_srch_users' javaScriptEscape="true" text="사용자 목록 조회에 실패하였습니다."/>';
        s_msg_f_srch_users_lang = s_msg_f_srch_users.replace('arg_f_srch_users', f_srch_users);

        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage(s_msg_f_srch_users_lang, false);
            return false;
        }

        $("#saveBtn").css( "display", "inline");
        $("#cancel").css( "display", "inline");
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

            for(var j = 0; j < G_ROLES_LIST.length; j++) {
                var roleName = G_ROLES_LIST[j].metadata.name;
                 if((NAME_SPACE === G_USERS_LIST[i].cpNamespace) && (roleName === G_USERS_LIST[i].roleSetCode)) {
                    selectRole  += "<option selected name='roleSelect' value='" + roleName + "'" + "id='role" + j + "'>" + roleName + "</option>";
                } else {
                    selectRole  += "<option name='roleSelect' value='" + roleName + "'" + "id='role" + j + "'>" + roleName + "</option>";
                }
            }


            var selectboxHtml =  "<td><select class='roleList'>" + selectRole + "</select></td>";

            if((NAME_SPACE === G_USERS_LIST[i].cpNamespace) && (NAMESPACE_ADMIN === G_USERS_LIST[i].userType)) {
                var selectboxHtml = "<td><select disabled='disabled' class='roleList'>" + selectRole + "</select></td>";
            }

            htmlString.push(
                "<tr>"
                + "<td>" + checkBox + "</td>"
                + "<td class='userId'>" + G_USERS_LIST[i].userId + "</td>"
                + "<td>" + G_USERS_LIST[i].serviceAccountName + "</td>"
                +  selectboxHtml
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

        var change_user_per = '사용자 권한 변경';
        var cfm = '확인';
        var cxl ='취소';
        var q_change_user_per = '사용자 권한 변경을 저장하시겠습니까?';

        var s_msg_change_user_per = '<spring:message code="M0089" arguments='arg_change_user_per' javaScriptEscape="true" text="사용자 권한 변경"/>';
        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';
        var s_msg_cxl = '<spring:message code="M0029" arguments='arg_cxl' javaScriptEscape="true" text="취소"/>';
        var s_msg_q_change_user_per = '<spring:message code="M0088" arguments='arg_q_change_user_per' javaScriptEscape="true" text="사용자 권한 변경을 저장하시겠습니까?"/>';

        s_msg_change_user_per_lang = s_msg_change_user_per.replace('arg_change_user_per', change_user_per);
        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);
        s_msg_cxl_lang = s_msg_cxl.replace('arg_cxl', cxl);
        s_msg_q_change_user_per_lang = s_msg_q_change_user_per.replace('arg_q_change_user_per', q_change_user_per);

        var code = "<p class='account_modal_p'>" + s_msg_q_change_user_per_lang + "</p>";
        procSetLayerPopup(s_msg_change_user_per_lang, code, s_msg_cfm_lang, s_msg_cxl_lang, 'x', 'updateUserConfig()', null, null);
    });


    var updateUserConfig = function () {
        $("#commonLayerPopup").modal("hide");

        var checkbox = $('input[name="checkbox_name"]:checked');

        var userId ="";
        var sa = "";
        var role = "";
        var array = [];
        var map = new Map();

        checkbox.each(function(i) {
            var tr = checkbox.parent().parent().eq(i);
            var td = tr.children();

            userId = td.eq(1).text();
            sa = td.eq(2).text();
            role = td.eq(3).find('select :selected').text();

            map = makeMap(userId, sa, role);
            array.push(map);
        });

        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_USERS_CONFIG %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", NAME_SPACE);

        procCallAjax(reqUrl, "PUT", JSON.stringify(array), false, callbackUpdateUserConfig);
    };


    var callbackUpdateUserConfig = function (data) {

        var change_per = '권한 변경';
        var cfm = '확인';
        var resultCode = '권한이 정상적으로 변경되었습니다.';
        var failResultCode = '권한 변경을 실패하였습니다.';

        var s_msg_change_per = '<spring:message code="M0092" arguments='arg_change_per' javaScriptEscape="true" text="권한 변경"/>';
        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';
        var s_msg_resultCode = '<spring:message code="M0090" arguments='arg_resultCode' javaScriptEscape="true" text="권한이 정상적으로 변경되었습니다."/>';
        var s_msg_failResultCode = '<spring:message code="M0091" arguments='arg_failResultCode' javaScriptEscape="true" text="권한 변경을 실패하였습니다."/>';

        s_msg_change_per_lang = s_msg_change_per.replace('arg_change_per', change_per);
        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);
        s_msg_resultCode_lang = s_msg_resultCode.replace('arg_resultCode', resultCode);
        s_msg_failResultCode_lang = s_msg_failResultCode.replace('arg_failResultCode', failResultCode);


        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage(s_msg_failResultCode_lang);
            return false;
        } else {
            var nextActionUrl = data.nextActionUrl;
            procViewLoading('hide');
            procSetLayerPopup(s_msg_change_per_lang, s_msg_resultCode_lang, s_msg_cfm_lang, null, 'x', 'procMovePage("' + nextActionUrl + '")', null, null);

        }
    };

    var makeMap = function (userId, sa, roleName) {
        var param = {
            "userId": userId,
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
