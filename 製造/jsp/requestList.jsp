<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>申請一覧</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;<!-- テーブルの表示幅 -->
        }
        th, td {
            border: 1px solid #ddd;<!-- 枠線の太さ -->
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;<!-- 背景色 -->
        }
    </style>
</head>
<body>
    <h2>申請一覧</h2>
    <table>
        <thead>
            <tr>
                <th>日時</th>
                <th>内容</th>
                <th>ステータス</th>
                <th>承認者</th>
            </tr>
        </thead>
        <tbody>
            <%-- 申請一覧のデータをここで表示 --%>
            <%-- 例として5つの申請を表示する --%>
            <% for (int i = 1; i <= 5; i++) { %>
                <tr>
                    <td>日時<%= i %></td>
                    <td>内容<%= i %></td>
                    <td>ステータス<%= i %></td>
                    <td>承認者<%= i %></td>
                </tr>
            <% } %>
        </tbody>
    </table>

    <h2>部下の申請一覧</h2>
    <table>
        <thead>
            <tr>
                <th>日時</th>
                <th>内容</th>
                <th>ステータス</th>
                <th>申請者</th>
            </tr>
        </thead>
        <tbody>
            <%-- 部下の申請一覧のデータをここで表示 --%>
            <%-- 例として15件の申請を表示する --%>
            <% for (int i = 1; i <= 15; i++) { %>
                <tr>
                    <td>日時<%= i %></td>
                    <td>内容<%= i %></td>
                    <td>ステータス<%= i %></td>
                    <td>申請者<%= i %></td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
