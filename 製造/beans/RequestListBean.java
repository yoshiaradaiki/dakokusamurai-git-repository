// 担当者：鈴木
// 作成日時：2024/6/12
// 修正：20246/12　湯　フィールド、コンストラクタの追加
package beans;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class RequestListBean implements Serializable {
	//月末申請テーブル主キー：月末申請ID
	private int month_req_id;
	//打刻修正申請テーブル主キー：打刻修正申請ID
	private int stamp_rev_req_id;
	//外部キー：勤怠状況表ID
	private int att_status_id;
	//外部キー：打刻修正ID
	private int stamp_rev_id;
	//申請日時
	private Timestamp date_and_time;
	//差し戻しの理由
	private String reason;
	//0：差し戻し１：承認待ち２：承認済み３：キャンセル
	private int status;
	//内容　0：変更申請 or１： 勤怠状況表提出 
	private int content;
	//申請者
	private String name;
	//承認者
	private String boss_name;

	private int created_users_id;
	private int updated_users_id;

	public RequestListBean() {
	}

	public RequestListBean(int month_req_id, int stamp_rev_req_id, int att_status_id, int stamp_rev_id,
			Timestamp date_and_time, String reason, int status, int content, String name, String boss_name,
			int created_users_id, int updated_users_id) {
		super();
		this.month_req_id = month_req_id;
		this.stamp_rev_req_id = stamp_rev_req_id;
		this.att_status_id = att_status_id;
		this.stamp_rev_id = stamp_rev_id;
		this.date_and_time = date_and_time;
		this.reason = reason;
		this.status = status;
		this.content = content;
		this.name = name;
		this.boss_name = boss_name;
		this.created_users_id = created_users_id;
		this.updated_users_id = updated_users_id;
	}

	public RequestListBean(Timestamp date_and_time, int status) {
		this.date_and_time = date_and_time;
		this.status = status;
	}

	public int getAtt_status_id() {
		return att_status_id;
	}

	public void setAtt_status_id(int att_status_id) {
		this.att_status_id = att_status_id;
	}

	public int getStamp_rev_id() {
		return stamp_rev_id;
	}

	public void setStamp_rev_id(int stamp_rev_id) {
		this.stamp_rev_id = stamp_rev_id;
	}

	public Date getDate_and_time() {
		return date_and_time;
	}

	public void setDate_and_time(Timestamp date_and_time) {
		this.date_and_time = date_and_time;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBoss_name() {
		return boss_name;
	}

	public void setBoss_name(String boss_name) {
		this.boss_name = boss_name;
	}

	public int getCreated_users_id() {
		return created_users_id;
	}

	public void setCreated_users_id(int created_users_id) {
		this.created_users_id = created_users_id;
	}

	public int getUpdated_users_id() {
		return updated_users_id;
	}

	public void setUpdated_users_id(int updated_users_id) {
		this.updated_users_id = updated_users_id;
	}

	public int getMonth_req_id() {
		return month_req_id;
	}

	public int getStamp_rev_req_id() {
		return stamp_rev_req_id;
	}

}
