package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends DAO {

	// 学生テーブルから、指定された学校コードに所属する学生を取得するための基本SQL
	private String baseSql = "select * from student where school_cd = ?";

	/**
	 * 学生番号を指定して、学生情報を1件取得するメソッド
	 * @param no 学生番号
	 * @return 該当する学生情報。存在しない場合は null
	 */
	public Student get(String no) throws Exception {
		Student student = null;

		// データベース接続を取得
		Connection con = getConnection();

		PreparedStatement statement = null;

		try {
			// 学生番号を条件にして学生情報を検索するSQL
			statement = con.prepareStatement("select * from student where no = ? ");
			statement.setString(1, no);

			// SQLを実行し、検索結果を取得
			ResultSet resultSet = statement.executeQuery();

			// 学校情報を取得するため、SchoolDaoを生成
			SchoolDao dao = new SchoolDao();

			// 検索結果が存在する場合
			if (resultSet.next()) {
				student = new Student();

				// ResultSetから取得した値をStudentオブジェクトにセットする
				student.setNo(resultSet.getString("no"));
				student.setName(resultSet.getString("name"));
				student.setEntYear(resultSet.getInt("ent_year"));
				student.setClassNum(resultSet.getString("class_num"));
				student.setAttend(resultSet.getBoolean("is_attend"));

				// school_cdを使って学校情報を取得し、Studentにセットする
				student.setSchool(dao.get(resultSet.getString("school_cd")));
			}

		} catch (Exception e) {
			throw e;

		} finally {
			// PreparedStatementを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			// データベース接続を閉じる
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

	/**
	 * ResultSetの検索結果をStudentリストに変換する共通処理
	 * @param rs SQLの検索結果
	 * @param school 学校情報
	 * @return Studentオブジェクトのリスト
	 */
	private List<Student> postFilter(ResultSet rs, School school) throws Exception {
		List<Student> list = new ArrayList<>();

		try {
			// 検索結果がある間、1件ずつStudentオブジェクトに変換する
			while (rs.next()) {
				Student student = new Student();

				student.setNo(rs.getString("no"));
				student.setName(rs.getString("name"));
				student.setEntYear(rs.getInt("ent_year"));
				student.setClassNum(rs.getString("class_num"));
				student.setAttend(rs.getBoolean("is_attend"));

				// 引数で受け取った学校情報をセットする
				student.setSchool(school);

				// 作成したStudentをリストに追加する
				list.add(student);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 学校、入学年度、クラス番号、在学フラグで学生を検索するメソッド
	 */
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend)
			throws Exception {

		List<Student> list = new ArrayList<>();

		Connection con = getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;

		// 入学年度とクラス番号を条件に追加
		String condition = " and ent_year = ? and class_num = ? ";

		// 学生番号の昇順で並べる
		String order = " order by no asc";

		// 在学中の学生だけを取得する場合の条件
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend = true ";
		}

		try {
			// SQL文を作成
			st = con.prepareStatement(baseSql + condition + conditionIsAttend + order);

			// ? に値をセットする
			st.setString(1, school.getCd());
			st.setInt(2, entYear);
			st.setString(3, classNum);

			// SQLを実行
			rs = st.executeQuery();

			// 検索結果をStudentリストに変換
			list = postFilter(rs, school);

		} catch (Exception e) {
			throw e;

		} finally {
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

	/**
	 * 学校、入学年度、在学フラグで学生を検索するメソッド
	 */
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {

		List<Student> list = new ArrayList<>();

		Connection con = getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;

		// 入学年度を条件に追加
		String condition = " and ent_year = ? ";

		String order = " order by no asc";

		// 在学中のみ検索する場合
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend = true ";
		}

		try {
			st = con.prepareStatement(baseSql + condition + conditionIsAttend + order);

			st.setString(1, school.getCd());
			st.setInt(2, entYear);

			rs = st.executeQuery();

			list = postFilter(rs, school);

		} catch (Exception e) {
			throw e;

		} finally {
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

	/**
	 * 指定された学校に存在する入学年度の一覧を取得するメソッド
	 */
	public List<Integer> getEntYearSet(School school) throws Exception {

		List<Integer> list = new ArrayList<>();

		Connection con = getConnection();

		// distinctを使って、重複しない入学年度だけを取得する
		String sql = "select distinct ent_year from student where school_cd = ? order by ent_year desc";

		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, school.getCd());

		ResultSet rs = st.executeQuery();

		// 取得した入学年度をリストに追加する
		while (rs.next()) {
			list.add(rs.getInt("ent_year"));
		}

		rs.close();
		st.close();
		con.close();

		return list;
	}

	/**
	 * 学校と在学フラグで学生を検索するメソッド
	 */
	public List<Student> filter(School school, boolean isAttend) throws Exception {

		List<Student> list = new ArrayList<>();

		Connection con = getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;

		String order = " order by no asc ";

		// 在学中のみ検索する場合
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend = true ";
		}

		try {
			st = con.prepareStatement(baseSql + conditionIsAttend + order);

			st.setString(1, school.getCd());

			rs = st.executeQuery();

			list = postFilter(rs, school);

		} catch (Exception e) {
			throw e;

		} finally {
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

	/**
	 * 学生情報を登録または更新するメソッド
	 * 学生番号が存在しない場合はinsert、存在する場合はupdateを行う
	 */
	public boolean save(Student student) throws Exception {
		Connection con = getConnection();

		PreparedStatement st = null;

		int count = 0;

		// 既に登録されている学生情報を確認するための変数
		Student old = null;

		try {
			// 学生番号で既存データを検索する
			old = get(student.getNo());

			// 既存データがない場合、新規登録を行う
			if (old == null) {
				st = con.prepareStatement(
						"insert into student values (?, ?, ?, ?, ?, ?)");

				st.setString(1, student.getNo());
				st.setString(2, student.getName());
				st.setInt(3, student.getEntYear());
				st.setString(4, student.getClassNum());
				st.setBoolean(5, student.isAttend());
				st.setString(6, student.getSchool().getCd());

			} else {
				// 既存データがある場合、学生情報を更新する
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

			// insertまたはupdateを実行し、更新件数を取得する
			count = st.executeUpdate();

		} catch (Exception e) {
			throw e;

		} finally {
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

		// 更新件数が1件以上なら成功
		return count > 0;
	}
}