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

	<%-- ********************自分の申請一覧フォーム******************** --%>
<h2>申請一覧</h2>
<table style="width: 100%; border-collapse: collapse;">
    <thead>
        <tr>
            <th style="text-align: center;">日時</th>
            <th style="text-align: center;">内容</th>
            <th style="text-align: center;">ステータス</th>
            <th style="text-align: center;">承認者</th>
            <th style="text-align: center;">操作</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="request" items="${requestList}">
            <tr>
                <td style="text-align: center;"><c:out value="${request.dateAndTime}" /></td>
                <td style="text-align: center;"><c:out value="${request.content}" /></td>
                <td style="text-align: center;"><c:out value="${request.status}" /></td>
                <td style="text-align: center;"><c:out value="${request.bossName}" /></td>
                <td style="text-align: center;">
                    <c:choose>
                        <c:when test="${request.status < 3}">
                            <c:choose>
                                <c:when test="${request.content == 0 && request.status < 2}">
                                    <button type="button" onclick="resubmit()">再申請</button>
                                    <button type="button" onclick="resubmit()">キャンセル</button>
                                </c:when>
                                <c:when test="${request.content == 1 && request.status < 2}">
                                    <button type="button" onclick="resubmit()">再提出</button>
                                    <button type="button" onclick="resubmit()">キャンセル</button>
                                </c:when>
                                <c:otherwise>
                                </c:otherwise>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>

	<!-- ページネーション -->
	<div class="pagination">
		<%-- 前のページへのリンク --%>
		<c:if test="${currentPage > 1}">
			<c:url value="ListPageController" var="prevPageUrl">
				<c:param name="page" value="${currentPage - 1}" />
			</c:url>
			<a href="${prevPageUrl}">前のページへ</a>
		</c:if>

		<%-- 現在のページ番号 --%>
		<span>現在ページ ${currentPage}/${totalPages}</span>

		<%-- 次のページへのリンク --%>
		<c:if test="${currentPage < totalPages}">
			<c:url value="ListPageController" var="nextPageUrl">
				<c:param name="page" value="${currentPage + 1}" />
			</c:url>
			<a href="${nextPageUrl}">次のページへ</a>
		</c:if>
	</div>


	<%-- 　*******************部下の申請一覧フォーム******************** --%>



	<!-- 社員画面へ遷移、WEB-INFフォルダへのアクセス？ -->
	<a href="社員画面.jsp">戻る</a>
</body>
</html>
