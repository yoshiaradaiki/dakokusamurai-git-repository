<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>勤怠状況詳細画面</title>
</head>
<body>
	<h1>申請フォーム</h1>
	<table border=1>
		<tr>
			<td colspan="3">結合セル</td>
			<td colspan="2">就業時間</td>
			<td colspan="2">就業詳細時間</td>
			<td colspan="3">結合セル</td>
		</tr>
		<tr>
			<td>日付</td>
			<td>曜日</td>
			<td>勤怠状況</td>
			<td>開始時刻</td>
			<td>終了時刻</td>
			<td>開始時刻</td>
			<td>終了時刻</td>
			<td>休憩</td>
			<td>実労働時間</td>
			<td>備考</td>
		</tr>
		<!-- ここにhtml文でテキストの枠を作る -->
		<% 
	for (int i = 0; i < list.size(); i++) {
		String[] data = list.get(i);
		out.print("<tr><td>""</td>");
		out.print("<td>""</td>"); 
		out.print("<td>""</td>"); 
		out.print("<td>""</td>"); 
		out.print("<td>""</td>"); 
		out.print("<td>""</td>"); 
		out.print("<td>""</td>"); 
		out.print("<td>""</td>"); 
		out.print("<td>""</td></tr>"); 
		%>
		<!-- ここまでにhtml文でテキストの枠を作る -->
		<input type="button" value="戻る" onclick="history.back()">
		<% 
	}
// ここに条件を設定します
    boolean condition = true; // ここで条件を設定

    if (condition) {
    	%>
		<!-- 条件が真の場合に表示する申請フォーム -->
		<form 　action="AttDetailRevRequest" 　method="post">
			<select name="kintaicode">
				<option value="">1：出勤</option> //プルダウンメニュー
				<option value="">6：遅刻</option>
				<option value="">7：早退</option>
				<option value="">8：土祝出勤</option>
				<option value="">10：1日有給休暇</option>
				<option value="">11：半日有給休暇</option>
				<option value="">12：特別休暇</option>
				<option value="">13：休日</option>
				<option value="">14：欠勤</option>
			</select></br> <input type="text" name="workIn"><br /> <input type="text"
				name="workOut"><br /> <input type="text" name="rest_time"><br />
			<input type="text" name="note"><br /> <input type="submit"
				value="変更申請">
			<%
    } else {
    	 %>
			<!-- 条件が偽の場合に表示する承認フォーム -->
			<form action="" method="post">
				<input type="submit" value="承認"> <input type="text"
					name="reason"> <input type="submit" value=差し戻し">
			</form>
			}
</body>
</html>