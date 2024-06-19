//***担当者：横山
//***作成日時:6/12

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

import beans.StampRevBean;

public class StampRevDAO {
	//データベース接続に使用する情報
		private final String JDBC_URL="jdbc:h2:tcp://localhost/C:\\dakokusamuraiDB\\dakokusamuraiDB";
		private final String DB_USER ="dakokuSamurai";
		private final String DB_PASS ="dakokusamurai";
		
		
		//打刻修正登録
		public boolean insertStampRev(StampRevBean stampRevBean) {

			//JDBCドライバを読み込む
			try {
				Class.forName("org.h2.Driver");
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
				throw new IllegalStateException("JDBCドライバをよみこめませんでした");
			}
			try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
				
				//★★★SQL★★★
				//追加したい情報：打刻修正ID+打刻ID+修正出勤+退勤+休憩時間+勤怠状況+備考
				String sql= "INSERT INTO STAMP_REVISION "
						+ "(STAMP_REV_ID ,STAMP_ID ,WORKIN_REV ,WORKOUT_REV ,REST_TIME ,WORK_STATUS ,NOTE )\n"
						+ "VALUES(?,?,?,?,?,?,?)";
				
			   //SQLを実行する
				PreparedStatement pStmt = conn.prepareStatement(sql);
				
				
				//Localtime変換 分/秒/
				LocalTime localTime1 = stampRevBean.getWorkIn_rev(); //補正開始時間GET
				LocalTime localTime2 = stampRevBean.getWorkOut_rev(); //補正終了時間GET
				LocalTime localTime3 = stampRevBean.getRest_time(); //休憩時間
				
				// Time型への変換
				Time time1 = Time.valueOf(localTime1); // 補正開始時間
				Time time2 = Time.valueOf(localTime2); // 補正終了時間
				Time time3 = Time.valueOf(localTime3.withSecond(0)); // 休憩時間
//				//破棄する文章
//				Time time1 = new Time(localTime1.getHour(), 0, 0); //時間
//				Time time2 = new Time(0, localTime2.getMinute(), 0);//分
//				Time time3 = new Time(localTime3.getHour(), 0, 0); //休憩時間のため　時間で取得
				
				pStmt.setInt(1,stampRevBean.getStamp_rev_id());
				pStmt.setInt(2,stampRevBean.getStamp_id());
				pStmt.setTime(3,time1);
				pStmt.setTime(4,time2);
				pStmt.setTime(5,time3);
				pStmt.setInt(6,stampRevBean.getWork_status());
				pStmt.setString(7,stampRevBean.getNote());
	
				
				//Insert文を実行
				int result =pStmt.executeUpdate();
				if(result !=1) {
					return false;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
			
		}
		
		//users_idを取り出す
		public int findStampRevId(int stamp_id) {
			ResultSet rs = null;
			int users_id = 0;
			//JDBCドライバを読み込む
			try {
				Class.forName("org.h2.Driver");
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
				throw new IllegalStateException("JDBCドライバをよみこめませんでした");
			}
			try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
				
				String sql= "SELECT users_id FROM STAMP_REVISION WHERE stamp_id = ?";
				
			   //SQLを実行する
				PreparedStatement pStmt = conn.prepareStatement(sql);
				
				pStmt.setInt(1, stamp_id);
	
				
				//SELECT文を実行
				rs =pStmt.executeQuery();
				
				while (rs.next()) {
					users_id = rs.getInt("users_id");
				}
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return users_id;
			
	}
		
		

}
