//作成者：鈴木
//作成日時：6/13

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

public class StampDAO {
    private final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\dakokuSamuraiDB\\dakokuSamuraiDB";
    private final String DB_USER = "dakokuSamurai";
    private final String DB_PASS = "dakokusamurai";
    
    // util.Date型をsql.Date型に変換する
    private java.sql.Date changeDate(Date date) {
    	if (date != null) {
    		long timeInMilliSeconds = date.getTime();
    		java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
    		return sqlDate;    		
    	} else {
    		return null;
    	}
    }
    // LocalTime型をsql.Time型に変換する
    private Time changeTime(LocalTime lTime) {
    	if (lTime != null) {
    		return new Time(lTime.getHour(), lTime.getMinute(), 0);    		
    	} else {
    		return null;
    	}
    }
    
    //メソッド：insertStamp
    //引数　　：利用者ID, 日付, 出勤時刻, 補正出勤時刻,
    //戻り値　：true or false
    public boolean insertStamp(int users_id, Date stamp_date, LocalTime workIn_raw, LocalTime workIn_re)
    {
    	//JDBCドライバを読み込む
    	try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバの読み込みに失敗しました");
		}
    	try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
    		
    		String sql= "INSERT INTO stamp(users_id, stamp_date, workIn_raw, workIn_re)\n"
    				  + "VALUES(?,?,?,?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		
    		//INSERT文の？に使用する値を設定
    		ps.setInt(1, users_id);
    		ps.setDate(2, changeDate(stamp_date));
    		ps.setTime(3, changeTime(workIn_raw));
    		ps.setTime(4, changeTime(workIn_re));
    		
            ps.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
    
    //メソッド：updateStamp
    //引数　　：利用者ID, 日付, 退勤時刻, 補正退勤時刻,
    //戻り値　：true or false
    public boolean updateStamp(int users_id, Date stamp_date, LocalTime workOut_raw, LocalTime workOut_re) {
    	
    	//JDBCドライバを読み込む
    	try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバの読み込みに失敗しました");
		}
    	try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
    		
    		String sql= "UPDATE stamp SET workOut_raw = ?, workOut_re = ?\n"
    				+ "WHERE users_id = ? AND stamp_date = ?";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		
    		//INSERT文の？に使用する値を設定
    		ps.setTime(1, changeTime(workOut_raw));
    		ps.setTime(2, changeTime(workOut_re));
    		ps.setInt(3, users_id);
    		ps.setDate(4, changeDate(stamp_date));
    		
            ps.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
    }
    
    //メソッド：findStampCheck
    //引数　　：利用者ID, 日付
    //戻り値　：true or false
    public Boolean findStampCheck(int users_id, Date stamp_date) {
    	
    	//JDBCドライバを読み込む
    	try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバの読み込みに失敗しました");
		}
    	try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
    		
    		String sql="SELECT *                                               \n"
    				  + "FROM stamp                                            \n"
    				  + "WHERE EXISTS (SELECT * FROM stamp                     \n"
    				  + "              WHERE users_id = ? AND stamp_date = ?)";
    				  
    		PreparedStatement ps = conn.prepareStatement(sql);
    		//INSERT文の？に使用する値を設定
    		ps.setInt(1, users_id);
    		ps.setDate(2, changeDate(stamp_date)); 		
    		ResultSet rs = ps.executeQuery();
    		
    		if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
  