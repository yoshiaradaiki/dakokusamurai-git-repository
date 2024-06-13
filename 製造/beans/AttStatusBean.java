//***　担当者：湯 
//***　作成日時：2024/6/12
package beans;

import java.time.LocalDate;

public class AttStatusBean {
	//勤怠状況表テーブル主キー：勤怠状況表ID
	private int att_status_id;
	//外部キー：利用者ID
	private int users_id;
	//年月
	private LocalDate years;
	//作成者
	private int created_users_id;
	//更新者
	private int updated_users_id;

	public AttStatusBean() {

	}

	public AttStatusBean(int att_status_id, int users_id, LocalDate years, int created_users_id, int updated_users_id) {
		super();
		this.att_status_id = att_status_id;
		this.users_id = users_id;
		this.years = years;
		this.created_users_id = created_users_id;
		this.updated_users_id = updated_users_id;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public LocalDate getYears() {
		return years;
	}

	public void setYears(LocalDate years) {
		this.years = years;
	}

	public int getAtt_status_id() {
		return att_status_id;
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
