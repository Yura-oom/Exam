package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. セッションを取得する
        HttpSession session = req.getSession();

        // 2. ログイン中の先生情報を取得する
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 3. 科目DAOを生成する
        SubjectDao subjectDao = new SubjectDao();

        // 4. ログイン中の先生の学校に所属する科目一覧を取得する
        List<Subject> subjectList = subjectDao.filter(teacher.getSchool());

        // 5. JSPで使えるようにリクエストにセットする
        req.setAttribute("subjects", subjectList);

        // 6. 科目一覧画面へフォワードする
        req.getRequestDispatcher("subject_list.jsp").forward(req, res);
    }
}