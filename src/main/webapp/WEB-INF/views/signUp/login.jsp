<%--
  Login Page

  author: kjhoon
  version: 1.0
  Date: 2020.09.22
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.paasta.container.platform.web.user.common.Constants" %>
<%@ include file="../common/alert.jsp" %>

<html>

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Container Platform Login</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/simple-line-icons/2.4.1/css/simple-line-icons.min.css"
          rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel='stylesheet' type='text/css' href='<c:url value="/resources/css/cp-common.css"/>'>
    <link rel='stylesheet' type='text/css' href='<c:url value="/resources/css/gspinner.min.css"/>'>
    <link rel="stylesheet" href="/resources/css/style-login.css">
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/images/favicon.ico"/>">
</head>
<body>
<div id="loginDiv">
    <form name="frm" action="/login" method="post" enctype="text/plain" id="loginForm">
        <div class="form-icon">
            <img class="panel-image" src="/resources/images/main/logo_56x56.png">
            <h4>Container Platform Login</h4>
        </div>
        <div class="form-group">
            <input type="text" autofocus="autofocus" class="form-control item" id="userId" placeholder="아이디를 입력하세요">
        </div>
        <div class="form-group">
            <input type="password" class="form-control item" id="password" placeholder="비밀번호를 입력하세요">
        </div>
        <div class="custom-control custom-checkbox my-1 mr-sm-2">
            <input type="checkbox" class="custom-control-input" id="rememberMe">
            <label class="custom-control-label" for="rememberMe">Remember me</label>
        </div>
        <div class="form-group">
            <button type="button" class="btn login-form-btn" id="loginBtn">로그인</button>
        </div>

        <div class="form-group">
            <button type="button" class="btn login-form-btn" id="registerBtn">사용자 등록</button>
        </div>
    </form>
</div>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src='<c:url value="/resources/js/cp-common.js"/>'></script>
<script type="text/javascript" src='<c:url value="/resources/js/jquery.cookie.js"/>'></script>
<script>
    $(document).ready(function () {
        var rememberID = $.cookie('<%=Constants.CP_REMEMBER_ME_KEY%>');

        if (rememberID) {
            $("#userId").val(rememberID);
            $("#rememberMe").prop("checked", true);
        }

    });
</script>
<script>

    //ID 저장 시 Alert 띄우기
    $("#rememberMe").click(function (e) {
        if ($(this).is(":checked")) {
            if (!confirm("공용 PC에서 로그인정보를 저장할 경우, 타인에게 노출될 위험이 있습니다. 아이디를 저장하시겠습니까?")) {
                $("#rememberMe").prop("checked", false);
            }
        }
    });

   // 사용자 등록페이지 이동
    $("#registerBtn").on('click', function (event) {
        procMovePage('/signUp');
    });


    // Enter Key 입력 시 로그인 버튼 이벤트 실행
    $("#password").keyup(function (event) {
        if (event.keyCode === 13) {
            $("#loginBtn").click();
        }
    });

    // 로그인 요청
    $("#loginBtn").on('click', function (event) {

        var userId = $("#userId").val();
        var password = $("#password").val();

        var loginObj = {userId: userId, password: password};
        var loginJson = JSON.stringify(loginObj);

        procCallAjax("/login", "POST", loginJson, false, resourceloginCallback);
    });

    var resourceloginCallback = function (data) {

        //rememberme
        var rememberMe = $("#rememberMe").is(":checked");
        var userId = $("#userId").val();
        var namespace = data.namespace ;

        var namespace_json = JSON.stringify(namespace);

        //namespace List
        $.cookie("namespace", namespace_json, {
            "expires" : new Date(+new Date() + (60 * 60 * 1000))
        });

        //remember me
        if (rememberMe) {
            $.cookie('<%=Constants.CP_REMEMBER_ME_KEY%>', userId, {
                "expires": 30
            });
        } else {
            $.removeCookie('<%=Constants.CP_REMEMBER_ME_KEY%>');
        }

        procMovePage('<%=Constants.URI_WORKLOAD_OVERVIEW%>');
    };
</script>
</body>
</html>
