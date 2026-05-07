package scoremanager.main;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        request.setCharacterEncoding("UTF-8");

        // フォームから値を取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");

        // ログイン中の先生を取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        School school = teacher.getSchool();
        

        // Subjectオブジェクト作成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        // DB登録
        SubjectDao dao = new SubjectDao();
        boolean result = dao.save(subject);

        if (result) {
            // 登録成功
            request.getRequestDispatcher("/scoremanager/main/subject_create_done.jsp")
                   .forward(request, response);
        } else {
            // 登録失敗
            request.setAttribute("error", "登録に失敗しました。");
            request.getRequestDispatcher("/scoremanager/main/subject_create.jsp")
                   .forward(request, response);
        }
    }
}