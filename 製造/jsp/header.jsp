<!-- 吉新 -->
<!-- 2024/06/17 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>打刻侍</h1>
現在ログイン中：<c:out value="${sessionUsersBean.emp_name}" /><br>
<a href="LogoutController">ログアウト</a>
</body>
</html>