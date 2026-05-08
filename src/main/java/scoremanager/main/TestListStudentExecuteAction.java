package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.TestListStudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestListStudentExecuteAction extends Action {

    @Override

    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");

        // 学生番号取得
        String studentNo =
                request.getParameter("student_no");

        // ログイン中の先生取得
        Teacher teacher =
                (Teacher) request.getSession()
                .getAttribute("user");

        // ログインチェック
        if (teacher == null) {
            response.sendRedirect(
                    request.getContextPath() + "/login.jsp");

            return;

        }

        // Studentインスタンス生成
        Student student = new Student();
        student.setNo(studentNo);

        // DAO生成
        TestListStudentDao testListStudentDao =
                new TestListStudentDao();

        // 成績一覧取得
        List<TestListStudent> list =
                testListStudentDao.filter(student);

        // request scopeへセット
        request.setAttribute(
                "student_no",studentNo);

        request.setAttribute(

                "list",list);

        // JSPへフォワード
        request.getRequestDispatcher(

                "test_list_student.jsp")

                .forward(request, response);

    }

}
 