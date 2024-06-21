//社員画面：出勤ボタン
//作成者：鈴木
//作成日時：2024/06/14

package servlet;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.StampBean;
import beans.UsersBean;
import logic.EmpLogic;

@WebServlet("/WorkInController")
public class WorkInController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WorkInController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		//セッションから利用者IDを取得
		HttpSession session = request.getSession();
		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();

		//現在の日付を取得
		Date date = new Date();

		//現在の時刻を取得
		LocalTime workIn_raw = LocalTime.now();

		//EmpLogicのインスタンス化
		EmpLogic empLogic = new EmpLogic();

		//勤怠の有無をチェック
		StampBean stampBean = empLogic.attCheck(users_id, date);
		System.out.println(stampBean.getWorkIn_raw());
		System.out.println(stampBean.getWorkOut_raw());

		if (stampBean.getWorkIn_raw() == null) {
			// 勤怠情報がない場合、登録処理を行う
			boolean result = empLogic.insertStamp(users_id, date, workIn_raw);

			if (result) {
				// 登録成功（成功表示＆社員画面リダイレクト）
				request.setAttribute("resultMsg", "出勤時刻を登録しました");
			} else {
				// 登録失敗（エラー表示＆社員画面リダイレクト）
				request.setAttribute("resultMsg", "出勤時刻の登録に失敗しました");
			}
		} else {
			// 勤怠情報が既に存在する場合、エラーメッセージを表示
			request.setAttribute("resultMsg", "既に出勤登録があります");
		}

		request.getRequestDispatcher("WEB-INF/jsp/usersMain.jsp").forward(request, response);
	}
}
