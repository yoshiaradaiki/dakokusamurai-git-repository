//作成者：横山
//作成日：6/17

package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import logic.AttStatusLogic;


//部下の月末申請を承認する
//承認ボタン押下処理
@WebServlet("/AttApprovalController")
public class AttApprovalController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//--------------------------session-----------------------------------
		HttpSession session = request.getSession();
		UsersBean sessionUsersBean = (UsersBean)session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();
		
		//-------------------------logicに値をセットする---------------------------------
		
		//利用者ID・パラメーター年月
		int sub_users_id = Integer.parseInt(request.getParameter("users_id")) ;
		String year = request.getParameter("year");
		String month = request.getParameter("month");
		String dateString = year + "-" + month + "-1"; //1日
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = null;
		
	    try {
			date = dateFormat.parse(dateString);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	    //勤怠状況表logic生成
	    AttStatusLogic attStatusLogic = new AttStatusLogic();
		
//		//利用者IDと勤怠状況表ID紐づけ
//		int month_req_id = attStatusLogic.findAttStatusId(sub_users_id, date); 
	    int month_req_id = Integer.parseInt(request.getParameter("month_req_id")); 

		//申請一覧のステータスを2：承認済みにする
		int status = 2; 
		//承認時は理由は不要
		String reason = ""; 
		//一覧に勤怠状況表ID・ステータス・理由を更新
		attStatusLogic.updateMonthReq(month_req_id, status, reason, users_id);
		
		
		//-------------------------申請一覧に上司部下情報を渡す---------------------------------
		//自分の申請一覧にsessionのusers_idを渡す
		List<RequestListBean> myRequestListBeans = attStatusLogic.findMyRequest(users_id);
		//********************　 ページング　********************//
		int page = 1;
		int recordsPerPage = 5; // 申請一覧の1ページあたりの表示件数
		int listSize = myRequestListBeans.size();
		int start = (page - 1) * recordsPerPage;
		int end = Math.min(start + recordsPerPage, listSize);
		int totalPages = (int) Math.ceil((double) myRequestListBeans.size() / recordsPerPage);
		List<RequestListBean> displayedRequests = myRequestListBeans.subList(start, end);//範囲指定
		
		request.setAttribute("requestList", displayedRequests);
		request.setAttribute("currentPage", page);
		request.setAttribute("totalPages", totalPages);
		//********************　 ページング　********************//
		
		
		//--------------------------部下の申請一覧----------------------------------------------

		
		//自分の申請一覧にsessionのusers_idを渡す
		List<RequestListBean> mySubRequestListBeans = attStatusLogic.findMySubRequest(users_id);
		
		//********************　 ページング　********************//
		int page2 = 1;
		int recordsPerPage2 = 15; // 部下の申請一覧の1ページあたりの表示件数
		if (request.getParameter("page2") != null) {
			page2 = Integer.parseInt(request.getParameter("page2"));
		}
		int listSize2 = mySubRequestListBeans.size();
		int start2 = (page2 - 1) * recordsPerPage2;
		int end2 = Math.min(start2 + recordsPerPage2, listSize2);
		int totalPages2 = (int) Math.ceil((double) mySubRequestListBeans.size() / recordsPerPage2);
		List<RequestListBean> displayedRequests2 = mySubRequestListBeans.subList(start2, end2);//範囲指定
		
		request.setAttribute("requestList2", displayedRequests2);
		request.setAttribute("currentPage2", page2);
		request.setAttribute("totalPages2", totalPages2);
		//********************　 ページング　********************//

		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/requestList.jsp");
		dispatcher.forward(request, response); 

	}

}
//破棄
//リクエストに自分の申請一覧をセット
//request.setAttribute("myRequestListBeans", myRequestListBeans);
//リクエストに部下の申請一覧をセット
//request.setAttribute("mySubRequestListBeans", mySubRequestListBeans);
//UsersBean sessionUsersBean = (UsersBean)session.getAttribute("sessionUsersBean");
//int users_id = sessionUsersBean1.getUsers_id();
////★★上記のセッションは同じ処理のため破棄していいのでは？
//破棄
//勤怠状況表IDを取得
//int att_status_id =Integer.parseInt( request.getParameter("att_status_id"));
