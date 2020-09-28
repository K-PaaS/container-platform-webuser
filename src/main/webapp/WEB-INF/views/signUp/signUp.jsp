<%--
  Sign Up page

  author: hrjin
  version: 1.0
  Date: 2020.09.22
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Container Platform Sign Up</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="/resources/css/style-signUp.css">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico"/>">
</head>
<body>
<div class="registration-form">
    <form name="frm" action="/register" method="post" enctype="text/plain">
        <div class="form-icon">
            <img  class="panel-image" src="/resources/images/main/logo_56x56.png" style="width: 100px; height: 100px;">
        </div>
        <div class="form-group">
            <input type="text" class="form-control item" id="userId" placeholder="User Id" maxlength="12">
            <span class="info_box" id="isExistUserId" style="font-size: medium; font-variant: small-caps;"></span>
        </div>
        <div class="form-group">
            <input type="password" class="form-control item" id="password" placeholder="Password" maxlength="12">
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
            <button type="button" class="btn btn-size cancel">Cancel</button>
        </div>
    </form>
</div>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.15/jquery.mask.min.js"></script>
<script type="text/javascript" src="/resources/js/cp-common.js"></script>
</body>
</html>
<script type="text/javascript">
    var users = [];

    var getUsersList = function () {
        var reqUrl = "<%= Constants.URI_USERS %>";
        procCallAjax(reqUrl, "GET", null, null, callbackGetUsersList);
    };

    var callbackGetUsersList = function (data) {
        users = data;
        console.log("userList ::: " + users);
    };


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
        if(!manCheck && commonUtils.regexIdPwd(userId)) {
            msg = "아이디는 영문 소문자로 시작하는 4~12자 이내의 영문 소문자, 숫자만 혼합하여 사용 가능합니다.";
            manCheck = true;
        }
        // user id 중복 검사
        if(!manCheck && commonUtils.duplicatedId(userId)) {
            msg = "중복된 아이디는 사용하실 수 없습니다.";
            manCheck = true;
        }
        // 비밀번호
        if (!manCheck && commonUtils.isBlank(password)) {
            msg = "비밀번호를 입력하세요.";
            manCheck = true;
        }
        // 비밀번호 정규식 검사
        if (!manCheck && commonUtils.regexIdPwd(password)) {
            msg = "비밀번호는 영문 소문자로 시작하는 4~12자 이내의 영문 소문자, 숫자만 혼합하여 사용 가능합니다.";
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
            alert(msg);
            return;
        }

        // json param
        var userListParam = {
            "userId": userId,
            "password": password,
            "email": email
        };

        console.log("form data >>> " + JSON.stringify(userListParam));

        procCallAjax("/register", "POST", JSON.stringify(userListParam), 'singleView', callbackRegisterUser);


    });

    var callbackRegisterUser = function(data) {
        console.log("data >>> " + JSON.stringify(data));

        // todo :: 회원가입 성공하면 어디로? 우선 /intro/overview
        procMovePage(data.nextActionUrl);
    };

    $(function(){
        $('#userId').keyup(function(){
            $('#isExistUserId').html('');

        });

        $('#userId').keyup(function(){
            if(($.inArray($('#userId').val(), users) != -1) || commonUtils.regexIdPwd($('#userId').val())){
                $('#isExistUserId').html('사용할 수 없는 아이디입니다.<br>');
                $('#isExistUserId').attr('style', 'color: #f82a2aa3');
            } else{
                $('#isExistUserId').html('사용 가능한 아이디입니다.');
                $('#isExistUserId').attr('style', 'color: #199894b3');
            }

        });

        $('#password').keyup(function(){
            $('#chkNotice').html('');
        });

        $('#passwordConfirm').keyup(function(){

            if($('#password').val() != $('#passwordConfirm').val()){
                $('#chkNotice').html('비밀번호가 일치하지 않습니다.<br>');
                $('#chkNotice').attr('style', 'color: #f82a2aa3');
            } else{
                $('#chkNotice').html('비밀번호가 일치합니다.');
                $('#chkNotice').attr('style', 'color: #199894b3');
            }

        });
    });

    $(document.body).ready(function () {
        getUsersList();
    });

</script>