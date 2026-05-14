package scoremanager.main;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// セッションを取得する
		HttpSession session = req.getSession();

		// セッションからログイン中の先生情報を取得する
		Teacher teacher = (Teacher) session.getAttribute("user");

		// リクエストパラメータから学生番号を取得する
		String no = req.getParameter("no");

		// リクエストパラメータから学生名を取得する
		String name = req.getParameter("name");

		// リクエストパラメータから入学年度を文字列として取得する
		String entYearStr = req.getParameter("ent_year");

		// 入学年度を文字列からint型に変換する
		int entYear = Integer.parseInt(entYearStr);

		// リクエストパラメータからクラス番号を取得する
		String classNum = req.getParameter("class_num");

		// チェックボックスの値を取得する
		// チェックされている場合は値が入り、チェックされていない場合はnullになる
		String isAttends = req.getParameter("is_attend");

		// 在学フラグを入れる変数
		boolean isAttend;

		// チェックボックスがチェックされていればtrue、されていなければfalseにする
		if (isAttends != null) {
			isAttend = true;
		} else {
			isAttend = false;
		}

		// 学生情報を登録・更新するためのDAOを生成する
		StudentDao studentDao = new StudentDao();

		// 更新する学生情報を入れるStudentオブジェクトを生成する
		Student student = new Student();

		// 学生番号をセットする
		student.setNo(no);

		// 学生名をセットする
		student.setName(name);

		// 入学年度をセットする
		student.setEntYear(entYear);

		// クラス番号をセットする
		student.setClassNum(classNum);

		// 在学フラグをセットする
		student.setAttend(isAttend);

		// ログイン中の先生の学校情報をセットする
		student.setSchool(teacher.getSchool());

		// 学生情報をデータベースに保存する
		studentDao.save(student);

		// 学生情報変更完了画面へフォワードする
		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}
}