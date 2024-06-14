package logic;

import java.util.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

import beans.RequestListBean;
import beans.StampBean;
import beans.UsersBean;
import dao.CalendarDAO;
import dao.MonthReqDAO;
import dao.StampDAO;
import dao.UsersDAO;

public class EmpLogic {

	//メソッド名：insertStamp
	//引数　　　：users_id, stamp_date, workIn_raw
	//戻り値　　：
	//処理概要　：
	public boolean insertStamp(int users_id, Date stamp_date, LocalTime workIn_raw) {
		StampDAO stampDAO = new StampDAO();

		//時間
		int h = workIn_raw.getHour();
		//分
		int m = workIn_raw.getMinute();

		LocalTime workIn_re;

		//丸め処理
		if (0 <= m && m <= 15) {
			m = 15;
		} else if (16 <= m && m <= 30) {
			m = 30;
		} else if (31 <= m && m <= 45) {
			m = 45;
		} else {
			m = 0;
			h += 1;
		}

		//補正時刻生成
		workIn_re = LocalTime.of(h, m);

		Time workIn_raw_sql = Time.valueOf(workIn_raw);
		Time workIn_re_sql = Time.valueOf(workIn_re);
		//出勤補正時刻を渡して、DAOメソッドを呼び出す
		return stampDAO.insertStamp(users_id, stamp_date, workIn_raw, workIn_re);
	}

	//メソッド名：updateStamp
	//引数　　　： users_id, stamp_date, workOut_raw
	//戻り値　　：
	//処理概要　：
	public boolean updateStamp(int users_id, Date stamp_date, LocalTime workOut_raw) {
		StampDAO stampDAO = new StampDAO();

		//時間
		int h = workOut_raw.getHour();
		//分
		int m = workOut_raw.getMinute();

		LocalTime workOut_re;

		//丸め処理
		if (0 <= m && m <= 14) {
			m = 0;
		} else if (15 <= m && m <= 29) {
			m = 15;
		} else if (30 <= m && m <= 44) {
			m = 30;
		} else {
			m = 45;
		}

		//補正時刻生成
		workOut_re = LocalTime.of(h, m);

		public static Time valueOf(LocalTime time) {
	        return new Time(time.getHour(), time.getMinute(), time.getSecond());
	    }

		//退勤補正時刻を渡して、DAOメソッドを呼び出す
		return stampDAO.updateStamp(users_id, stamp_date, workOut_raw, workOut_re);
	}

	//メソッド名：findMyRequest
	//引数　　　：users_id
	//戻り値　　：
	//処理概要　：
	public List<RequestListBean> findMyRequest(int users_id) {
		MonthReqDAO monthReqDAO = new MonthReqDAO();
		return monthReqDAO.findMyRequest(users_id);
	}

	//メソッド名：findMySubRequest
	//引数　　　：users_id
	//戻り値　　：
	//処理概要　：
	public List<RequestListBean> findMySubRequest(int users_id) {
		MonthReqDAO monthReqDAO = new MonthReqDAO();
		return monthReqDAO.findMySubRequest(users_id);
	}

	//メソッド名：findMyAttStatusUsers
	//引数　　　：users_id
	//戻り値　　：
	//処理概要　：
	public UsersBean findMyAttStatusUsers(int users_id) {
		UsersDAO usersDAO = new UsersDAO();
		return usersDAO.findMyAttStatusUsers(users_id);
	}

	//メソッド名：findMyAttStatusMonthStamp
	//引数　　　：users_id, date
	//戻り値　　：
	//処理概要　：
	public StampBean findMyAttStatusMonthStamp(int users_id, Date date) {
		CalendarDAO calendarDAO = new CalendarDAO();
		return calendarDAO.findMyAttStatusMonthStamp(users_id, date);
	}

}
