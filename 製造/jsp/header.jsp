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
	<style>
		body {
		    font-size: 20px;
	        text-align: center; /* bodyの要素を中央揃え */
	        font-family: 'HG行書体'; /* フォントの指定 */
	    }
	    
	    h1 {
	    	font-size: 40px;
	    	font-family: 'HG行書体'; /* フォントの指定 */
	    }
	    
	    div {
	        margin-top: 10px;
	    }
	</style>
<h1>打刻侍</h1>
現在ログイン中：<c:out value="${sessionUsersBean.emp_name}" /><br>
<div><a href="LogoutController">ログアウト</a></div>
</body>
</html>