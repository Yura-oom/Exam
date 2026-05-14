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

public class TestDao extends DAO {

	// TESTテーブルから取得する基本項目をまとめたSQL
	private String baseSql = "select STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM from TEST";

	/**
	 * 学生・科目・学校・回数を指定して、得点情報を1件取得するメソッド
	 * @param student 学生情報
	 * @param subject 科目情報
	 * @param school 学校情報
	 * @param no 試験回数
	 * @return 該当する得点情報。存在しない場合は null
	 */
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		Test test = null;

		// try-with-resourcesを使い、処理後にConnectionとPreparedStatementを自動で閉じる
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(
						baseSql + " where STUDENT_NO = ? and SUBJECT_CD = ? and SCHOOL_CD = ? and NO = ?")) {

			// 学生番号をセットする
			st.setString(1, student.getNo());

			// 科目コードをセットする
			st.setString(2, subject.getCd());

			// 学校コードをセットする
			st.setString(3, school.getCd());

			// 試験回数をセットする
			st.setInt(4, no);

			// SQLを実行し、検索結果を取得する
			ResultSet rs = st.executeQuery();

			// ResultSetをTestオブジェクトのリストに変換する
			List<Test> list = postFilter(rs, school);

			// 検索結果が存在する場合、先頭の1件を取得する
			if (!list.isEmpty()) {
				test = list.get(0);
			}
		}

		return test;
	}

	/**
	 * ResultSetの検索結果をTestオブジェクトのリストに変換するメソッド
	 * @param rSet SQLの検索結果
	 * @param school 学校情報
	 * @return Testオブジェクトのリスト
	 */
	public List<Test> postFilter(ResultSet rSet, School school) throws Exception {
		List<Test> list = new ArrayList<>();

		// 検索結果を1件ずつ取り出す
		while (rSet.next()) {
			Test test = new Test();

			// 学生番号だけを持つStudentオブジェクトを作成する
			Student student = new Student();
			student.setNo(rSet.getString("STUDENT_NO"));
			test.setStudent(student);

			// 科目コードだけを持つSubjectオブジェクトを作成する
			Subject subject = new Subject();
			subject.setCd(rSet.getString("SUBJECT_CD"));
			test.setSubject(subject);

			// 学校情報をセットする
			test.setSchool(school);

			// 試験回数をセットする
			test.setNo(rSet.getInt("NO"));

			// 得点をセットする
			test.setPoint(rSet.getInt("POINT"));

			// クラス番号をセットする
			test.setClassNum(rSet.getString("CLASS_NUM"));

			// 作成したTestオブジェクトをリストに追加する
			list.add(test);
		}

		return list;
	}

	/**
	 * 入学年度・クラス番号・科目・試験回数・学校情報を条件にして得点一覧を取得するメソッド
	 * @param entYear 入学年度
	 * @param classNum クラス番号
	 * @param subject 科目情報
	 * @param num 試験回数
	 * @param school 学校情報
	 * @return 条件に一致する得点情報のリスト
	 */
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();

		// TESTテーブルとSTUDENTテーブルを結合し、
		// 入学年度・クラス番号・科目コード・試験回数・学校コードで得点情報を検索するSQL
		String sql = baseSql
				+ " join STUDENT on TEST.STUDENT_NO = STUDENT.NO "
				+ " where ENT_YEAR = ? "
				+ " and CLASS_NUM = ? "
				+ " and SUBJECT_CD = ? "
				+ " and TEST.NO = ? "
				+ " and TEST.SCHOOL_CD = ?";

		// try-with-resourcesにより、ConnectionとPreparedStatementを自動で閉じる
		try (Connection con = getConnection();
				PreparedStatement st = con.prepareStatement(sql)) {

			// 入学年度をセットする
			st.setInt(1, entYear);

			// クラス番号をセットする
			st.setString(2, classNum);

			// 科目コードをセットする
			st.setString(3, subject.getCd());

			// 試験回数をセットする
			st.setInt(4, num);

			// 学校コードをセットする
			st.setString(5, school.getCd());

			// SQLを実行する
			ResultSet rs = st.executeQuery();

			// 検索結果をTestリストに変換する
			list = postFilter(rs, school);
		}

		return list;
	}

	/**
	 * 複数の得点情報をまとめて保存するメソッド
	 * 途中でエラーが発生した場合は、すべての処理を取り消す
	 * @param list 保存する得点情報のリスト
	 * @return 保存に成功した場合 true
	 */
	public boolean save(List<Test> list) throws Exception {
		Connection con = getConnection();

		try {
			// 自動コミットを無効にし、トランザクションを開始する
			con.setAutoCommit(false);

			// リスト内の得点情報を1件ずつ保存する
			for (Test test : list) {
				save(test, con);
			}

			// すべて成功した場合、変更を確定する
			con.commit();

		} catch (Exception e) {
			// エラーが発生した場合、すべての変更を取り消す
			con.rollback();
			return false;

		} finally {
			// データベース接続を閉じる
			con.close();
		}

		return true;
	}

	/**
	 * 得点情報を1件保存するメソッド
	 * merge文を使い、同じキーのデータがあれば更新し、なければ新規登録する
	 * @param test 保存する得点情報
	 * @param connection データベース接続
	 * @return 保存に成功した場合 true
	 */
	public boolean save(Test test, Connection connection) throws Exception {
		// STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NOをキーとして、
		// 既存データがあれば更新し、なければ新規登録するSQL
		String sql = "merge into TEST key (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO) values(?, ?, ?, ?, ?, ?)";

		// PreparedStatementは処理後に自動で閉じる
		try (PreparedStatement st = connection.prepareStatement(sql)) {

			// 学生番号をセットする
			st.setString(1, test.getStudent().getNo());

			// 科目コードをセットする
			st.setString(2, test.getSubject().getCd());

			// 学校コードをセットする
			st.setString(3, test.getSchool().getCd());

			// 試験回数をセットする
			st.setInt(4, test.getNo());

			// 得点をセットする
			st.setInt(5, test.getPoint());

			// クラス番号をセットする
			st.setString(6, test.getClassNum());

			// SQLを実行する
			st.executeUpdate();
		}

		return true;
	}
}