//申請一覧画面：再提出ボタン
//機能：勤務状況表画面へ
//作成者：湯
//作成日：2024/6/14
package servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
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
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		//セッションスコープから利用者IDを取得
		//		HttpSession session = request.getSession();
		//		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");
		//		int users_id = sessionUsersBean.getUsers_id();
		int att_status_id = Integer.parseInt(request.getParameter("att_status_id"));

		//勤怠状況表IDで利用者IDと年月を取得する
		UsersBean usersBean = new RequestListLogic().findMyAttStatusUsers(att_status_id);
		System.out.println("取得した利用者IDは" + usersBean.getUsers_id());
		//取得した利用者IDと年月をゲット
		Date year_and_month = (Date) usersBean.getYear_and_month();
		int users_id = usersBean.getUsers_id();
		//勤怠状況表年月設定
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(year_and_month);
		// 年を取得
		int year = calendar.get(Calendar.YEAR);
		// 月を取得
		int month = calendar.get(Calendar.MONTH) + 1;

		request.setAttribute("year", year);
		request.setAttribute("month", month);
		

		System.out.println("取得した利用者IDは" + users_id);
		System.out.println("取得した勤怠状況表IDは" + att_status_id);
		System.out.println("取得した勤怠状況表の年月は" + year_and_month);

		//------------------------------------------------------------------------------------//
		//DBから再提出ボタンを押下時の勤怠状況表を取得する処理
		//↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

		//取得した勤怠状況表IDによって差し戻された一ヶ月分の勤怠状況表を表示する
		List<StampBean> stampBean = new RequestListLogic().findAttStatus(users_id, year_and_month);
		System.out.println("stampBean"+stampBean.get(0).getStamp_id());
		//取得した勤怠状況表IDによって勤怠状況表に差し戻しの理由を取得し、表示する
		RequestListBean requestListBean = new RequestListLogic().findAttStatusMonthReason(att_status_id);
		//------------------------------------------------------------------------------------//
		//フォーム切り替えのリクエストセット　申請フォーム：0
		int formstatus = 3;
		request.setAttribute("formstatus", formstatus);
		//JSPから取得するためにセットする
		request.setAttribute("usersBean", usersBean);//利用者ID
		request.setAttribute("stampBean", stampBean);//勤怠状況表
		request.setAttribute("requestListBean", requestListBean);//理由
		request.setAttribute("att_status_id", att_status_id);//理由

		//"attendanceStatus.jsp"へ転送する
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/attendanceStatus.jsp");
		dispatcher.forward(request, response);

	}

}
