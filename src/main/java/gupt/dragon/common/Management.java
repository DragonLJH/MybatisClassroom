package gupt.dragon.common;

public class Management {

	private String date;

	private String mor;

	private String reserve;

	private String projectName;

	private String projectManager;

	private String classroom;

	private String remark;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMor() {
		return mor;
	}

	public void setMor(String mor) {
		this.mor = mor;
	}

	public String getReserve() {
		return reserve;
	}

	public void setReserve(String reserve) {
		this.reserve = reserve;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectManager() {
		return projectManager;
	}

	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "date=" + date + ", mor=" + mor + ", reserve=" + reserve + ", projectName=" + projectName
				+ ", projectManager=" + projectManager + ", classroom=" + classroom + ", remark=" + remark;
	}

}
