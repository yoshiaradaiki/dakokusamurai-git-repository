<!-- 湯振裕
　　　2024-6-17
 -->
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
	border: 1px solid #1e90ff;
	padding: 4px;
	text-align: center;
	background-color: #f0f8ff; /* デフォルトの背景色を設定 */
}

th {
	background-color: #87cefa;
}

.pagination {
	margin-top: 10px;
	text-align: center;
}

.pagination a {
	display: inline-block;
	padding: 5px 10px;
	margin: 0 5px;
	background-color: #f2f2f2;
	color: #333;
	text-decoration: none;
	border: 1px solid #ccc;
}

.pagination a:hover {
	background-color: #ddd;
}

.pagination span {
	display: inline-block;
	padding: 5px 10px;
	background-color: #4CAF50;
	color: white;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp" /><hr>
	<%-- ********************自分の申請一覧フォーム******************** --%>
	<h2>申請一覧</h2>
	<table style="width: 100%; border-collapse: collapse;">
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
					<td style="text-align: center;"><c:out
							value="${request.dateAndTime}" /></td>
					<td style="text-align: center;"><c:out
							value="${request.content}" /> <c:choose>
							<c:when test="${request.content == 0}">
     					       ：変更申請
     					   </c:when>
							<c:when test="${request.content == 1}">
      					      ：勤怠状況表提出
      					  </c:when>
						</c:choose></td>
					<td style="text-align: center;"><c:out
							value="${request.status}" /> <c:choose>
							<c:when test="${request.status == 0}">
   					         ：差し戻し
   					     </c:when>
							<c:when test="${request.status == 1}">
   					         ：承認待ち
    				    </c:when>
							<c:when test="${request.status == 2}">
     					      ：承認済み
   					     </c:when>
							<c:when test="${request.status == 3}">
        				    ：キャンセル
       					 </c:when>
						</c:choose></td>
					<td style="text-align: center;"><c:out
							value="${request.bossName}" /></td>
					<td style="text-align: center;"><c:choose>
							<c:when test="${request.status < 3}">
								<c:choose>
									<c:when test="${request.content == 0 && request.status == 0}">
										<button type="button" onclick="goRevDetail()">再申請</button>
										<button type="button" onclick="goRequestList()">キャンセル</button>
									</c:when>
									<c:when test="${request.content == 1 && request.status == 0}">
										<button type="button" onclick="goRevMonth()">再提出</button>
										<button type="button" onclick="goRequestList()">キャンセル</button>
									</c:when>
									<c:when test="${request.status < 2}">
										<button type="button" onclick="goRequestList()">キャンセル</button>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- ページネーション -->
	<div class="pagination">
		<%-- 前のページへのリンク --%>
		<c:if test="${currentPage > 1}">
			<c:url value="MyReqListPageController" var="prevPageUrl">
				<c:param name="page" value="${currentPage - 1}" />
			</c:url>
			<a href="${prevPageUrl}">前のページへ</a>
		</c:if>

		<%-- 現在のページ番号 --%>
		<span>現在ページ ${currentPage}/${totalPages}</span>

		<%-- 次のページへのリンク --%>
		<c:if test="${currentPage < totalPages}">
			<c:url value="MyReqListPageController" var="nextPageUrl">
				<c:param name="page" value="${currentPage + 1}" />
			</c:url>
			<a href="${nextPageUrl}">次のページへ</a>
		</c:if>
	</div>


	<%-- 　*******************部下の申請一覧フォーム******************** --%>
	<h2>部下の申請一覧</h2>
	<table style="width: 100%; border-collapse: collapse;">
		<thead>
			<tr>
				<th>日時</th>
				<th>内容</th>
				<th>ステータス</th>
				<th>申請者</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="request" items="${requestList}">
				<tr>
					<td style="text-align: center;"><c:out
							value="${request.dateAndTime}" /></td>
					<td style="text-align: center;"><c:out
							value="${request.content}" /> <c:choose>
							<c:when test="${request.content == 0}">
     					      ： 変更申請
     					   </c:when>
							<c:when test="${request.content == 1}">
      					      ：勤怠状況表提出
      					  </c:when>
						</c:choose></td>
					<td style="text-align: center;"><c:out
							value="${request.status}" /> <c:choose>
							<c:when test="${request.status == 0}">
   					         ：差し戻し
   					     </c:when>
							<c:when test="${request.status == 1}">
   					         ：承認待ち
    				    </c:when>
							<c:when test="${request.status == 2}">
     					      ：承認済み
   					     </c:when>
							<c:when test="${request.status == 3}">
        				    ：キャンセル
       					 </c:when>
						</c:choose></td>
					<td style="text-align: center;"><c:out value="${request.name}" /></td>
					<td style="text-align: center;"><c:choose>
							<c:when test="${request.status == 1 || request.status == 2}">
								<c:choose>
									<c:when test="${request.content == 0}">
										<button type="button" onclick="goRevDetail()">変更詳細</button>
									</c:when>
									<c:when test="${request.content == 1}">
										<button type="button" onclick="goRevMonth()">提出詳細</button>
									</c:when>
									<c:otherwise>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- ページネーション2 -->
	<div class="pagination">
		<%-- 前のページへのリンク --%>
		<c:if test="${currentPage2 > 1}">
			<c:url value="MyReqListPageController" var="prevPageUrl">
				<c:param name="page2" value="${currentPage2 - 1}" />
			</c:url>
			<a href="${prevPageUrl}">前のページへ</a>
		</c:if>

		<%-- 現在のページ番号 --%>
		<span>現在ページ ${currentPage2}/${totalPages2}</span>

		<%-- 次のページへのリンク --%>
		<c:if test="${currentPage2 < totalPages2}">
			<c:url value="MyReqListPageController" var="nextPageUrl">
				<c:param name="page2" value="${currentPage2 + 1}" />
			</c:url>
			<a href="${nextPageUrl}">次のページへ</a>
		</c:if>
	</div>


	<!-- 社員画面へ遷移、WEB-INFフォルダへのアクセス？ -->
	<a href="/WEB-INF/jsp/社員画面.jsp">戻る</a>
</body>
</html>

<script>
	//勤怠状況詳細画面へ遷移
	function goRevDetail() {
		window.location.href = '/WEB-INF/jsp/attendanceStatusDetail.jsp';
	}
	//勤怠状況表画面へ遷移
	function goRevMonthl() {
		window.location.href = '/WEB-INF/jsp/attendanceStatus.jsp';
	}
	//再描画
	function goRequestList() {
		window.location.href = '/WEB-INF/jsp/requestLis.jsp';
	}
</script>
