package bean;

public class TestListSubject implements java.io.Serializable {
	
	private String no;
	private String name;
	private String classNum;
	private int entYear;
	private School school;
	private boolean isAttend;
	
	public String getNo() {
		return no;
	
	}
	
	public String getName() {
		return name;
	
	}
	
	public int getEntYear() {
		return entYear;
	
	}
	
	public String getClassNum() {
		return classNum;
	
	}
	
	public School getSchool() {
		return school;
	
	}
	
	public boolean isAttend() {
		return isAttend;
	
	}
	
	public void setNo() {
		return;
	
	}
	
	public void setName(String name) {
		this.name = name;
	
	}
	
	public void setEntYear() {
	
	}
	
	public void setClassNum(String classNum) {
		return;
	
	}
	
	public void setAttend() {
	
	}
	
	public void setSchool(School school) {
		this.school = school;
	
	}
}
