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

		Teacher teacher =
				(Teacher) request.getSession().getAttribute("user");

		if (teacher == null) {
			teacher =
					(Teacher) request.getSession().getAttribute("teacher");
		}

		if (teacher == null) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
			return;
		}

		setSelectData(request, teacher);

		String entYearStr = request.getParameter("ent_year");
		String classNum = request.getParameter("class_num");
		String subjectCd = request.getParameter("subject_cd");

		if (entYearStr == null || entYearStr.equals("")
				|| classNum == null || classNum.equals("")
				|| subjectCd == null || subjectCd.equals("")) {

			request.setAttribute(
					"error",
					"入学年度・クラス・科目を選択してください");

			request.getRequestDispatcher("test_list_subject.jsp")
					.forward(request, response);
			return;
		}

		int entYear = Integer.parseInt(entYearStr);

		SubjectDao subjectDao = new SubjectDao();

		Subject subject =
				subjectDao.get(subjectCd, teacher.getSchool());

		if (subject == null) {
			request.setAttribute("error", "科目情報が見つかりませんでした。");

			request.getRequestDispatcher("test_list_subject.jsp")
					.forward(request, response);
			return;
		}

		TestListSubjectDao dao = new TestListSubjectDao();

		List<TestListSubject> list =
				dao.filter(
						entYear,
						classNum,
						subject,
						teacher.getSchool());

		request.setAttribute("ent_year", entYear);
		request.setAttribute("class_num", classNum);
		request.setAttribute("subject_cd", subjectCd);
		request.setAttribute("subject", subject);
		request.setAttribute("tests", list);

		request.getRequestDispatcher("test_list_subject.jsp")
				.forward(request, response);
	}

	private void setSelectData(HttpServletRequest request, Teacher teacher)
			throws Exception {

		List<Integer> entYearSet = new ArrayList<>();

		int thisYear = LocalDate.now().getYear();

		for (int i = thisYear - 10; i <= thisYear + 1; i++) {
			entYearSet.add(i);
		}

		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();

		request.setAttribute("entYearSet", entYearSet);

		request.setAttribute(
				"classNumSet",
				classNumDao.filter(teacher.getSchool()));

		request.setAttribute(
				"subjectSet",
				subjectDao.filter(teacher.getSchool()));
	}
}