//***作成者：横山
//***作成日：2024/6/12

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.AttStatusBean;
import beans.UsersBean;

public class AttDAO {
	//データベース接続に使用する情報
	private final String JDBC_URL="jdbc:h2:tcp://localhost/C:\\dakokusamuraiDB\\dakokusamuraiDB";
	private final String DB_USER ="dakokuSamurai";
	private final String DB_PASS ="dakokusamurai";
	

	//勤怠状況表登録
	public boolean insertAttStatus(AttStatusBean attStatusBean) {	
	
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
			//追加したい情報：勤怠状況表ID+利用者ID+年月
			sql = "INSERT INTO ATT_STATUS (ATT_STATUS_ID, USERS_ID, YEARS)\n"
					+ "VALUES (?, ?, ?);";
			
			
			//DATE型変換　java.util.Dateをjava.sql.Dateに変換する　　
			 long timeInMilliSeconds = attStatusBean.getYears().getTime();
		     java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
		     
		   //SQLを実行する
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1,attStatusBean.getAtt_status_id());
			pStmt.setInt(2,attStatusBean.getUsers_id());
			pStmt.setDate(3,sqlDate);
			
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
	
	//利用者の取得
	//申請一覧から申請IDを取得する
	public UsersBean  findUserget(int att_status_id) {
		//UserBean格納インスタンス
		UsersBean usersBean = new UsersBean(); 
	
		//JDBCドライバを読み込む
		try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			throw new IllegalStateException("JDBCドライバをよみこめませんでした");
		}
		try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			
			//★★★SQL★★★
			//利用者IDを取得したい➡勤怠状況表の勤怠状況表IDは？
			String sql= "SELECT users_id, years FROM att_status where att_status_id = ? ";
			
			//SQLを実行する
			PreparedStatement pStmt = conn.prepareStatement(sql);
			pStmt.setInt(1,att_status_id);
			
			 //結果を処理する
			// SQLの結果を取得
			ResultSet rs = pStmt.executeQuery();
			//一件ずつ処理する
            while (rs.next()) {
                 usersBean.setUsers_id(rs.getInt("users_id")); 
                 usersBean.setYear_and_month(rs.getDate("years"));
            } 
            
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return usersBean;
	}

}


//破棄する文章
//		PreparedStatement ps = conn.prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS);
//if( ps.executeUpdate() > 0) {
//	ResultSet rs = ps.getGeneratedKeys();
//	while(rs.next()) {
//		AttStatusBean.setatt_status_id(rs.getInt("user_id"));
//	}
//}


	//2024　6/13　使用変更によりカレンダーDAOと作業が同時になるため破棄
////勤怠状況表に表示するカレンダー部分のデータを取得(１ヶ月)
//public void findAttStatusMonthStamp(AttStatusBean attStatusBean) {
//	//作成した一覧を収納するインスタンス
//	AttDAO AttList = new AttDAO();
//	CalendarBean CB = new CalendarBean();
//	
//	
//	//JDBCドライバを読み込む
//		try {
//			Class.forName("org.h2.Driver");
//		}catch(ClassNotFoundException e) {
//			throw new IllegalStateException("JDBCドライバをよみこめませんでした");
//		}
//		try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
//			String sql="";
//			
//			//SQL　カレンダー/打刻/打刻修正
//			sql="";
//			
//			//SQLを実行する
//			PreparedStatement pStmt = conn.prepareStatement(sql);
//			
//			
//			
//			pStmt.setInt(1,attStatusBean.setUsers_id());
//			//SQLを実行する
//			
//			ResultSet rs = pStmt.executeQuery();
//			while (rs.next()) {
//				
//				 
//
//				
//			}
//		
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	

