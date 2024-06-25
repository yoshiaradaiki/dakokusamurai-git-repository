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
	<style>
		body {
	        text-align: center; /* bodyの要素を中央揃え */
	    }
	    h1 {
	    	font-size: 50px;
	    	font-family: 'HG行書体'; /* フォントの指定 */
	    	margin-top: 70px;
	    }
	    div {
	    	margin-top: 10px;
	    }
	    input[type="submit"] {
	        padding: 5px 20px; /* パディングを設定してボタンのサイズを調整 */
	        font-size: 20px; /* ボタンのテキストのフォントサイズ */
	        font-family: 'HG行書体'; /* フォントの指定 */
	        background-color: #1F2F0A; /* ボタンの背景色*/
	        color: #C29B36; /* ボタンの文字色 */
		}
	</style>
	
	<h1>打刻侍</h1>
	<form action="LoginController" method="get">
		<div><label for="login_id">ログインID：</label>
		<!-- リクエストにlogin_idがあれば表示する -->
		<input type="text" id="login_id" name="login_id" value="<c:out value='${not empty login_id ? login_id : ""}' />"></div><br>
		<div><label for="password">パスワード：</label>
		<input type="password" id="password" name="password"></div><br>
		<p style="color:red;"><c:out value="${errorMsg}"/></p>
		<input type="submit" value="ログイン"><br>
	</form>
</body>
</html>