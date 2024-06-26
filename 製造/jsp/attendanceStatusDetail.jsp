<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="beans.StampBean"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>勤怠状況詳細画面</title>
</head>
<body>
<style>
 body {
        text-align: center; /* bodyの要素を中央揃え */
        background-color: ;
     
    }
table {
	border-collapse: collapse;
	width: 100%;
}
/**表　中身**/
th, td {
	border: 1px solid #000 ;
	padding: 4px;
	text-align: center;
	background-color: #f8f4e6 ; /* デフォルトの背景色を設定　#EEE8AA */
}
/**表　中身**/
th {
	/**color: #ffffff;**/
	background-color: #ebe1a9;
} 

.button-group1  {
        margin-top: 20px; /* ボタン間の余白 */
        display: flex; /* ボタンを横並べにする */
        justify-content: center; /* ボタンを中央揃え */
    }
     .button-group1 form {
        margin: 0 10px; /* ボタン間の余白 */
    }
    /* 各ボタンのスタイル */
    .button-group1 input[type="submit"] {
        padding: 10px 30px; /* パディングを設定してボタンのサイズを調整 */
        font-size: 20px; /* ボタンのテキストのフォントサイズ */
        font-family: 'HG行書体'; /* フォントの指定 */
        background-color: #1F2F0A; /* ボタンの背景色*/
        color: #C29B36; /* ボタンの文字色 */
    }
