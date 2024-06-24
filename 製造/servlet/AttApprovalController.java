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
		//勤怠状況表logic生成
		AttStatusLogic attStatusLogic = new AttStatusLogic();
		
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
		
		//利用者IDと勤怠状況表ID紐づけ
		int att_status_id = attStatusLogic.findAttStatusId(sub_users_id, date); 

		//申請一覧のステータスを2：承認済みにする
		int status = 2; 
		//承認時は理由は不要
		String reason = ""; 
		//一覧に勤怠状況表ID・ステータス・理由を更新
		attStatusLogic.updateMonthReq(att_status_id, status, reason, users_id);
		
		
		//-------------------------申請一覧に上司部下情報を渡す---------------------------------
		//自分の申請一覧にsessionのusers_idを渡す
		List<RequestListBean> myRequestListBeans = attStatusLogic.findMyRequest(users_id);
		//リクエストに自分の申請一覧をセット
		request.setAttribute("myRequestListBeans", myRequestListBeans);

		
		//--------------------------部下の申請一覧----------------------------------------------

		
		//自分の申請一覧にsessionのusers_idを渡す
		List<RequestListBean> mySubRequestListBeans = attStatusLogic.findMySubRequest(users_id);
		//リクエストに部下の申請一覧をセット
		request.setAttribute("mySubRequestListBeans", mySubRequestListBeans);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/requestList.jsp");
		dispatcher.forward(request, response); 

	}

}
//破棄
//UsersBean sessionUsersBean = (UsersBean)session.getAttribute("sessionUsersBean");
//int users_id = sessionUsersBean1.getUsers_id();
////★★上記のセッションは同じ処理のため破棄していいのでは？
//破棄
//勤怠状況表IDを取得
//int att_status_id =Integer.parseInt( request.getParameter("att_status_id"));
