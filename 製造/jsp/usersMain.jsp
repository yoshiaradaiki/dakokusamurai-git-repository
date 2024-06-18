<!-- 社員画面 -->
<!-- 作成者：鈴木 -->
<!-- 作成日時：6月18日 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>社員画面</title>
<style>
    body {
        text-align: center; /* bodyの要素を中央揃え */
    }
    #dateDisplay {
        font-size: 30px; /* 月・日・曜日表示のフォントサイズ */
        font-family: 'HG行書体'; /* フォントの指定 */
        margin-bottom: 0px; /* 月日表示と時刻表示の間の余白 */
        color: #000; /* 文字色の指定 */
    }
    #clockDisplay {
        font-size: 60px; /* 時刻表示のフォントサイズ */
        font-family: 'HG行書体'; /* フォントの指定 */
        color: #000; /* 文字色の指定 */
    }
    .button-group1 {
        margin-top: 10px; /* ボタン間の余白 */
        display: flex; /* ボタンを横並べにする */
        justify-content: center; /* ボタンを中央揃え */
    }
    .button-group1 form {
        margin: 0 10px; /* ボタン間の余白 */
    }
    .button-group2 {
        margin-top: 10px; /* ボタン間の余白 */
    }
    .button-group2 form {
        margin-top: 25px; /* ボタン間の余白 */
    }
    /* 各ボタンのスタイル */
    .button-group1 input[type="submit"] {
        padding: 10px 30px; /* パディングを設定してボタンのサイズを調整 */
        font-size: 20px; /* ボタンのテキストのフォントサイズ */
        font-family: 'HG行書体'; /* フォントの指定 */
        background-color: #1F2F0A; /* ボタンの背景色*/
        color: #C29B36; /* ボタンの文字色 */
    }
    .button-group2 input[type="submit"] {
        padding: 5px 20px; /* パディングを設定してボタンのサイズを調整 */
        font-size: 20px; /* ボタンのテキストのフォントサイズ */
        font-family: 'HG行書体'; /* フォントの指定 */
        background-color: #1F2F0A; /* ボタンの背景色*/
        color: #C29B36; /* ボタンの文字色 */

    }
</style>
</head>
<body>
<!------------------------------------- 月日表示 -------------------------------------->
<jsp:include page="header.jsp" /><hr>
<script>
    // 月・日・曜日を取得
    var today = new Date();
    var month = today.getMonth() + 1; // 月が0から始まるため＋1
    var day = today.getDate();
    var dayOfWeek = today.getDay();

    // 曜日表記
    var weekdays = ["日", "月", "火", "水", "木", "金", "土"];
    var dayOfWeekText = weekdays[dayOfWeek];

    // 画面に出力
    var dateDisplay = document.createElement("div");
    dateDisplay.id = "dateDisplay";
    dateDisplay.textContent = month + "月" + day + "日 (" + dayOfWeekText + ")";
    document.body.appendChild(dateDisplay);
</script>
<!------------------------------------- 時刻表示 -------------------------------------->
<script>
    // 2桁表示
    function twoDigit(num) {
        return (num < 10 ? "0" : "") + num;
    }

    // 時、分を取得して表示
    function clockDisplay() {
        let nowTime = new Date();
        let nowHour = twoDigit(nowTime.getHours());
        let nowMin  = twoDigit(nowTime.getMinutes());
        let msg = nowHour + ":" + nowMin;
        document.getElementById("clockDisplay").textContent = msg;
    }

    // 1秒ごとに時計を更新
    setInterval(clockDisplay, 1000);
</script>

<!-- 月日表示 -->
<div id="dateDisplay"></div>
<!-- 時刻表示 -->
<p id="clockDisplay" style="font-size: 60px;"></p>

<div class="button-group1">
    <form action="WorkInController" method="get">
        <input type="submit" value="出勤">
    </form>
    <form action="WorkOutController" method="get">
        <input type="submit" value="退勤">
    </form>
</div>

<div class="button-group2">
    <form action="RequestController" method="get">
        <input type="submit" value="申請一覧">
    </form>
    <form action="AttStatusController" method="get">
        <input type="submit" value="勤怠状況表">
    </form>
</div>

</body>
</html>
