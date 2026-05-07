package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectUpdateAction extends Action{
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		//ローカル変数の宣言
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		SubjectDao subjectdao = new SubjectDao();
		
		//リクエストパラメータ―の取得
		String subCd = req.getParameter("cd");
		
		//DBからデータ取得
		Subject subject = subjectdao.get(subCd, teacher.getSchool());
		//DBへデータ保存
		//なし
		
		//レスポンス値をセット
		req.setAttribute("cd", subCd);
		req.setAttribute("name", subject.getName());
		
		// JSPへフォワード
		req.getRequestDispatcher("subject_update.jsp").forward(req, res);
	}
}