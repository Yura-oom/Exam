
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends DAO{
	
	private String baseSql = "select * from student where school_cd = ?";
	
	public Student get(String no) throws Exception{
		Student student = null;
		
		Connection con = getConnection();
		
		PreparedStatement statement = null;
		try {
			statement = con.prepareStatement("select * from student where no = ? ");
			statement.setString(1, no);
			ResultSet resultSet = statement.executeQuery();
					// 学校Daoを初期化
			SchoolDao dao = new SchoolDao();
				
			if (resultSet.next()) {
				student = new Student();
				student.setNo(resultSet.getString("no"));
				student.setName(resultSet.getString("name"));
				student.setEntYear(resultSet.getInt("ent_Year"));
				student.setClassNum(resultSet.getString("class_num"));
				student.setAttend(resultSet.getBoolean("is_attend"));
				student.setSchool(dao.get(resultSet.getString("school_cd")));
				
			}		
		}catch (Exception e) {
			throw e;
		}finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return student;
		
	}
	
	private List<Student> postFilter(ResultSet rs, School school)throws Exception{
		List<Student> list = new ArrayList<>();
		try {
			while(rs.next()) {
				Student student = new Student();
				student.setNo(rs.getString("no"));
				student.setName(rs.getString("name"));
				student.setEntYear(rs.getInt("ent_Year"));
				student.setClassNum(rs.getString("class_num"));
				student.setAttend(rs.getBoolean("is_attend"));
				student.setSchool(school);
				
				list.add(student);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend)
	throws Exception{
		
		List<Student> list = new ArrayList<>();
		
		Connection con = getConnection();
		
		PreparedStatement st = null;
		
		ResultSet rs = null;
		
		String condition = "and ent_year = ? and class_num = ? ";
		String order = "order by no asc";
		
		String conditionIsAttend = "";
		if(isAttend) {
			conditionIsAttend = "and is_attend = true ";
		}
		try {
			st = con.prepareStatement(baseSql + condition + conditionIsAttend + order);
			st.setString(1, school.getCd());
			st.setInt(2, entYear);
			st.setString(3, classNum);
			rs = st.executeQuery();
			list = postFilter(rs, school);
		}catch (Exception e) {
			throw e;
		}finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}
	
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception{
		
		List<Student> list = new ArrayList<>();
		
		Connection con = getConnection();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String condition = "and ent_year = ? ";
		String order = "order by no asc";
		String conditionIsAttend = "";
		if(isAttend) {
			conditionIsAttend = "and is_attend = true ";
		}
		
		try {
			st = con.prepareStatement(baseSql + condition + conditionIsAttend + order);
			st.setString(1, school.getCd());
			st.setInt(2, entYear);
			rs = st.executeQuery();
			list = postFilter(rs, school);
		}catch (Exception e) {
			throw e;
		}finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}
	
	public List<Student> filter(School school, boolean isAttend)throws Exception{
		
		List<Student> list = new ArrayList<>();
		
		Connection con = getConnection();
		
		PreparedStatement st = null;
		ResultSet rs = null;
		String order = "order by no asc ";
		
		String conditionIsAttend = "";
		if(isAttend) {
			conditionIsAttend = "and is_attend = true ";
		}
		
		try {
			st = con.prepareStatement(baseSql + conditionIsAttend + order);
			st.setString(1, school.getCd());
			rs = st.executeQuery();
			list = postFilter(rs, school);
		}catch (Exception e) {
			throw e;
		}finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}
	
	
	public boolean save(Student student) throws Exception{
		Connection con = getConnection();
		
		PreparedStatement st = null;
		
		int count = 0;
		Student old = null;
		
		try {
			old = get(student.getNo());
			if(old == null) {
				st = con.prepareStatement(
						"insert into student values (?, ?, ?, ?, ?, ?)");
				st.setString(1, student.getNo());
				st.setString(2, student.getName());
				st.setInt(3, student.getEntYear());
				st.setString(4, student.getClassNum());
				st.setBoolean(5, student.isAttend());
				st.setString(6, student.getSchool().getCd());
			}else {
				st = con.prepareStatement(
						"update student set name = ?, ent_year = ?, class_num = ?, "
						+ "is_attend = ?, school_cd = ? where no = ?");
				st.setString(1, student.getName());
				st.setInt(2, student.getEntYear());
				st.setString(3, student.getClassNum());
				st.setBoolean(4, student.isAttend());
				School school = student.getSchool();
				st.setString(5, school.getCd());
				st.setString(6, student.getNo());
			}
			count = st.executeUpdate();
			
		}catch (Exception e) {
			throw e;
		}finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		if(count > 0) {
			return true;
		}else {
			return false;
		}
	}
	

}
