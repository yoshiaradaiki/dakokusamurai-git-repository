// 担当者：鈴木
// 作成日時：2024/6/12
package beans;

import java.io.Serializable;
import java.util.Date;

public class RequestListBean implements Serializable {
	private int request_id;
	private int att_status_id;
	private Date date_and_time;
	private String reason;
	private int status;
	private int content;
	private String name;
	private String boss_name;
	private int created_users_id;
	private int updated_users_id;

	public RequestListBean() {
	}

	public RequestListBean(int request_id, int request_foreign_id, Date date_and_time,
			String reason, int status, int content, String name, String boss_name) {
		this.request_id = request_id;
		this.att_status_id = request_foreign_id;
		this.date_and_time = date_and_time;
		this.reason = reason;
		this.status = status;
		this.content = content;
		this.name = name;
		this.boss_name = boss_name;
	}

	public int getAtt_status_id() {
		return att_status_id;
	}

	public void setAtt_status_id(int att_status_id) {
		this.att_status_id = att_status_id;
	}

	public Date getDate_and_time() {
		return date_and_time;
	}

	public void setDate_and_time(Date date_and_time) {
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

	public int getRequest_id() {
		return request_id;
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

	
}
