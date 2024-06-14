//社員画面：申請一覧ボタン押下時の処理
package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.RequestListBean;

/**
 * Servlet implementation class ReqListController
 */
@WebServlet("/ReqListController")
public class ReqListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReqListController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 申請一覧を取得する処理（例としてダミーデータを使用）
		List<RequestListBean> requestList = findMyRequest();

		// リクエスト属性に申請一覧を設定する
		request.setAttribute("requestList", requestList);

		// JSPにフォワードする
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/requestList.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/requestList.jsp");
		dispatcher.forward(request, response);
	}

	//ダミーデータ　元：EmｐLogic.findMyRequest(int users_id)
	private List<RequestListBean> findMyRequest() {
		// 申請一覧を取得する処理（ここではダミーデータを返す）
		// 実際の場合はデータベースから取得する処理が必要
		List<RequestListBean> requestList = new ArrayList<>();
		// ダミーデータを追加
		String dateText = "2024年12月25日";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日", Locale.JAPANESE);
        LocalDate date = LocalDate.parse(dateText, formatter);
		requestList.add(new RequestListBean(date, 1, 0, "承認者"));
		requestList.add(new RequestListBean(date, 1, 0, "承認者"));
		requestList.add(new RequestListBean(date, 1, 0, "承認者"));
		requestList.add(new RequestListBean(date, 1, 0, "承認者"));
		requestList.add(new RequestListBean(date, 1, 0, "承認者"));

		return requestList;
	}

}
