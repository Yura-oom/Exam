package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override

	public void execute(HttpServletRequest request,

			HttpServletResponse response) throws Exception {

		request.setCharacterEncoding("UTF-8");

		// パラメータ取得
		String entYearStr =

				request.getParameter("ent_year");

		String classNum =

				request.getParameter("class_num");

		String subjectCd =

				request.getParameter("subject_cd");

		// ログイン中の先生取得
		Teacher teacher =

				(Teacher) request.getSession()

				.getAttribute("user");

		// ログインチェック
		if (teacher == null) {

			response.sendRedirect(

					request.getContextPath()

					+ "/login.jsp");

			return;

		}

		// 入力チェック
		if (entYearStr == null

				|| entYearStr.equals("")

				|| classNum == null

				|| classNum.equals("")

				|| subjectCd == null

				|| subjectCd.equals("")) {

			request.setAttribute(

					"error",

					"入学年度・クラス・科目を選択してください");

			request.getRequestDispatcher(

					"test_list.jsp")

					.forward(request, response);

			return;

		}

		// int変換
		int entYear =

				Integer.parseInt(entYearStr);

		// 科目取得
		SubjectDao subjectDao =

				new SubjectDao();

		Subject subject =

				subjectDao.get(

						subjectCd,

						teacher.getSchool());

		// DAO生成
		TestListSubjectDao dao =

				new TestListSubjectDao();

		// 成績一覧取得
		List<TestListSubject> list =

				dao.filter(

						entYear,

						classNum,

						subject,

						teacher.getSchool());

		// request scopeへセット

		request.setAttribute(

				"ent_year",

				entYear);

		request.setAttribute(

				"class_num",

				classNum);

		request.setAttribute(

				"subject_cd",

				subjectCd);

		request.setAttribute(

				"subject",

				subject);

		request.setAttribute(

				"tests",

				list);

		// JSPへフォワード

		request.getRequestDispatcher(

				"test_list_subject.jsp")

				.forward(request, response);

	}

}
 