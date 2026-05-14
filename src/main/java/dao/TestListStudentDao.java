package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends DAO {

	 // ResultSetのデータを
	  //TestListStudentのListへ変換するメソッド
	private List<TestListStudent> postFilter(ResultSet rs) throws Exception {

		// 成績一覧リスト生成
		List<TestListStudent> list = new ArrayList<>();

		// ResultSetから1件ずつ取得
		while (rs.next()) {

			// TestListStudentインスタンス生成
			TestListStudent test = new TestListStudent();
			
			test.setSubjectName(
					rs.getString("NAME"));

			test.setSubjectCd(
					rs.getString("SUBJECT_CD"));

			test.setNum(
					rs.getInt("NO"));

			test.setPoint(
					rs.getInt("POINT"));

			list.add(test);
		}

		// 成績一覧返却
		return list;
	}

	 //学生番号をもとに
	 //学生成績一覧を取得するメソッド
	public List<TestListStudent> filter(Student student) throws Exception {

		// 成績一覧リスト生成
		List<TestListStudent> list = new ArrayList<>();

		// データベース接続
		Connection connection = getConnection();

		// SQL実行用PreparedStatement
		PreparedStatement statement = null;

		// SQL結果格納用ResultSet
		ResultSet resultSet = null;

		try {

			// SQL文作成
			String sql =
					"SELECT DISTINCT SUB.NAME, T.SUBJECT_CD, T.NO, T.POINT " +
					"FROM TEST T " +
					"JOIN SUBJECT SUB ON T.SUBJECT_CD = SUB.CD " +
					"AND T.SCHOOL_CD = SUB.SCHOOL_CD " +
					"WHERE T.STUDENT_NO = ? " +
					"ORDER BY T.SUBJECT_CD ASC, T.NO ASC";

			// SQL準備
			statement =
					connection.prepareStatement(sql);

			// 学生番号セット
			statement.setString(
					1,
					student.getNo());

			// SQL実行
			resultSet =
					statement.executeQuery();

			// ResultSetをListへ変換
			list = postFilter(resultSet);

		} finally {

			// ResultSetを閉じる
			if (resultSet != null) {
				resultSet.close();
			}

			// PreparedStatementを閉じる
			if (statement != null) {
				statement.close();
			}

			// Connectionを閉じる
			if (connection != null) {
				connection.close();
			}
		}

		// 成績一覧返却
		return list;
	}
}