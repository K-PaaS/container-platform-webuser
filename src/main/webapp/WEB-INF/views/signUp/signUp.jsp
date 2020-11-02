<%--
  Sign Up page

  author: hrjin
  version: 1.0
  Date: 2020.09.22
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/alert.jsp" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Container Platform Sign Up</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel='stylesheet' type='text/css' href='<c:url value="/resources/css/cp-common.css"/>'>
    <link rel='stylesheet' type='text/css' href='<c:url value="/resources/css/gspinner.min.css"/>'>
    <link rel="stylesheet" href="/resources/css/style-signUp.css">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico"/>">
</head>
<body>
<div class="registration-form">
    <form name="frm" action="/register" method="post" enctype="text/plain">
        <div class="form-icon">
            <img  class="panel-image" src="/resources/images/main/logo_56x56.png" style="width: 100px; height: 100px;">
            <h4 style="margin-top: 20px;">Container Platform Sign Up</h4>
        </div>
        <div class="form-group">
            <input type="text" class="form-control item" id="userId" placeholder="User Id">
            <span style="margin-top: 2px; margin-left:15px; color: blue; font-size: x-small; font-variant: small-caps;"> * User ID는 시작과 끝이 영(소문자)/숫자로 253자 이하, 특수문자는 '-' 또는 '.'만 사용 가능</span>
        </div>
        <div class="form-group">
            <input type="password" class="form-control item" id="password" placeholder="Password" maxlength="40">
            <span style="margin-top: 2px; margin-left:15px; color: blue; font-size: x-small; font-variant: small-caps;"> * Password 는 4~40자 이하로 영문으로 시작, 최소 하나 이상의 숫자와 특수 문자 혼합</span>
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
</body>
</html>
<script type="text/javascript">

    $('#registerBtn').click(function(){

        var userId = $("#userId").val();
        var password = $("#password").val();
        var email = $("#email").val();

        var manCheck = false;
        var msg = "";

        // user id
        if (!manCheck && commonUtils.isBlank(userId)) {
            msg = "아이디를 입력하세요.";
            manCheck = true;
        }
        // user id 정규식 검사
        if(!manCheck && commonUtils.regexId(userId)) {
            msg = "User ID는 최대 253자 내의 영문 소문자 또는 숫자로 시작하고 끝나야 하며, 특수문자는 - 또는 . 만 사용 가능합니다.";
            manCheck = true;
        }
        // 비밀번호
        if (!manCheck && commonUtils.isBlank(password)) {
            msg = "비밀번호를 입력하세요.";
            manCheck = true;
        }
        // 비밀번호 정규식 검사
        if (!manCheck && commonUtils.regexPwd(password)) {
            msg = "비밀번호는 영문으로 시작하고, 최소 하나 이상의 숫자와 특수 문자를 혼합하여 4~40자 이내로 사용 가능합니다.";
            manCheck = true;
        }
        // 비밀번호 확인
        if (!manCheck && commonUtils.isBlank($("#passwordConfirm").val())) {
            msg = "비밀번호를 확인해주세요.";
            manCheck = true;
        }
        if(password !== $('#passwordConfirm').val()){
            msg = "비밀번호를 한 번 더 확인해주세요.";
            manCheck = true;
        }

        // 메일주소
        if (!manCheck && commonUtils.isBlank(email)) {
            msg = "메일 주소를 입력하세요.";
            manCheck = true;
        }

        // 메일주소
        if (!manCheck && commonUtils.regexEmail(email)) {
            msg = "이메일 형식이 올바르지 않습니다.";
            manCheck = true;
        }

        // 알림 노출
        if(manCheck) {
            procAlertMessage(msg);
            return;
        }

        // json param
        var userListParam = {
            "userId": userId,
            "password": password,
            "email": email
        };

        procCallAjax("/register", "POST", JSON.stringify(userListParam), false, callbackRegisterUser);


    });

    var callbackRegisterUser = function(data) {
        procAlertMessage("회원 가입이 완료되었습니다. 로그인 페이지로 이동합니다.");
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
            if($('#password').val() != $('#passwordConfirm').val()){
                $('#chkNotice').html('비밀번호가 일치하지 않습니다.<br>');
                $('#chkNotice').attr('style', 'color: #f82a2aa3');
                $('.info_box').show();

            } else{
                $('#chkNotice').html('비밀번호가 일치합니다.');
                $('#chkNotice').attr('style', 'color: #199894b3');
                $('.info_box').show();
            }

        });
    });



</script>