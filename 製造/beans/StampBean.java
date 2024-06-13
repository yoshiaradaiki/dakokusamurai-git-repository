//担当者:長江
//作成日時:2024/06/12


package beans;

import java.io.Serializable;
import java.util.Date;

public class StampBean implements Serializable {

	public int stamp_id;
	public String users_id;
	public Date stamp_date;
	public Date workIn_raw;
	public Date workOut_raw;
	public Date workIn_re;
	public Date workOut_re;
	public int week;
	public Date rest_time;
	public Date real_work_time;
	public String note;
	
	public StampBean() {
	}


	
	public StampBean(int stamp_id, String users_id, Date stamp_date, Date workIn_raw, Date workOut_raw, Date workIn_re,
			Date workOut_re, int week, Date rest_time, Date real_work_time, String note) {
		super();
		this.stamp_id = stamp_id;
		this.users_id = users_id;
		this.stamp_date = stamp_date;
		this.workIn_raw = workIn_raw;
		this.workOut_raw = workOut_raw;
		this.workIn_re = workIn_re;
		this.workOut_re = workOut_re;
		this.week = week;
		this.rest_time = rest_time;
		this.real_work_time = real_work_time;
		this.note = note;
	}



	public int getStamp_id() {
		return stamp_id;
	}

	public String getUsers_id() {
		return users_id;
	}

	public void setUsers_id(String users_id) {
		this.users_id = users_id;
	}

	public Date getStamp_date() {
		return stamp_date;
	}

	public void setStamp_date(Date stamp_date) {
		this.stamp_date = stamp_date;
	}

	public Date getWorkIn_raw() {
		return workIn_raw;
	}

	public void setWorkIn_raw(Date workIn_raw) {
		this.workIn_raw = workIn_raw;
	}

	public Date getWorkOut_raw() {
		return workOut_raw;
	}

	public void setWorkOut_raw(Date workOut_raw) {
		this.workOut_raw = workOut_raw;
	}

	public Date getWorkIn_re() {
		return workIn_re;
	}

	public void setWorkIn_re(Date workIn_re) {
		this.workIn_re = workIn_re;
	}

	public Date getWorkOut_re() {
		return workOut_re;
	}

	public void setWorkOut_re(Date workOut_re) {
		this.workOut_re = workOut_re;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public Date getRest_time() {
		return rest_time;
	}

	public void setRest_time(Date rest_time) {
		this.rest_time = rest_time;
	}

	public Date getReal_work_time() {
		return real_work_time;
	}

	public void setReal_work_time(Date real_work_time) {
		this.real_work_time = real_work_time;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
}
