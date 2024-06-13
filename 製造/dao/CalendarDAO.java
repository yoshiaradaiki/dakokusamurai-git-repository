// 担当者：吉新
// 2024/06/12
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
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
	
	
	// month
	public List<StampBean> findMyAttStatusDetailStamp(int users_id, Date date) {
		
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
				+ "       COALESCE(sr2.note, c.note) AS note\n"
				+ "FROM calendar c\n"
				+ "LEFT JOIN\n"
				+ "(SELECT * FROM STAMP WHERE users_id = 1) s ON c.calendar_date = s.stamp_date\n"
				+ "LEFT JOIN (\n"
				+ "    SELECT sr.*\n"
				+ "    FROM (\n"
				+ "        SELECT sr.*,\n"
				+ "               ROW_NUMBER() OVER (PARTITION BY sr.stamp_id ORDER BY scr.date_req DESC) AS row_num\n"
				+ "        FROM stamp_revision sr\n"
				+ "        JOIN stamp_correct_req scr ON sr.stamp_rev_id = scr.stamp_rev_id\n"
				+ "        WHERE scr.status = 2\n"
				+ "    ) sr\n"
				+ "    WHERE row_num = ?\n"
				+ ") sr2 ON s.stamp_id = sr2.stamp_id\n"
				+ "AND c.calendar_date BETWEEN ? AND ?\n"
				+ "ORDER BY c.calendar_date ASC;";
		
		ArrayList<StampBean> stampBeans = new ArrayList<>();
		StampBean stampBean = null;
		
		try {
			// DB接続
			con = ConnectionDB();
						
			// SQL文組み立て
			LocalDate localsDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
			java.sql.Date sDate = java.sql.Date.valueOf(localsDate);

			LocalDate localeMonth = localsDate.plusMonths(1).minusDays(1);
			java.sql.Date eDate = java.sql.Date.valueOf(localeMonth);
			psmt = con.prepareStatement(sql);
			psmt.setInt(1, users_id);
			psmt.setDate(2, sDate);
			psmt.setDate(3, eDate);
			
			
			// SQLの結果を取得
			rs = psmt.executeQuery();
			// １行ずつ取得
			while (rs.next()) {
				stampBean = new StampBean();
//				stampBean.setUsers_id(rs.getInt("users_id"));
				stampBean.setStamp_date(rs.getDate("calendar_date"));
				stampBean.setWorkIn_raw(timeNullCheck(rs.getTime("workIn_raw")));
				stampBean.setWorkOut_raw(timeNullCheck(rs.getTime("workOut_raw")));
				stampBean.setWorkIn_re(timeNullCheck(rs.getTime("workIn_re")));
				stampBean.setWorkOut_re(timeNullCheck(rs.getTime("workOut_re")));
				stampBean.setWork_status(rs.getInt("work_status"));
				stampBean.setRest_time(timeNullCheck(rs.getTime("rest_time")));
				if (stampBean.getStamp_date() != null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(stampBean.getStamp_date());
					// 曜日の取得と挿入
					stampBean.setWeek(calendar.get(Calendar.DAY_OF_WEEK));
				}
				LocalTime re = null;
				LocalTime x = null;

				// workIn_reとworkOut_reがnullでないかチェック
				if (stampBean.getWorkIn_re() != null && stampBean.getWorkOut_re() != null) {
				    // workIn_reとworkOut_reの差を計算
				    Duration duration = Duration.between(stampBean.getWorkIn_re(), stampBean.getWorkOut_re());
				    // workIn_reとworkOut_reの差をLocalTimeに変換
				    re = LocalTime.ofSecondOfDay(duration.getSeconds());

				    // rest_timeがnullでないかチェック
				    if (stampBean.getRest_time() != null) {
				        // aとrest_timeの差を計算
				        Duration diff = Duration.between(re, stampBean.getRest_time());
				        // aとrest_timeの差をLocalTimeに変換
				        x = LocalTime.ofSecondOfDay(diff.getSeconds());
				    } else {
				        // aと1時間の差を計算
				        Duration oneHour = Duration.ofHours(1);
				        Duration diff = Duration.between(re, re.plus(oneHour));
				        // aと1時間の差をLocalTimeに変換
				        x = LocalTime.ofSecondOfDay(diff.getSeconds());
				    }
				    stampBean.setReal_work_time(x);
				}
				stampBean.setNote(rs.getString("note"));
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
	
	
	public static void main(String[] args) {
		CalendarDAO calendarDAO = new CalendarDAO();
		java.sql.Date date = java.sql.Date.valueOf("2024-06-01");
		List<StampBean> stampBeans = calendarDAO.findMyAttStatusDetailStamp(1, date);
		for (StampBean stampBean : stampBeans) {
			System.out.print(stampBean.getStamp_date() + "：");
			System.out.print(stampBean.getWorkIn_re() + "：");
			System.out.print(stampBean.getWorkOut_re() + "：");
			System.out.println(stampBean.getReal_work_time());
		}
	}
}
