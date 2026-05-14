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

        // 文字化けを防ぐため、リクエストの文字コードをUTF-8に設定する
        request.setCharacterEncoding("UTF-8");

        // セッションからログイン中の先生情報を取得する
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        // ログインしていない場合はログイン画面へリダイレクトする
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ログイン中の先生が所属する学校情報を取得する
        School school = teacher.getSchool();

        // リクエストパラメータから入学年度を取得する
        String entYearStr = request.getParameter("ent_year");

        // リクエストパラメータからクラス番号を取得する
        String classNum = request.getParameter("class_num");

        // リクエストパラメータから科目コードを取得する
        String subjectCd = request.getParameter("subject_cd");

        // リクエストパラメータから試験回数を取得する
        String noStr = request.getParameter("no");

        // 入学年度を文字列からint型に変換する
        int entYear = Integer.parseInt(entYearStr);

        // 試験回数を文字列からint型に変換する
        int no = Integer.parseInt(noStr);

        // 科目情報を扱うDAOを生成する
        SubjectDao subjectDao = new SubjectDao();

        // 学生情報を扱うDAOを生成する
        StudentDao studentDao = new StudentDao();

        // 得点情報を扱うDAOを生成する
        TestDao testDao = new TestDao();

        // 科目コードと学校情報から科目情報を取得する
        Subject subject = subjectDao.get(subjectCd, school);

        // 指定された学校・入学年度・クラス番号に所属する在学中の学生一覧を取得する
        List<Student> studentList =
                studentDao.filter(school, entYear, classNum, true);

        // 複数の得点情報を保存するためのデータベース接続
        Connection connection = null;

        try {
            // TestDaoからデータベース接続を取得する
            connection = testDao.getConnection();

            // 学生一覧を1件ずつ処理する
            for (Student student : studentList) {

                // JSPの入力欄から、各学生の得点を取得する
                // 例：学生番号が A001 の場合、point_A001 という名前で送信される
                String pointStr = request.getParameter("point_" + student.getNo());

                // 得点が未入力の場合、その学生の登録処理は行わない
                if (pointStr == null || pointStr.isEmpty()) {
                    continue;
                }

                // 得点を文字列からint型に変換する
                int point = Integer.parseInt(pointStr);

                // 登録する得点情報を入れるTestオブジェクトを生成する
                Test test = new Test();

                // 学生情報をセットする
                test.setStudent(student);

                // クラス番号をセットする
                test.setClassNum(classNum);

                // 科目情報をセットする
                test.setSubject(subject);

                // 学校情報をセットする
                test.setSchool(school);

                // 試験回数をセットする
                test.setNo(no);

                // 得点をセットする
                test.setPoint(point);

                // 得点情報をデータベースに保存する
                testDao.save(test, connection);
            }

        } finally {
            // データベース接続を閉じる
            if (connection != null) {
                connection.close();
            }
        }

        // 成績登録完了画面へフォワードする
        request.getRequestDispatcher("/scoremanager/main/test_regist_done.jsp")
               .forward(request, response);
    }
}