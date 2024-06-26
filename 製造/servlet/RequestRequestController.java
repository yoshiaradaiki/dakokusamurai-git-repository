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

import beans.RequestListBean;
import beans.StampBean;
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
		response.setContentType("text/html; charset=utf-8");

//		//セッション取得
//		HttpSession session = request.getSession();
//		//取得したsession情報をUserBeanに渡す
//		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");

		//リクエストから情報取得
		int stamp_rev_id = Integer.parseInt(request.getParameter("stamp_rev_id"));

		//インスタンス
		RequestListLogic reqLogic = new RequestListLogic();
		//部下の利用者ID、氏名を取得
		UsersBean usersBean = reqLogic.findUsersStampRevId(stamp_rev_id);
		System.out.println("取得した部下の利用者IDは" + usersBean.getUsers_id());
		System.out.println("取得した部下の氏名は" + usersBean.getEmp_name());
		//打刻修正データを取得
		StampBean stampBean = reqLogic.findStampRev(stamp_rev_id);
		//差し戻しの理由を取得
		RequestListBean reqBean = reqLogic.findAttDetailReason(stamp_rev_id);

		//JSPから取得するためにセットする
		request.setAttribute("formstatus", 0);//0：申請フォーム
		request.setAttribute("usersBean", usersBean);//利用者ID
		request.setAttribute("stampBean", stampBean);//勤怠状況詳細
		request.setAttribute("requestListBean", reqBean);//理由

		//"attendanceStatusDetail.jsp"へ転送する
		request.getRequestDispatcher("WEB-INF/jsp/attendanceStatusDetail.jsp").forward(request, response);
	}

}
