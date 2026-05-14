package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		request.setCharacterEncoding("UTF-8");

		// セッションからログイン中の先生情報を取得
		Teacher teacher =
				(Teacher) request.getSession().getAttribute("user");

		// userで取得できない場合teacherでも取得
		if (teacher == null) {
			teacher =
					(Teacher) request.getSession().getAttribute("teacher");
		}

		// ログインしていない場合ログイン画面へ戻す
		if (teacher == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		// プルダウン表示用データをセット
		setSelectData(request, teacher);

		// リクエストパラメータ取得
		String entYearStr = request.getParameter("ent_year");
		String classNum = request.getParameter("class_num");
		String subjectCd = request.getParameter("subject_cd");

		// 未入力チェック
		if (entYearStr == null || entYearStr.equals("")
				|| classNum == null || classNum.equals("")
				|| subjectCd == null || subjectCd.equals("")) {

			// エラーメッセージセット
			request.setAttribute(
					"error",
					"入学年度・クラス・科目を選択してください");

			// JSPへフォワード
			request.getRequestDispatcher("test_list_subject.jsp")
					.forward(request, response);
			return;
		}

		// 入学年度をint型へ変換
		int entYear = Integer.parseInt(entYearStr);

		// SubjectDao生成
		SubjectDao subjectDao = new SubjectDao();

		// 科目情報取得
		Subject subject =
				subjectDao.get(subjectCd, teacher.getSchool());

		// 科目が存在しない場合
		if (subject == null) {

			// エラーメッセージセット
			request.setAttribute("error", "科目情報が見つかりませんでした。");

			// JSPへフォワード
			request.getRequestDispatcher("test_list_subject.jsp")
					.forward(request, response);
			return;
		}

		// TestListSubjectDao生成
		TestListSubjectDao dao = new TestListSubjectDao();

		// 条件に一致する成績一覧取得
		List<TestListSubject> list =
				dao.filter(
						entYear,
						classNum,
						subject,
						teacher.getSchool());

		// JSPへ渡すデータをセット
		request.setAttribute("ent_year", entYear);
		request.setAttribute("class_num", classNum);
		request.setAttribute("subject_cd", subjectCd);
		request.setAttribute("subject", subject);
		request.setAttribute("tests", list);

		// JSPへフォワード
		request.getRequestDispatcher("test_list_subject.jsp")
				.forward(request, response);
	}

	// プルダウン用データをセットするメソッド
	private void setSelectData(HttpServletRequest request, Teacher teacher)
			throws Exception {

		// 入学年度一覧リスト
		List<Integer> entYearSet = new ArrayList<>();

		// 現在年取得
		int thisYear = LocalDate.now().getYear();

		// 10年前〜今年+1年までリストへ追加
		for (int i = thisYear - 10; i <= thisYear + 1; i++) {
			entYearSet.add(i);
		}

		// DAO生成
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();

		// リクエストへセット
		request.setAttribute("entYearSet", entYearSet);

		// クラス番号一覧セット
		request.setAttribute(
				"classNumSet",
				classNumDao.filter(teacher.getSchool()));

		// 科目一覧セット
		request.setAttribute(
				"subjectSet",
				subjectDao.filter(teacher.getSchool()));
	}
}