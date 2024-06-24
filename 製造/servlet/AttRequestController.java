//作成者：横山
//作成日：6/17

package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.RequestListBean;
import beans.UsersBean;
import logic.AttStatusLogic;


//勤怠状況表申請ボタン押下処理
//勤怠状況表の作成　インサート処理

@WebServlet("/AttRequestController")
public class AttRequestController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//--------------セッション------------------------------
		//session情報取得
		HttpSession session = request.getSession();
		//取得したsession情報をUserBeanに渡す
		UsersBean sessionUsersBean =(UsersBean)session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();
		
		//--------------リクエスト------------------------------
		
		//String➡Date変換
		 try {
//			 	String dateString = request.getParameter("date");
			String year = request.getParameter("year");
			String month = request.getParameter("month");
			String dateString = year + "-" + month + "-1"; //1日
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = dateFormat.parse(dateString);
			System.out.println(date);    
			    
				String reason = "";  //承認時は理由は不要
			    //再申請場合　
				if(!(reason.equals(""))) {
					//理由が記入された場合
					  reason += reason;
					  System.out.println(reason);
				}else {
					//理由が空の場合
					System.out.println(reason);
				}
				
				//--------------logic------------------------------
				
				AttStatusLogic attStatusLogic = new AttStatusLogic();

				
				//勤怠状況表　DBに一月分のデータを挿入⑦
				int att_status_id =attStatusLogic.insertAttStatus(users_id, date);
				System.out.println(att_status_id);
				//申請一覧のステータス　1：承認まち
				int status = 1;
				//勤怠状況表　月末申請に登録⑥
				attStatusLogic.insertMonthReq(att_status_id, status,users_id,users_id);
				
			    // 変換成功した場合の処理
			} catch (ParseException e) {
			    e.printStackTrace(); // エラーをログに出力するなど、適切なエラーハンドリングを行う
			    // 変換失敗時の処理
			} 
		 
		
		//自分の申請一覧--------------------------------------------------------------
		//自分の申請一覧にsessionのusers_idを渡す
		 AttStatusLogic attStatusLogic = new AttStatusLogic();
		 
		List<RequestListBean> myRequestListBeans = attStatusLogic.findMyRequest(users_id);
		//リクエストに自分の申請一覧をセット
		request.setAttribute("myRequestListBeans", myRequestListBeans);

		
		//部下の申請一覧--------------------------------------------------------------

		//自分の申請一覧にsessionのusers_idを渡す
		List<RequestListBean> mySubRequestListBeans = attStatusLogic.findMySubRequest(users_id);
		//リクエストに部下の申請一覧をセット
		request.setAttribute("mySubRequestListBeans", mySubRequestListBeans);
		
		//申請一覧遷移
		request.getRequestDispatcher("WEB-INF/jsp/requestList.jsp").forward(request, response);

	}

}
//破棄
//UsersBean sessionUsersBean = (UsersBean)session.getAttribute("sessionUsersBean");
//int users_id = sessionUsersBean1.getUsers_id();
////★★上記のセッションは同じ処理のため破棄していいのでは？

//破棄
//勤怠状況表Id 取得しsetする
//heddenから勤怠状況表IDを取得
//int att_status_id  =Integer.parseInt( request.getParameter("att_status_id"));
//取得した勤怠状況表IDを利用者IDと日時を元に取得
//int att_status_id = attStatusLogic.findAttStatusId(users_id,date);
