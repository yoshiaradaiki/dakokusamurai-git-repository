// 担当者：吉新
// 作成日時：2024/06/12
// 更新者：横山
package beans;

import java.time.LocalTime;

public class StampRevBean {
	// フィールド
	private int stamp_rev_id;
	private int stamp_id;
	private LocalTime workIn_rev;
	private LocalTime workOut_rev;
	private LocalTime rest_time;
	private int work_status;
	private String note;
	
	// コンストラクタ
	public StampRevBean() {}

	public StampRevBean(int stamp_rev_id, int stamp_id, LocalTime workIn_rev, LocalTime workOut_rev,
			LocalTime rest_time, int work_status, String note) {
		this.stamp_rev_id = stamp_rev_id;
		this.stamp_id = stamp_id;
		this.workIn_rev = workIn_rev;
		this.workOut_rev = workOut_rev;
		this.rest_time = rest_time;
		this.work_status = work_status;
		this.note = note;
	}

	// ゲッターセッター

	public int getStamp_rev_id() {
		return stamp_rev_id;
	}

	public void setStamp_rev_id(int stamp_rev_id) {
		this.stamp_rev_id = stamp_rev_id;
	}

	public int getStamp_id() {
		return stamp_id;
	}

	public void setStamp_id(int stamp_id) {
		this.stamp_id = stamp_id;
	}

	public LocalTime getWorkIn_rev() {
		return workIn_rev;
	}

	public void setWorkIn_rev(LocalTime workIn_rev) {
		this.workIn_rev = workIn_rev;
	}

	public LocalTime getWorkOut_rev() {
		return workOut_rev;
	}

	public void setWorkOut_rev(LocalTime workOut_rev) {
		this.workOut_rev = workOut_rev;
	}

	public LocalTime getRest_time() {
		return rest_time;
	}

	public void setRest_time(LocalTime rest_time) {
		this.rest_time = rest_time;
	}

	public int getWork_status() {
		return work_status;
	}

	public void setWork_status(int work_status) {
		this.work_status = work_status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
	
	
}