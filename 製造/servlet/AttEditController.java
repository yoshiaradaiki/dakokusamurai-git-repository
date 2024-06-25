//作成者：横山
//作成日：6/17
package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;
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
		
		LocalDate currentDate = LocalDate.now();
		
		int year=currentDate.getYear();
		int month=currentDate.getMonthValue();
		int formdate=currentDate.getDayOfMonth();
		try {
			year = Integer.parseInt(request.getParameter("year"));
			month = Integer.parseInt(request.getParameter("month"));
			formdate = Integer.parseInt(request.getParameter("date"));
		} catch (NumberFormatException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		//Dateに年・月・日を渡す
		//1月は0のため-1、1日が欲しいので1を入力している
//		Date date = new Date(year,month,formdate);
		// Calendar オブジェクトを取得し、日付をセットする
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, formdate); // 年、月（0から始まるので注意）、日をセット
        
        // Date オブジェクトに変換する
        Date date = calendar.getTime();
		System.out.println(date);
		
		//-------------------------AttStatuLogicのメソッド取得---------------------------------
		//AttStatusLogi インスタンス作成 
		AttStatusLogic attStatusLogic = new AttStatusLogic();
		
		//6/21　横山追加
		//フォーム切り替えのリクエストセット　申請フォーム：0
		int formstatus =0;
		request.setAttribute("formstatus",formstatus);
		
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
		request.setAttribute("date", date);
		request.setAttribute("usersBean", attDetailUsers);
		request.setAttribute("stampBean", attDetailStamp);
		request.setAttribute("requestListBean", attDetailUsersRequestList);
		
		
		request.getRequestDispatcher("WEB-INF/jsp/attendanceStatusDetail.jsp").forward(request, response);
	}
//　チャットGPTアドバイス
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	    request.setCharacterEncoding("utf-8");
//	    response.setContentType("text/html; charset=utf-8");
//
//	    // セッション情報取得
//	    HttpSession session = request.getSession();
//	    UsersBean sessionUsersBean =(UsersBean)session.getAttribute("sessionUsersBean");
//	    int users_id = sessionUsersBean.getUsers_id();
//	    
//	    // フォーム情報の取得と年月処理
//	    String yearParam = request.getParameter("year");
//	    String monthParam = request.getParameter("month");
//
//	    if (yearParam == null || monthParam == null) {
//	        // パラメータが送信されていない場合のエラーハンドリング
//	        // 通常はフォームの設定を確認する必要があります
//	        response.getWriter().println("<p>Error: 年または月が指定されていません。</p>");
//	        return;
//	    }
//
//	    int year;
//	    int month;
//
//	    try {
//	        year = Integer.parseInt(yearParam);
//	        month = Integer.parseInt(monthParam);
//	    } catch (NumberFormatException e) {
//	        // 数値への変換に失敗した場合のエラーハンドリング
//	        response.getWriter().println("<p>Error: 年または月の値が不正です。</p>");
//	        return;
//	    }
//
//	    // Dateオブジェクトの生成（1月は0であるため、-1する必要があります）
//	    Date date = new Date(year - 1900, month - 1, 1);
//	    
//	    // AttStatusLogicのインスタンス作成
//	    AttStatusLogic attStatusLogic = new AttStatusLogic();
//	    
//	    // 利用者を取得するlogic
//	    UsersBean attDetailUsers = attStatusLogic.findMyAttStatusUsers(users_id);
//	    attDetailUsers.setYear_and_month(date);
//	    
//	    // 勤怠状況詳細の一行取得logic
//	    StampBean attDetailStamp = attStatusLogic.findMyAttDetailStatusStamp(users_id, date);
//	    
//	    // 勤怠状況詳細の差し戻し理由logic
//	    RequestListBean attDetailUsersRequestList = attStatusLogic.findMyAttDetailStatusRequest(users_id);
//	    
//	    // 取得した情報を次の画面に投げる処理
//	    request.setAttribute("attDetailUsers", attDetailUsers);
//	    request.setAttribute("attDetailStamp", attDetailStamp);
//	    request.setAttribute("attDetailUsersRequestList", attDetailUsersRequestList);
//	    
//	    request.getRequestDispatcher("WEB-INF/jsp/attendanceStatusDetail.jsp").forward(request, response);
//	}


}
