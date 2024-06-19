//勤怠状況Logic
//担当:長江
//2024/06/13
package logic;

import java.util.Date;
import java.util.List;

import beans.RequestListBean;
import beans.StampRevBean;
import dao.MonthReqDAO;
import dao.StampDAO;
import dao.StampRevDAO;
import dao.StampRevReqDAO;

public class AttDetailLogic {

//打刻情報があるかチェック・打刻情報がない場合は登録
//メソッド名：打刻情報があるかをチェック
//引数　　　：利用者ID,日付、打刻データ
//戻り値　　：なし
//処理概要　：打刻情報があるかチェックして、打刻情報がない場合は登録する
public void findStampCheck(int users_id, Date stamp_date) {
	StampDAO  stampDAO = new StampDAO();
	if(!stampDAO.findStampCheck(users_id,stamp_date)){	
		 stampDAO.insertStamp(users_id,stamp_date,null,null );
	}

}

//打刻修正申請にテーブルに登録する
//メソッド名：打刻修正申請を登録
//引数　　　：打刻修正申請データ
//戻り値　　：boolean true:成功　false:失敗
//処理概要　：変更申請ボタンを押すと打刻修正データを登録する
public Boolean insertStampRevReq(RequestListBean requestListBean) {
	StampRevReqDAO stampRevReqDAO = new StampRevReqDAO();
	return stampRevReqDAO.insertStampRevReq(requestListBean);					
}


//打刻修正テーブルに登録する
//メソッド名：打刻修正を登録
//引数　　　：打刻修正データ
//戻り値　　：boolean true:成功　false:失敗
//処理概要　：変更申請ボタンを押すと打刻修正データを登録する
public boolean insertStampRev(StampRevBean stampRevBean) {
	StampRevDAO stampRevDAO = new StampRevDAO();
	return stampRevDAO.insertStampRev(stampRevBean);				

}

//部下の打刻修正申請を承認か差し戻しする
//メソッド名：打刻修正申請を更新
//引数　　　：打刻修正申請ID、ステータス、理由
//戻り値　　：打刻修正申請データ
//処理概要　：承認ボタンまたは差し戻しボタンを押して部下の打刻修正申請を承認か差し戻しする
public boolean updateStampRevReq(int stamp_rev_req_id, int status, String reason,int updated_users_id) {
	StampRevReqDAO stampRevReqDAO = new StampRevReqDAO();
	return stampRevReqDAO.updateStampRevReq( stamp_rev_req_id, status,  reason, updated_users_id);				
    
}
//メソッド名：findMyRequest
//引数　　　：users_id
//戻り値　　：利用者ID
//処理概要　：
public List<RequestListBean> findMyRequest(int users_id) {
	MonthReqDAO monthReqDAO = new MonthReqDAO();
	return monthReqDAO.findMyRequest(users_id);
}

//メソッド名：findMySubRequest
//引数　　　：users_id
//戻り値　　：利用者ID
//処理概要　：
public List<RequestListBean> findMySubRequest(int users_id) {
	MonthReqDAO monthReqDAO = new MonthReqDAO();
	return monthReqDAO.findMySubRequest(users_id);
}

//メソッド名：findMySubRequest
//引数　　　：stamp_id
//戻り値　　：利用者ID
//処理概要　：stamp_idから打刻修正IDを取得する
//public int findStampRevId(int stamp_id) {
//	StampRevDAO stampRevDAO = new StampRevDAO();
//	return stampRevDAO.findStampRevId(stamp_id);
//}
    
}
