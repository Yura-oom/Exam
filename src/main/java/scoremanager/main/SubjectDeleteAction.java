package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String cd = request.getParameter("cd");

        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        School school = teacher.getSchool();

        SubjectDao dao = new SubjectDao();
        Subject subject = dao.get(cd, school);

        request.setAttribute("subject", subject);

        request.getRequestDispatcher("/scoremanager/main/subject_delete.jsp")
               .forward(request, response);
    }
}