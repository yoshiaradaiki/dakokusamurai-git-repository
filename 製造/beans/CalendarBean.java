//***　担当者：湯 
//***　作成日時：2024/6/12
package beans;

import java.util.Date;

public class CalendarBean {
	private int calendar_id;
	private Date calendar_date;
	private String work_status;
	private String note;
	
	public CalendarBean() {
		
	}
	
	public CalendarBean(int calendar_id, Date calendar_date, String work_status, String note) {
		this.calendar_id = calendar_id;
		this.calendar_date = calendar_date;
		this.work_status = work_status;
		this.note = note;
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
	
	
}
