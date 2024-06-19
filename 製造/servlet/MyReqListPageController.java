//申請一覧テスト用コントローラ
package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Request;

/**
 * Servlet implementation class MyReqListPageController
 */
@WebServlet("/MyReqListPageController")
public class MyReqListPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyReqListPageController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int page = 1;
		int recordsPerPage = 5; // 申請一覧の1ページあたりの表示件数
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int page2 = 1;
		int recordsPerPage2 = 15; // 部下の申請一覧の1ページあたりの表示件数
		if (request.getParameter("page2") != null) {
			page2 = Integer.parseInt(request.getParameter("page2"));
		}

		// 申請一覧のデータ取得とページング処理
		List<Request> requestList = prepareDummyData();// 申請一覧のデータ取得処理
		int listSize = requestList.size();
		int start = (page - 1) * recordsPerPage;
		int end = Math.min(start + recordsPerPage, listSize);
		int totalPages = (int) Math.ceil((double) requestList.size() / recordsPerPage);
		List<Request> displayedRequests = requestList.subList(start, end);//範囲指定

		// 部下の申請一覧のデータ取得とページング処理
		List<Request> requestList2 = prepareDummyData();// 部下の申請一覧のデータ取得処理
		int listSize2 = requestList2.size();
		int start2 = (page2 - 1) * recordsPerPage2;
		int end2 = Math.min(start2 + recordsPerPage2, listSize2);
		int totalPages2 = (int) Math.ceil((double) requestList2.size() / recordsPerPage2);
		List<Request> displayedRequests2 = requestList2.subList(start2, end2);

		// リクエストスコープにセット
		request.setAttribute("requestList", displayedRequests);
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);

		request.setAttribute("requestList2", displayedRequests2);
		request.setAttribute("currentPage2", page2);
		request.setAttribute("totalPages2", totalPages2);

//		request.setAttribute("requestListBean", requestListBean);//自分が提出した申請
//		request.setAttribute("subrequestListBean", subrequestListBean);//自分宛ての申請

		

		
		// JSPにフォワード
		request.getRequestDispatcher("/WEB-INF/jsp/requestList.jsp").forward(request, response);

		// JSPにフォワード
		//		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/requestList.jsp");
		//		dispatcher.forward(request, response);
	}

	// ダミーデータを生成するメソッド（実際にはデータベースから取得するなどに置き換える）
	List<Request> prepareDummyData() {
		List<Request> requestList = new ArrayList<>();
		for (int i = 1; i <= 20; i++) {
			requestList.add(new Request("" + i, 0, 1, "上司１", "部下１"));
			requestList.add(new Request("" + i, 1, 0, "上司1", "部下2"));
			requestList.add(new Request("" + i, 1, 1, "上司1", "部下4"));
			requestList.add(new Request("" + i, 0, 0, "上司１", "部下１"));
		}
		return requestList;
	}
}
