<%--
  Users main
  @author hrjin
  @version 1.0
  @since 2020.10.05
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<div class="content">
    <div class="cluster_tabs_user_config clearfix">
        <button id="createBtn" type="button" class="colors4 common-btn pull-right" onclick="updateUsersRolePage();">설정</button>
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
                                <col style='width:25%;'>
                                <col style='width:25%;'>
                            </colgroup>
                            <thead>
                            <tr id="noResultArea" style="display: none;"><td colspan='4'><p class='user_p'>사용자가 존재하지 않습니다.</p></td></tr>
                            <tr id="resultHeaderArea">
                                <td>User ID</td>
                                <td>권한</td>
                                <td>생성일</td>
                                <td>수정일</td>
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
        if (!procCheckValidData(data)) {
            procViewLoading('hide');
            procAlertMessage('사용자 목록 조회에 실패하였습니다.', false);
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

            if ((nvl(searchKeyword) === "") || userId.indexOf(searchKeyword) > -1) {
                htmlString.push(
                    "<tr>"
                    + "<td class='userId'>" + items[i].userId + "</td>"
                    + "<td>" + items[i].roleSetCode + "</td>"
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

      if(userType === namespaceAdminType) {
          procMovePage("<%= Constants.URI_USERS_CONFIG %>");
      } else {
          procAlertMessage(resultCode);
      }

    };

    // ON LOAD
    $(document.body).ready(function () {
        getUsersList();
    });

</script>
