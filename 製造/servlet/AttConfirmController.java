//***作成者：横山
//***作成日：2024/6/13
package servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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


//年月を確定し押下したときの処理
//勤怠状況表を再描画
//sessionから利用者情報を取得

@WebServlet("/AttConfirmController")
public class AttConfirmController extends HttpServlet {
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
		//フォーム情報の取得　String➡INT変換
		int year =Integer.parseInt(request.getParameter("year")) ;
		int month = Integer.parseInt(request.getParameter("month")) ;
		
		//パラメーターに値が入っているのか確認   ★★★要確認　月1～12条件でもよいのでは？　
		if((year != 0) && (month != 0) ) {
			  // Calendarインスタンスの生成
		    Calendar calendar = Calendar.getInstance();
		    
		    // 年月日をセットする
		    calendar.set(Calendar.YEAR, year);
		    calendar.set(Calendar.MONTH, month - 1); // Calendarの月は0から始まるため、-1する
		    calendar.set(Calendar.DAY_OF_MONTH, 1); // 1日を設定する
		    
		    // CalendarからDateオブジェクトを取得する
		    Date date = calendar.getTime();
		    

			//-------------------------AttStatuLogicのメソッド取得---------------------------------
			//AttStatusLogi インスタンス作成 
			AttStatusLogic attStatusLogic = new AttStatusLogic();
			
			//メソッドにUsersBeanへセッションUsers_idをセットしたものを再セットする
			UsersBean attUsers = attStatusLogic.findMyAttStatusUsers(users_id);
			//UserBeanにIdしかセットしていないので年月は別で取得する
			attUsers.setYear_and_month(date); 
			
			List<StampBean> attStampList = attStatusLogic.findMyAttStatusMonthStamp(users_id,date);
			
			RequestListBean attRequestList = attStatusLogic.findMyAttStatusMonthRequest(users_id, date);
			
			
			//----------------------取得した情報を次の画面に投げる処理-----------------------------
			request.setAttribute("formstatus", 0);
			request.setAttribute("year", year);
			request.setAttribute("month", month);
			request.setAttribute("usersBean", attUsers);
			request.setAttribute("stampBeans", attStampList);
			request.setAttribute("requestListBean", attRequestList);
			
			request.getRequestDispatcher("WEB-INF/jsp/attendanceStatus.jsp").forward(request, response);

		}else {
			System.out.println("パラメーター取得なし");
		}
		
	}

}
