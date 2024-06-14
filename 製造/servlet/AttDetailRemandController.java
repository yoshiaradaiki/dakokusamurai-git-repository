//担当:長江
//2024/06/14
//勤怠状況詳細の差し戻しボタン



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

/**
 * Servlet implementation class AttDetailRemandController
 */
@WebServlet("/AttDetailRemandController")
public class AttDetailRemandController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		
		AttDetailLogic attDetailLogic = new AttDetailLogic();	
		
		
		int stamp_rev_req_id =Integer.parseInt( request.getParameter("stamp_rev_req_id"));
		int status = 0;//差し戻し
		String reason = "";//差し戻し時は差し戻し理由が必要
		
		attDetailLogic.updateStampRevReq(stamp_rev_req_id, status, reason);
		
		HttpSession session = request.getSession();
		
//　　　自分の申請一覧		
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
