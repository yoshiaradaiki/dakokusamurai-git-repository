//担当:長江
//2024/06/14
//勤怠状況詳細画面の変更申請ボタン
package servlet;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.StampBean;
import beans.UsersBean;
import logic.AttDetailLogic;


@WebServlet("/AttDetailRevRequestController")
public class AttDetailRevRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	// 文字列型をLocalTime型に変換
	private LocalTime stringToLocalTime(String s) {
		if (s != null) {
			return LocalTime.parse(s);
		} else {
			return null;
		}
	}
		
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		UsersBean sessionUsersBean = (UsersBean)session.getAttribute("sessionUsersBean");
		
		Date requestDate = (Date)request.getAttribute("requestDate");
		
		StampBean  stampBean= new StampBean();
		stampBean.setUsers_id(sessionUsersBean.getUsers_id());
		stampBean.setStamp_date(requestDate);
		
		int work_status = Integer.parseInt(request.getParameter("work_status"));
		
		LocalTime workIn_rev = stringToLocalTime(request.getParameter("workIn_rev"));
		
		LocalTime workOut_rev = stringToLocalTime(request.getParameter("workOut_rev"));
		
		LocalTime date = stringToLocalTime(request.getParameter("workOut_rev"));
		
		LocalTime rest_time = stringToLocalTime(request.getParameter("rest_time"));
		
		
		String note = request.getParameter("note");
		
		int status = 1;
		
		int content= request.getParameter("content"); 
				
		
//		打刻情報チェック
		AttDetailLogic attDetailLogic = new AttDetailLogic();	
		attDetailLogic.findStampCheck(sessionUsersBean.getUsers_id(),requestDate,stampBean);
		
		
		
//　　　打刻修正テーブルに登録
		boolean stampRevBean = new AttDetailLogic().insertStampRev(work_status,workIn_rev,workOut_rev,rest_time,note); 
		
		
		
		
//　　　打刻修正申請を提出　
		boolean requestListBean = new AttDetailLogic().insertStampRevReq(date_and_time,status,content);
		
		
		
					
		request.setAttribute(requestListBean,requestListBean);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/attendanceStatusDetail.jsp");
		dispatcher.forward(request, response);

	}

	

}
