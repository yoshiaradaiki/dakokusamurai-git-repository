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

@WebServlet("/ReqListController")
public class ReqListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		//********************　 ページング　********************//
		int page = 1;
		int recordsPerPage = 5; // 申請一覧の1ページあたりの表示件数
		if (request.getParameter("page") != null) {
			page = Integer.parseInt(request.getParameter("page"));
		}

		int page2 = 1;
		int recordsPerPage2 = 15; // 部下の申請一覧の1ページあたりの表示件数
		if (request.getParameter("page2") != null) {
			page2 = Integer.parseInt(request.getParameter("page2"));
		}
		//********************　 ページング　********************//

		//--------セッション利用者IDゲット----------//
		HttpSession session = request.getSession();
		UsersBean sessionUsersBean = (UsersBean) session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();
		session.setAttribute("sessionUsersBean", sessionUsersBean);
		//--------セッション利用者ID----------//

		EmpLogic empLogic = new EmpLogic();

		//自分が提出した申請を表示
		List<RequestListBean> requestListBean = empLogic.findMyRequest(users_id);

		//System.out.println("requestListBeanは" + requestListBean != null);

		//********************　 ページング　********************//
		if (requestListBean != null) {
			int listSize = requestListBean.size();

			int start = (page - 1) * recordsPerPage;
			int end = Math.min(start + recordsPerPage, listSize);
			int totalPages = (int) Math.ceil((double) requestListBean.size() / recordsPerPage);
			List<RequestListBean> displayedRequests = requestListBean.subList(start, end);//範囲指定

			//********************　 ページング　********************//
			//自分宛ての申請を表示
			List<RequestListBean> subrequestListBean = empLogic.findMySubRequest(users_id);

			//********************　 ページング　********************//
			int listSize2 = subrequestListBean.size();
			int start2 = (page2 - 1) * recordsPerPage2;
			int end2 = Math.min(start2 + recordsPerPage2, listSize2);
			int totalPages2 = (int) Math.ceil((double) subrequestListBean.size() / recordsPerPage2);
			List<RequestListBean> displayedRequests2 = subrequestListBean.subList(start2, end2);//範囲指定

			//********************　 ページング　********************//

			//JSPで取得する属性を入れる
			//		request.setAttribute("requestListBean", requestListBean);//自分が提出した申請
			//		request.setAttribute("subrequestListBean", subrequestListBean);//自分宛ての申請

			//********************　 ページング　********************//
			request.setAttribute("requestList", displayedRequests);
			request.setAttribute("currentPage", page);
			request.setAttribute("totalPages", totalPages);
			request.setAttribute("requestList2", displayedRequests2);
			request.setAttribute("currentPage2", page2);
			request.setAttribute("totalPages2", totalPages2);
			//********************　 ページング　********************//

		}

		//"attendanceStatus.jsp"へ送る
		request.getRequestDispatcher("WEB-INF/jsp/requestList.jsp").forward(request, response);

	}
}
