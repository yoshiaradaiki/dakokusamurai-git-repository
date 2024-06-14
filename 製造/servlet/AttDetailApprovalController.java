//担当:長江
//2024/06/14
//勤怠状況詳細の承認ボタン

package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.RequestListBean;
import beans.UsersBean;
import logic.AttDetailLogic;


@WebServlet("/AttDetailApprovalController")
public class AttDetailApprovalController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		
//        部下の打刻修正申請を承認する
		
		AttDetailLogic attDetailLogic = new AttDetailLogic();
		
		int stamp_rev_req_id =Integer.parseInt( request.getParameter("stamp_rev_req_id"));
		int status = 2; //承認済み
		String reason = "";  //承認時は理由は不要
		
		attDetailLogic.updateStampRevReq(stamp_rev_req_id, status, reason);
		
        HttpSession session = request.getSession();
        
//		自分の申請一覧
		UsersBean sessionUsersBean = (UsersBean)session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();
		List<RequestListBean> myRequestListBeans = attDetailLogic.findMyRequest(users_id);
		
		request.setAttribute("myRequestListBeans", myRequestListBeans);

//		部下の申請一覧
		UsersBean sessionUsersBean = (UsersBean)session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();
		List<RequestListBean> mySubRequestListBeans = attDetailLogic.findMySubRequest(users_id);
		
		request.setAttribute("mySubRequestListBeans", mySubRequestListBeans);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/requestList.jsp");
		dispatcher.forward(request, response);

								
	}

}
