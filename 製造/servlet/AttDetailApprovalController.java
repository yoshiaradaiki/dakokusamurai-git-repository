//担当:長江
//2024/06/14
//勤怠状況詳細の承認ボタン

package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		request.setAttribute(, );
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/attendanceStatusDetail.jsp");
		dispatcher.forward(request, response);

								
	}

}
