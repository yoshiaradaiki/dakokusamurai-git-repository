// 担当者：湯
// 作成日時：2024/6/13
package logic;

import java.util.Date;
import java.util.List;

import beans.RequestListBean;
import beans.StampBean;
import beans.UsersBean;
import dao.AttDAO;
import dao.CalendarDAO;
import dao.MonthReqDAO;
import dao.StampRevReqDAO;

public class RequestListLogic {

	//メソッド名：自分の利用者IDを取得（勤怠状況表画面のヘッダーに表示）
	//引数　　　：セッションスコープに保存された利用者ID
	//戻り値　　：勤怠状況表データ
	//処理概要　：再提出ボタン：選択された月末申請の勤怠状況表のヘッダーに利用者IDを表示
	public UsersBean findMyAttStatusUsers(int att_status_id) {
		AttDAO attDAO = new AttDAO();
		return attDAO.findUsers(att_status_id);
	}

	//メソッド名：差し戻された月末申請（勤怠状況表）を取得
	//引数　　　：勤怠状況表ID
	//戻り値　　：打刻リスト
	//処理概要　：再提出ボタン：差し戻された一ヶ月分の勤怠状況表を取得する
	public List<StampBean> findAttStatus(int users_id, Date date) {
		CalendarDAO calendarDAO = new CalendarDAO();
		return calendarDAO.findMyAttStatusMonthStamp(users_id, date);
	}

	//メソッド名：勤怠状況表に差し戻された理由を取得
	//引数　　　：勤怠状況表ID
	//戻り値　　：月末申請リスト
	//処理概要　：再提出ボタン：勤怠状況表に差し戻しの理由を取得し、表示する
	public RequestListBean findAttStatusMonthReason(int att_status_id) {
		MonthReqDAO monthReqDAO = new MonthReqDAO();
		return monthReqDAO.findAttStatusMonthRequest(att_status_id);
	}

	//メソッド名：自分の利用者IDを取得（勤怠状況詳細画面のヘッダーに表示）
	//引数　　　：セッションスコープに保存された利用者ID
	//戻り値　　：利用者データ
	//処理概要　：再申請ボタン：選択された変更申請の勤怠状況詳細画面のヘッダーに利用者IDを表示する
	public UsersBean findMyAttDetailUsers(int att_status_id) {
		AttDAO attDAO = new AttDAO();
		return attDAO.findUsers(att_status_id);
	}

	//メソッド名：差し戻された変更申請（勤怠状況詳細）を取得
	//引数　　　：打刻修正ID
	//戻り値　　：打刻修正データ
	//処理概要　：再申請ボタン：選択された差し戻された一日分の打刻修正データ（勤怠状況詳細画面）を取得する
	public StampBean findAttStatusDetail(int users_id, Date date) {
		CalendarDAO calendarDAO = new CalendarDAO();
		return calendarDAO.findMyAttStatusDetailStamp(users_id, date);
	}

	//メソッド名：勤怠状況詳細に差し戻された理由を取得
	//引数　　　：打刻修正ID
	//戻り値　　：月末申請リスト
	//処理概要　：再申請ボタン：選択された差し戻された一日分の打刻修正データの差し戻しの理由を取得
	public RequestListBean findAttDetailReason(int stamp_rev_id) {
		StampRevReqDAO stampRevReqDAO = new StampRevReqDAO();
		return stampRevReqDAO.findAttStatusDetailRequest(stamp_rev_id);
	}

	//メソッド名：月末申請にデータ更新
	//引数　　　：月末申請ID
	//戻り値　　：boolean(true：成功、false：失敗)
	//処理概要　：キャンセルボタン：月末申請データを更新し、申請一覧画面再描画する
	public Boolean updateReqCancelByAttStatus(int month_req_id, int status, String reason, int updated_users_id) {
		MonthReqDAO monthReqDAO = new MonthReqDAO();
		return monthReqDAO.updateMonthReq(month_req_id, status, reason, updated_users_id);
	}

	//メソッド名：打刻修正申請にデータ更新
	//引数　　　：打刻修正申請ID
	//戻り値　　：boolean(true：成功、false：失敗)
	//処理概要　：キャンセルボタン：打刻修正申請データを更新し、申請一覧画面再描画する
	public Boolean updateReqCancelByOneDay(int stamp_rev_req_id, int status, String reason, int updated_users_id) {
		StampRevReqDAO stampRevReqDAO = new StampRevReqDAO();
		return stampRevReqDAO.updateStampRevReq(stamp_rev_req_id, status, reason, updated_users_id);
	}

	//メソッド名：部下の利用者IDと年月を取得（勤怠状況表画面のヘッダーに表示）
	//引数　　　：セッションスコープに保存された利用者ID
	//戻り値　　：利用者ID
	//処理概要　：申請詳細ボタン：選択された年月の勤怠状況表のヘッダーに部下の利用者IDを表示する
	public UsersBean findMySubAttStatusUsers(int att_status_id) {
		AttDAO attDAO = new AttDAO();
		return attDAO.findUsers(att_status_id);
	}

	//メソッド名：部下の月末申請（勤怠状況表）を取得
	//引数　　　：勤怠状況表ID
	//戻り値　　：打刻リスト
	//処理概要　：申請詳細ボタン：選択された年月の勤怠状況表データの一覧を取得
	public List<StampBean> findAttStatusMonthStamp(int users_id, Date date) {
		CalendarDAO calendarDAO = new CalendarDAO();
		return calendarDAO.findMyAttStatusMonthStamp(users_id, date);
	}

	//メソッド名：部下の利用者IDを取得（勤怠状況詳細画面のヘッダーに表示）
	//引数　　　：部下の利用者ID
	//戻り値　　：利用者データ
	//処理概要　：変更詳細ボタン：選択された一日分の打刻修正データのヘッダーに部下の利用者IDを表示する
	public UsersBean findMySubAttDetailUsers(int att_status_id) {
		AttDAO attDAO = new AttDAO();
		//セッションスコープに保存された利用者ID
		return attDAO.findUsers(att_status_id);
	}

	//メソッド名：部下の変更申請（勤怠状況詳細）を取得
	//引数　　　：打刻修正ID
	//戻り値　　：打刻修正データ
	//処理概要　：変更詳細ボタン：選択された一日分の打刻修正データの詳細を取得
	public StampBean findAttDetailStamp(int users_id, Date date) {
		CalendarDAO calendarDAO = new CalendarDAO();
		return calendarDAO.findMyAttStatusDetailStamp(users_id, date);
	}

}
