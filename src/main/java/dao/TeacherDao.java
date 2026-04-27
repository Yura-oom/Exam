////package dao;
////
////import java.sql.Connection;
////import java.sql.PreparedStatement;
////import java.sql.ResultSet;
////import java.sql.SQLException;
////
////import bean.Teacher;
////
////public class TeacherDao extends DAO {
////	/**
////	 * getメソッド 教員IDを指定して教員インスタンスを1件取得する
////	 *
////	 * @param id:String
////	 *            教員ID
////	 * @return 教員クラスのインスタンス 存在しない場合はnull
////	 * @throws Exception
////	 */
////	public Teacher get(String id) throws Exception {
////		// 教員インスタンスを初期化
////		Teacher teacher = new Teacher();
////		// コネクションを確立
////		Connection connection = getConnection();
////		// プリペアードステートメント
////		PreparedStatement statement = null;
////
////		try {
////			// プリペアードステートメントにSQL文をセット
////			statement = connection.prepareStatement("select * from teacher where id=?");
////			// プリペアードステートメントに教員IDをバインド
////			statement.setString(1, id);
////			// プリペアードステートメントを実行
////			ResultSet resultSet = statement.executeQuery();
////
////			// 学校Daoを初期化
////			
////
////			if (resultSet.next()) {
////				// リザルトセットが存在する場合
////				// 教員インスタンスに検索結果をセット
////				
////				
////				
////				// 学校フィールドには学校コードで検索した学校インスタンスをセット
////				
////			} else {
////				// リザルトセットが存在しない場合
////				// 教員インスタンスにnullをセット
////				teacher = null;
////			}
////		} catch (Exception e) {
////			throw e;
////		} finally {
////			// プリペアードステートメントを閉じる
////			if (statement != null) {
////				try {
////					statement.close();
////				} catch (SQLException sqle) {
////					throw sqle;
////				}
////			}
////			// コネクションを閉じる
////			if (connection != null) {
////				try {
////					connection.close();
////				} catch (SQLException sqle) {
////					throw sqle;
////				}
////			}
////		}
////
////		return teacher;
////	}
////
////	/**
////	 * loginメソッド 教員IDとパスワードで認証する
////	 *
////	 * @param id:String
////	 *            教員ID
////	 * @param password:String
////	 *            パスワード
////	 * @return 認証成功:教員クラスのインスタンス, 認証失敗:null
////	 * @throws Exception
////	 */
////	public Teacher login(String id, String password) throws Exception {
////		// 教員クラスのインスタンスを取得
////		Teacher teacher = get(id);
////		// 教員がnullまたはパスワードが一致しない場合
////		if (teacher == null || !teacher.getPassword().equals(password)) {
////			return null;
////		}
////		return teacher;
////	}
////}
//
//
//package dao;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//import bean.School;
//import bean.Teacher;
//
//public class TeacherDao extends DAO{
//	public Teacher get(String id) throws Exception {
//		Teacher teacher = null;
//		
//		Connection con = getConnection();
//		SchoolDao dao = new SchoolDao();
//		
//		PreparedStatement st;
//		st= con.prepareStatement(
//				"select * from teacher where id = ? ");
//		st.setString(1, id);
//		ResultSet rs=st.executeQuery();
//		
//		while (rs.next()) {
//			teacher = new Teacher();
//			teacher.setId(rs.getString("id"));
//			teacher.setName(rs.getString("name"));
//			teacher.setPassword(rs.getString("password"));
//			School school = new School();
//			school = dao.get(rs.getString("school_cd"));
//			teacher.setSchool(school);
//		}
//		
//		st.close();
//		con.close();
//		return teacher;
//	}
//	
//	public Teacher login(String id, String password) throws Exception{
//		Teacher teacher = null;
//		
//		Connection con = getConnection();
//		
//		PreparedStatement st;
//		st = con.prepareStatement(
//				"select * from teacher where id = ? and password = ?");
//		st.setString(1, id);
//		st.setString(2, password);
//		ResultSet rs = st.executeQuery();
//		
//		while (rs.next()) {
//			teacher = new Teacher();
//			teacher.setId(rs.getString("id"));
//			teacher.setPassword(rs.getString("password"));
//			teacher.setName(rs.getString("name"));
//		}
//		
//		st.close();
//		con.close();
//		return teacher;
//	}
//
//}
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;
import bean.Teacher;

public class TeacherDao extends DAO {
	/**
	 * getメソッド 教員IDを指定して教員インスタンスを1件取得する
	 *
	 * @param id:String
	 *            教員ID
	 * @return 教員クラスのインスタンス 存在しない場合はnull
	 * @throws Exception
	 */
	public Teacher get(String id) throws Exception {
		// 教員インスタンスを初期化
		Teacher teacher = new Teacher();
		// コネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from teacher where id=?");
			// プリペアードステートメントに教員IDをバインド
			statement.setString(1, id);
			// プリペアードステートメントを実行
			ResultSet resultSet = statement.executeQuery();

			// 学校Daoを初期化
			

			if (resultSet.next()) {
				// リザルトセットが存在する場合
				// 教員インスタンスに検索結果をセット
				teacher.setId(resultSet.getString("id"));
				teacher.setPassword(resultSet.getString("password"));
				teacher.setName(resultSet.getString("name"));
				
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				SchoolDao dao = new SchoolDao();
				School school = new School();
				school = dao.get(resultSet.getString("school_cd"));
				teacher.setSchool(school);
				
			} else {
				// リザルトセットが存在しない場合
				// 教員インスタンスにnullをセット
				teacher = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return teacher;
	}

	/**
	 * loginメソッド 教員IDとパスワードで認証する
	 *
	 * @param id:String
	 *            教員ID
	 * @param password:String
	 *            パスワード
	 * @return 認証成功:教員クラスのインスタンス, 認証失敗:null
	 * @throws Exception
	 */
	public Teacher login(String id, String password) throws Exception {
		// 教員クラスのインスタンスを取得
		Teacher teacher = get(id);
		// 教員がnullまたはパスワードが一致しない場合
		if (teacher == null || !teacher.getPassword().equals(password)) {
			return null;
		}
		return teacher;
	}
}
