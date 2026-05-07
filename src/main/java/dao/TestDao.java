package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDao extends DAO{
	public String baseSql = "select STUDENT_NO,SUBJECT_CD, SCHOOL_CD,NO,POINT,CLASS_NUM from TEST";
	
	public Test get(Student student, Subject subject,School school, int no) throws Exception {
		Test test = null;
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(baseSql + "where STUDENT_NO=? and SUBJECT_CD=? and SCHOOL_CD=? and NO=?")) {
					st.setString(1, student.getNo());
					st.setString(2,subject.getCd());
					st.setString(3,school.getCd());
					st.setInt(4, no);
					ResultSet rs = st.executeQuery();
					List<Test> list = postFilter(rs, school);
					if (!list.isEmpty()) {
						test = list.get(0);
					}
				}
				return test;
	}
	public List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		while (rSet.next()) {
			Test test = new Test();
			Student student = new Student ();
			student.setNo(rSet.getString("STUDENT_NO"));
			test.setStudent(student);
			
			Subject subject = new Subject();
			subject.setCd(rSet.getString("SUBJECT_CD"));
			test.setSubject(subject);
			
			test.setSchool(school);
			test.setNo(rSet.getInt("NO"));
			test.setPoint(rSet.getInt("POINT"));
			test.setClassNum(rSet.getString("CLASS_NUM"));
			list.add(test);
		}
		return list;
	}
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
			List<Test> list = new ArrayList<>();
			String sql = baseSql + "join STUDENT on TEST.STUDENT_NO = STUDENT.NO WHERE ENT_YEAR=?"
					+ " AND CLASS_NUM=? AND SUBJECT_CD=? ANDTEST.NO=? AND TEST.SCHOOL_CD=?";
			try (Connection con = getConnection();
					PreparedStatement st = con.prepareStatement(sql)) {
				st.setInt(1,entYear);
				st.setString(2,classNum);
				st.setString(3,subject.getCd());
				ResultSet rs = st.executeQuery();
				list = postFilter(rs,school);
			}
			return list;
	}
	public boolean save(List<Test> list)throws Exception {
		Connection con = getConnection();
		try {
			con.setAutoCommit(false);
			for (Test test : list ) {
				save(test, con);
			}
			con.commit();
		} catch (Exception e) {
			con.rollback();
			return false;
		}finally {
			con.close();
		}
		return true;
	}
	public boolean save(Test test, Connection connection) throws Exception {
		String sql = "merge into TEST key (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO) values(?,?,?,?,?,?)";
		try (PreparedStatement st = connection.prepareStatement(sql)) {
			st.setString(1,test.getStudent().getNo());
			st.setString(2, test.getSubject().getCd());
			st.setString(3,test.getSchool().getCd());
			st.setInt(4,test.getNo());
			st.setInt(5,test.getPoint());
			st.setString(6,test.getClassNum());
			st.executeUpdate();
		}
		return true;
	}
}