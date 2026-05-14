package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		// セッションを取得する
		HttpSession session = req.getSession();

		// セッションからログイン中の先生情報を取得する
		Teacher teacher = (Teacher) session.getAttribute("user");

		// リクエストパラメータから更新対象の学生番号を取得する
		String stuNo = req.getParameter("no");

		// クラス番号を取得するためのDAOを生成する
		ClassNumDao classNumDao = new ClassNumDao();

		// 学生情報を取得するためのDAOを生成する
		StudentDao studentDao = new StudentDao();

		// 学生番号を使って、更新対象の学生情報を取得する
		Student stu = studentDao.get(stuNo);

		// ログイン中の先生が所属する学校のクラス番号一覧を取得する
		List<String> list = classNumDao.filter(teacher.getSchool());

		// 学生の在学状態をJSPに渡す
		req.setAttribute("is_attend", stu.isAttend());

		// 学生番号をJSPに渡す
		req.setAttribute("no", stu.getNo());

		// 学生名をJSPに渡す
		req.setAttribute("name", stu.getName());

		// 入学年度をJSPに渡す
		req.setAttribute("ent_year", stu.getEntYear());

		// クラス番号一覧をJSPに渡す
		req.setAttribute("class_num_set", list);

		// 学生情報変更画面へフォワードする
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}
}