//担当:長江
//更新日：2024/06/17
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
		
//	　　セッションを取得
		HttpSession session = request.getSession();
		
//　　　自分の申請一覧		
		UsersBean sessionUsersBean = (UsersBean)session.getAttribute("sessionUsersBean");
//      セッションから利用者IDを取得		
		int users_id = sessionUsersBean.getUsers_id();
		
		AttDetailLogic attDetailLogic = new AttDetailLogic();	
		
//		hiddenから取得		
		int stamp_rev_req_id =Integer.parseInt( request.getParameter("stamp_rev_req_id"));//打刻修正申請IDを取得
		int status = 0;//差し戻し
		String reason = request.getParameter("reason");//差し戻し時は差し戻し理由が必要
		
//		打刻修正申請IDを実行し、打刻修正申請ID、ステータス、理由の結果を取得
		attDetailLogic.updateStampRevReq(stamp_rev_req_id, status, reason,users_id);

//　　　申請一覧を取得
		List<RequestListBean> myRequestListBean = attDetailLogic.findMyRequest(users_id);
		List<RequestListBean> subRequestListBean = attDetailLogic.findMySubRequest(users_id);
		
		
		
//　　　遷移先画面である申請一覧画面へ値を渡す					
		request.setAttribute("myRequestListBean", myRequestListBean);
		request.setAttribute("subRequestListBean", subRequestListBean);	
		
//		JSPからサーブレットへ転送		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/RequestList.jsp");
		dispatcher.forward(request, response);
	}

}
