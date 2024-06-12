//***　担当者：湯 
//***　作成日時：2024/6/12
package beans;

import java.util.Date;

public class AttStatusBean {
	private int att_status_id;
	private int users_id;
	private Date years;

	public AttStatusBean() {

	}

	public AttStatusBean(int att_status_id, int users_id, Date years) {
		this.att_status_id = att_status_id;
		this.users_id = users_id;
		this.years = years;
	}

	public int getUsers_id() {
		return users_id;
	}

	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public Date getYears() {
		return years;
	}

	public void setYears(Date years) {
		this.years = years;
	}

	public int getAtt_status_id() {
		return att_status_id;
	}
	
	
}
