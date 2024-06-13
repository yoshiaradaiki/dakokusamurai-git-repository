//***　担当者：湯 
//***　作成日時：2024/6/12
package beans;

import java.util.Date;

public class CalendarBean {
	//カレンダーテーブル主キー：カレンダーID
	private int calendar_id;
	//日付
	private Date calendar_date;
	//勤怠状況
	private String work_status;
	//備考
	private String note;
	//作成者
	private int created_users_id;
	//更新者
	private int updated_users_id;
	
 	public CalendarBean() {
		
	}

	public CalendarBean(int calendar_id, Date calendar_date, String work_status, String note, int created_users_id,
			int updated_users_id) {
		super();
		this.calendar_id = calendar_id;
		this.calendar_date = calendar_date;
		this.work_status = work_status;
		this.note = note;
		this.created_users_id = created_users_id;
		this.updated_users_id = updated_users_id;
	}

	public Date getCalendar_date() {
		return calendar_date;
	}

	public void setCalendar_date(Date calendar_date) {
		this.calendar_date = calendar_date;
	}

	public String getWork_status() {
		return work_status;
	}

	public void setWork_status(String work_status) {
		this.work_status = work_status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getCalendar_id() {
		return calendar_id;
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
