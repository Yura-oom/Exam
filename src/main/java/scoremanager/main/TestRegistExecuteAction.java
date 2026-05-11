package scoremanager.main;

import java.sql.Connection;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        School school = teacher.getSchool();

        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");
        String noStr = request.getParameter("no");

        int entYear = Integer.parseInt(entYearStr);
        int no = Integer.parseInt(noStr);

        SubjectDao subjectDao = new SubjectDao();
        StudentDao studentDao = new StudentDao();
        TestDao testDao = new TestDao();

        Subject subject = subjectDao.get(subjectCd, school);

        List<Student> studentList =
                studentDao.filter(school, entYear, classNum, true);
        Connection connection = null;

        try {
            connection = testDao.getConnection();

            for (Student student : studentList) {

                String pointStr = request.getParameter("point_" + student.getNo());

                if (pointStr == null || pointStr.isEmpty()) {
                    continue;
                }

                int point = Integer.parseInt(pointStr);

                Test test = new Test();
                test.setStudent(student);
                test.setClassNum(classNum);
                test.setSubject(subject);
                test.setSchool(school);
                test.setNo(no);
                test.setPoint(point);

                testDao.save(test, connection);
            }

        } finally {
            if (connection != null) {
                connection.close();
            }
        }

        request.getRequestDispatcher("/scoremanager/main/test_regist_done.jsp")
               .forward(request, response);
    }
}