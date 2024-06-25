// 担当者：湯
// 作成日時：2024/6/12
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.RequestListBean;

public class MonthReqDAO {

	//H2に接続準備
	final String JDBC_URL = "jdbc:h2:tcp://localhost/C:\\dakokuSamuraiDB\\dakokuSamuraiDB";
	final String DB_USER = "dakokuSamurai";
	final String DB_PASSWORD = "dakokusamurai";

	public static void main(String[] args) {
		//テスト用
		//		MonthReqDAO monthReqDAO = new MonthReqDAO();//インスタンス
		//		monthReqDAO.insertMonthReq(1, 0, 10, 10);//成功
		//		monthReqDAO.updateMonthReq(9, 8, "理由うう", 3);//成功
		//		monthReqDAO.findMyRequest(1);//成功
		//		monthReqDAO.findMySubRequest(1);//成功

		//		@SuppressWarnings("deprecation")
		//		Date requestDate = new Date(2024, 6, 1); // 例として2024年6月1日を使用します
		//		monthReqDAO.findMyAttStatusMonthRequest(1, requestDate);
		//		monthReqDAO.findAttStatusMonthRequest(1);//成功
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
			String insertSql = "INSERT INTO month_req (att_status_id, status, created_users_id, updated_users_id)\n"
					+ "VALUES (?, ?, ?, ?); ";
			PreparedStatement pStmt = conn.prepareStatement(insertSql);

			//INSERT文の？に使用する値を設定
			pStmt.setInt(1, att_status_id);
			pStmt.setInt(2, status);
			pStmt.setInt(3, created_users_id);
			pStmt.setInt(4, updated_users_id);

			//INSERT文を実行
			int result = pStmt.executeUpdate();
			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("エラー：データを追加できませんでした。");
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
					+ "SET status = ?, updated_users_id = ?, reason = ?"
					+ "WHERE month_req_id = ?;";
			PreparedStatement pStmt = conn.prepareStatement(updateSql);

			//UPDATE文の？に使用する値を設定
			pStmt.setInt(1, status);
			pStmt.setInt(2, updated_users_id);
			pStmt.setString(3, reason);
			pStmt.setInt(4, month_req_id);

			//UPDATE文を実行
			int result = pStmt.executeUpdate();

			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("エラー：データを更新できませんでした。");
			return false;
		}
		return true;
	}

	//メソッド名：打刻修正申請更新
	//引数　　　：打刻修正申請ID、承認・差し戻し、理由、更新者＝上司の利用者ID
	//戻り値　　：boolean(true：成功、false：失敗)
	//テスト：成功　湯
	public Boolean updateOneDayReq(int stamp_rev_req_id, int status, String reason, int updated_users_id) {

		//H2DBへ接続する
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("H2データベースに接続しました。");

			//UPDATE文の準備
			String updateSql = "UPDATE stamp_correct_req "
					+ "SET status = ?, reason = ?, updated_users_id = ?"
					+ "WHERE stamp_rev_req_id = ?;";
			PreparedStatement pStmt = conn.prepareStatement(updateSql);

			//UPDATE文の？に使用する値を設定
			pStmt.setInt(1, status);
			pStmt.setString(2, reason);
			pStmt.setInt(3, updated_users_id);
			pStmt.setInt(4, stamp_rev_req_id);

			//UPDATE文を実行
			int result = pStmt.executeUpdate();

			if (result != 1) {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("エラー：データを更新できませんでした。");
			return false;
		}
		return true;
	}

	//メソッド名：自分の申請を取得
	//引数　　　：自分の利用者ID
	//戻り値　　：申請一覧リスト
	//テスト：成功　湯
	public List<RequestListBean> findMyRequest(int users_id) {
		//ArrayList作成
		List<RequestListBean> myReqList = new ArrayList<>();

		//H2DBへ接続する
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("H2データベースに接続しました。");

			//SELECT文の準備　ステータス番号の小さい順で並べる
			String selectSql = "SELECT\r\n"
					+ "    m.date_req,\r\n"
					+ "    m.status,\r\n"
					+ "    u2.emp_name,\r\n"
					+ "    ast.att_status_id AS att_status_id,\r\n"
					+ "    m.month_req_id AS month_req_id,\r\n"
					+ "    NULL AS stamp_rev_id, -- ダミーの列を追加\r\n"
					+ "    NULL AS stamp_rev_req_id, -- ダミーの列を追加\r\n"
					+ "    0 AS content\r\n"
					+ "FROM\r\n"
					+ "    month_req m\r\n"
					+ "    JOIN att_status ast ON m.att_status_id = ast.att_status_id\r\n"
					+ "    JOIN users u1 ON u1.users_id = ast.users_id\r\n"
					+ "    JOIN users u2 ON u2.users_id = u1.boss_users_id\r\n"
					+ "WHERE\r\n"
					+ "    u1.users_id = ?\r\n"
					+ "    AND m.status IN (0, 1, 2)\r\n"
					+ "\r\n"
					+ "UNION ALL\r\n"
					+ "\r\n"
					+ "SELECT\r\n"
					+ "    scr.date_req,\r\n"
					+ "    scr.status,\r\n"
					+ "    u2.emp_name,\r\n"
					+ "    NULL AS att_status_id, -- ダミーの列を追加\r\n"
					+ "    NULL AS month_req_id, -- ダミーの列を追加\r\n"
					+ "    sr.stamp_rev_id AS stamp_rev_id,\r\n"
					+ "    scr.stamp_rev_req_id AS stamp_rev_req_id,\r\n"
					+ "    1 AS content\r\n"
					+ "FROM\r\n"
					+ "    stamp_correct_req scr\r\n"
					+ "    JOIN stamp_revision sr ON scr.stamp_rev_id = sr.stamp_rev_id\r\n"
					+ "    JOIN stamp s ON s.stamp_id = sr.stamp_id\r\n"
					+ "    JOIN users u1 ON u1.users_id = s.users_id\r\n"
					+ "    JOIN users u2 ON u2.users_id = u1.boss_users_id\r\n"
					+ "WHERE\r\n"
					+ "    u1.users_id = ?\r\n"
					+ "    AND scr.status IN (0, 1, 2)\r\n"
					+ "\r\n"
					+ "ORDER BY\r\n"
					+ "    status ASC,\r\n"
					+ "    date_req DESC;\r\n"
					+ "";
			PreparedStatement pStmt = conn.prepareStatement(selectSql);
			//UPDATE文の？に使用する値を設定
			pStmt.setInt(1, users_id);
			pStmt.setInt(2, users_id);

			//SELECT文を実行
			ResultSet rs = pStmt.executeQuery();

			//SELECT文の結果をArrayListに格納
			while (rs.next()) {
				RequestListBean reqListBean = new RequestListBean();
				//申請日時を取得
				reqListBean.setDate_and_time(rs.getDate("date_req"));
				reqListBean.setStatus(rs.getInt("status"));
				reqListBean.setBoss_name(rs.getString("emp_name"));
				reqListBean.setContent(rs.getInt("content"));
				//				reqListBean.setRequest_id(rs.getInt("request_id"));//月末申請ID打刻修正申請ID
				//				reqListBean.setRequest_foreign_id(rs.getInt("request_foreign_id"));
				reqListBean.setAtt_status_id(rs.getInt("att_status_id"));
				reqListBean.setMonth_req_id(rs.getInt("month_req_id"));
				reqListBean.setStamp_rev_id(rs.getInt("stamp_rev_id"));
				reqListBean.setStamp_rev_req_id(rs.getInt("stamp_rev_req_id"));

				//検査結果出力
				//				System.out.print(reqListBean.getDate_and_time() + " ");
				//				System.out.print(reqListBean.getStatus() + " ");
				//				System.out.print(reqListBean.getBoss_name() + " ");//承認者上司名
				//				System.out.print(reqListBean.getContent() + " ");
				//				System.out.print(reqListBean.getRequest_id() + " ");
				//				System.out.println(reqListBean.getRequest_foreign_id() + " ");
				//				System.out.print(reqListBean.getAtt_status_id() + " ");
				//				System.out.print(reqListBean.getMonth_req_id() + " ");
				//				System.out.print(reqListBean.getStamp_rev_id() + " ");
				//				System.out.println(reqListBean.getStamp_rev_req_id() + " ");

				myReqList.add(reqListBean);
			}
			System.out.println("検索成功しました。");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return myReqList;
	}

	//メソッド名：部下の申請を取得
	//引数　　　：自分の利用者ID(上司利用者ID)
	//戻り値　　：申請一覧リスト
	//テスト：成功　湯
	public List<RequestListBean> findMySubRequest(int users_id) {
		//ArrayList作成
		List<RequestListBean> subReqList = new ArrayList<>();

		//H2DBへ接続する
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("H2データベースに接続しました。");

			//SELECT文の準備　ステータス番号の小さい順で並べる
			String selectSql = "SELECT\r\n"
					+ "    m.date_req,\r\n"
					+ "    m.status,\r\n"
					+ "    u1.emp_name,\r\n"
					+ "    ast.att_status_id AS att_status_id,\r\n"
					+ "    m.month_req_id AS month_req_id,\r\n"
					+ "    NULL AS stamp_rev_id, -- ダミーの列を追加\r\n"
					+ "    NULL AS stamp_rev_req_id, -- ダミーの列を追加\r\n"
					+ "    0 AS content\r\n"
					+ "FROM\r\n"
					+ "    month_req m\r\n"
					+ "    JOIN att_status ast ON m.att_status_id = ast.att_status_id\r\n"
					+ "    JOIN users u1 ON u1.users_id = ast.users_id\r\n"
					+ "    JOIN users u2 ON u2.users_id = u1.boss_users_id\r\n"
					+ "WHERE\r\n"
					+ "    u2.users_id = ?\r\n"
					+ "    AND m.status IN (1, 2)\r\n"
					+ "\r\n"
					+ "UNION ALL\r\n"
					+ "\r\n"
					+ "SELECT\r\n"
					+ "    scr.date_req,\r\n"
					+ "    scr.status,\r\n"
					+ "    u1.emp_name,\r\n"
					+ "    NULL AS att_status_id, -- ダミーの列を追加\r\n"
					+ "    NULL AS month_req_id, -- ダミーの列を追加\r\n"
					+ "    sr.stamp_rev_id AS stamp_rev_id,\r\n"
					+ "    scr.stamp_rev_req_id AS stamp_rev_req_id,\r\n"
					+ "    1 AS content\r\n"
					+ "FROM\r\n"
					+ "    stamp_correct_req scr\r\n"
					+ "    JOIN stamp_revision sr ON scr.stamp_rev_id = sr.stamp_rev_id\r\n"
					+ "    JOIN stamp s ON s.stamp_id = sr.stamp_id\r\n"
					+ "    JOIN users u1 ON u1.users_id = s.users_id\r\n"
					+ "    JOIN users u2 ON u2.users_id = u1.boss_users_id\r\n"
					+ "WHERE\r\n"
					+ "    u2.users_id = ?\r\n"
					+ "    AND scr.status IN (1, 2)\r\n"
					+ "\r\n"
					+ "ORDER BY\r\n"
					+ "    status ASC,\r\n"
					+ "    date_req DESC;";
			PreparedStatement pStmt = conn.prepareStatement(selectSql);
			//UPDATE文の？に使用する値を設定
			pStmt.setInt(1, users_id);
			pStmt.setInt(2, users_id);

			//SELECT文を実行
			ResultSet rs = pStmt.executeQuery();

			//SELECT文の結果をArrayListに格納
			while (rs.next()) {
				RequestListBean reqListBean = new RequestListBean();
				//申請日時を取得
				reqListBean.setDate_and_time(rs.getDate("date_req"));
				reqListBean.setStatus(rs.getInt("status"));
				reqListBean.setName(rs.getString("emp_name"));//申請者
				reqListBean.setContent(rs.getInt("content"));
				//				reqListBean.setRequest_id(rs.getInt("request_id"));//月末申請ID打刻修正申請ID
				//				reqListBean.setRequest_foreign_id(rs.getInt("reuqest_foreign_id"));//外部キー
				reqListBean.setAtt_status_id(rs.getInt("att_status_id"));
				reqListBean.setMonth_req_id(rs.getInt("month_req_id"));
				reqListBean.setStamp_rev_id(rs.getInt("stamp_rev_id"));
				reqListBean.setStamp_rev_req_id(rs.getInt("stamp_rev_req_id"));
				//検査結果出力
//				System.out.print(reqListBean.getDate_and_time() + " ");
//				System.out.print(reqListBean.getStatus() + " ");
//				System.out.print(reqListBean.getName() + " ");
//				System.out.print(reqListBean.getContent() + " ");
//				System.out.print(reqListBean.getRequest_id() + " ");
//				System.out.println(reqListBean.getRequest_foreign_id() + " ");
//				System.out.print(reqListBean.getRequest_id() + " ");
//				System.out.println(reqListBean.getRequest_foreign_id() + " ");
//				System.out.print(reqListBean.getAtt_status_id() + " ");
//				System.out.print(reqListBean.getMonth_req_id() + " ");
//				System.out.print(reqListBean.getStamp_rev_id() + " ");
//				System.out.println(reqListBean.getStamp_rev_req_id() + " ");

				subReqList.add(reqListBean);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("検索成功しました。");
			return null;
		}

		return subReqList;
	}

	//メソッド名：自分の勤怠状況表に表示する差し戻しの理由を取得（勤怠状況表画面で年月を選択する時）
	//引数　　　：利用者ID、勤怠状況表.日時
	//戻り値　　：申請一覧リスト（打刻修正申請テーブル+月末申請テーブル）
	//テスト：成功　湯
	public RequestListBean findMyAttStatusMonthRequest(int users_id, Date years) {//選択された年月の一番新しい差し戻された理由を表示
		RequestListBean requestListBean = null;

		//H2DBへ接続する
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
			System.out.println("H2データベースに接続しました。");

			//SELECT文の準備
			String selectSql = "SELECT month_req.reason\n"
					+ "FROM month_req\n"
					+ "INNER JOIN att_status ON month_req.att_status_id = att_status.att_status_id\n"
					+ "INNER JOIN users ON att_status.users_id = users.users_id\n"
					+ "WHERE users.users_id = ? AND att_status.years = ?;";//選択された年月の一番新しい差し戻された理由を表示

			PreparedStatement pStmt = conn.prepareStatement(selectSql);

			//型の変換
			long timeInMilliSeconds = years.getTime();
			java.sql.Date sqlDate = new java.sql.Date(timeInMilliSeconds);

			//UPDATE文の？に使用する値を設定
			pStmt.setInt(1, users_id);//セッションスコープに保存された利用者IDを取得
			pStmt.setDate(2, sqlDate);//選択された年月を取得

			//実行
			pStmt.executeQuery();

			RequestListBean reqListBean = new RequestListBean();
			System.out.println(reqListBean.getReason());

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("検索成功しました。");
			return null;
		}
		return requestListBean;
	}

	//メソッド名：勤怠状況表に表示する差し戻しの理由を取得
	//引数　　　：勤怠状況表ID
	//戻り値　　：申請一覧リスト（打刻修正申請テーブル+月末申請テーブル）
	//テスト：成功　湯
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
			pStmt.setInt(1, reqListBean.getRequest_id());

			//実行
			pStmt.executeQuery();

			System.out.println(reqListBean.getReason());

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("検索成功しました。");
			return null;
		}
		return requestListBean;
	}
}
