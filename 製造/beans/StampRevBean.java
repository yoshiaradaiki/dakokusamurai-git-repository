// 担当者：吉新
// 作成日時：2024/06/12
package beans;

import java.sql.Timestamp;

public class StampRevBean {
	// フィールド
	private int request_id;
	private int request_foreign_id;
	private Timestamp date_and_time;
	private String reason;
	private int status;
	private int content;
	private String name;
	private String boss_name;
	
	// コンストラクタ
	public StampRevBean() {}
	
	public StampRevBean(int request_id, int request_foreign_id, Timestamp date_and_time, String reason, int status,
			int content, String name, String boss_name) {
		this.request_id = request_id;
		this.request_foreign_id = request_foreign_id;
		this.date_and_time = date_and_time;
		this.reason = reason;
		this.status = status;
		this.content = content;
		this.name = name;
		this.boss_name = boss_name;
	}

	// ゲッターセッター
	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public int getRequest_foreign_id() {
		return request_foreign_id;
	}

	public void setRequest_foreign_id(int request_foreign_id) {
		this.request_foreign_id = request_foreign_id;
	}

	public Timestamp getDate_and_time() {
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
}
