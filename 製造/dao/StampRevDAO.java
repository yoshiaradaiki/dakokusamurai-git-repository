//***担当者：横山
//***作成日時:6/12

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
				String sql="";
				
				//★★★SQL★★★
				sql = "";
				
			
			   //SQLを実行する
				PreparedStatement pStmt = conn.prepareStatement(sql);
				
				pStmt.setInt(1,stampRevBean.getRequest_id());
				pStmt.setInt(2,stampRevBean.getRequest_foreign_id());
				pStmt.setTimestamp(3,stampRevBean.getDate_and_time());
				pStmt.setString(4,stampRevBean.getReason());
				pStmt.setInt(5,stampRevBean.getStatus());
				pStmt.setInt(6,stampRevBean.getContent());
				pStmt.setString(7,stampRevBean.getName());
				pStmt.setString(8,stampRevBean.getBoss_name());
				
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
			
		
		//勤怠状況表に表示するカレンダー部分のデータを取得(１ヶ月)
		public void findAttStatusDetailStamp(StampRevBean stampRevBean) {
			
		}
}
