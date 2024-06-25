//申請一覧画面：提出(申請)詳細ボタン（勤怠状況表）
//機能：部下から提出した勤務状況表画面へ
//作成者：湯
//作成日：2024/6/14
package servlet;

import java.io.IOException;
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
import logic.AttStatusLogic;
import logic.RequestListLogic;

/**
 * Servlet implementation class RequestDetailController
 */
@WebServlet("/RequestDetailController")
public class RequestDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RequestDetailController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		// セッションから利用者IDを取得
//		HttpSession session = request.getSession();
//		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");
		// int users_id = sessionUsersBean.getUsers_id();
		
		// 勤怠状況表、月末申請の各主キーをリクエストから取得
		int att_status_id = Integer.parseInt(request.getParameter("att_status_id"));
//		int month_req_id = Integer.parseInt(request.getParameter("month_req_id"));

		// 渡すデータは選択した申請した年月
		// 勤怠状況表IDから
		//現在の日付を取得
//		Date date = new Date();
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(date);
//		// 今日の日付を1日にする
//		calendar.set(Calendar.DAY_OF_MONTH, 1);
//		// 先月の日付に変更する
//		calendar.add(Calendar.MONTH, -1);
//		// 変更後の日付を取得
//		date = calendar.getTime();
//		// 年を取得
//		int year = calendar.get(Calendar.YEAR);
//		// 月を取得
//		int month = calendar.get(Calendar.MONTH) + 1;;
//		
//		request.setAttribute("year", year);
//		request.setAttribute("month", month);

		// AttStatusLogic のインスタンスを生成
		AttStatusLogic attStatusLogic = new AttStatusLogic();
		RequestListLogic requestListLogic = new RequestListLogic();
		
		//勤怠状況表の利用者取得
		UsersBean usersBean = requestListLogic.findMyAttStatusUsers(att_status_id);

		//勤怠状況表の利用者取得
//		UsersBean usersBean = attStatusLogic.findMyAttStatusUsers(users_id);
//		usersBean.setYear_and_month(date);
		
		Date date = usersBean.getYear_and_month();
		int users_id = usersBean.getUsers_id();
		
		//勤怠状況表の表示
		List<StampBean> stampBeans = attStatusLogic.findMyAttStatusMonthStamp(users_id, date);
		//6/20　横山追加
		//フォーム切り替えのリクエストセット　申請フォーム：0
		int formstatus = 1;
		request.setAttribute("formstatus",formstatus);
	
		//差し戻し理由を一覧から取得
		RequestListBean requestListBean = attStatusLogic.findMyAttStatusMonthRequest(users_id, date);

		//JSPで取得する属性を入れる
		request.setAttribute("usersBean", usersBean);//利用者ID
		request.setAttribute("stampBeans", stampBeans);//勤怠状況表
		request.setAttribute("requestListBean", requestListBean); //理由

		//"attendanceStatus.jsp"へ送る	
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/attendanceStatus.jsp");
		dispatcher.forward(request, response);
	}

}
