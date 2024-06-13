//担当:長江
//2024/06/12


package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import beans.RequestListBean;
//データベース接続
public class StampRevReqDAO {
	private final String JDBC_URL = "jdbc:h2:C:/dakokuSamuraiDB/dakokuSamuraiDB.mv";
	private final String DB_USER = "dakokuSamurai";
	private final String DB_PASS = "dakokusamurai";

	//	打刻修正申請登録
	public  boolean insertStampRevReq(RequestListBean requestListBean) {
//		JDBCドライバを読み込む
		try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバをよみこめませんでした");
		}
		try(Connection conn=DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
				
		String sql="INSERT INTO  stamp_correct_req (stamp_rev_id,\n"
				+ "status, created_users_id,updated_users)VALUE(?,?,?,?)";
		
		PreparedStatement pStmt = conn.prepareStatement(sql);
		
		//INSERT文の？に使用する値を設定
		RequestListBean reqListBean = new RequestListBean();
		pStmt.setInt(1, reqListBean.getStamp_rev_id());
		pStmt.setInt(2, reqListBean.getStatus());
		pStmt.setInt(3, reqListBean.getCreated_users_id());
		pStmt.setInt(4, reqListBean.getUpdated_users_id());
		
		     int result =pStmt.executeUpdate();
			 if(result !=1) {
				 return false;
			 }
		}catch (SQLException e) {
            e.printStackTrace();
           return false;
	  }
		return true;
	}

	//		打刻修正申請更新（承認など）															
	public boolean updateStampRevReq(int stamp_rev_req_id, int status, String reason){
		
		try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバをよみこめませんでした");
		}
		try(Connection conn=DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
				
			String updateSql = "UPDATE stamp_correct_re,\n"
					+ "SET status = ?, reason = ?, updated_users_id = ?\n"
					+ "WHERE month_req_id = ?;";
			PreparedStatement pStmt = conn.prepareStatement(sql);
 
			//UPDATE文の？に使用する値を設定
			RequestListBean reqListBean = new RequestListBean();
			pStmt.setInt(1, reqListBean.getStatus());
			pStmt.setString(2, reqListBean.getReason());
			pStmt.setInt(3, reqListBean.getUpdated_users_id());
			pStmt.setInt(4, reqListBean.getMonth_req_id());
 
			//UPDATE文を実行
			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		}catch (SQLException e) {
           e.printStackTrace();
           return false;
	  }
		return true;
     }

	//		勤怠状況詳細表に表示する差し戻しの理由を取得																	

	public  RequestListBean findMyAttStatusDetailRequest(int users_id, Date date) {
		RequestListBean requestListBean = new RequestListBean(); 
		try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバをよみこめませんでした");
		}
    	try(Connection conn=DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
    			
    		String sql="SELECT scr.reason FROM stamp_revision sr join stamp  st on sr.stamp_id=st.stamp_id "
    				+ "join stamp_corecct_req scr on sr.stamp_rev_id=scr.stamp_rev_id "
    				+ " WHERE st.stamp_date = ? and st.users_id= ?  ";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		long timeInMilliSeconds = date.getTime();
            java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
    		pStmt.setDate(1,sqlDate );
    		pStmt.setInt(2,users_id);
    		
    		
    		 ResultSet  rs = pStmt.executeQuery();
    		 while(rs.next()) {
 				String rason=rs.getString("reason");
 				requestListBean.setReason(rason);
 			}
						
    				
    	}catch (SQLException e) {
    	     e.printStackTrace();
     }
    	return requestListBean;
	}

	//		勤怠状況詳細表に表示する差し戻しの理由を取得																	

	public RequestListBean  findAttStatusDetailRequest(int stamp_rev_id) {
		RequestListBean requestListBean = new RequestListBean(); 
		try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバをよみこめませんでした");
		}
    	try(Connection conn=DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
    			
    		String sql="SELECT reason FROM stamp_correct_req WHERE stamp_rev_id=?";
    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setInt(1, stamp_rev_id);
    		
    		
    		 ResultSet  rs = pStmt.executeQuery();
    		 while(rs.next()) {
 				String rason=rs.getString("reason");
 				requestListBean.setReason(rason);
 			}
						
    				
    	}catch (SQLException e) {
    	     e.printStackTrace();
     }
    	return requestListBean;
    }
   }
