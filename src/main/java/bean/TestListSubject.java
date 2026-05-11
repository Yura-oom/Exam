package bean;

import java.util.HashMap;
import java.util.Map;

public class TestListSubject implements java.io.Serializable {

	private String classNum;
	private int entYear;
	private String studentName;
	private String studentNo;
	private Map<Integer, Integer> points = new HashMap<>();

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public int getEntYear() {
		return entYear;
	}

	public void setEntYear(int entYear) {
		this.entYear = entYear;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public Map<Integer, Integer> getPoints() {
		return points;
	}

	public void setPoints(Map<Integer, Integer> points) {
		this.points = points;
	}

	public String getPoint(int key) {
		Integer value = points.get(key);
		if (value == null) {
			return "";
			
		}
		return String.valueOf(value);
	}

	public void putPoint(int key, int value) {
		points.put(key, value);
	}
}