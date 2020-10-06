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
            <input type="text" class="form-control item" id="userId" placeholder="User Id" maxlength="12">
        </div>
        <div class="form-group">
            <input type="password" class="form-control item" id="password" placeholder="Password" maxlength="12">
        </div>

        <div class="form-group">
            <button type="button" class="btn" id="loginBtn">Login</button>
        </div>
    </form>
</div>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src='<c:url value="/resources/js/cp-common.js"/>'></script>
<script>
    $("#loginBtn").on('click', function (event) {

        var userId = $("#userId").val();
        var password = $("#password").val();
        var loginObj = {userId: userId, password: password};
        var loginJson = JSON.stringify(loginObj);

        procCallAjax("/login", "POST", loginJson, false, resourceloginCallback);
    });

    var resourceloginCallback = function (data) {
        procMovePage('<%=Constants.URI_WORKLOAD_OVERVIEW%>');
    };

</script>
</body>
</html>
