// 担当者：吉新
// 2024/06/12
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
	
	public List<StampBean> findMyAttStatusDetailStamp(int users_id, Date date) {
		Connection con = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		// SQL文
		String sql = "SELECT users_id, users_name FROM USERS WHERE users_id = ?";
		
		ArrayList<StampBean> stampBeans = new ArrayList<>();
		StampBean stampBean = null;
		
		try {
			// DB接続
			con = ConnectionDB();
						
			// SQL文組み立て
			long timeInMilliSeconds = date.getTime();
			java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
			psmt = con.prepareStatement(sql);
//			psmt.setInt(1, users_id);
			psmt.setDate(1, sqlDate);
			
			
			// SQLの結果を取得
			rs = psmt.executeQuery();
			
			// １行ずつ取得
			while (rs.next()) {
				stampBean = new StampBean();
				stampBean.setUsers_id(rs.getInt("users_id"));
				stampBean.setStamp_date(rs.getDate("stump_date"));
				stampBean.setWorkIn_raw(rs.getDate("a"));
				stampBean.setWorkOut_raw(sqlDate);
				stampBean.setWorkIn_re(sqlDate);
				stampBean.setWorkOut_re(sqlDate);
				stampBean.setWeek(users_id);
				stampBean.setRest_time(sqlDate);
				stampBean.setReal_work_time(sqlDate);
				stampBean.setNote(sql);
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
//		calendarDAO.findMyAttStatusDetailStamp(1);
	}
}
