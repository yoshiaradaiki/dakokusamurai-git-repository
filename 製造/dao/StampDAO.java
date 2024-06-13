//作成者：鈴木
//作成日時：6/13

package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class StampDAO {
    private final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\dakokuSamuraiDB\\dakokuSamuraiDB";
    private final String DB_USER = "dakokuSamurai";
    private final String DB_PASS = "dakokusamurai";
    
    //メソッド：insertStamp
    //引数　　：利用者ID, 日付, 出勤時刻, 補正出勤時刻,
    //戻り値　：true or false
    public boolean insertStamp(int users_id, Date stamp_date, Time workln_raw, Time wolkln_re)
    {
    	//JDBCドライバを読み込む
    	try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバの読み込みに失敗しました");
		}
    	try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
    		
    		String sql= "INSERT INTO stamp(users_id, stamp_date, workln_raw, wolkln_re)\n"
    				  + "VALUE(?,?,?,?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		
    		//INSERT文の？に使用する値を設定
    		ps.setInt(1, users_id);
    		ps.setDate(2, stamp_date);
    		ps.setTime(3, workln_raw);
    		ps.setTime(4, wolkln_re);
    		
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
    public boolean updateStamp(int users_id, Date stamp_date, Time workOut_raw, Time wolkOut_re) {
    	
    	//JDBCドライバを読み込む
    	try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバの読み込みに失敗しました");
		}
    	try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
    		
    		String sql= "INSERT INTO stamp(users_id, stamp_date, WorkOut_raw, wolkOut_re)\n"
    				  + "VALUE(?,?,?,?)";
    		PreparedStatement ps = conn.prepareStatement(sql);
    		
    		//INSERT文の？に使用する値を設定
    		ps.setInt(1, users_id);
    		ps.setDate(2, stamp_date);
    		ps.setTime(3, workOut_raw);
    		ps.setTime(4, wolkOut_re);
    		
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
    		ps.setDate(2, stamp_date); 		
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
  