/**差し戻し理由のテキストエリア拡張防止**/
textarea { 
padding: 20 10px; /* ボタン間の余白 */
resize: none;
width:300px;
height:20px;
}
.button-groupBack{
     text-align: left;
}
</style>
<jsp:include page="header.jsp"/><hr>
	<h1>勤怠状況詳細</h1>
	<fmt:formatDate value="${usersBean.year_and_month}" pattern="yyyy年　M月" />
	<br>
	<!-- 年月 -->
	基本時間:9:00～18：00
	<br> 休憩時間:12:00～13:00
	<br> 社員番号：
	<c:out value="${usersBean.emp_no}" />
	 氏名：
	<c:out value="${usersBean.emp_name}" />
	<br>

	<% //if (社員がログインし自分の打刻修正を見る場合)true:申請フォーム false:承認フォーム{ %>
	<% if((Integer)request.getAttribute("formstatus") ==0){ %>
	<h1>申請フォーム</h1>
	<table border=1>
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
		<tr>
			<!--日付を表示  -->
			<td><fmt:formatDate value="${stampBean.stamp_date}" pattern="d" /></td>
			<!--曜日を表示  -->
			<c:choose>
				<c:when test="${stampBean.week == 1}"> <!--	月曜を表示  -->
					<td>日</td>
				</c:when>
				<c:when test="${stampBean.week == 2}">
					<td>月</td>
				</c:when>
				<c:when test="${stampBean.week == 3}">
					<td>火</td>
				</c:when>
				<c:when test="${stampBean.week == 4}">
					<td>水</td>
				</c:when>
				<c:when test="${stampBean.week == 5}">
					<td>木</td>
				</c:when>
				<c:when test="${stampBean.week == 6}">
					<td>金</td>
				</c:when>
				<c:when test="${stampBean.week == 7}">
					<td>土</td>
				</c:when>
				<c:otherwise>
					<td></td>
				</c:otherwise>
			</c:choose>
			<!--勤怠状況コードを表示  -->
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
				<c:otherwise>
					<td></td>
				</c:otherwise>
			</c:choose>

			<td><c:out value="${stampBean.workIn_re}" /></td>
			<td><c:out value="${stampBean.workOut_re}" /></td>
			<td><c:out value="${stampBean.workIn_raw}" /></td>
			<td><c:out value="${stampBean.workOut_raw}" /></td>
			<td><c:out value="${stampBean.rest_time}" /></td>
			<td><c:out value="${stampBean.real_work_time}" /></td>
			<td><c:out value="${stampBean.note}" /></td>
		</tr>
	</table>
	<form action="AttDetailRevRequestController" method="get">
		<!-- ここにフォームの内容を追加 -->
		<h3>変更内容</h3>
		記入してください<br> 
		勤怠状況： 
		<select name="work_status">
			<option value="1">1：出勤</option>
			<option value="6">6：遅刻</option>
			<option value="7">7：早退</options>
			<option value="8">8：土祝出勤</option>
			<option value="10">10：1日有給休暇</option>
			<option value="11">11：半日有給休暇</option>
			<option value="12">12：特別休暇</option>
			<option value="13">13：休日</option>
			<option value="14">14：欠勤</option>
		</select><br> 
		開始時刻： <input type="time" name="workIn_rev" value="${stampBean.workIn_re}" required><br> 
			
		終了時刻： <input type="time" name="workOut_rev" value="${stampBean.workOut_re}"required><br>
		休憩： 
		<input type="time" name="rest_time" value="${stampBean.rest_time}"required><br>
		備考：
		<textarea name="note" cols="" rows="" 　maxlength="20" ><c:out
				value="${stamBean.note}" /></textarea><br>
		<p style="color:red"><c:out value="${errorMsg}" /></p>
		理由
		<p>
			<c:out value="${requestListBean.reason}" />
		</p>
			<input type="hidden" name="requestDate" value="${stampBean.stamp_date}">
			<input type="submit" value="変更申請">
			<input type="hidden" name="stamp_rev_req_id" value="${stamp_rev_req_id}">
	</form>
			<%}else{ %>
<% //if (上司がログインし部下の打刻修正を見る場合){ %>		
		<h1>承認フォーム</h1>
		<table border=1>
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
			<tr>
				<td><fmt:formatDate value="${stampBean.stamp_date}" pattern="d" /></td>
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
					<c:otherwise>
					<td></td>
				</c:otherwise>
				</c:choose>
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
					<c:otherwise>
					<td></td>
				</c:otherwise>
				</c:choose>

				<td><c:out value="${stampBean.workIn_re}" /></td>
				<td><c:out value="${stampBean.workOut_re}" /></td>
				<td><c:out value="${stampBean.workIn_raw}" /></td>
				<td><c:out value="${stampBean.workOut_raw}" /></td>
				<td><c:out value="${stampBean.rest_time}" /></td>
				<td><c:out value="${stampBean.real_work_time}" /></td>
				<td><c:out value="${stampBean.note}" /></td>
			</tr>
		</table>
		<h3>変更内容</h3>
		勤怠状況：
		<c:choose>
			<c:when test="${stampBean.work_status == 1}">
	        1：出勤
	    </c:when>
			<c:when test="${stampBean.work_status == 6}">
	        6：遅刻
	    </c:when>
			<c:when test="${stampBean.work_status == 7}">
	       7：早退
	    </c:when>
			<c:when test="${stampBean.work_status == 8}">
	        8：土祝出勤
	    </c:when>
			<c:when test="${stampBean.work_status == 10}">
	        10：1日有給休暇
	    </c:when>
			<c:when test="${stampBean.work_status == 11}">
	        11：半日有給休暇
	    </c:when>
			<c:when test="${stampBean.work_status == 12}">
	        12：特別休暇
	    </c:when>
			<c:when test="${stampBean.work_status == 13}">
	        13：休日
	    </c:when>
			<c:when test="${stampBean.work_status == 14}">
	        14：欠勤
	    </c:when>
		    <c:otherwise>
			<td></td>
		    </c:otherwise>
		</c:choose><br>
		開始時刻：
		<c:out value="${stampBean.workIn_re}" /><br>
		終了時刻：
		<c:out value="${stampBean.workOut_re}" /><br>
		休憩：
		<c:out value="${stampBean.rest_time}" /><br>
		備考：
		<c:out value="${stampBean.note}" /><br>

		<form method="get" action="AttDetailApprovalController">
			<input type="submit" value="承認">
			<input type="hidden" name="stamp_rev_id" value="${stamp_rev_id}">
			<input type="hidden" name="stamp_rev_req_id" value="${stamp_rev_req_id}">
		</form>

		<form method="get" action="AttDetailRemandController">
			理由:
			<textarea name="reason" cols="" rows="" 　maxlength="20" ></textarea><br>
			<input type="hidden" name="stamp_rev_id" value="${stamp_rev_id}"   >
			<input type="hidden" name="stamp_rev_req_id" value="${stamp_rev_req_id}">
			<p style="color:red"><c:out value="${errorMsg}" /></p>
			<input type="submit" value="差し戻し">
		</form>
	<% } %>
	<form action="BackController" method="get">
	<input type="submit" value="戻る">	
</form>
</html>