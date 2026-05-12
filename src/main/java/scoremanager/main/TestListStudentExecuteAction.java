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

		String studentNo = request.getParameter("student_no");

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

		if (studentNo == null || studentNo.equals("")) {
			request.setAttribute("error", "学生番号を入力してください。");

			request.getRequestDispatcher("test_list_student.jsp")
					.forward(request, response);
			return;
		}

		StudentDao studentDao = new StudentDao();
		Student student = studentDao.get(studentNo);

		if (student == null) {
			request.setAttribute("error", "学生情報が存在しませんでした。");
		} else {
			TestListStudentDao testListStudentDao =
					new TestListStudentDao();

			List<TestListStudent> list =
					testListStudentDao.filter(student);

			request.setAttribute("student", student);
			request.setAttribute("list", list);

			if (list == null || list.isEmpty()) {
				request.setAttribute("error", "成績情報が存在しませんでした。");
			}
		}

		request.setAttribute("student_no", studentNo);

		request.getRequestDispatcher("test_list_student.jsp")
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