<%--
  Sign Up page

  @author hrjin
  @version 1.0
  @since 2020.09.22
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ include file="../common/alert_new.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sign Up</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel='stylesheet' type='text/css' href='<c:url value="/resources/css/cp-common.css"/>'>
    <link rel='stylesheet' type='text/css' href='<c:url value="/resources/css/gspinner.min.css"/>'>
    <link rel="stylesheet" href="/resources/css/style-signUp.css">
</head>
<body>
<div class="registration-form">
    <form name="frm" action="/register" method="post" enctype="text/plain">
        <div class="form-icon">
            <img  src="/resources/images/main/logo_60x48.png" style="margin-bottom: 10px">
            <h4 style="margin-top: 20px;">Container Platform Sign Up</h4>
        </div>
        <div class="form-group">
            <input type="text" class="form-control item" id="userId" placeholder="User Id">
            <span style="margin-top: 2px; margin-left:15px; color: blue; font-size: x-small; font-variant: small-caps;">* <spring:message code="M0127" text="User ID는 시작과 끝이 영(소문자)/숫자로 253자 이하, 특수문자는 '-' 또는 '.'만 사용 가능"/></span>
        </div>
        <div class="form-group">
            <input type="password" class="form-control item" id="password" placeholder="Password" maxlength="40">
            <span style="margin-top: 2px; margin-left:15px; color: blue; font-size: x-small; font-variant: small-caps;"> * <spring:message code="M0128" text="Password 는 4~40자 이하로 영문으로 시작, 최소 하나 이상의 숫자와 특수 문자 혼합"/></span>
        </div>
        <div class="form-group">
            <input type="password" class="form-control item" id="passwordConfirm" placeholder="Password Confirm">
            <span class="info_box" id="chkNotice" style="font-size: medium; font-variant: small-caps;"></span>
        </div>
        <div class="form-group">
            <input type="text" class="form-control item" id="email" placeholder="E-mail" maxlength="50">
        </div>
        <div class="form-group">
            <button type="button" class="btn btn-size register" id="registerBtn">Register</button>
            <button type="button" class="btn btn-size cancel" id="cancelBtn">Cancel</button>
        </div>
    </form>
