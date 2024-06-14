//申請一覧画面：再申請ボタン
//機能：勤務状況詳細画面へ
//作成者：湯
//作成日：2024/6/14
package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.RequestListBean;
import beans.StampRevBean;
import beans.UsersBean;
import logic.RequestListLogic;

/**
 * Servlet implementation class RequestRequestController
 */
@WebServlet("/RequestRequestController")
public class RequestRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestRequestController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//セッションを取得
		HttpSession session = request.getSession();
		//セッションスコープから利用者IDを取得
		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");

		//"stamp_rev_id"を取得
		int stamp_rev_id = Integer.parseInt(request.getParameter("stamp_rev_id"));

		//------------------------------------------------------------------------------------//
		//DBから再提出ボタンを押下時の勤怠状況詳細データを取得する処理
		//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		//UsersBeanに利用者IDをセット
		UsersBean usersBean = new UsersBean();
		usersBean.setUsers_id(sessionUsersBean.getUsers_id());
		//取得した勤怠状況詳細IDによって差し戻された一ヶ月分の勤怠状況詳細データを表示する
		StampRevBean stampRevBean = new RequestListLogic().findAttStatusDetail(stamp_rev_id);
		//取得した勤怠状況詳細IDによって勤怠状況詳細に差し戻しの理由を取得し、表示する
		RequestListBean reqListBeanReason = new RequestListLogic().findAttDetailReason(stamp_rev_id);
		//------------------------------------------------------------------------------------//

		//JSPから取得するためにセットする
		request.setAttribute("usersBean", usersBean);//利用者ID
		request.setAttribute("stampRevBean", stampRevBean);//勤怠状況詳細
		request.setAttribute("reqListBeanReason", reqListBeanReason);//理由

		//"attendanceStatusDetail.jsp"へ転送する
		request.getRequestDispatcher("WEB-INF/jsp/attendanceStatusDetail.jsp").forward(request, response);
	}

}
