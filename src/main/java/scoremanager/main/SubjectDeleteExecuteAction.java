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

        // 削除対象の科目情報を入れるSubjectオブジェクトを生成する
        Subject subject = new Subject();

        // 削除対象の科目コードをセットする
        subject.setCd(cd);

        // 削除対象の学校情報をセットする
        subject.setSchool(school);

        // 科目情報を削除するためのDAOを生成する
        SubjectDao dao = new SubjectDao();

        // 科目情報を削除する
        boolean result = dao.delete(subject);

        // 削除に成功した場合
        if (result) {
            // 科目削除完了画面へフォワードする
            request.getRequestDispatcher("/scoremanager/main/subject_delete_done.jsp")
                   .forward(request, response);

        } else {
            // 削除に失敗した場合、エラーメッセージをJSPに渡す
            request.setAttribute("error", "削除に失敗しました。");

            // 科目削除確認画面へ戻る
            request.getRequestDispatcher("/scoremanager/main/subject_delete.jsp")
                   .forward(request, response);
        }
    }
}