</div>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
<script type="text/javascript" src="/resources/js/cp-common.js"></script>
<script type="text/javascript" src='<c:url value="/resources/js/g-spinner.min.js"/>'></script>
</body>
</html>
<script type="text/javascript">

    $('#registerBtn').click(function() {

        var userId = $("#userId").val();
        var password = $("#password").val();
        var email = $("#email").val();
        var passwordConfirm = $("#passwordConfirm").val();

        var manCheck = false;
        var msg = "";

        var ent_id = 'Pods 목록 조회에 실패하였습니다.';
        var condition_id = 'User ID는 최대 253자 내의 영문 소문자 또는 숫자로 시작하고 끝나야 하며, 특수문자는 - 또는 . 만 사용 가능합니다.';
        var ent_pwd = '비밀번호를 입력하세요.';
        var condition_pwd = '비밀번호는 영문으로 시작하고, 최소 하나 이상의 숫자와 특수 문자를 혼합하여 4~40자 이내로 사용 가능합니다.';
        var cfm_pwd = '비밀번호를 확인해주세요.';
        var more_cfm_pwd = '비밀번호를 한 번 더 확인해주세요.';
        var ent_eamil = '메일 주소를 입력하세요.';
        var f_eamil = '이메일 형식이 올바르지 않습니다.';

        var s_msg_ent_id= '<spring:message code="M0129" arguments='arg_ent_id' javaScriptEscape="true" text="아이디를 입력하세요."/>';
        var s_msg_condition_id= '<spring:message code="M0130" arguments='arg_condition_id' javaScriptEscape="true" text="User ID는 최대 253자 내의 영문 소문자 또는 숫자로 시작하고 끝나야 하며, 특수문자는 - 또는 . 만 사용 가능합니다."/>';
        var s_msg_ent_pwd= '<spring:message code="M0131" arguments='arg_ent_pwd' javaScriptEscape="true" text="비밀번호를 입력하세요."/>';
        var s_msg_condition_pwd= '<spring:message code="M0132" arguments='arg_condition_pwd' javaScriptEscape="true" text="비밀번호는 영문으로 시작하고, 최소 하나 이상의 숫자와 특수 문자를 혼합하여 4~40자 이내로 사용 가능합니다."/>';
        var s_msg_cfm_pwd= '<spring:message code="M0133" arguments='arg_cfm_pwd' javaScriptEscape="true" text="비밀번호를 확인해주세요."/>';
        var s_msg_more_cfm_pwd= '<spring:message code="M0134" arguments='arg_more_cfm_pwd' javaScriptEscape="true" text="비밀번호를 한 번 더 확인해주세요."/>';
        var s_msg_ent_eamil= '<spring:message code="M0135" arguments='arg_ent_eamil' javaScriptEscape="true" text="메일 주소를 입력하세요."/>';
        var s_msg_f_eamil= '<spring:message code="M0136" arguments='arg_f_eamil' javaScriptEscape="true" text="이메일 형식이 올바르지 않습니다."/>';

        s_msg_ent_id_lang = s_msg_ent_id.replace('arg_ent_id', ent_id);
        s_msg_condition_id_lang = s_msg_condition_id.replace('arg_condition_id', condition_id);
        s_msg_ent_pwd_lang = s_msg_ent_pwd.replace('arg_ent_pwd', ent_pwd);
        s_msg_condition_pwd_lang = s_msg_condition_pwd.replace('arg_condition_pwd', condition_pwd);
        s_msg_cfm_pwd_lang = s_msg_cfm_pwd.replace('arg_cfm_pwd', cfm_pwd);
        s_msg_more_cfm_pwd_lang = s_msg_more_cfm_pwd.replace('arg_more_cfm_pwd', more_cfm_pwd);
        s_msg_ent_eamil_lang = s_msg_ent_eamil.replace('arg_ent_eamil', ent_eamil);
        s_msg_f_eamil_lang = s_msg_f_eamil.replace('arg_f_eamil', f_eamil);


        // user id
        if (!manCheck && commonUtils.isBlank(userId)) {
            msg = s_msg_ent_id_lang;
            manCheck = true;
        }
        // user id 정규식 검사 (user id regular expression check)
        if(!manCheck && commonUtils.regexId(userId)) {
            msg = s_msg_condition_id_lang;
            manCheck = true;
        }
        // 비밀번호 (password)
        if (!manCheck && commonUtils.isBlank(password)) {
            msg = s_msg_ent_pwd_lang;
            manCheck = true;
        }
        // 비밀번호 정규식 검사 (password regular expression check)
        if (!manCheck && commonUtils.regexPwd(password)) {
            msg = s_msg_condition_pwd_lang;
            manCheck = true;
        }
        // 비밀번호 확인 (Confirm Password)
        if (!manCheck && commonUtils.isBlank($("#passwordConfirm").val())) {
            msg = s_msg_cfm_pwd_lang;
            manCheck = true;
        }
        if(password !== $('#passwordConfirm').val()){
            msg = s_msg_more_cfm_pwd_lang;
            manCheck = true;
        }

        // 메일주소 (Email Address)
        if (!manCheck && commonUtils.isBlank(email)) {
            msg = s_msg_ent_eamil_lang;
            manCheck = true;
        }

        // 메일주소 (Email Address)
        if (!manCheck && commonUtils.regexEmail(email)) {
            msg = s_msg_f_eamil_lang;
            manCheck = true;
        }

        // 알림 노출 (alert exposure)
        if(manCheck) {
            procAlertMessage(msg);
            return;
        }

        // json param
        var userListParam = {
            "userId": userId,
            "password": password,
            "passwordConfirm": passwordConfirm,
            "email": email
        };

        procCallAjax("/register", "POST", JSON.stringify(userListParam), false, callbackRegisterUser);


    });

    var callbackRegisterUser = function(data) {

        var join_user = '회원 가입이 완료되었습니다. 로그인 페이지로 이동합니다.';
        var s_msg_join_user= '<spring:message code="M0137" arguments='arg_join_user' javaScriptEscape="true" text="회원 가입이 완료되었습니다. 로그인 페이지로 이동합니다."/>';
        s_msg_join_user_lang = s_msg_join_user.replace('arg_join_user', join_user);

        procAlertMessage(s_msg_join_user_lang);
        setTimeout(function(){procMovePage(data.nextActionUrl)}, 1500);
    };

    $("#cancelBtn").on("click", function () {
        procMovePage("/");
    });

    $(document).ready(function() {
        $('#userId').focus();
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
                $('.info_box').show();

            } else{
                $('#chkNotice').html(s_msg_cwm_pwd_lang);
                $('#chkNotice').attr('style', 'color: #199894b3');
                $('.info_box').show();
            }

        });
    });



</script>