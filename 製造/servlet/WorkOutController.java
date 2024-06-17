//社員画面：退勤ボタン
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

@WebServlet("/WolkOutController")
public class WorkOutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public WorkOutController() {
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
		LocalTime workOut_raw = LocalTime.now();

		//登録処理
		EmpLogic empLogic = new EmpLogic();
		boolean result = empLogic.updateStamp(users_id, date, workOut_raw);

		if (result) {
			//登録成功（社員画面リダイレクト）
			request.getRequestDispatcher("userMain.jsp").forward(request, response);
		} else {
			//登録失敗（エラー表示＆社員画面リダイレクト）
			request.setAttribute("errorMsg", "出勤時刻の登録に失敗しました。");
			request.getRequestDispatcher("WEB-INF/jsp/userMain.jsp").forward(request, response);
		}
	}
}
