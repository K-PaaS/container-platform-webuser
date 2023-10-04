<%--
  Users update info

  @author hrjin
  @version 1.0
  @since 2020.10.15
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="org.container.platform.web.user.common.Constants" %>
<link rel="stylesheet" href="/resources/css/cp-common.css">
<div class="update-content">
        <div class="update_wrap" style="border: solid 2px; border-color:#e7eaec;">
            <div class="form-icon">
                <img  src="/resources/images/main/logo_60x48.png" style ="display: block; margin: 0 auto 30px;">
            </div>
            <div class="form-group">
                <input type="text" class="update-form" id="userId" placeholder="User Id" maxlength="12" value="${user.userId}" disabled>
            </div>
            <div class="form-group">
                <input type="password" class="update-form" id="currentPassword" placeholder="Current password" maxlength="40">
            </div>
            <div class="form-group">
                <input type="password" class="update-form" id="password" placeholder="New password" maxlength="40">
            </div>
            <div class="form-group">
                <input type="password" class="update-form" id="passwordConfirm" placeholder="Confirm password">
                <span class="update_info_box" id="chkNotice" style="font-size: large; font-variant: small-caps;"></span>
            </div>
            <div class="form-group">
                <input type="text" class="update-form" id="email" placeholder="E-mail" maxlength="50" value="${user.email}">
            </div>
            <div class="form-group">
                <input type="text" class="update-form" id="desc" placeholder="Description" maxlength="50" value="${user.description}">
            </div>
            <div class="empty-margin">
            </div>
            <div class="common-cu center">
                <button id="updateBtn" type="button" class="colors8 common-btn" style="margin-right: 20px;"><spring:message code="M0025" text="수정"/></button>
                <button id="cancel" type="button" class="colors5 common-btn" onclick="cancelBtn();"><spring:message code="M0028" text="이전"/></button>
            </div>
    </div>
</div>
<script type="text/javascript">

    $("#updateBtn").on('click', function () {

        var edit_mbsh_info = '회원 정보 수정';
        var cfm = '확인';
        var cxl ='취소';
        var q_edit_mbsh_info = '회원 정보를 수정하시겠습니까?';

        var s_msg_edit_mbsh_info = '<spring:message code="M0099" arguments='arg_edit_mbsh_info' javaScriptEscape="true" text="회원 정보 수정"/>';
        var s_msg_cfm = '<spring:message code="M0022" arguments='arg_cfm' javaScriptEscape="true" text="확인"/>';
        var s_msg_cxl = '<spring:message code="M0029" arguments='arg_cxl' javaScriptEscape="true" text="취소"/>';
        var s_msg_q_edit_mbsh_info = '<spring:message code="M0098" arguments='arg_q_edit_mbsh_info' javaScriptEscape="true" text="회원 정보를 수정하시겠습니까?"/>';

        s_msg_edit_mbsh_info_lang = s_msg_edit_mbsh_info.replace('arg_edit_mbsh_info', edit_mbsh_info);
        s_msg_cfm_lang = s_msg_cfm.replace('arg_cfm', cfm);
        s_msg_cxl_lang = s_msg_cxl.replace('arg_cxl', cxl);
        s_msg_q_edit_mbsh_info_lang = s_msg_q_edit_mbsh_info.replace('arg_q_edit_mbsh_info', q_edit_mbsh_info);

        var code = "<p class='account_modal_p'>"+ s_msg_q_edit_mbsh_info_lang +"</p>";
        procSetLayerPopup(s_msg_edit_mbsh_info_lang, code, s_msg_cfm_lang, s_msg_cxl_lang, 'x', 'validatePassword()', null, null);
    });

    var validatePassword = function () {
        var userId = $("#userId").val();
        var password = $("#currentPassword").val();

        var loginObj = {userId: userId, password: password};
        var loginJson = JSON.stringify(loginObj);

        var reqUrl = "<%= Constants.URI_USERS_VERIFY_PASSWORD %>";

       procCallAjax(reqUrl, "POST", loginJson, false, createUpdateUserInfo);
    };

    var createUpdateUserInfo = function () {
        $("#commonLayerPopup").modal("hide");

        var userId = $("#userId").val();
        var reqUrl = "<%= Constants.API_URL %><%= Constants.URI_API_USERS_INFO %>"
            .replace("{cluster:.+}", CLUSTER_NAME)
            .replace("{userId:.+}", userId);

        var param = {
            "userId": $("#userId").val(),
            "password": $("#password").val(),
            "passwordConfirm": $("#passwordConfirm").val(),
            "email": $("#email").val(),
            "description": $("#desc").val()
        };

        procCallAjax(reqUrl, "PUT", JSON.stringify(param), false, callbackUpdateUserInfo);
    };


    var callbackUpdateUserInfo = function (data) {

        var edited_mbsh_info = '회원 정보 수정이 완료되었습니다. 이전 페이지로 이동합니다.';

        var s_msg_edited_mbsh_info= '<spring:message code="M0100" arguments='arg_edited_mbsh_info' javaScriptEscape="true" text="회원 정보 수정이 완료되었습니다. 이전 페이지로 이동합니다."/>';
        s_msg_edited_mbsh_info_lang = s_msg_edited_mbsh_info.replace('arg_edited_mbsh_info', edited_mbsh_info);

        procAlertMessage(s_msg_edited_mbsh_info_lang);
        setTimeout(function(){procMovePage(-1)}, 1500);

    };

    var cancelBtn = function () {
        procMovePage(-1);
    };

    // ON LOAD
    $(document.body).ready(function () {
        procViewLoading('hide');
        $('#currentPassword').focus();

        $('#password').keyup(function(){
            $('#chkNotice').html('');
        });

        $('#passwordConfirm').keyup(function(){

            var f_pwd = '비밀번호가 일치하지 않습니다.';
            var cwm_pwd = '비밀번호가 일치합니다.';

            var s_msg_f_pwd= '<spring:message code="M0101" arguments='arg_f_pwd' javaScriptEscape="true" text="비밀번호가 일치하지 않습니다."/>';
            var s_msg_cwm_pwd= '<spring:message code="M0102" arguments='arg_cwm_pwd' javaScriptEscape="true" text="비밀번호가 일치합니다."/>';

            s_msg_f_pwd_lang = s_msg_f_pwd.replace('arg_f_pwd', f_pwd);
            s_msg_cwm_pwd_lang = s_msg_cwm_pwd.replace('arg_cwm_pwd', cwm_pwd);

            if($('#password').val() != $('#passwordConfirm').val()){
                $('#chkNotice').html(s_msg_f_pwd_lang + '<br>');
                $('#chkNotice').attr('style', 'color: #f82a2aa3');
                $('.update_info_box').show();

            } else{
                $('#chkNotice').html(s_msg_cwm_pwd_lang);
                $('#chkNotice').attr('style', 'color: #199894b3');
                $('.update_info_box').show();
            }

        });
    });
</script>
