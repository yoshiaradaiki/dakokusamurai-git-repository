//社員画面：勤怠状況表ボタン
//作成者：鈴木
//作成日時：2024/06/14

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
import javax.servlet.http.HttpSession;

import beans.RequestListBean;
import beans.StampBean;
import beans.UsersBean;
import logic.AttStatusLogic;

@WebServlet("/AttStatusController")
public class AttStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		// セッションから利用者IDを取得
		HttpSession session = request.getSession();
		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();

		//現在の日付を取得
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		// 今日の日付を1日にする
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		// 先月の日付に変更する
		calendar.add(Calendar.MONTH, -1);
		// 変更後の日付を取得
		date = calendar.getTime();
		// 年を取得
		int year = calendar.get(Calendar.YEAR);
		// 月を取得
		int month = calendar.get(Calendar.MONTH);
		
		request.setAttribute("year", year);
		request.setAttribute("month", month);

		// AttStatusLogic のインスタンスを生成
		AttStatusLogic attStatusLogic = new AttStatusLogic();

		//勤怠状況表の利用者取得
		UsersBean usersBean = attStatusLogic.findMyAttStatusUsers(users_id);
		usersBean.setYear_and_month(date);
		

		//勤怠状況表の表示
		List<StampBean> stampBeans = attStatusLogic.findMyAttStatusMonthStamp(users_id, date);
		//6/20　横山追加
		//フォーム切り替えのリクエストセット　申請フォーム：0
		int formstatus =0;
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
