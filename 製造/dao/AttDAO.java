//***作成者：横山
//***作成日：2024/6/12

package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import beans.UsersBean;

public class AttDAO {
	//H2データベース接続に使用する情報
	private final String JDBC_URL="jdbc:h2:tcp://localhost/C:\\dakokusamuraiDB\\dakokusamuraiDB";
	private final String DB_USER ="dakokuSamurai";
	private final String DB_PASS ="dakokusamurai";
	
	//テスト用
	public static void main(String[]args) {
		AttDAO attDAO = new AttDAO();
		
		@SuppressWarnings("deprecation")
		Date requestDate = new Date(2024, 7, 1); // 例として2024年6月1日を使用します
//		attDAO.insertAttStatus(1,requestDate);
//		monthReqDAO.findMyAttStatusMonthRequest(1, requestDate);
//		monthReqDAO.findAttStatusMonthRequest(1);//成功
		attDAO.findUsers(2);
	}
	
	//勤怠状況表登録--------------------------------------------------------------
	public int insertAttStatus(int users_id,Date date) {	
		//返却ID　連番追加確認　追加できている場合1以上
		//-1が主キーにはなりえないため
		int id = -1;
		
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
			sql = "INSERT INTO att_status (USERS_ID, YEARS,created_users_id,updated_users_id)\n"
					+ "VALUES (?, ? ,?, ?);";
			
			//★★★SQL★★★ StampRevDAOを参考に
//			//追加したい情報：打刻修正ID+打刻ID+修正出勤+退勤+休憩時間+勤怠状況+備考
//			String sql= "INSERT INTO STAMP_REVISION "
//				+ "(STAMP_ID ,WORKIN_REV ,WORKOUT_REV ,REST_TIME ,WORK_STATUS ,NOTE, created_users_id,updated_users_id )\n"
//					+ "VALUES(?,?,?,?,?,?,?,?)";
			
			
			//DATE型変換　java.util.Dateをjava.sql.Dateに変換する　　
			 long timeInMilliSeconds = date.getTime();
		     java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
		     
		   //SQLを実行する
			PreparedStatement pStmt = conn.prepareStatement(sql,java.sql.Statement.RETURN_GENERATED_KEYS);
//			pStmt.setInt(1,att_status_id);
			pStmt.setInt(1,users_id);
			pStmt.setDate(2,sqlDate);
			pStmt.setInt(3,users_id);
			pStmt.setInt(4,users_id);
			
			
			//Insert文を実行
			int result =pStmt.executeUpdate();
//			ResultSet rs = pStmt.getGeneratedKeys();
//			if(rs.next()) {
//			id = rs.getInt(1);
//			}
//			System.out.println("result" +result);
			if(result > 0) {
				try(ResultSet rs = pStmt.getGeneratedKeys()) {
					if(rs.next()) {
					   id = rs.getInt(1);
					   System.out.println(rs.getInt(1));
					   //カラムの一番目？？？
					}
						}catch (SQLException e) {
							e.printStackTrace();
							System.out.println("pStmt.getGeneratedKeys()でエラー");
							}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return -2;
		}
		return id;
		
	}
	
	//利用者の取得--------------------------------------------------------------
	//申請一覧から勤怠状況表IDを取得する
	public UsersBean  findUsers(int att_status_id) {
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
			String sql= "SELECT a.users_id, a.years, u.emp_name\r\n"
					+ "FROM att_status a\r\n"
					+ "JOIN users u ON a.users_id = u.users_id\r\n"
					+ "WHERE a.att_status_id = ?;";
			
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
                 usersBean.setEmp_name(rs.getString("emp_name"));
                 usersBean.setEmp_no(rs.getString("users_id"));
                 
                 System.out.println("取得した利用者IDは" + usersBean.getUsers_id());
//                 System.out.println("取得した氏名は" + usersBean.getEmp_name());
                 System.out.println("取得した氏名は" + usersBean.getEmp_no());
            } 
            
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return usersBean;
	}
	
	//勤怠状況表ID取得メソッド--------------------------------------------------------------
	//
	public int findAttStatusId(int users_id,Date date){
		int att_status_id = 0;
				//JDBCドライバを読み込む
				try {
					Class.forName("org.h2.Driver");
				}catch(ClassNotFoundException e) {
					e.printStackTrace();
					throw new IllegalStateException("JDBCドライバをよみこめませんでした");
				}
				try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
					
					//★★★SQL★★★
					//勤怠状況表IDを利用者IDと日時を元に取得
					String sql= "SELECT att_status_id FROM att_status where users_id = ? and years = ?";
					
					//DATE型変換　java.util.Dateをjava.sql.Dateに変換する　　
					 long timeInMilliSeconds = date.getTime();
				     java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
					
					
					//SQLを実行する
					PreparedStatement pStmt = conn.prepareStatement(sql);
					pStmt.setInt(1,users_id);
					pStmt.setDate(2,sqlDate);
					
					 //結果を処理する
					// SQLの結果を取得
					ResultSet rs = pStmt.executeQuery();
				
					
					//一件ずつ処理する
		            while (rs.next()) {
		            	att_status_id = rs.getInt("att_status_id"); 
		           
		            } 
		            
				} catch (SQLException e) {
					e.printStackTrace();
					 
				}
				return att_status_id;
				
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
//			}
//		
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//	}
	
	

