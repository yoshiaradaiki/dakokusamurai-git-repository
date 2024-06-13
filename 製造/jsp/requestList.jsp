<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>申請一覧</title>
<style>
    table {
        border-collapse: collapse;
        width: 100%;
    }
    th, td {
        border: 1px solid #ddd;
        padding: 8px;
        text-align: left;
    }
    th {
        background-color: #f2f2f2;
    }
</style>
</head>
<body>
    <h4>申請一覧</h4>
    <table>
        <thead>
            <tr>
                <th>日時</th>
                <th>内容</th>
                <th>ステータス</th>
                <th>承認者</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="request" items="${requestList}">
                <tr>
                    <td><c:out value="${request.date_and_time}" /></td>
                    <td><c:out value="${request.content}" /></td>
                    <td><c:out value="${request.status}" /></td>
                    <td><c:out value="${request.boss_name}" /></td>
                    <td>
                        <button onclick="submitForm('resubmit', ${request.month_req_id})">再申請</button>
                        <button onclick="submitForm('cancel', ${request.month_req_id})">キャンセル</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <script>
        function submitForm(action, requestId) {
            var form = document.createElement('form');
            form.setAttribute('method', 'post');
            form.setAttribute('action', 'processRequest');

            var hiddenField = document.createElement('input');
            hiddenField.setAttribute('type', 'hidden');
            hiddenField.setAttribute('name', 'action');
            hiddenField.setAttribute('value', action);
            form.appendChild(hiddenField);

            var requestIdField = document.createElement('input');
            requestIdField.setAttribute('type', 'hidden');
            requestIdField.setAttribute('name', 'requestId');
            requestIdField.setAttribute('value', requestId);
            form.appendChild(requestIdField);

            document.body.appendChild(form);
            form.submit();
        }
    </script>
    <h4>部下の申請一覧</h4>
    <table>
        <thead>
            <tr>
                <th>日時</th>
                <th>内容</th>
                <th>ステータス</th>
                <th>承認者</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="request２" items="${requestList}">
                <tr>
                    <td><c:out value="${request2.date_and_time}" /></td>
                    <td><c:out value="${request2.content}" /></td>
                    <td><c:out value="${request2.status}" /></td>
                    <td><c:out value="${request2.boss_name}" /></td>
                    <td>
                        <button onclick="submitForm('resubmit', ${request2.month_req_id})">再申請</button>
                        <button onclick="submitForm('cancel', ${request2.month_req_id})">キャンセル</button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <script>
        function submitForm(action, requestId) {
            var form = document.createElement('form');
            form.setAttribute('method', 'post');
            form.setAttribute('action', 'processRequest');

            var hiddenField = document.createElement('input');
            hiddenField.setAttribute('type', 'hidden');
            hiddenField.setAttribute('name', 'action');
            hiddenField.setAttribute('value', action);
            form.appendChild(hiddenField);

            var requestIdField = document.createElement('input');
            requestIdField.setAttribute('type', 'hidden');
            requestIdField.setAttribute('name', 'requestId');
            requestIdField.setAttribute('value', requestId);
            form.appendChild(requestIdField);

            document.body.appendChild(form);
            form.submit();
        }
    </script>
</body>
</html>
