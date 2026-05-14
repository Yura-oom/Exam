package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");

		// リクエストから学生番号取得
		String studentNo = request.getParameter("student_no");

		// セッションからログイン中の先生情報取得
		Teacher teacher =
				(Teacher) request.getSession().getAttribute("user");

		// userで取得できない場合teacherでも取得
		if (teacher == null) {
			teacher =
					(Teacher) request.getSession().getAttribute("teacher");
		}

		// ログインしていない場合ログイン画面へリダイレクト
		if (teacher == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		// プルダウン表示用データセット
		setSelectData(request, teacher);

		// 学生番号未入力チェック
		if (studentNo == null || studentNo.equals("")) {

			// エラーメッセージセット
			request.setAttribute("error", "学生番号を入力してください。");

			// JSPへフォワード
			request.getRequestDispatcher("test_list_student.jsp")
					.forward(request, response);
			return;
		}

		// StudentDao生成
		StudentDao studentDao = new StudentDao();

		// 学生情報取得
		Student student = studentDao.get(studentNo);

		// 学生情報が存在しない場合
		if (student == null) {

			// エラーメッセージセット
			request.setAttribute("error", "学生情報が存在しませんでした。");

		} else {

			// TestListStudentDao生成
			TestListStudentDao testListStudentDao =
					new TestListStudentDao();

			// 学生の成績一覧取得
			List<TestListStudent> list =
					testListStudentDao.filter(student);

			// JSPへ学生情報セット
			request.setAttribute("student", student);

			// JSPへ成績一覧セット
			request.setAttribute("list", list);

			// 成績情報が存在しない場合
			if (list == null || list.isEmpty()) {

				// エラーメッセージセット
				request.setAttribute("error", "成績情報が存在しませんでした。");
			}
		}

		// 入力された学生番号保持
		request.setAttribute("student_no", studentNo);

		// JSPへフォワード
		request.getRequestDispatcher("test_list_student.jsp")
				.forward(request, response);
	}

	// プルダウン表示用データセットメソッド
	private void setSelectData(HttpServletRequest request, Teacher teacher)
			throws Exception {

		// 入学年度一覧リスト
		List<Integer> entYearSet = new ArrayList<>();

		// 現在年取得
		int thisYear = LocalDate.now().getYear();

		// 10年前〜今年+1年まで追加
		for (int i = thisYear - 10; i <= thisYear + 1; i++) {
			entYearSet.add(i);
		}

		// DAO生成
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();

		// 入学年度一覧セット
		request.setAttribute("entYearSet", entYearSet);

		// クラス一覧セット
		request.setAttribute(
				"classNumSet",
				classNumDao.filter(teacher.getSchool()));

		// 科目一覧セット
		request.setAttribute(
				"subjectSet",
				subjectDao.filter(teacher.getSchool()));
	}
}