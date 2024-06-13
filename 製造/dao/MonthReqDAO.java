// 担当者：湯
// 作成日時：2024/6/12
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.RequestListBean;
import beans.UsersBean;

public class MonthReqDAO {

	//H2に接続準備
	final String JDBC_URL = "jdbc:h2:C:/dakokuSamuraiDB/dakokuSamuraiDB";
	final String DB_USER = "dakokuSamurai";
	final String DB_PASSWORD = "dakokusamurai";

	public static void main(String[] args) {
		//テスト用
//		MonthReqDAO monthReqDAO = new MonthReqDAO();
//		monthReqDAO.insertMonthReq(1, 10, 10, 10);//成功
//	/monthReqDAO.updateMonthReq(8, 8, "理由うう", 3);//成功
	}

	//メソッド名：月末申請登録
	//引数　　　：勤怠状況表ID、ステータス、作成者、更新者
	//戻り値　　：boolean(true：成功、false：失敗)
	//テスト：成功　湯
	public Boolean insertMonthReq(int att_status_id, int status, int created_users_id, int updated_users_id) {

		//H2DBへ接続する
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("H2データベースに接続しました。");

			//INSERT文の準備
			String insertSql = "INSERT INTO month_req (att_status_id, status,　created_users_id, updated_users_id)\n"
					+ "VALUES (?,?, ?, ?); ";
			PreparedStatement pStmt = conn.prepareStatement(insertSql);

			//INSERT文の？に使用する値を設定
			RequestListBean reqListBean = new RequestListBean();
			pStmt.setInt(1, reqListBean.getAtt_status_id());
			pStmt.setInt(2, reqListBean.getStatus());
			pStmt.setInt(3, reqListBean.getCreated_users_id());
			pStmt.setInt(4, reqListBean.getUpdated_users_id());

			//INSERT文を実行
			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//メソッド名：月末申請更新
	//引数　　　：月末申請ID、承認・差し戻し、理由、更新者＝上司の利用者ID
	//戻り値　　：boolean(true：成功、false：失敗)
	//テスト：成功　湯
	public Boolean updateMonthReq(int month_req_id, int status, String reason, int updated_users_id) {

		//H2DBへ接続する
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("H2データベースに接続しました。");

			//UPDATE文の準備
			String updateSql = "UPDATE month_req "
					+ "SET status = ?, reason = ?, updated_users_id = ?"
					+ "WHERE month_req_id = ?;";
			PreparedStatement pStmt = conn.prepareStatement(updateSql);

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
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//メソッド名：自分の申請を取得
	//引数　　　：自分の利用者ID
	//戻り値　　：申請一覧リスト
	public List<RequestListBean> findMyRequest(int users_id) {
		//ArrayList作成
		List<RequestListBean> myReqList = new ArrayList<>();

		//H2DBへ接続する
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("H2データベースに接続しました。");

			//SELECT文の準備　ステータス番号の小さい順で並べる
			String selectSql = "SELECT m.date_req, m.status, u.users_id FROM month_req m\n"
					+ " INNER JOIN att_status a ON m.att_status_id = a.att_status_id\n"
					+ " INNER JOIN users u ON m.users_id = u.users_id\n"
					+ "UNION\n"
					+ "SELECT s.date_req, s.status, u.users_id\n"
					+ "FROM stamp_correct_req s\n"
					+ "INNER JOIN stamp_revision r ON s.stamp_revision_id = r.stamp_revision_id\n"
					+ "INNER JOIN users u ON r.users_id = u.users_id\n"
					+ "WHERE u.users_id = ?\n"
					+ "ORDER BY status ASC;";
			PreparedStatement pStmt = conn.prepareStatement(selectSql);
			//UPDATE文の？に使用する値を設定
			UsersBean usersBean = new UsersBean();
			pStmt.setInt(1, usersBean.getUsers_id());

			//SELECT文を実行
			ResultSet rs = pStmt.executeQuery();

			//SELECT文の結果をArrayListに格納
			while (rs.next()) {
				Timestamp date_and_time = rs.getTimestamp("date_and_time");
				int status = rs.getInt("status");
				//RequestListBean reqListBean = new RequestListBean(date_and_time, status);
				myReqList.add(new RequestListBean(date_and_time, status));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return myReqList;
	}

	//メソッド名：部下の申請を取得
	//引数　　　：部下の利用者ID
	//戻り値　　：申請一覧リスト
	public List<RequestListBean> findMySubRequest(int users_id) {
		//ArrayList作成
		List<RequestListBean> subReqList = new ArrayList<>();

		//H2DBへ接続する
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("H2データベースに接続しました。");

			//SELECT文の準備　ステータス番号の小さい順で並べる
			String selectSql = "SELECT m.date_req, m.status, u.users_id FROM month_req m\n"
					+ " INNER JOIN att_status a ON m.att_status_id = a.att_status_id\n"
					+ " INNER JOIN users u ON m.users_id = u.users_id\n"
					+ "UNION\n"
					+ "SELECT s.date_req, s.status, u.users_id\n"
					+ "FROM stamp_correct_req s\n"
					+ "INNER JOIN stamp_revision r ON s.stamp_revision_id = r.stamp_revision_id\n"
					+ "INNER JOIN users u ON r.users_id = u.users_id\n"
					+ "WHERE u.users_id = ?\n"
					+ "ORDER BY status ASC;";
			PreparedStatement pStmt = conn.prepareStatement(selectSql);
			//UPDATE文の？に使用する値を設定
			UsersBean usersBean = new UsersBean();
			pStmt.setInt(1, usersBean.getUsers_id());

			//SELECT文を実行
			ResultSet rs = pStmt.executeQuery();

			//SELECT文の結果をArrayListに格納
			while (rs.next()) {
				Timestamp date_and_time = rs.getTimestamp("date_and_time");
				int status = rs.getInt("status");
				//RequestListBean reqListBean = new RequestListBean(date_and_time, status);
				subReqList.add(new RequestListBean(date_and_time, status));
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return subReqList;
	}

	//メソッド名：自分の勤怠状況表に表示する差し戻しの理由を取得（勤怠状況表画面で年月を選択する時）
	//引数　　　：利用者ID、勤怠状況表.日時
	//戻り値　　：申請一覧リスト（打刻修正申請テーブル+月末申請テーブル）
	public RequestListBean findMyAttStatusMonthRequest(int users_id, Date years) {//選択された年月の一番新しい差し戻された理由を表示
		RequestListBean requestListBean = null;

		//H2DBへ接続する
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("H2データベースに接続しました。");

			//SELECT文の準備
			String selectSql = "SELECT month_req.reason\n"
					+ "FROM month_req\n"
					+ "INNER JOIN att_status ON month_req.users_id = att_status.users_id\n"
					+ "INNER JOIN users ON att_status.users_id = users.users_id\n"
					+ "WHERE users.users_id = ? AND att_status.years = ?;";//選択された年月の一番新しい差し戻された理由を表示

			PreparedStatement pStmt = conn.prepareStatement(selectSql);

			//型の変換
			long timeInMilliSeconds = years.getTime();
			java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);
			//UPDATE文の？に使用する値を設定
			//UsersBean usersBean = new UsersBean();
			//AttStatusBean attStatusBran = new AttStatusBean();
			pStmt.setInt(1, users_id);//セッションスコープに保存された利用者IDを取得
			pStmt.setDate(2, sqlDate);//選択された年月を取得

			//実行
			pStmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return requestListBean;
	}

	//メソッド名：勤怠状況表に表示する差し戻しの理由を取得
	//引数　　　：勤怠状況表ID
	//戻り値　　：申請一覧リスト（打刻修正申請テーブル+月末申請テーブル）
	public RequestListBean findAttStatusMonthRequest(int att_status_id) {
		RequestListBean requestListBean = null;

		//H2DBへ接続する
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("H2データベースに接続しました。");

			//SELECT文の準備
			String selectSql = "SELECT reason\n"
					+ "FROM month_req\n"
					+ "WHERE att_status_id = ?";
			PreparedStatement pStmt = conn.prepareStatement(selectSql);

			//UPDATE文の？に使用する値を設定
			RequestListBean reqListBean = new RequestListBean();
			pStmt.setInt(1, reqListBean.getAtt_status_id());

			//実行
			pStmt.executeQuery();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return requestListBean;
	}
}
