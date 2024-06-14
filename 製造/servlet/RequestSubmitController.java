//申請一覧画面：再提出ボタン
//機能：勤務状況表画面へ
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
import beans.StampBean;
import beans.UsersBean;
import logic.RequestListLogic;

/**
 * Servlet implementation class RequestSubmitController
 */
@WebServlet("/RequestSubmitController")
public class RequestSubmitController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestSubmitController() {
		super();
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
		//UsersBeanから利用者IDを取得
		//int users_id = sessionUsersBean.getUsers_id();
		//"att_status_id"勤怠状況表IDパラメータを文字列として取得し、Integer.parseInt()を利用して整数に変換する
		int att_status_id = Integer.parseInt(request.getParameter("att_status_id"));

		//------------------------------------------------------------------------------------//
		//DBから再提出ボタンを押下時の勤怠状況表を取得する処理
		//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
		//UsersBeanに利用者IDをセット
		UsersBean usersBean = new UsersBean();
		usersBean.setUsers_id(sessionUsersBean.getUsers_id());
		//取得した勤怠状況表IDによって差し戻された一ヶ月分の勤怠状況表を表示する
		List<StampBean> stampBean = new RequestListLogic().findAttStatus(att_status_id);
		//取得した勤怠状況表IDによって勤怠状況表に差し戻しの理由を取得し、表示する
		RequestListBean reqListBeanReason = new RequestListLogic().findAttStatusMonthReason(att_status_id);
		//------------------------------------------------------------------------------------//

		//JSPから取得するためにセットする
		request.setAttribute("usersBean", usersBean);//利用者ID
		request.setAttribute("attStatusBean", stampBean);//勤怠状況表
		request.setAttribute("reqListBeanReason", reqListBeanReason);//理由
		
		//"attendanceStatus.jsp"へ転送する
		request.getRequestDispatcher("WEB-INF/jsp/attendanceStatus.jsp").forward(request, response);
	}

}
