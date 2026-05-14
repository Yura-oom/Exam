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

        // セッションからログイン中の先生情報を取得する
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        // ログインしていない場合はログイン画面へリダイレクトする
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ログイン中の先生が所属する学校情報を取得する
        School school = teacher.getSchool();

        // 学生情報を扱うDAOを生成する
        StudentDao studentDao = new StudentDao();

        // クラス番号を扱うDAOを生成する
        ClassNumDao classNumDao = new ClassNumDao();

        // 科目情報を扱うDAOを生成する
        SubjectDao subjectDao = new SubjectDao();

        // 入学年度の一覧を取得し、JSPに渡す
        request.setAttribute("entYearSet", studentDao.getEntYearSet(school));

        // クラス番号の一覧を取得し、JSPに渡す
        request.setAttribute("classNumSet", classNumDao.filter(school));

        // 科目の一覧を取得し、JSPに渡す
        request.setAttribute("subjectSet", subjectDao.filter(school));

        // 検索フォームから送信された入学年度を取得する
        String entYearStr = request.getParameter("ent_year");

        // 検索フォームから送信されたクラス番号を取得する
        String classNum = request.getParameter("class_num");

        // 検索フォームから送信された科目コードを取得する
        String subjectCd = request.getParameter("subject_cd");

        // 検索フォームから送信された試験回数を取得する
        String no = request.getParameter("no");

        // 入力された入学年度をJSPに戻す
        request.setAttribute("ent_year", entYearStr);

        // 入力されたクラス番号をJSPに戻す
        request.setAttribute("class_num", classNum);

        // 入力された科目コードをJSPに戻す
        request.setAttribute("subject_cd", subjectCd);

        // 入力された試験回数をJSPに戻す
        request.setAttribute("no", no);

        // 入学年度・クラス番号・科目コード・試験回数がすべて入力されている場合
        if (entYearStr != null && !entYearStr.isEmpty()
                && classNum != null && !classNum.isEmpty()
                && subjectCd != null && !subjectCd.isEmpty()
                && no != null && !no.isEmpty()) {

            // 入学年度を文字列からint型に変換する
            int entYear = Integer.parseInt(entYearStr);

            // 指定された学校・入学年度・クラス番号に所属する在学中の学生一覧を取得する
            List<Student> studentList = studentDao.filter(school, entYear, classNum, true);

            // 指定された科目コードから科目情報を取得する
            Subject subject = subjectDao.get(subjectCd, school);

            // 学生一覧をJSPに渡す
            request.setAttribute("studentList", studentList);

            // 科目情報をJSPに渡す
            request.setAttribute("subject", subject);

            // 条件に合う学生が存在しない場合、エラーメッセージをJSPに渡す
            if (studentList == null || studentList.isEmpty()) {
                request.setAttribute("error", "学生情報が存在しませんでした");
            }
        }

        // 成績登録画面へフォワードする
        request.getRequestDispatcher("/scoremanager/main/test_regist.jsp")
               .forward(request, response);
    }
}