//担当者:長江
//作成日時:2024/06/12


package beans;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

public class StampBean implements Serializable {

	private int stamp_id;
	private int users_id;
	private Date stamp_date;
	private LocalTime workIn_raw;
	private LocalTime workOut_raw;
	private LocalTime workIn_re;
	private LocalTime workOut_re;
	private int work_status;
	private int week;
	private LocalTime rest_time;
	private LocalTime real_work_time;
	private String note;
	
	public StampBean() {
	}


	
	public StampBean(int stamp_id, int users_id, Date stamp_date, LocalTime workIn_raw, LocalTime workOut_raw, LocalTime workIn_re,
			LocalTime workOut_re, int work_status, int week, LocalTime rest_time, LocalTime real_work_time, String note) {
		super();
		this.stamp_id = stamp_id;
		this.users_id = users_id;
		this.stamp_date = stamp_date;
		this.workIn_raw = workIn_raw;
		this.workOut_raw = workOut_raw;
		this.workIn_re = workIn_re;
		this.workOut_re = workOut_re;
		this.work_status = work_status;
		this.week = week;
		this.rest_time = rest_time;
		this.real_work_time = real_work_time;
		this.note = note;
	}



	public int getUsers_id() {
		return users_id;
	}



	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}



	public Date getStamp_date() {
		return stamp_date;
	}



	public void setStamp_date(Date stamp_date) {
		this.stamp_date = stamp_date;
	}



	public LocalTime getWorkIn_raw() {
		return workIn_raw;
	}



	public void setWorkIn_raw(LocalTime workIn_raw) {
		this.workIn_raw = workIn_raw;
	}



	public LocalTime getWorkOut_raw() {
		return workOut_raw;
	}



	public void setWorkOut_raw(LocalTime workOut_raw) {
		this.workOut_raw = workOut_raw;
	}



	public LocalTime getWorkIn_re() {
		return workIn_re;
	}



	public void setWorkIn_re(LocalTime workIn_re) {
		this.workIn_re = workIn_re;
	}



	public LocalTime getWorkOut_re() {
		return workOut_re;
	}



	public void setWorkOut_re(LocalTime workOut_re) {
		this.workOut_re = workOut_re;
	}



	public int getWork_status() {
		return work_status;
	}



	public void setWork_status(int work_status) {
		this.work_status = work_status;
	}



	public int getWeek() {
		return week;
	}



	public void setWeek(int week) {
		this.week = week;
	}



	public LocalTime getRest_time() {
		return rest_time;
	}



	public void setRest_time(LocalTime rest_time) {
		this.rest_time = rest_time;
	}



	public LocalTime getReal_work_time() {
		return real_work_time;
	}



	public void setReal_work_time(LocalTime real_work_time) {
		this.real_work_time = real_work_time;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public int getStamp_id() {
		return stamp_id;
	}



	public void setStamp_id(int stamp_id) {
		this.stamp_id = stamp_id;
	}
}
