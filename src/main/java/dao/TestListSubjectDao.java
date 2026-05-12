package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends DAO {

	private String baseSql =
			"select "
			+ "s.ent_year, "
			+ "s.no as student_no, "
			+ "s.name as student_name, "
			+ "s.class_num, "
			+ "t.no as test_no, "
			+ "t.point "
			+ "from student s "
			+ "left join test t "
			+ "on s.no = t.student_no "
			+ "and t.subject_cd = ? "
			+ "and t.school_cd = ? "
			+ "where s.school_cd = ? "
			+ "and s.ent_year = ? "
			+ "and s.class_num = ? "
			+ "and s.is_attend = true "
			+ "order by s.no, t.no";

	private List<TestListSubject> postFilter(ResultSet rs) throws Exception {

		Map<String, TestListSubject> map = new LinkedHashMap<>();

		while (rs.next()) {

			String studentNo = rs.getString("student_no");

			TestListSubject test = map.get(studentNo);

			if (test == null) {
				test = new TestListSubject();

				test.setEntYear(rs.getInt("ent_year"));
				test.setStudentNo(studentNo);
				test.setStudentName(rs.getString("student_name"));
				test.setClassNum(rs.getString("class_num"));
				test.setPoints(new LinkedHashMap<Integer, Integer>());

				map.put(studentNo, test);
			}

			Object testNoObj = rs.getObject("test_no");

			if (testNoObj != null) {
				int testNo = rs.getInt("test_no");
				int point = rs.getInt("point");

				test.putPoint(testNo, point);
			}
		}

		return new ArrayList<TestListSubject>(map.values());
	}

	public List<TestListSubject> filter(
			int entYear,
			String classNum,
			Subject subject,
			School school) throws Exception {

		Connection con = getConnection();

		PreparedStatement st = con.prepareStatement(baseSql);

		st.setString(1, subject.getCd());
		st.setString(2, school.getCd());
		st.setString(3, school.getCd());
		st.setInt(4, entYear);
		st.setString(5, classNum);

		ResultSet rs = st.executeQuery();

		List<TestListSubject> list = postFilter(rs);

		rs.close();
		st.close();
		con.close();

		return list;
	}
}