package servlet;

import java.io.IOException;
import java.util.Date; // Dateクラスをインポート

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.EmpLogic;

@WebServlet("/WorkOutController")
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
		int users_id = (int) session.getAttribute("users_id");

		// 現在時刻を取得
		Date date = new Date();

	}

}