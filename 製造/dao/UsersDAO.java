//作成者：鈴木
//作成日時：6/12

package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import beans.UserBean;

public class UsersDAO {
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\dakokuSamuraiDB\\dakokuSamuraiDB";
	private final String DB_USER = "dakokuSamurai";
	private final String DB_PASS = "dakokusamurai";
	
	//メソッド　：findLoginCheck
	//引数　　　：ログインID、パスワード
	//戻り値　　：ユーザーデータ（利用者ID,社員番号,氏名,権限レベル,上司フラグ）※存在しない場合は、NULL
	public UserBean findLoginCheck(String login_id, String password) {
		UserBean users = null;
		try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバの読み込みに失敗しました");
		}
		try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			String sql = "                                                             \n"
					+ "CASE                                                            \n"
					+ "WHEN EXISTS (                                                   \n"
					+ "SELECT *                                                        \n"
					+ "FROM users                                                      \n"
					+ "WHERE boss_users_id = (SELECT users_id FROM users               \n"
					+ "                       WHERE login_id = ?                       \n"
					+ "                       AND password = ?                         \n"
					+ "                       AND deleteflag = false)                  \n"
					+ "THEN true                                                       \n"
					+ "ELSE false                                                      \n"
					+ "END as boss_flag                                                \n"
					+ "FROM users                                                      \n"
					+ "WHERE login_id = ? AND password = ? AND delete_flag = false";
			PreparedStatement ps = conn.prepareStatement(sql);
			//
			ps.setString(1,login_id );
			ps.setString(2,password);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int users_id = rs.getInt("users_id");
				String emp_name = rs.getString("emp_name");
				String boss_users_id = rs.getString("boss_users_id");
				int level = rs.getInt("level");
				Boolean boss_flag = rs.getBoolean("boss_flag");
				users = new UserBean();
				users.setUsers_id(users_id);
				users.setEmp_name(emp_name);
				users.setBoss_users_id(boss_users_id);
				users.setLevel(level);
				users.setBoss_flag(boss_flag);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	//メソッド名：findMyAttStatusUsers
	//引数　　　：利用者ID
	//戻り値　　：勤怠状況表/勤怠状況詳細に表示する値（年月,社員番号,氏名）※存在しない場合は、NULL
	public UserBean findMyAttStatusUsers(int users_id) {
		UserBean users = null;
		try {
			Class.forName("org.h2.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバの読み込みに失敗しました");
		}
		try(Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			String sql = "                   \n"
					+ "SELECT *              \n"
					+ "FROM users            \n"
					+ "WHERE users_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			//
			ps.setInt(1,users_id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Date year_and_month = rs.getDate("year_and_month");
				String emp_no = rs.getString("emp_no");
				String emp_name = rs.getString("emp_name");
				users = new UserBean();
				users.setYear_and_month(year_and_month);
				users.setEmp_no(emp_no);
				users.setEmp_name(emp_name);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
}
