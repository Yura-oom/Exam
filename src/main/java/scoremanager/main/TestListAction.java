package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class TestListAction extends Action {

    @Override
    public void execute(HttpServletRequest request,
            HttpServletResponse response) throws Exception {

                HttpSession session = request.getSession();

        // セッションからログイン中の先生情報取得
        Teacher teacher = (Teacher) session.getAttribute("user");

        // ログインしていない場合ログイン画面へリダイレクト
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 現在日付取得
        LocalDate todaysDate = LocalDate.now();

        // 現在年取得
        int year = todaysDate.getYear();

        // 入学年度一覧リスト生成
        List<Integer> entYearSet = new ArrayList<>();

        // 10年前〜今年まで追加
        for (int i = year - 10; i < year + 1; i++) {
            entYearSet.add(i);
        }

        // ClassNumDao生成
        ClassNumDao classNumDao = new ClassNumDao();

        // クラス一覧取得
        List<String> classNumSet =
                classNumDao.filter(teacher.getSchool());

        // SubjectDao生成
        SubjectDao subjectDao = new SubjectDao();

        // 科目一覧取得
        List<Subject> subjectSet =
                subjectDao.filter(teacher.getSchool());

        // JSPへ入学年度一覧セット
        request.setAttribute("ent_year_set", entYearSet);

        // JSPへクラス一覧セット
        request.setAttribute("class_num_set", classNumSet);

        // JSPへ科目一覧セット
        request.setAttribute("subject_set", subjectSet);

        // JSPへフォワード
        request.getRequestDispatcher("test_list.jsp")
               .forward(request, response);
    }
}