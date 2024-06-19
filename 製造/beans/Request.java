package beans;

public class Request {
	private String dateAndTime;
	private int content;
	private int status;
	private String bossName;
	private String name;

	public Request(String dateAndTime, int content, int status, String bossName, String name) {
		this.dateAndTime = dateAndTime;
		this.content = content;
		this.status = status;
		this.bossName = bossName;
		this.name = name;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}

	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	public int getContent() {
		return content;
	}

	public void setContent(int content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
