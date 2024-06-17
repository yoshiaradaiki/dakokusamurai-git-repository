package servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.RequestListBean;
import beans.StampBean;
import beans.UsersBean;
import logic.AttStatusLogic;


//編集ボタン押下処理
//勤怠状況詳細へ画面遷移する
//渡したい情報：年月

@WebServlet("/AttEditController")
public class AttEditController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=utf-8");

		//--------------セッション------------------------------
		//session情報取得
		HttpSession session = request.getSession();
		//取得したsession情報をUserBeanに渡す
		UsersBean sessionUsersBean =(UsersBean)session.getAttribute("sessionUsersBean");
		int users_id = sessionUsersBean.getUsers_id();
		
		//-----------------------フォーム情報の取得と年月処理---------------------------------
		//フォーム情報の取得
		int year =Integer.parseInt(request.getParameter("year")) ;
		int month = Integer.parseInt(request.getParameter("month")) ;
		//Dateに年・月・日を渡す
		//1月は0のため-1、1日が欲しいので1を入力している
		Date date = new Date(year,month-1,1);
		
		//-------------------------AttStatuLogicのメソッド取得---------------------------------
		//AttStatusLogi インスタンス作成 
		AttStatusLogic attStatusLogic = new AttStatusLogic();
		
		//メソッド
		//利用者を取得するlogic①
		UsersBean attDetailUsers = attStatusLogic.findMyAttStatusUsers(users_id);
		//UserBeanにIdしかセットしていないので年月は別で取得する
		attDetailUsers.setYear_and_month(date); 
		
		//勤怠状況詳細の一行取得logic④
		StampBean attDetailStamp = attStatusLogic.findMyAttDetailStatusStamp(users_id,date);
		//勤怠状況詳細の差し戻し理由logic④
		RequestListBean attDetailUsersRequestList = attStatusLogic.findMyAttDetailStatusRequest(users_id);
//		RequestListBean attDetailUsersRequestList = attStatusLogic.findMyAttDetailStatusRequest(att_status_id); //★★エラーがでる

		
		//----------------------取得した情報を次の画面に投げる処理-----------------------------
		request.setAttribute("attDetailUsers", attDetailUsers);
		request.setAttribute("attDetailStamp", attDetailStamp);
		request.setAttribute("attDetailUsersRequestList", attDetailUsersRequestList);
		
		
		request.getRequestDispatcher("WEB-INF/jsp/attendanceStatusDetail.jsp").forward(request, response);
	}

}
