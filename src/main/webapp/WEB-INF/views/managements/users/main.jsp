<%--
  Users main

  @author hrjin
  @version 1.0
  @since 2020.10.05
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<div class="content">
    <div class="cluster_tabs_user_config clearfix" style="width: 100%">
        <button id="createBtn" type="button" class="colors4 common-btn pull-right" onclick="updateUsersRolePage();"><p class='user_p'><spring:message code="M0103" text="설정"/></button>
    </div>
    <div class="cluster_content01 row two_line two_view">
        <ul>
            <li>
                <div class="sortable_wrap">
                    <div class="sortable_top user">
                        <p>Users</p>
                    </div>
                    <div class="view_table_wrap">
                        <div class="table-search-wrap">
                            <input type="text" id="table-search-01" name="" class="table-search user" placeholder="User ID" onkeypress="if(event.keyCode===13) {setUsersList(this.value);}" />
                            <button name="button" class="btn" id="userSearchBtn" type="button">
                                <i class="fas fa-search"></i>
                            </button>
                        </div>
                        <table class="table_event condition alignL user">
                            <colgroup>
                                <col style='width:auto;'>
                                <col style='width:25%;'>
                                <col style='width:15%;'>
                                <col style='width:20%;'>
                                <col style='width:20%;'>
                            </colgroup>
                            <thead>
                            <tr id="noResultArea" style="display: none;"><td colspan='4'><p class='user_p'><spring:message code="M0086" text="사용자가 존재하지 않습니다."/></p></td></tr>
                            <tr id="resultHeaderArea">
                                <td>User ID</td>
                                <td>Role</td>
                                <td>Authority</td>
                                <td>Created Time</td>
                                <td>Modified Time</td>
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

<script type="text/javascript">
    var G_USERS_LIST;

    var nsAdminAuth = "<%= Constants.NAMESPACE_ADMIN %>";

    // GET LIST
    var getUsersList = function() {
        procViewLoading('show');
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_USERS_LIST_BY_NAMESPACE %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{namespace:.+}", NAME_SPACE);

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

        G_USERS_LIST = data.items;

        procViewLoading('hide');
        setUsersList("");
    };

    // SET LIST
    var setUsersList = function(searchKeyword) {
        procViewLoading('show');
        var userId;

        var resultArea = $('#resultArea');
        var resultHeaderArea = $('#resultHeaderArea');
        var noResultArea = $('#noResultArea');

        var items = G_USERS_LIST;
        var listLength = items.length;

        var checkListCount = 0;
        var htmlString = [];


        for (var i = 0; i < listLength; i++) {
            userId = items[i].userId;

            var authority = "User";
            if(items[i].userType === nsAdminAuth) {
                authority = "Administrator";
            }

            if ((nvl(searchKeyword) === "") || userId.indexOf(searchKeyword) > -1) {
                htmlString.push(
                    "<tr>"
                    + "<td class='userId'>" + items[i].userId + "</td>"
                    + "<td>" + items[i].roleSetCode + "</td>"
                    + "<td>" + authority + "</td>"
                    + "<td>" + items[i].created + "</td>"
                    + "<td>" + items[i].lastModified + "</td>"
                    + "</tr>");

                checkListCount++;
            }
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

        procViewLoading('hide');
    };

    // BIND (SERACH USER BUTTON)
    $("#userSearchBtn").on("click", function () {
        var keyword = $("#table-search-01").val();
        setUsersList(keyword);
    });


    var updateUsersRolePage = function() {
      var namespaceAdminType = "<%= Constants.NAMESPACE_ADMIN %>";
      var userType = current_user_type;
      var resultCode = '접근할 수 없는 권한입니다.';
        var s_msg_resultCode= '<spring:message code="M0104" arguments='arg_resultCode' javaScriptEscape="true" text="접근할 수 없는 권한입니다."/>';
        s_msg_resultCode_lang = s_msg_resultCode.replace('arg_resultCode', resultCode);

      if(userType === namespaceAdminType) {
          procMovePage("<%= Constants.URI_USERS_CONFIG %>");
      } else {
          procAlertMessage(s_msg_resultCode_lang);
      }

    };

    // ON LOAD
    $(document.body).ready(function () {
        getUsersList();
    });

</script>
