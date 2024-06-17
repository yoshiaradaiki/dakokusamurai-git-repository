<!-- 横山 -->
<!-- 2024/06/14 -->

<%@page import="beans.StampBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<% 
StampBean stampBean = (StampBean)request.getParameter(StampBean);
String[] week ={"日","月","火","水","木","金","土"}; //配列曜日

UsersBean usersBean = new usersBean(); 

%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻侍　勤怠状況表</title>

</head>
<body>
	<jsp:include page="header.jsp" /><hr>
	<form><!-- フォームの切り替え　JSで残すOrサーブレットで実行 -->
		<div style="double">
			<h3>勤怠状況表</h3>
		</div>
			<!----------------------------上部　年月・利用者 ------------------------------ -->
			<!-- プルダウン -->
			<form action="/AttConfirmController" >
				<select name="year">
					<option value="2024">2024年</option>
					<option value="2025" ">2025年</option>
					<option value="2026">2026年</option>
					<option value="2027">2027年</option>
					<option value="2028" ">2028年</option>
					<option value="2029">2029年</option>
				</select>
	
				<select  name="month">
					<option value="1">1月</option>
					<option value="2" >2月</option>
					<option value="3">3月</option>
					<option value="4">4月</option>
					<option value="5">5月</option>
					<option value="6">6月</option>
					<option value="7">7月</option>
					<option value="8">8月</option>
					<option value="9">9月</option>
					<option value="10">10月</option>
					<option value="11">11月</option>
					<option value="12">12月</option>	
				</select>
				<input type="submit" value="確定" ><br>
			</form>
			<p>勤務時間：9：00～18：00</p>
			<p>休憩時間：12：00～13：00</p>
			<tr>
			
				<th>氏名：${sessionUsersBean.emp_name} </th>  
				<th>社員番号：${sessionUsersBean.emp_no}</th>
				
			</tr><br>
		<!----------------------------中部 　表------------------------------ -->
		<table border="1">
			<tr>
				<th colspan="4"></th>
				<th colspan="2">就業時間</th>
				<th colspan="2">就業詳細時間</th>
				<th colspan="3"></th>
			</tr>
			<tr>
				<th>日付<th>
				<th>曜日<th>
				<th>勤怠状況<th>
				<th>開始時刻<th>
				<th>終了時刻</th>
				<th>開始時刻</th>
				<th>終了時刻</th>
				<th>休憩</th>
				<th>実労働時間</th>
				<th>備考</th>
				<th><form action="attendanceStatusDetail.jsp" >
						<input type="submit" value="編集">
					</form>
				</th>
			</tr>
			<!-- ここに打刻修正＞月末申請＞カレンダーの優先順位で表をだす -->
			<c:forEach  var="attSteatusList" items="${AttSteatusList} }">
				<td><c:out value="${StampBean.getStamp_date().getDate()}"</td>
				<td><c:out value="${StampBean.getStamp} "></td>
				
			<tr>
				
		</tabel>
		
		<!----------------------------中部 　下部　申請}------------------------------ -->
		<!--ボタン出現処理　上司IDを持っていたら差し戻し理由・承認・差し戻しをボタンをだす-->

		
		<form action="AttRequestController" >
			<input type="hidden" value="${date} " name="date">
			<input type="submit" value="申請">
		</form>
		
		<!--UsersBeanの中に上司IDがある場合  -->
		<c:if test="${not empty UserBean.boos_users_id}">
			<input type="text" name="reason" >
		    <button onclick="AttApprovalController(${UserBean.id})">承認</button> 
		    <button onclick="AttRemandController(${UserBean.id})">差し戻し</button>
		</c:if>
		
			<!-- 参考用 
			//サーブレット処理
			// 例として、特定のユーザーが管理者権限を持っている場合にボタンを表示するとします
				boolean isAdminUser = // 管理者かどうかの条件を取得する処理（例えばセッションから取得するなど）
				
				// 条件をリクエストスコープにセットする
				request.setAttribute("isAdminUser", isAdminUser);
				
				// リクエストをJSPにフォワードするなどして、JSPで表示を行う
				request.getRequestDispatcher("/your_jsp_page.jsp").forward(request, response);
			//Jsp		
			<c:if test="${isAdminUser}">
		    isAdminUserがtrueの場合のみボタンを表示 
		    <button>管理者用ボタン</button>
			</c:if> -->
		
	</form>
</body>
</html>