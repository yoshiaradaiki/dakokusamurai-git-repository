<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>勤怠状況表</title>

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
	
				<select name="month">
					<option value="1">1月</option>
					<option value="2" ">2月</option>
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
				<th>氏名：${UsersBean.name} </th>  
				<th>社員番号：${UsersBean.emp_no}</th>
				
			</tr><br>
		<!----------------------------中部 　表------------------------------ -->
		<tabel border="1">
			<tr>
				<th>		</th>
				<th>就業時間</th>
				<th>就業詳細時間</th>
			</tr>
			<tr>
				<tb>日付<tb>
				<tb>曜日<tb>
				<tb>勤怠状況<tb>
				<tb>開始時刻<tb>
				<tb>終了時刻</tb>
				<tb>開始時刻</tb>
				<tb>終了時刻</tb>
				<tb>休憩</tb>
				<tb>実労働時間</tb>
				<tb>備考</tb>
				<tb><form action="attendanceStatusDetail.jsp" >
						<input type="submit" value="編集">
					</form>
				</tb>
			</tr>
			<!-- ここに打刻修正＞月末申請＞カレンダーの優先順位で表をだす -->
			<c:forEach var="" items="">
				<tr>
					<td><c:out value="${Calender.日付}" /></td>
					<td><c:out value="${Calender.曜日}" /></td>
					<td><c:out value="${Calender.勤怠状況}" /></td>
					<td><c:out value="${}" /></td>
					<td><c:out value="${}" /></td>
					<td><c:out value="${}" /></td>
					<td><c:out value="${}" /></td>
					<td><c:out value="${}" /></td>
					<td><c:out value="${}" /></td>
					<td><c:out value="${}" /></td>
					<td><c:out value="${}" /></td>
					<td><c:out value="${}" /></td>
				</tr>
			</c:forEach>
			
			
			
		</tabel>
		
		<!----------------------------中部 　下部　申請------------------------------ -->
		<form action="AttRequestController" >
			<input type="sbmit" value="申請">
			
		</form>
	</form>
</body>
</html>