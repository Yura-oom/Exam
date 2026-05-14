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

        // リクエストパラメータから削除対象の科目コードを取得する
        String cd = request.getParameter("cd");

        // セッションからログイン中の先生情報を取得する
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        // ログインしていない場合はログイン画面へリダイレクトする
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ログイン中の先生が所属する学校情報を取得する
        School school = teacher.getSchool();

        // 科目情報を取得するためのDAOを生成する
        SubjectDao dao = new SubjectDao();

        // 科目コードと学校情報を使って、削除対象の科目情報を取得する
        Subject subject = dao.get(cd, school);

        // 取得した科目情報をJSPに渡す
        request.setAttribute("subject", subject);

        // 科目削除確認画面へフォワードする
        request.getRequestDispatcher("/scoremanager/main/subject_delete.jsp")
               .forward(request, response);
    }
}