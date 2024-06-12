// 担当者：鈴木
// 作成日時：2024/6/12
package beans;

import java.io.Serializable;
import java.util.Date;

public class RequestListBean implements Serializable {
	private int request_id;             
	private int request_foreign_id;     
	private Date date_and_time;         
	private String reason;    			
	private int status;    				
	private int content;   				
	private String name;                
	private String boss_name;   		
	
	public RequestListBean() {}
	public RequestListBean(int request_id,int request_foreign_id,Date date_and_time,
			String reason,int status,int content,String name,String boss_name) {
		this.request_id = request_id;
		this.request_foreign_id = request_foreign_id;
		this.date_and_time = date_and_time;
		this.reason = reason;
		this.status = status;
		this.content = content;
		this.name = name;
		this.boss_name = boss_name;
	}
	public int getrequest_id() {
		return request_id;
	}
	public int getrequest_foreign_id() {
		return request_foreign_id;
	}
	public void setrequest_foreign_id(int request_foreign_id) {
		this.request_foreign_id = request_foreign_id;
	}
	public Date getdate_and_time(){
		return date_and_time;
	}
	public void setdate_and_time(Date date_and_time){
		this.date_and_time = date_and_time;
	}
	public String getreason(){
		return reason;
	}
	public void setreason(String reason){
		this.reason = reason;
	}
	public int getstatus(){
		return status;
	}
	public void setstatus(int status){
		this.status = status;
	}
	public int getcontent(){
		return content;
	}
	public void setcontent( int content){
		this.content = content;
	}
	public String getname(){
		return name;
	}
	public void setname(String name){
		this.name = name;
	}
	public String getboss_name(){
		return boss_name;
	}
	public void setboss_name(String boss_name){
		this.boss_name = boss_name;
	}
		
}    
