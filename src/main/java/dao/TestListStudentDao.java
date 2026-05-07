package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends DAO {
	
   private List<TestListStudent> postFilter(ResultSet rs) throws Exception {
	   
       List<TestListStudent> list = new ArrayList<>();
       while (rs.next()) {
           TestListStudent test = new TestListStudent();
           test.setSubjectName(rs.getString("NAME"));
           test.setSubjectCd(rs.getString("SUBJECT_CD"));
           test.setNum(rs.getInt("NO"));
           test.setPoint(rs.getInt("POINT"));
           list.add(test);
       }
       return list;
   }
   public List<TestListStudent> filter(Student student) throws Exception {
       List<TestListStudent> list = new ArrayList<>();
       Connection connection = getConnection();
       PreparedStatement statement = null;
       ResultSet resultSet = null;
       try {
           
           String sql = "SELECT SUB.NAME, T.SUBJECT_CD, T.NO, T.POINT " +
                        "FROM TEST T " +
                        "JOIN SUBJECT SUB ON T.SUBJECT_CD = SUB.CD AND T.SCHOOL_CD = SUB.SCHOOL_CD " +
                        "WHERE T.STUDENT_NO = ? " +
                        "ORDER BY T.SUBJECT_CD ASC";
           statement = connection.prepareStatement(sql);
           statement.setString(1, student.getNo());
           resultSet = statement.executeQuery();
           list = postFilter(resultSet);
       } finally {
           if (resultSet != null) {
               resultSet.close();
           }
           if (statement != null) {
               statement.close();
           }
           if (connection != null) {
               connection.close();
           }
       }
       return list;
   }
}