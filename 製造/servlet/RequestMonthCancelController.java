//申請一覧画面：キャンセルボタン（月末申請）
//機能：月末申請をキャンセルし、申請画面再描画する
//作成者：湯
//作成日：2024/6/14
package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.RequestListBean;
import beans.UsersBean;
import logic.EmpLogic;
import logic.RequestListLogic;

/**
 * Servlet implementation class RequestMonthCancelController
 */
@WebServlet("/RequestMonthCancelController")
public class RequestMonthCancelController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestMonthCancelController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//セッションスコープから利用者IDを取得
		HttpSession session = request.getSession();
		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();
		//session.setAttribute("sessionUsersBean", sessionUsersBean);
		System.out.println(users_id + "さんの月末申請をキャンセルしました");

		//月末申請IDを取得
		int month_req_id = Integer.parseInt(request.getParameter("month_req_id"));

		System.out.println("月末申請IDは" + month_req_id);
		//取得した月末申請IDによって、月末申請をキャンセルする（月末申請.ステータスを3に更新、更新者を利用者IDに更新する）
		// 月末申請をキャンセルする操作を行うためのロジッククラスのインスタンスを作成
		RequestListLogic requestListLogic = new RequestListLogic();

		
		//差し戻しの理由を取得
		//勤怠状況表IDを取得
		int att_status_id = Integer.parseInt(request.getParameter("att_status_id"));//理由を取得するため
		RequestListBean reqBean = requestListLogic.findAttStatusMonthReason(att_status_id);//理由を取得するメソッド
		String reason = reqBean.getReason();
		System.out.println("取得した理由は" + reason);
		//差し戻しの理由を取得
		
		

		int status = 3;
		// 月末申請のキャンセル操作を実行し、その結果を取得
		Boolean isCancelled = requestListLogic.updateReqCancelByAttStatus(month_req_id, status, reason, users_id);

		System.out.println(month_req_id + "勤怠状況表IDをキャンセルしました");

		// キャンセル操作の結果をリクエストスコープに設定
		request.setAttribute("isCancelled", isCancelled);
		request.setAttribute("sessionUsersBean", sessionUsersBean);
		//request.setAttribute("resultMsg", "月末申請をキャンセルしました。");

		//********************　 ページング　********************//
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
		//********************　 ページング　********************//

		EmpLogic empLogic = new EmpLogic();

		//自分が提出した申請を表示
		List<RequestListBean> requestListBean = empLogic.findMyRequest(users_id);

		//System.out.println("requestListBeanは" + requestListBean != null);

		//********************　 ページング　********************//
		if (requestListBean != null) {
			int listSize = requestListBean.size();

			int start = (page - 1) * recordsPerPage;
			int end = Math.min(start + recordsPerPage, listSize);
			int totalPages = (int) Math.ceil((double) requestListBean.size() / recordsPerPage);
			List<RequestListBean> displayedRequests = requestListBean.subList(start, end);//範囲指定

			//********************　 ページング　********************//
			//自分宛ての申請を表示
			List<RequestListBean> subrequestListBean = empLogic.findMySubRequest(users_id);

			//********************　 ページング　********************//
			int listSize2 = subrequestListBean.size();
			int start2 = (page2 - 1) * recordsPerPage2;
			int end2 = Math.min(start2 + recordsPerPage2, listSize2);
			int totalPages2 = (int) Math.ceil((double) subrequestListBean.size() / recordsPerPage2);
			List<RequestListBean> displayedRequests2 = subrequestListBean.subList(start2, end2);//範囲指定

			//********************　 ページング　********************//

			//JSPで取得する属性を入れる
			//		request.setAttribute("requestListBean", requestListBean);//自分が提出した申請
			//		request.setAttribute("subrequestListBean", subrequestListBean);//自分宛ての申請

			//********************　 ページング　********************//
			request.setAttribute("requestList", displayedRequests);
			request.setAttribute("currentPage", page);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("requestList2", displayedRequests2);
			request.setAttribute("currentPage2", page2);
			request.setAttribute("totalPages2", totalPages2);
			//********************　 ページング　********************//
		}

		// 同じJSPページにフォワードする
		request.getRequestDispatcher("/WEB-INF/jsp/requestList.jsp").forward(request, response);
	}

}
