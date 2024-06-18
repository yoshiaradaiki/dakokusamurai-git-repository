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

		// セッションから利用者IDを取得
		HttpSession session = request.getSession();
		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();

		// 現在時刻を取得
		Date date = new Date();

		// 現在時刻からLocalTimeを取得
		LocalTime workIn_raw = LocalTime.now();

		//登録処理（出勤ボタン押下時）
		EmpLogic empLogic = new EmpLogic();
		boolean result = empLogic.insertStamp(users_id, date, workIn_raw);

		if (result) {
			//登録成功（社員画面リダイレクト）
			request.getRequestDispatcher("WEB-INF/jsp/userMain.jsp").forward(request, response);
		} else {
			//登録失敗（エラー表示＆社員画面リダイレクト）
			request.setAttribute("errorMsg", "出勤時刻の登録に失敗しました。");
			request.getRequestDispatcher("WEB-INF/jsp/userMain.jsp").forward(request, response);
		}
	}
}
