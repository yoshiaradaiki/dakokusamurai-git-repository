//社員画面：申請一覧ボタン
//作成者：鈴木
//作成日時：2024/06/14

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
import beans.UsersBean;
import logic.EmpLogic;

@WebServlet("/RequestController")
public class RequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		// セッションから利用者IDを取得
		HttpSession session = request.getSession();
		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();

		EmpLogic empLogic = new EmpLogic();

		//自分が提出した申請を表示
		List<RequestListBean> requestListBean = empLogic.findMyRequest(users_id);

		//自分宛ての申請を表示
		List<RequestListBean> subrequestListBean = empLogic.findMySubRequest(users_id);

		//JSPで取得する属性を入れる
		request.setAttribute("requestListBean", requestListBean);//自分が提出した申請
		request.setAttribute("subrequestListBean", subrequestListBean);//自分宛ての申請

		//"attendanceStatus.jsp"へ送る
		request.getRequestDispatcher("WEB-INF/jsp/requestList.jsp").forward(request, response);
	}
}
