<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>社員画面</title>
</head>

<body>
  <script>
<!-------------------------------------月日表示-------------------------------------->

			//月・日・曜日を取得
			var today = new Date();
			var month = today.getMonth() + 1;      //月が0から始まるため＋1
			var day = today.getDate();
			var dayOfWeek = today.getDay();

			// 曜日表記
			var weekdays = ["日", "月", "火", "水", "木", "金", "土"];
			var dayOfWeekText = weekdays[dayOfWeek];

			// 画面に出力
			document.write( month + "月" + day + "日 (" + dayOfWeekText + ")");

<!-------------------------------------時刻表示-------------------------------------->

			//2桁表示
			function twoDigit(num) {
			let ret;
			
			//「分」が10以下なら0を付けて2桁表示   （例）10:01
			if( num < 10 )
			  ret = "0" + num; 
			else 
			  ret = num; 
			return ret;
			}

			//時、分を取得
			function clockDisplay() {
			let nowTime = new Date();
			let nowHour = twoDigit( nowTime.getHours() );
			let owMin  = twoDigit( nowTime.getMinutes() );
			let msg = nowHour + ":" + nowMin;
			document.getElementById("realtime").innerHTML = msg;
			}
			//1秒ごとに時計を更新
			setInterval('clockDisplay()',1000);

 </script>

			<!--時刻表示-->
			<p id="realtime"></p>


			<form action="WorkInController" method="get">
				<input type="submit" value="出勤">
			</form>

			<form action="WorkOutController" method="get">
				<input type="submit" value="退勤">
			</form>

			<form action="RequestController" method="get">
				<input type="submit" value="申請一覧">
			</form>

			<form action="AttStatusController" method="get">
				<input type="submit" value="勤怠状況表">
			</form>
</body>
</html>