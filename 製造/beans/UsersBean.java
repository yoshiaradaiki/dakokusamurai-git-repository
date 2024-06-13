//***担当者：横山
//***作成日時:6/12

package beans;

import java.sql.Date;

public class UsersBean {
	
	
	//フィールド
	private int users_id;
	private String login_id;
	private String password;
	private String emp_no;
	private String emp_name;
	private String boss_users_id;
	private int level;
	private boolean delete_flag;
	private boolean boss_flag;
	private Date year_and_month;
	
	
	//コンストラクタ
	public UsersBean() {}
	
	public UsersBean(int users_id, String login_id, String password, String emp_no, String emp_name,
			String boss_users_id, int level, boolean delete_flag, boolean boss_flag, Date year_and_month) {
		this.users_id = users_id;
		this.login_id = login_id;
		this.password = password;
		this.emp_no = emp_no;
		this.emp_name = emp_name;
		this.boss_users_id = boss_users_id;
		this.level = level;
		this.delete_flag = delete_flag;
		this.boss_flag = boss_flag;
		this.year_and_month = year_and_month;
	}
	
	//getset
	public String getLogin_id() {
		return login_id;
	}
	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}

	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmp_no() {
		return emp_no;
	}
	public void setEmp_no(String emp_no) {
		this.emp_no = emp_no;
	}
	public String getEmp_name() {
		return emp_name;
	}
	public void setEmp_name(String emp_name) {
		this.emp_name = emp_name;
	}
	public String getBoss_users_id() {
		return boss_users_id;
	}
	public void setBoss_users_id(String boss_users_id) {
		this.boss_users_id = boss_users_id;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isDelete_flag() {
		return delete_flag;
	}
	public void setDelete_flag(boolean delete_flag) {
		this.delete_flag = delete_flag;
	}
	public boolean isBoss_flag() {
		return boss_flag;
	}
	public void setBoss_flag(boolean boss_flag) {
		this.boss_flag = boss_flag;
	}
	public Date getYear_and_month() {
		return year_and_month;
	}
	public void setYear_and_month(Date year_and_month) {
		this.year_and_month = year_and_month;
	}
	public int getUsers_id() {
		return users_id;
	}
	
}
