package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.AttDetailLogic;


//部下の月末申請を承認する
//承認ボタン押下処理

@WebServlet("/AttApprovalController")
public class AttApprovalController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
//      部下の月末申請を承認する
		AttDetailLogic attDetailLogic = new AttDetailLogic();
		
		int stamp_rev_req_id =Integer.parseInt( request.getParameter("stamp_rev_req_id"));
		int status = 2; //承認済み
		String reason = "";  //承認時は理由は不要
		
		attDetailLogic.updateStampRevReq(stamp_rev_req_id, status, reason);
		
		
	}

}
