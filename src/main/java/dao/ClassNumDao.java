package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;


public class ClassNumDao extends DAO {

	public ClassNum get(String class_num, School school) throws Exception {

		// クラス番号インスタンスを初期化
		ClassNum classNum = new ClassNum();
		// データベースへのコネクションを確立
		Connection con = getConnection();
		// プリペアードステートメント
		PreparedStatement st = null;

		try {
			
			st=con.prepareStatement(
					"selrct * from class_num where school_cd = ? and class_num = ?");
			st.setString(1, school.getCd());
			st.setString(2, class_num);
			
			SchoolDao dao = new SchoolDao();
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				classNum.setSchool(dao.get(rs.getString("school_cd")));
				classNum.setClass_num(rs.getString("class_num"));
			}else {
				classNum.setClass_num(null);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return classNum;
	}

	/**
	 * filterメソッド 学校を指定してクラス番号の一覧を取得する
	 *
	 * @param school:School
	 * @return クラス番号の一覧:List<String>
	 * @throws Exception
	 */
	public List<String> filter(School school) throws Exception {
		// リストを初期化
		List<String> list = new ArrayList<>();
		// データベースへのコネクションを確立
		Connection con = getConnection();
		// プリペアードステートメント
		PreparedStatement st = null;

		try {
			// プリペアードステートメントにSQL文をセット
			st=con.prepareStatement(
					"select * from class_num where school_cd = ?");
			// プリペアードステートメントに学校コードをバインド
			st.setString(1, school.getCd());
			// プリペアードステートメントを実行
			

			ResultSet rSet = st.executeQuery(); // エラー解消のため宣言　書き換え必要
			// リザルトセットを全件走査
			while (rSet.next()) {
				// リストにクラス番号を追加
				list.add(rSet.getString("class_num"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
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
	 * 登録用のsaveメソッド
	 * @param classNum
	 * @return 実行可否
	 * @throws Exception
	 */
	public boolean save(ClassNum classNum) throws Exception {

		// コネクションを確立
		Connection con = getConnection();
		// プリペアードステートメント
		PreparedStatement st = null;
		// 実行件数
		int count = 0;

		try {
			// プリペアードステートメントにINSERT文をセット
			st=con.prepareStatement(
					"insert into class_num values(?, ?)");
			// プリペアードステートメントに値をバインド
			st.setString(1, classNum.getSchool().getCd());
			st.setString(2, classNum.getClass_num());
			
			// プリペアードステートメントを実行
			count = st.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}

	/**
	 * 変更用saveメソッド
	 * @param classNum
	 * @param newClassNum
	 * @return 変更可否
	 * @throws Exception
	 */
	public boolean save(ClassNum classNum, String newClassNum) throws Exception {

		// コネクションを確立
		Connection con = getConnection();
		// プリペアードステートメント
		PreparedStatement st = null;
		// 実行件数
		int count = 0;

		try {
			// プリペアードステートメントにUPDATE文をセット
			st=con.prepareStatement(
					"update class_num set class_num = ?");
			
			st.setString(1, newClassNum);
			
			// プリペアードステートメントを実行
			count = st.executeUpdate();
			
			// プリペアードステートメントを閉じる
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			// 登録されている学生のクラスも変更
			st = con.prepareStatement(
					"update student set class_num = ? where class_num = ? and school_cd = ?");
			st.setString(1, newClassNum);
			st.setString(2, classNum.getClass_num());
			st.setString(3, classNum.getSchool().getCd());
			
			count += st.executeUpdate();
			// プリペアードステートメントを閉じる
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			// テストに登録されているクラスも変更
			st = con.prepareStatement(
					"update test set class_num = ? where class_num = ? and school_cd = ?");
			
			st.setString(1, newClassNum);
			st.setString(2, classNum.getClass_num());
			st.setString(3, classNum.getSchool().getCd());
			
			count += st.executeUpdate();

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (st != null) {
				try {
					st.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (con != null) {
				try {
					con.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		if (count >= 3) {
			// 実行件数が3件以上ある場合
			return true;
		} else {
			// 実行件数が3件未満の場合
			return false;
		}
	}

}