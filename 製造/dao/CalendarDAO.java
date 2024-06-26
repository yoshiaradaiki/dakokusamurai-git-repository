// 担当者：吉新
// 2024/06/12
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import beans.StampBean;

public class CalendarDAO {
	private static final String JDBC_DRIVER = "org.h2.Driver";
	private static final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\dakokuSamuraiDB\\dakokuSamuraiDB";
	private static final String JDBC_USER = "dakokuSamurai";
	private static final String JDBC_PASS = "dakokusamurai";
	
	// コネクション用メソッド
	private static Connection ConnectionDB () {
		Connection con = null;
		
		try {
			Class.forName(JDBC_DRIVER);
			con = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("ドライバが見つかりません");
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DBに接続できません");
			System.out.println(e.getMessage());
		}
		return con;
	}
	
	private LocalTime timeNullCheck(Time time) {
		if (time != null) {
			return time.toLocalTime();
		} else {
			return null;
		}
	}
	
	
	// 1ヶ月取得するメソッド
	public List<StampBean> findMyAttStatusMonthStamp(int users_id, Date date) {
		
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		// SQL文
		String sql = ""
				+ "SELECT c.calendar_date,\n"
				+ "       s.workIn_raw,\n"
				+ "       s.workOut_raw,\n"
				+ "       COALESCE(sr2.workIn_rev, s.workIn_re) AS workIn_re,\n"
				+ "       COALESCE(sr2.workOut_rev, s.workOut_re) AS workOut_re,\n"
				+ "       COALESCE(sr2.work_status, c.work_status) AS work_status,\n"
				+ "       sr2.rest_time,\n"
				+ "       COALESCE(sr2.note, c.note) AS note,\n"
				+ "       s.stamp_id\n"
				+ "FROM calendar c\n"
				+ "LEFT JOIN\n"
				+ "(SELECT * FROM STAMP WHERE users_id = ?) s ON c.calendar_date = s.stamp_date\n"
				+ "LEFT JOIN (\n"
				+ "    SELECT sr.*\n"
				+ "    FROM (\n"
				+ "        SELECT sr.*,\n"
				+ "               ROW_NUMBER() OVER (PARTITION BY sr.stamp_id ORDER BY scr.date_req DESC) AS row_num\n"
				+ "        FROM stamp_revision sr\n"
				+ "        JOIN stamp_correct_req scr ON sr.stamp_rev_id = scr.stamp_rev_id\n"
				+ "        WHERE scr.status = 2\n"
				+ "    ) sr\n"
				+ "    WHERE row_num = 1\n"
				+ ") sr2 ON s.stamp_id = sr2.stamp_id\n"
				+ "WHERE c.calendar_date BETWEEN ? AND ?\n"
				+ "ORDER BY c.calendar_date ASC;";
		
		ArrayList<StampBean> stampBeans = new ArrayList<>();
		StampBean stampBean = null;
		
		try {
			// DB接続
			con = ConnectionDB();
						
			// SQL文組み立て
			// 受け取ったdateを元に開始年月日と終了年月日を取得
//			LocalDate localsDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
//			java.sql.Date sDate = java.sql.Date.valueOf(localsDate);
//			LocalDate localeMonth = localsDate.plusMonths(1).minusDays(1);
//			java.sql.Date eDate = java.sql.Date.valueOf(localeMonth);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			// dateの月の１日をセット
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			java.sql.Date sDate = new java.sql.Date(calendar.getTime().getTime());
			// 次の月をセットして１日戻る（対象付きの月末が取得できる）
			calendar.add(Calendar.MONTH, 1);
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			java.sql.Date eDate = new java.sql.Date(calendar.getTime().getTime());
			
			System.out.println(sDate);
			System.out.println(eDate);
			
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, users_id);
			psmt.setDate(2, sDate);
			psmt.setDate(3, eDate);
			
			
			// SQLの結果を取得
			rs = psmt.executeQuery();
			// １行ずつ取得
			while (rs.next()) {
				// StampBeanのインスタンス化
				stampBean = new StampBean();
				// 日付の取得(yyyy-mm-dd)
				stampBean.setStamp_date(rs.getDate("calendar_date"));
				// 出勤時刻の取得(hh:mm)
				stampBean.setWorkIn_raw(timeNullCheck(rs.getTime("workIn_raw")));
				// 退勤時刻の取得(hh:mm)
				stampBean.setWorkOut_raw(timeNullCheck(rs.getTime("workOut_raw")));
				// 補正出勤時刻の取得(hh:mm)
				stampBean.setWorkIn_re(timeNullCheck(rs.getTime("workIn_re")));
				// 補正退勤時刻の取得(hh:mm)
				stampBean.setWorkOut_re(timeNullCheck(rs.getTime("workOut_re")));
				// 勤怠状況の取得(int)
				stampBean.setWork_status(rs.getInt("work_status"));
				// 休憩時間の取得(hh:mm)
				stampBean.setRest_time(timeNullCheck(rs.getTime("rest_time")));
				// 出勤しているが休憩時間が取得できない場合はデフォルトで1:00を入れる
				if (stampBean.getRest_time() == null && stampBean.getWorkIn_raw() != null) {
					LocalTime localTime = LocalTime.of(1, 0);
					stampBean.setRest_time(localTime);
				}
				// 日付から曜日の取得(int)
				if (stampBean.getStamp_date() != null) {
					Calendar calendarWeek = Calendar.getInstance();
					calendarWeek.setTime(stampBean.getStamp_date());
					// 曜日の取得と挿入
					stampBean.setWeek(calendarWeek.get(Calendar.DAY_OF_WEEK));
				}
				// 補正出退勤時刻と休憩時間をもとに実労働時間を取得(hh:mm)
				if (stampBean.getWorkIn_re() != null && 
					stampBean.getWorkOut_re() != null && 
					stampBean.getRest_time() != null) {
					// 時刻を分にする
					int wi = stampBean.getWorkIn_re().getHour() * 60 + stampBean.getWorkIn_re().getMinute();
					int wo = stampBean.getWorkOut_re().getHour() * 60 + stampBean.getWorkOut_re().getMinute();
					int rt = stampBean.getRest_time().getHour() * 60 + stampBean.getRest_time().getMinute();
					// 差を計算
					int rwt = wo - wi - rt;
					if (rwt < 0) {
						rwt = 0;
					}
					// 時刻に戻す
					int rwtH = rwt / 60;
					int rwtM = rwt % 60;
					LocalTime localTime = LocalTime.of(rwtH, rwtM);
					stampBean.setReal_work_time(localTime);
				}
				// 備考の取得(String)
				stampBean.setNote(rs.getString("note"));
				// 打刻ID(int)
				stampBean.setStamp_id(rs.getInt("stamp_id"));
				// リストに追加
				stampBeans.add(stampBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DBアクセスエラー");
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("ぬるぽ");
			System.out.println(e.getMessage());
		} finally {
			if(rs != null){
		        try{
		            rs.close();
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		    }
		        if(psmt != null){
		        try{
		        	psmt.close();
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		    }
		        if(con != null){
		        try{
		            con.close();
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		    }
		}
		return stampBeans;
	}
	
	// 1日分取得するメソッド(古いデータ)
	public StampBean findMyAttStatusDetailStamp(int users_id, Date date) {
		
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		// SQL文
		String sql = ""
				+ "SELECT c.calendar_date,\n"
				+ "       s.workIn_raw,\n"
				+ "       s.workOut_raw,\n"
				+ "       COALESCE(sr2.workIn_rev, s.workIn_re) AS workIn_re,\n"
				+ "       COALESCE(sr2.workOut_rev, s.workOut_re) AS workOut_re,\n"
				+ "       COALESCE(sr2.work_status, c.work_status) AS work_status,\n"
				+ "       sr2.rest_time,\n"
				+ "       COALESCE(sr2.note, c.note) AS note,\n"
				+ "       s.stamp_id\n"
				+ "FROM calendar c\n"
				+ "LEFT JOIN\n"
				+ "(SELECT * FROM STAMP WHERE users_id = ?) s ON c.calendar_date = s.stamp_date\n"
				+ "LEFT JOIN (\n"
				+ "    SELECT sr.*\n"
				+ "    FROM (\n"
				+ "        SELECT sr.*,\n"
				+ "               ROW_NUMBER() OVER (PARTITION BY sr.stamp_id ORDER BY scr.date_req DESC) AS row_num\n"
				+ "        FROM stamp_revision sr\n"
				+ "        JOIN stamp_correct_req scr ON sr.stamp_rev_id = scr.stamp_rev_id\n"
				+ "        WHERE scr.status = 2\n"
				+ "    ) sr\n"
				+ "    WHERE row_num = 1\n"
				+ ") sr2 ON s.stamp_id = sr2.stamp_id\n"
				+ "WHERE c.calendar_date = ?";
		
		StampBean stampBean = null;
		
		try {
			// DB接続
			con = ConnectionDB();
						
			// SQL文組み立て
//			long timeInMilliSeconds = date.getTime();
//		    java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
//		    System.out.println(sqlDate);
//		    String strDate = date.getYear() + "-" + date.getMonth() + "-" + date.getDate();
			// 上の変換だとうまくいかない
//			LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDate());
//			java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
			
			Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        java.sql.Date sqlDate = new java.sql.Date(calendar.getTime().getTime());
			
			System.out.println(sqlDate);
			
		    
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, users_id);
			psmt.setDate(2, sqlDate);
			
			// SQLの結果を取得
			rs = psmt.executeQuery();
			// １行ずつ取得
			while (rs.next()) {
				// StampBeanのインスタンス化
				stampBean = new StampBean();
				// 日付の取得(yyyy-mm-dd)
				stampBean.setStamp_date(rs.getDate("calendar_date"));
				// 出勤時刻の取得(hh:mm)
				stampBean.setWorkIn_raw(timeNullCheck(rs.getTime("workIn_raw")));
				// 退勤時刻の取得(hh:mm)
				stampBean.setWorkOut_raw(timeNullCheck(rs.getTime("workOut_raw")));
				// 補正出勤時刻の取得(hh:mm)
				stampBean.setWorkIn_re(timeNullCheck(rs.getTime("workIn_re")));
				// 補正退勤時刻の取得(hh:mm)
				stampBean.setWorkOut_re(timeNullCheck(rs.getTime("workOut_re")));
				// 勤怠状況の取得(int)
				stampBean.setWork_status(rs.getInt("work_status"));
				// 休憩時間の取得(hh:mm)
				stampBean.setRest_time(timeNullCheck(rs.getTime("rest_time")));
				// 出勤しているが休憩時間が取得できない場合はデフォルトで1:00を入れる
				if (stampBean.getRest_time() == null && stampBean.getWorkIn_raw() != null) {
					LocalTime localTime = LocalTime.of(1, 0);
					stampBean.setRest_time(localTime);
				}
				// 日付から曜日の取得(int)
				if (stampBean.getStamp_date() != null) {
					Calendar calendarWeek = Calendar.getInstance();
					calendarWeek.setTime(stampBean.getStamp_date());
					// 曜日の取得と挿入
					stampBean.setWeek(calendarWeek.get(Calendar.DAY_OF_WEEK));
				}
				// 補正出退勤時刻と休憩時間をもとに実労働時間を取得(hh:mm)
				if (stampBean.getWorkIn_re() != null && 
					stampBean.getWorkOut_re() != null && 
					stampBean.getRest_time() != null) {
					// 時刻を分にする
					int wi = stampBean.getWorkIn_re().getHour() * 60 + stampBean.getWorkIn_re().getMinute();
					int wo = stampBean.getWorkOut_re().getHour() * 60 + stampBean.getWorkOut_re().getMinute();
					int rt = stampBean.getRest_time().getHour() * 60 + stampBean.getRest_time().getMinute();
					// 差を計算
					int rwt = wo - wi - rt;
					if (rwt < 0) {
						rwt = 0;
					}
					// 時刻に戻す
					int rwtH = rwt / 60;
					int rwtM = rwt % 60;
					LocalTime localTime = LocalTime.of(rwtH, rwtM);
					stampBean.setReal_work_time(localTime);
				}
				// 備考の取得(String)
				stampBean.setNote(rs.getString("note"));
				// 打刻ID(int)
				stampBean.setStamp_id(rs.getInt("stamp_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DBアクセスエラー");
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("ぬるぽ");
			System.out.println(e.getMessage());
		} finally {
			if(rs != null){
		        try{
		            rs.close();
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		    }
		        if(psmt != null){
		        try{
		        	psmt.close();
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		    }
		        if(con != null){
		        try{
		            con.close();
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		    }
		}
		return stampBean;
	}
	
	// 1日分取得するメソッド(申請データ)
	public StampBean findMyAttStatusDetailStampRequest(int stamp_rev_id) {
		
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		// SQL文
		String sql = ""
				+ "SELECT c.calendar_date,\r\n"
				+ "		s.workIn_raw,\r\n"
				+ "		s.workOut_raw,\r\n"
				+ "		COALESCE(sr.workIn_rev, s.workIn_re) AS workIn_re,\r\n"
				+ "		COALESCE(sr.workOut_rev, s.workOut_re) AS workOut_re,\r\n"
				+ "		COALESCE(sr.work_status, c.work_status) AS work_status,\r\n"
				+ "		sr.rest_time,\r\n"
				+ "		COALESCE(sr.note, c.note) AS note,\r\n"
				+ "		s.stamp_id\r\n"
				+ "	FROM calendar c\r\n"
				+ "	LEFT JOIN STAMP s ON c.calendar_date = s.stamp_date\r\n"
				+ "	LEFT JOIN STAMP_REVISION sr ON s.stamp_id = sr.stamp_id\r\n"
				+ "	LEFT JOIN STAMP_CORRECT_REQ scr ON sr.stamp_rev_id = scr.stamp_rev_id\r\n"
				+ "	WHERE sr.stamp_rev_id = ?;";
		
		StampBean stampBean = null;
		
		try {
			// DB接続
			con = ConnectionDB();
						
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, stamp_rev_id);
			
			// SQLの結果を取得
			rs = psmt.executeQuery();
			// １行ずつ取得
			while (rs.next()) {
				// StampBeanのインスタンス化
				stampBean = new StampBean();
				// 日付の取得(yyyy-mm-dd)
				stampBean.setStamp_date(rs.getDate("calendar_date"));
				// 出勤時刻の取得(hh:mm)
				stampBean.setWorkIn_raw(timeNullCheck(rs.getTime("workIn_raw")));
				// 退勤時刻の取得(hh:mm)
				stampBean.setWorkOut_raw(timeNullCheck(rs.getTime("workOut_raw")));
				// 補正出勤時刻の取得(hh:mm)
				stampBean.setWorkIn_re(timeNullCheck(rs.getTime("workIn_re")));
				// 補正退勤時刻の取得(hh:mm)
				stampBean.setWorkOut_re(timeNullCheck(rs.getTime("workOut_re")));
				// 勤怠状況の取得(int)
				stampBean.setWork_status(rs.getInt("work_status"));
				// 休憩時間の取得(hh:mm)
				stampBean.setRest_time(timeNullCheck(rs.getTime("rest_time")));
				// 出勤しているが休憩時間が取得できない場合はデフォルトで1:00を入れる
				if (stampBean.getRest_time() == null && stampBean.getWorkIn_raw() != null) {
					LocalTime localTime = LocalTime.of(1, 0);
					stampBean.setRest_time(localTime);
				}
				// 日付から曜日の取得(int)
				if (stampBean.getStamp_date() != null) {
					Calendar calendarWeek = Calendar.getInstance();
					calendarWeek.setTime(stampBean.getStamp_date());
					// 曜日の取得と挿入
					stampBean.setWeek(calendarWeek.get(Calendar.DAY_OF_WEEK));
				}
				// 補正出退勤時刻と休憩時間をもとに実労働時間を取得(hh:mm)
				if (stampBean.getWorkIn_re() != null && 
					stampBean.getWorkOut_re() != null && 
					stampBean.getRest_time() != null) {
					// 時刻を分にする
					int wi = stampBean.getWorkIn_re().getHour() * 60 + stampBean.getWorkIn_re().getMinute();
					int wo = stampBean.getWorkOut_re().getHour() * 60 + stampBean.getWorkOut_re().getMinute();
					int rt = stampBean.getRest_time().getHour() * 60 + stampBean.getRest_time().getMinute();
					// 差を計算
					int rwt = wo - wi - rt;
					if (rwt < 0) {
						rwt = 0;
					}
					// 時刻に戻す
					int rwtH = rwt / 60;
					int rwtM = rwt % 60;
					LocalTime localTime = LocalTime.of(rwtH, rwtM);
					stampBean.setReal_work_time(localTime);
				}
				// 備考の取得(String)
				stampBean.setNote(rs.getString("note"));
				// 打刻ID(int)
				stampBean.setStamp_id(rs.getInt("stamp_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("DBアクセスエラー");
			System.out.println(e.getMessage());
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("ぬるぽ");
			System.out.println(e.getMessage());
		} finally {
			if(rs != null){
		        try{
		            rs.close();
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		    }
		        if(psmt != null){
		        try{
		        	psmt.close();
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		    }
		        if(con != null){
		        try{
		            con.close();
		        } catch (SQLException e) {
		        	System.out.println(e.getMessage());
		        }
		    }
		}
		return stampBean;
	}
}
