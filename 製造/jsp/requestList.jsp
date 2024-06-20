<!-- 湯
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
	background-color: #87cefa;
	color: #333;
	text-decoration: none;
	border: 1px solid #ccc;
}

.pagination a:hover {
	background-color: #e0ffff;
}

.pagination span {
	display: inline-block;
	padding: 5px 10px;
	background-color: #a9a9a9;
	color: white;
}
</style>
</head>
<body>
	<jsp:include page="header.jsp" /><hr>

	<%-- ********************自分の申請一覧フォーム******************** --%>
	<h3>申請一覧</h3>
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
							value="${request.date_and_time}" /></td>
					<td style="text-align: center;"><c:choose>
							<c:when test="${request.content eq 0}">
            変更申請
        </c:when>
							<c:when test="${request.content eq 1}">
            勤怠状況表提出
        </c:when>
							<c:otherwise>
								<c:out value="${request.content}" />
							</c:otherwise>
						</c:choose></td>
					<td style="text-align: center;"><c:choose>
							<c:when test="${request.status eq 0}">
            差し戻し
        </c:when>
							<c:when test="${request.status eq 1}">
            承認待ち
        </c:when>
							<c:when test="${request.status eq 2}">
            承認済み
        </c:when>
							<c:when test="${request.status eq 3}">
            キャンセル
        </c:when>
						</c:choose></td>

					<td style="text-align: center;"><c:out
							value="${request.boss_name}" /></td>
					<td style="text-align: center;"><c:choose>
							<c:when test="${request.status < 3}">
								<c:choose>
									<c:when test="${request.content == 0 && request.status == 0}">
										<form action="RequestRequestController" method="get">
											<input type="hidden" name="stamp_rev_id"
												value="${request.stamp_rev_id}">
											<button type="submit">再申請</button>
										</form>
										<form action="RequestMonthCancelController" method="get">
											<input type="hidden" name="month_req_id"
												value="${request.month_req_id}">
											<button type="submit">キャンセル</button>
										</form>
									</c:when>
									<c:when test="${request.content == 1 && request.status == 0}">
										<form action="RequestSubmitController" method="get">
											<input type="hidden" name="att_status_id"
												value="${request.att_status_id}">
											<button type="submit">再提出</button>
										</form>
										<form action="RequestOneDayCancelController" method="get">
											<input type="hidden" name="stamp_rev_req_id"
												value="${request.stamp_rev_req_id}">
											<button type="submit">キャンセル</button>
										</form>
									</c:when>
									<c:when test="${request.content == 0 && request.status < 2}">
										<form action="RequestOneDayCancelController" method="get">
											<input type="hidden" name="stamp_rev_req_id"
												value="${request.stamp_rev_req_id}">
											<button type="submit">キャンセル</button>
										</form>
									</c:when>
									<c:when test="${request.content == 1 && request.status < 2}">
										<form action="RequestMonthCancelController" method="get">
											<input type="hidden" name="month_req_id"
												value="${request.month_req_id}">
											<button type="submit">キャンセル</button>
										</form>
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

	<!-- 申請一覧のページネーション -->
	<div class="pagination">
		<c:if test="${currentPage > 1}">
			<c:url value="ReqListController" var="prevPageUrl">
				<c:param name="page" value="${currentPage - 1}" />
			</c:url>
			<a href="${prevPageUrl}">前のページへ</a>
		</c:if>

		<span>現在ページ ${currentPage}/${totalPages}</span>

		<c:if test="${currentPage < totalPages}">
			<c:url value="ReqListController" var="nextPageUrl">
				<c:param name="page" value="${currentPage + 1}" />
			</c:url>
			<a href="${nextPageUrl}">次のページへ</a>
		</c:if>
	</div>


	<%-- 　*******************部下の申請一覧フォーム******************** --%>
	<h3>部下の申請一覧</h3>
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
			<c:forEach var="request" items="${requestList2}">
				<tr>
					<td style="text-align: center;"><c:out
							value="${request.date_and_time}" /></td>
					<td style="text-align: center;"><c:choose>
							<c:when test="${request.content == 0}">
     					      変更申請
     					   </c:when>
							<c:when test="${request.content == 1}">
      					      勤怠状況表提出
      					  </c:when>
						</c:choose></td>
					<td style="text-align: center;"><c:choose>
							<c:when test="${request.status == 0}">
   					         差し戻し
   					     </c:when>
							<c:when test="${request.status == 1}">
   					         承認待ち
    				    </c:when>
							<c:when test="${request.status == 2}">
     					      承認済み
   					     </c:when>
							<c:when test="${request.status == 3}">
        				    キャンセル
       					 </c:when>
						</c:choose></td>
					<td style="text-align: center;"><c:out value="${request.name}" /></td>
					<td style="text-align: center;"><c:choose>
							<c:when test="${request.status == 1 || request.status == 2}">
								<c:choose>
									<c:when test="${request.content == 0}">
									<form action="RevDetailController" method="get">
											<input type="hidden" name="att_status_id"
												value="${request.att_status_id}">
										<button type="submit" >変更詳細</button>
									</c:when>
									<c:when test="${request.content == 1}">
									<form action="RequestDetailController" method="get">
											<input type="hidden" name="att_status_id"
												value="${request.att_status_id}">
										<button type="submit" >提出詳細</button>
										</form>
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


	<!-- 部下の申請一覧のページネーション -->
	<div class="pagination">
		<c:if test="${currentPage2 > 1}">
			<c:url value="ReqListController" var="prevPageUrl2">
				<c:param name="page2" value="${currentPage2 - 1}" />
			</c:url>
			<a href="${prevPageUrl2}">前のページへ</a>
		</c:if>

		<span>現在ページ ${currentPage2}/${totalPages2}</span>

		<c:if test="${currentPage2 < totalPages2}">
			<c:url value="ReqListController" var="nextPageUrl2">
				<c:param name="page2" value="${currentPage2 + 1}" />
			</c:url>
			<a href="${nextPageUrl2}">次のページへ</a>
		</c:if>
	</div>


	<!-- 社員画面へ遷移 -->
	<form action="BackController" method="get">
		<input type="submit" value="戻る">
	</form>

</body>
</html>
