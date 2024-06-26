<!-- 横山 -->
<!-- 2024/06/14 -->

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="beans.StampBean" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- ↑↑↑年月日を取得する 　使用方法<fmt:formatDate value="${date}" pattern="yyyy-MM-dd"/><br>-->


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>打刻侍　勤怠状況表</title>

</head>
<body>
<style>
body {
        text-align: center; /* bodyの要素を中央揃え */
        background-color: 
    }
table {
    border-collapse: collapse;
    width: 80%;
    margin: 0 auto; /*横幅自動調整*/
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

/**編集ボタン**/
.button-group1 input[type="submit"] {
	    padding: 5px 20px; /* パディングを設定してボタンのサイズを調整 */
        font-size: 15px; /* ボタンのテキスのフォントサイズ */ 
        /**font-family: 'HG行書体'; /* フォントの指定 */
        background-color: #e4dc8a; /* ボタンの背景色*/
        color: #000; /* ボタンの文字色 */
}
/**申請ボタン**/
.button-group2 input[type="submit"] {
	    padding: 10px 30px; /* パディングを設定してボタンのサイズを調整 */
        font-size: 20px; /* ボタンのテキストのフォントサイズ */
        font-family: 'HG行書体'; /* フォントの指定 */
        background-color: #1F2F0A; /* ボタンの背景色*/
        color: #C29B36; /* ボタンの文字色 */
}


.button-group3  {
        margin-top: 20px; /* ボタン間の余白 */
        display: flex; /* ボタンを横並べにする */
        justify-content: center; /* ボタンを中央揃え */
    }
     .button-group3 form {
        margin: 0 10px; /* ボタン間の余白 */
    }
    /* 各ボタンのスタイル */
    .button-group3 input[type="submit"] {
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
	<jsp:include page="header.jsp" /><hr>
	
	<!--確認用  -->
	<% if((Integer)request.getAttribute("formstatus") == 0){ %>
	
	<p>【申請フォーム】</p>
	<!-- フォームの切り替え　JSで残すOrサーブレットで実行 -->
		<div style="double">
			<h1>勤怠状況表</h1>
		</div>
			<!----------------------------上部　年月・利用者 ------------------------------ -->
			
			<!-- Null判定 -->
			<% Integer year = (Integer) request.getAttribute("year");
			   Integer month = (Integer) request.getAttribute("month");
			   boolean YearAndMonth = (year != null && month != null);
			   	
			%>
			<!-- リクエストを取得した場合 -->
			<% if (YearAndMonth) { %>
			    <form action="AttConfirmController" method="get">
				        <select name="year">
				            <%  for (int i = year - 3; i <= year + 3; i++) { %>
				            <option value="<%= i %>" <%= (i == year) ? "selected" : "" %>><%= i %>年</option>
					        <% } %>
				        </select>
				        
				        <select name="month">
				            <% for (int i = 1; i <= 12; i++) { %>
				            <option value="<%= i %>"<%= (i == month) ? "selected" : "" %>><%= i %>月</option>
				            <% } %>
				        </select>
			        <input type="submit" value="確定"><br>
			    </form>
			    
			<!-- リクエストなし　先月の年/月を表示する-->
			<% }else{ %>
				<!-- 確定ボタンで年月送信 -->
				<form action="AttConfirmController" method="get">
					<select name="year">
						<option value="2023">2023年</option>
						<option value="2024" selected>2024年</option>
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
						<option value="5" selected>5月</option>
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
			<% } %>
			<p>勤務時間：9：00～18：00</p>
			<p>休憩時間：12：00～13：00</p>
			<div >
				<p>社員番号：${usersBean.emp_no}</p>
				<p>氏名：${usersBean.emp_name} </p>  
			</div><br>
		<!----------------------------中部 　表------------------------------ -->
		<% if (year != null) { %> 
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
							<c:otherwise>
								<td>データなし</td>
							</c:otherwise>
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
							<div class="button-group1">
									 <form action="AttEditController">
									 	<!--  -->
										<input type="hidden" name="year" value="<fmt:formatDate value="${stampBean.stamp_date}" pattern="yyyy" />">
										<input type="hidden" name="month" value="<fmt:formatDate value="${stampBean.stamp_date}" pattern="M" />">
										<input type="hidden" name="date" value="<fmt:formatDate value="${stampBean.stamp_date}" pattern="d" />">
						               <input type="submit" value="編集"> 
					                 </form>
					          </div>
					    </td>
					</tr>				
			</c:forEach>		
		</table>
			<% } else { %>
			    <p>年月が選択されていません。</p>
			<% } %>

	<div class="button-group2">
		<!-- 申請 　hideen=勤怠状況表ID-->
			<form action="AttRequestController" method="get" >
				<input type="hidden" name="year"  value="${year}">
				<input type="hidden" name="month"  value="${month}"> 
				<!-- <input type="hidden" name="att_status_id" value="0">承認まち -->
				<input type="submit" value="申請">
			</form>
	</div>
	
	
	<!----------------------------再申請がある場会の表示------------------------------ -->
	<% }else if((Integer)request.getAttribute("formstatus") == 3){ %>
	
	<p>【申請フォーム】</p>
			<div style="double">
			<h1>勤怠状況表</h1>
		</div>
			<!----------------------------上部　年月・利用者 ------------------------------ -->
		<fmt:formatDate value="${usersBean.year_and_month}" pattern="yyyy年　M月" />
			<p>勤務時間：9：00～18：00</p>
			<p>休憩時間：12：00～13：00</p>
			<div>
				<p>社員番号：${usersBean.emp_no}</p>
				<p>氏名：${usersBean.emp_name} </p>  
			</div><br>
			
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
		   
	 <!-- ここにデータの表示を追加 ---------------------------------------------------------------->
		    <c:forEach var="stampBean" items="${stampBean}">
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
							<c:otherwise>
								<td>データなし</td>
							</c:otherwise>
					</c:choose>
						<!--開始・終了時刻/就業時間/備考  -->
						<td><c:out value="${stampBean.workIn_re}" /></td>
						<td><c:out value="${stampBean.workOut_re}" /></td>
						<td><c:out value="${stampBean.workIn_raw}" /></td>
						<td><c:out value="${stampBean.workOut_raw}" /></td>
						<td><c:out value="${stampBean.rest_time}" /></td>
						<td><c:out value="${stampBean.real_work_time}" /></td>
						<td><c:out value="${stampBean.note}" /></td>
					</tr>				
			</c:forEach>		
		</table>
	
			 	<c:if test="${not empty requestListBean.reason}">
				    理由:<input type="textarea" value="${requestListBean.reason}" >
				</c:if>
		<div class="button-group2">
		<!-- 申請 　hideen=勤怠状況表ID-->
			<form action="AttRequestController" method="get" >
				<input type="hidden" name="year"  value="${year}">
				<input type="hidden" name="month"  value="${month}"> 
				<input type="hidden" name="att_status_id" value="${att_status_id}">
				<input type="submit" value="申請">
			</form>
		</div>
	
	<!---------------------------申請フォームがある場会の表示------------------------------ -->
			 	

	<% } else { %>
	
	<p>【承認フォーム】</p>
			<div style="double">
			<h1>勤怠状況表</h1>
		</div>
			<!----------------------------上部　年月・利用者 ------------------------------ -->
		<fmt:formatDate value="${usersBean.year_and_month}" pattern="yyyy年　M月" />
			<p>勤務時間：9：00～18：00</p>
			<p>休憩時間：12：00～13：00</p>
			<div>
				<p>社員番号：${usersBean.emp_no}</p>
				<p>氏名：${usersBean.emp_name} </p>  
			</div><br>

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
		   
	 <!-- ここにデータの表示を追加 ---------------------------------------------------------------->
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
							<c:otherwise>
								<td>データなし</td>
							</c:otherwise>
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
						<c:if test="${not empty requestListBean.reason}">
							理由:<textarea name="reason" min="1" max="100"required  value="${requestListBean.reason}" ></textarea>※入力必須<br>
						</c:if>
			<div class="button-group3">
					<input type="hidden" name="year"  value="${year}">
					<input type="hidden" name="month"  value="${month}"> 
					<input type="hidden" name="users_id"  value="${usersBean.users_id}"> 
					<input type="hidden" name="month_req_id"  value="${month_req_id}"> 
					<input type="submit" value="差し戻し" >
					<p style="color:red;"><c:out value="${errorMsg}"/></p>
				</form>
				
				<!--承認  -->
				<form action="AttApprovalController" method="get" >
				<!-- 年月と表示している申請者を送信 -->
					<input type="hidden" name="year"  value="${year}">
					<input type="hidden" name="month"  value="${month}"> 
					<input type="hidden" name="users_id"  value="${usersBean.users_id}"> 
					<input type="hidden" name="month_req_id"  value="${month_req_id}"> 
					<input type="submit" value="承認">
				</form>
			</div>
		</div>	
	
	<% } %>	
	<!-- 申請一覧へ遷移、WEB-INFフォルダへのアクセス？ -->
	<!-- <a href="/WEB-INF/jsp/社員画面.jsp">戻る</a>    -->
		<div class="button-groupBack"">
			<form action="BackController" method="get">
				<input type="submit" value="戻る">	
			</form>
		</div>
</body>
</html>