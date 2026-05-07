package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String cd = request.getParameter("cd");

        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        School school = teacher.getSchool();

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setSchool(school);

        SubjectDao dao = new SubjectDao();
        boolean result = dao.delete(subject);

        if (result) {
            request.getRequestDispatcher("/scoremanager/main/subject_delete_done.jsp")
                   .forward(request, response);
        } else {
            request.setAttribute("error", "削除に失敗しました。");
            request.getRequestDispatcher("/scoremanager/main/subject_delete.jsp")
                   .forward(request, response);
        }
    }
}