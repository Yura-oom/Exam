package scoremanager.main;

import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class TestRegistAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Teacher teacher = (Teacher) request.getSession().getAttribute("user");
        

        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        School school = teacher.getSchool();

        StudentDao studentDao = new StudentDao();
        ClassNumDao classNumDao = new ClassNumDao();
        SubjectDao subjectDao = new SubjectDao();

        request.setAttribute("entYearSet", studentDao.getEntYearSet(school));
        request.setAttribute("classNumSet", classNumDao.filter(school));
        request.setAttribute("subjectSet", subjectDao.filter(school));

        String entYearStr = request.getParameter("ent_year");
        String classNum = request.getParameter("class_num");
        String subjectCd = request.getParameter("subject_cd");
        String no = request.getParameter("no");

        request.setAttribute("ent_year", entYearStr);
        request.setAttribute("class_num", classNum);
        request.setAttribute("subject_cd", subjectCd);
        request.setAttribute("no", no);

        if (entYearStr != null && !entYearStr.isEmpty()
                && classNum != null && !classNum.isEmpty()
                && subjectCd != null && !subjectCd.isEmpty()
                && no != null && !no.isEmpty()) {

            int entYear = Integer.parseInt(entYearStr);

            List<Student> studentList = studentDao.filter(school, entYear, classNum, true);
            Subject subject = subjectDao.get(subjectCd, school);

            request.setAttribute("studentList", studentList);
            request.setAttribute("subject", subject);

            if (studentList == null || studentList.isEmpty()) {
                request.setAttribute("error", "学生情報が存在しませんでした");
            }
        }

        request.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
               .forward(request, response);
    }
}