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
       // セッション取得
       HttpSession session = request.getSession();
       // ログイン中の先生情報取得
       Teacher teacher =
               (Teacher) session.getAttribute("user");
       // ログインチェック
       if (teacher == null) {
           response.sendRedirect(
                   request.getContextPath()
                   + "/login.jsp");
           return;
       }
       // 現在年取得
       LocalDate todaysDate = LocalDate.now();
       int year = todaysDate.getYear();
       
       // 入学年度リスト生成
       List<Integer> entYearSet =
               new ArrayList<>();
       for (int i = year - 10;
               i < year + 1;
               i++) {
           entYearSet.add(i);
       }
       // クラス番号一覧取得
       ClassNumDao classNumDao =
               new ClassNumDao();
       List<String> classNumSet =
               classNumDao.filter(
                       teacher.getSchool());
       // 科目一覧取得
       SubjectDao subjectDao =
               new SubjectDao();
       List<Subject> subjectSet =
               subjectDao.filter(
                       teacher.getSchool());
       
       // リクエストへセット
       request.setAttribute(
               "ent_year_set",
               entYearSet);
       request.setAttribute(
               "class_num_set",
               classNumSet);
       request.setAttribute(
               "subject_set",
               subjectSet);
       // JSPへフォワード
       request.getRequestDispatcher(
               "test_list.jsp")
               .forward(request, response);
   }
}