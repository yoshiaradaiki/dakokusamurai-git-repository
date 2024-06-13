//***横山
//***2024/6/13　　
package logic;

import java.util.Date;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import beans.AttStatusBean;
import beans.RequestListBean;
import beans.StampBean;
import beans.UsersBean;
import dao.AttDAO;
import dao.CalendarDAO;
import dao.MonthReqDAO;
import dao.UsersDAO;



public class AttStatusLogic {
	
	//------------------------------勤怠状況表--------------------------------------
	//引数　　　：user_id 
	//戻り値　　:UserBean 
	//使用DAO　　：UsersDAO
	//処理概要　：勤怠状況表の利用者取得
	public UsersBean findMyAttStatusUsers1(int users_id) {
		UsersDAO usersDAO = new UsersDAO();
		return usersDAO.findMyAttStatusUsers(users_id);
	}
	
	//引数　　　：user_id,date
	//戻り値　　：StampBean　打刻の表示を行うため
	//使用DAO　　：CalenderDAO
	//処理概要　：勤怠状況状況表の表示
	public List<StampBean> findMyAttStatusMonthStamp(int users_id, Date date) {
		CalendarDAO calenderDAO = new CalendarDAO();
		return calenderDAO.findMyAttStatusDetailStamp(users_id, date);
	}


	//引数　　　：user_id,date
	//戻り値　　：RequestListBean　
	//使用DAO　　：MonthReqDAO
	//処理概要　：差し戻し理由を一覧から取得する　
	public RequestListBean findMyAttStatusMonthRequest(int users_id, Date date){
		MonthReqDAO monthReqDAO = new MonthReqDAO();  
		return monthReqDAO.findMyAttStatusMonthRequest(users_id, date);
		
	}
	
	//------------------------------打刻修正--------------------------------------

	//引数　　　：user_id 
	//戻り値　　:UserBean 
	//使用DAO　 ：UsersDAO
	//処理概要　：勤怠状況表の利用者取得
	//処理概要　：勤怠状況表の利用者取得
	public UsersBean findMyAttStatusUsers(int users_id) {
		UsersDAO usersDAO = new UsersDAO();
		return usersDAO.findMyAttStatusUsers(users_id);	
	}
	
	//引数　　　：user_id 
	//戻り値　　:StampBean　打刻の表示を行うため
	//使用DAO　 ：UsersDAO
	//処理概要　：勤怠状況表の利用者取得
	//処理概要　：勤怠状況状況表の表示
	public StampBean findMyAttDetailStatusStamp(int users_id, Date date) {
		CalendarDAO calenderDAO = new CalendarDAO();
		//calendarで作成されてから確認
		return  calenderDAO.findMyAttStatusDetailStamp(int users_id, Date date); 
	
	}
	//引数　　　：user_id,date
	//戻り値　　：RequestListBean
	//使用DAO　　：MonthReqDAO
	//処理概要　：差し戻し理由を一覧から取得する
	public RequestListBean findMyAttDetailStatusRequest(int att_status_id) {
		MonthReqDAO monthReqDAO = new MonthReqDAO();
		return monthReqDAO.findAttStatusMonthRequest(att_status_id);
		
	}
	
	//戻り値　　：Boolean
	//使用DAO　　：monthDAO
	//月末申請を提出する 月末申請作成
	public Boolean insertMonthReq(int att_status_id, int status, int created_users_id, int updated_users_id) {
		MonthReqDAO monthDao = new MonthReqDAO(); 
		return  monthDao.insertMonthReq(att_status_id, status, created_users_id, updated_users_id);
		
	}
	//戻り値　　：Boolean
	//使用DAO　：AttDAO	
	//申請者を登録する　勤怠状況表テーブルに提出者を登録
	public boolean insertAttStatus(AttStatusBean attStatusBean) {
		AttDAO attDAO = new AttDAO();
		return attDAO.insertAttStatus(attStatusBean);
		
	}
	//戻り値　　：Boolean
	//使用DAO　　：monthDAO
	//部下の月末申請を承認する
	public Boolean updateMonthReq(int month_req_id, int status, String reason, int updated_users_id) {
		MonthReqDAO monthDao = new MonthReqDAO(); 
		return  monthDao.updateMonthReq(month_req_id, status,reason, updated_users_id);
	}
	//使用DAO　　：monthDAO
	//部下の1か月分の月末申請を差し戻す
	public List<RequestListBean> updateMonthReq(int month_req_id, int status, String reason) {
		MonthReqDAO monthDao = new MonthReqDAO(); 
		return  monthDao.findMySubRequest(status);
		
	}
	
	
	
}
