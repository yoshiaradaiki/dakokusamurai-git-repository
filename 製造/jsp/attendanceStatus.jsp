<!-- 横山 -->
<!-- 2024/06/14 -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="beans.StampBean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- ↑↑↑年月日を取得する 　使用方法<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/><br>-->

<%
//Samplに入る
StampBean stampBean = (StampBean) request.getAttribute("StampBean");
//UsersBean usersBean =new UsersBean();
//Date date =UsersBean.getyear_and_month(date);
//Date date = new Date(); 
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻侍　勤怠状況表</title>

</head>
<body>
	<jsp:include page="header.jsp" /><hr>
	<% if(true){ %>
	<h1>申請フォーム</h1>
	<!-- フォームの切り替え　JSで残すOrサーブレットで実行 -->
		<div style="double">
			<h3>勤怠状況表</h3>
		</div>
			<!----------------------------上部　年月・利用者 ------------------------------ -->
			<!-- プルダウン -->
			<form action="AttConfirmController" method="get"><!-- 確定ボタンで年月送信 -->
				<select name="year">
					<option value="2023">2023年</option>
					<option value="2024">2024年</option>
					<option value="2025" >2025年</option>
					<option value="2026">2026年</option>
					<option value="2027">2027年</option>
					<option value="2028" >2028年</option>
					<option value="2029">2029年</option>
				</select>
	
				<select  name="month">
					<option value="1">1月</option>
					<option value="2">2月</option>
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
				<th>社員番号：${UsersBean.emp_no}</th>
				<th>氏名：${UsersBean.emp_name} </th>  
			</tr><br>
		<!----------------------------中部 　表------------------------------ -->
		<table border="1">
		    <tr>
		        <th colspan="3"></th>
		        <th colspan="2">就業時間</th>
		        <th colspan="2">就業詳細時間</th>
		        <th colspan="4"></th>
		    </tr>
		    <tr>
		        <th>日付</th>
		        <th>曜日</th>
		        <th>勤怠状況</th>
		        <th>開始時刻</th>
		        <th>終了時刻</th>
		        <th>開始時刻</th>
		        <th>終了時刻</th>
		        <th>休憩</th>
		        <th>実労働時間</th>
		        <th>備考</th>
		        <th></th>
		    </tr>
		    <!--   -->
		    
	 <!-- ここにデータの表示を追加 ---------------------------------------------------------------->
	 
	 ★★★編集ボタン遷移NG
		    <c:forEach var="stampBean" items="${stampBeans}">
		    <tr>
		    	<!--日付  -->
				<td><fmt:formatDate value="${stampBean.stamp_date}" pattern="d" /></td>
				<!--曜日判定  -->
					<c:choose>
						<c:when test="${stampBean.week==1}">
							<td>日</td>
						</c:when>
						<c:when test="${stampBean.week==2}">
							<td>月</td>
						</c:when>
						<c:when test="${stampBean.week==3}">
							<td>火</td>
						</c:when>
						<c:when test="${stampBean.week==4}">
							<td>水</td>
						</c:when>
						<c:when test="${stampBean.week==5}">
							<td>木</td>
						</c:when>
						<c:when test="${stampBean.week==6}">
							<td>金</td>
						</c:when>
						<c:when test="${stampBean.week==7}">
							<td>土</td>
						</c:when>
						<c:otherwise>
							<td>データなし</td>
						</c:otherwise>
						
					</c:choose>
					
					<!-- 勤怠状況判定 -->
					<c:choose>
						<c:when test="${stampBean.work_status == 1}">
							<td>1：出勤</td>
						</c:when>
						<c:when test="${stampBean.work_status == 6}">
							<td>6：遅刻</td>
						</c:when>
						<c:when test="${stampBean.work_status == 7}">
							<td>7：早退</td>
						</c:when>
						<c:when test="${stampBean.work_status == 8}">
							<td>8：土祝出勤</td>
						</c:when>
						<c:when test="${stampBean.work_status == 10}">
							<td>10：1日有給休暇</td>
						</c:when>
						<c:when test="${stampBean.work_status == 11}">
							<td>11：半日有給休暇</td>
						</c:when>
						<c:when test="${stampBean.work_status == 12}">
							<td>12：特別休暇</td>
						</c:when>
						<c:when test="${stampBean.work_status == 13}">
							<td>13：休日</td>
						</c:when>
						<c:when test="${stampBean.work_status == 14}">
							<td>14：欠勤</td>
						</c:when>
						<c:when test="${stampBean.work_status == null}">
							<td>データ取得できません</td>
						</c:when>
					</c:choose>
						<!--開始・終了時刻/就業時間/備考  -->
						<td><c:out value="${stampBean.workIn_re}" /></td>
						<td><c:out value="${stampBean.workOut_re}" /></td>
						<td><c:out value="${stampBean.workIn_raw}" /></td>
						<td><c:out value="${stampBean.workOut_raw}" /></td>
						<td><c:out value="${stampBean.rest_time}" /></td>
						<td><c:out value="${stampBean.real_work_time}" /></td>
						<td><c:out value="${stampBean.note}" /></td>
						<!-- 編集ボタン -->
						 <td>
						 <form action="AttEditController">
							<input type="hidden" name="year" value="<fmt:formatDate value="${stampBean.stamp_date}" pattern="yyyy" />">
							<input type="hidden" name="month" value="<fmt:formatDate value="${stampBean.stamp_date}" pattern="M" />">
							<input type="hidden" name="date" value="<fmt:formatDate value="${stampBean.stamp_date}" pattern="d" />">
							
			               <input type="submit" value="編集"> 
		                 </form>
		                 </td>
					</tr>				
			</c:forEach>		
		</table>
	<!--  -->
			<form action="AttRequestController" method="get" >
				<input type="submit" value="申請">
			</form>
		
	<% }else{ %>
	<h1>承認フォーム</h1>
			<div style="double">
			<h3>勤怠状況表</h3>
		</div>
			<!----------------------------上部　年月・利用者 ------------------------------ -->
		<fmt:formatDate value="${usersBean.year_and_month}" pattern="yyyy年　M月" />
			<p>勤務時間：9：00～18：00</p>
			<p>休憩時間：12：00～13：00</p>
			<tr>
				<th>社員番号：${usersBean.emp_no}</th>
				<th>氏名：${usersBean.emp_name} </th>  
			</tr><br>
			
		<!----------------------------中部 　表------------------------------ -->
		<table border="1">
		    <tr>
		        <th colspan="3"></th>
		        <th colspan="2">就業時間</th>
		        <th colspan="2">就業詳細時間</th>
		        <th colspan="3"></th>
		    </tr>
		    <tr>
		        <th>日付</th>
		        <th>曜日</th>
		        <th>勤怠状況</th>
		        <th>開始時刻</th>
		        <th>終了時刻</th>
		        <th>開始時刻</th>
		        <th>終了時刻</th>
		        <th>休憩</th>
		        <th>実労働時間</th>
		        <th>備考</th>
		  
		    </tr>
		    <!--   -->
		    
	 <!-- ここにデータの表示を追加 ---------------------------------------------------------------->
		    <c:forEach var="stampBean" items="${stampBeans}">
		    <tr>
		    	<!--日付  -->
				<td><fmt:formatDate value="${stampBean.stamp_date}" pattern="d" /></td>
				<!--曜日判定  -->
					<c:choose>
						<c:when test="${stampBean.week==0}">
							<td>日</td>
						</c:when>
						<c:when test="${stampBean.week==1}">
							<td>月</td>
						</c:when>
						<c:when test="${stampBean.week==2}">
							<td>火</td>
						</c:when>
						<c:when test="${stampBean.week==3}">
							<td>水</td>
						</c:when>
						<c:when test="${stampBean.week==4}">
							<td>木</td>
						</c:when>
						<c:when test="${stampBean.week==5}">
							<td>金</td>
						</c:when>
						<c:when test="${stampBean.week==6}">
							<td>土</td>
						</c:when>
						
					</c:choose>
					
					<!-- 勤怠状況判定 -->
					<c:choose>
						<c:when test="${stampBean.work_status == 1}">
							<td>1：出勤</td>
						</c:when>
						<c:when test="${stampBean.work_status == 6}">
							<td>6：遅刻</td>
						</c:when>
						<c:when test="${stampBean.work_status == 7}">
							<td>7：早退</td>
						</c:when>
						<c:when test="${stampBean.work_status == 8}">
							<td>8：土祝出勤</td>
						</c:when>
						<c:when test="${stampBean.work_status == 10}">
							<td>10：1日有給休暇</td>
						</c:when>
						<c:when test="${stampBean.work_status == 11}">
							<td>11：半日有給休暇</td>
						</c:when>
						<c:when test="${stampBean.work_status == 12}">
							<td>12：特別休暇</td>
						</c:when>
						<c:when test="${stampBean.work_status == 13}">
							<td>13：休日</td>
						</c:when>
						<c:when test="${stampBean.work_status == 14}">
							<td>14：欠勤</td>
						</c:when>
						<c:when test="${stampBean.work_status == null}">
							<td>データ取得できません</td>
						</c:when>
					</c:choose>
						<!--開始・終了時刻/就業時間/備考  -->
						<td><c:out value="${stampBean.workIn_re}" /></td>
						<td><c:out value="${stampBean.workOut_re}" /></td>
						<td><c:out value="${stampBean.workIn_raw}" /></td>
						<td><c:out value="${stampBean.workOut_raw}" /></td>
						<td><c:out value="${stampBean.rest_time}" /></td>
						<td><c:out value="${stampBean.real_work_time}" /></td>
						<td><c:out value="${stampBean.note}" /></td>
						<!-- 編集ボタン -->
					</tr>				
			</c:forEach>		
		</table>
			<!-- 理由と差し戻し -->
			<form action="AttRemandController" method="get" >
				理由:<textarea name="reason"></textarea><br>
				<input type="submit" value="差し戻し">
			</form>
			<!--  -->
			<form action="AttApprovalController" method="get" >
				<input type="submit" value="承認">
			</form>
			
	<% } %>
	
	<!-- 申請一覧へ遷移、WEB-INFフォルダへのアクセス？ -->
	<!-- <a href="/WEB-INF/jsp/社員画面.jsp">戻る</a>    -->
	<a href="/WEB-INF/jsp/requestList.jsp">戻る</a>


	
</body>
</html>