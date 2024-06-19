package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Request;

/**
 * Servlet implementation class MySubReqListPageController
 */
@WebServlet("/MySubReqListPageController")
public class MySubReqListPageController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MySubReqListPageController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ダミーデータの生成
		MyReqListPageController myReqListPC = new MyReqListPageController();
		List<Request> requestList2 = myReqListPC.prepareDummyData();
		//List<Request> requestListSub = prepareDummyData();

		// ページングのためのパラメータ
		int page2 = 1;
		int recordsPerPage2 = 15; // 1ページあたりの表示件数
		if (request.getParameter("page2") != null) {
			page2 = Integer.parseInt(request.getParameter("page2"));
		}

		// ページングのための計算
		int start2 = (page2 - 1) * recordsPerPage2;
		int end2 = Math.min(start2 + recordsPerPage2, requestList2.size());
		int totalPages2 = (int) Math.ceil((double) requestList2.size() / recordsPerPage2);

		// 表示するリクエストリストをセット
		List<Request> displayedRequests2 = requestList2.subList(start2, end2);

		// リクエストスコープにリクエストリストとページ情報をセット
		request.setAttribute("requestList2", displayedRequests2);
		request.setAttribute("currentPage2", page2);
		request.setAttribute("totalPages2", totalPages2);

		// JSPにフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/requestList.jsp");
		dispatcher.forward(request, response);
	}

//	private List<Request> subDummyData() {
//		List<Request> requestList = new ArrayList<>();
//		for (int i = 1; i <= 20; i++) {
//			requestList.add(new Request("" + i, 0, 0, "部下１","上司１"));
//			requestList.add(new Request("" + i, 1, 1, "部下１","上司１"));
//			requestList.add(new Request("" + i, 1, 2, "部下１","上司１"));
//			requestList.add(new Request("" + i, 1, 3, "部下１","上司１"));
//		}
//		return requestList;
//	}
}
