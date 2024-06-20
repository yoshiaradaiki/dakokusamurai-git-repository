<!-- 吉新 -->
<!-- 2024/06/14 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻侍</title>
</head>
<body>
	<form action="LoginController" method="get">
		<label for="login_id">ログインID：</label>
		<!-- リクエストにlogin_idがあれば表示する -->
		<input type="text" id="login_id" name="login_id" value="<c:out value='${not empty login_id ? login_id : ""}' />"><br>
		<label for="password">パスワード：</label>
		<input type="password" id="password" name="password"><br>
		<input type="submit" value="ログイン"><br>
	</form>
	<p style="color:red;"><c:out value="${errorMsg}"/></p>
</body>
</html>