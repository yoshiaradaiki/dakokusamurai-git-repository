package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.UsersBean;
import logic.LoginLogic;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// フォーム情報からlogin_idとpasswordを取得
		String login_id = request.getParameter("login_id");
		String password = request.getParameter("password");
		// ログインチェックとセッションに入れる情報の取得
		UsersBean usersBean = new LoginLogic().findLoginCheck(login_id, password);
		RequestDispatcher rd = null;
		if(usersBean != null) {
			// ログイン成功時処理
			// SessionにUsersBeanの情報を設定
			HttpSession session = request.getSession();
			session.setAttribute("sessionUsersBean", usersBean);
			if (usersBean.getLevel() == 1) {
				// 社員画面
				rd = request.getRequestDispatcher("社員画面名");
			} else {
				// 管理者画面（仮）
				request.setAttribute("errorMsg", "管理者画面はありません。");
				rd = request.getRequestDispatcher("index.jsp");
			}
		} else {
			// ログイン失敗時処理
			request.setAttribute("login_id", login_id);
			request.setAttribute("errorMsg", "ログインIDまたはパスワードに間違いがあります。");
			rd = request.getRequestDispatcher("index.jsp");
		}
		// フォワード
		rd.forward(request, response);		
	}

}
