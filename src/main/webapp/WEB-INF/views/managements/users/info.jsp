<%--
  Users update info
  @author hrjin
  @version 1.0
  @since 2020.10.15
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<link rel="stylesheet" href="/resources/css/cp-common.css">
<div class="update-content">
        <div class="update_wrap">
            <div class="form-icon">
                <img class="update-form-image" src="/resources/images/main/logo_56x56.png">
            </div>
            <div class="form-group">
                <input type="text" class="update-form" id="userId" placeholder="User Id" maxlength="12" value="${user.userId}" disabled>
            </div>
            <div class="form-group">
                <input type="password" class="update-form" id="password" placeholder="Password" maxlength="40">
            </div>
            <div class="form-group">
                <input type="password" class="update-form" id="passwordConfirm" placeholder="Password Confirm">
                <span class="info_box" id="chkNotice" style="font-size: medium; font-variant: small-caps;"></span>
            </div>
            <div class="form-group">
                <input type="text" class="update-form" id="email" placeholder="E-mail" maxlength="50" value="${user.email}">
            </div>
            <div class="empty-margin">
            </div>
            <div class="common-cu center">
                <button id="updateBtn" type="button" class="colors8 common-btn" style="margin-right: 20px;">수정</button>
                <button id="cancel" type="button" class="colors5 common-btn" onclick="cancelBtn();">이전</button>
            </div>
    </div>
</div>
<script type="text/javascript">

    $("#updateBtn").on('click', function () {
        var code = "<p class='account_modal_p'>회원 정보를 수정하시겠습니까?</p>";
        procSetLayerPopup('회원 정보 수정', code, '확인', '취소', 'x', 'createUpdateUserInfo()', null, null);
    });

    var createUpdateUserInfo = function () {
        $("#commonLayerPopup").modal("hide");

        var userId = $("#userId").val();
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_USERS_INFO %>".replace("{userId:.+}", userId);

        var param = {
            "userId": $("#userId").val(),
            "password": $("#password").val(),
            "email": $("#email").val()
        };

        procCallAjax(reqUrl, "PUT", JSON.stringify(param), false, callbackUpdateUserInfo);
    };


    var callbackUpdateUserInfo = function (data) {
        procAlertMessage("회원 정보 수정이 완료되었습니다. 이전 페이지로 이동합니다.");
        setTimeout(function(){procMovePage(-1)}, 1500);

    };

    var cancelBtn = function () {
        procMovePage(-1);
    };

    // ON LOAD
    $(document.body).ready(function () {
        procViewLoading('hide');
        $('#password').focus();
    });
</script>
