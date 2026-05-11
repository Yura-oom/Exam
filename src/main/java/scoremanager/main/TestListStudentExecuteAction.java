package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
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
            response.sendRedirect(request.getContextPath() + "/login.jsp");
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
}