package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("UTF-8");
		
		// フォームから値を取得
        String cd = request.getParameter("cd");
        String name = request.getParameter("name");
        
        //ログイン情報取得
        Teacher teacher = (Teacher) request.getSession().getAttribute("user");

        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        
        //教科名が20文字以上の場合は更新できないので入力画面に戻す
        if (name.length() > 20) {
        	request.setAttribute("cd", cd);
        	request.setAttribute("name", name);
        	request.setAttribute("error2", "教科名は20文字以内にしてください");
        	request.getRequestDispatcher("/scoremanager/main/subject_update.jsp")
        		.forward(request, response);
        }else if(cd == null) {
        	request.setAttribute("error", "科目が存在していません");
		}else {
			//更新した状態のsubjectを作成
	        Subject subject = new Subject();
	        
	        subject.setCd(cd);
	        subject.setName(name);
	        subject.setSchool(teacher.getSchool());
	        
	        //DB更新
	        SubjectDao dao = new SubjectDao();
	        boolean result = dao.save(subject);
	        
	        if (result) {
	            //更新成功
	            request.getRequestDispatcher("/scoremanager/main/subject_update_done.jsp")
	                   .forward(request, response);
	        } else {
	            // 更新失敗
	            request.setAttribute("error", "登録に失敗しました。");
	            request.getRequestDispatcher("/scoremanager/main/subject_update.jsp")
	                   .forward(request, response);
	        }
		}
        
        
	}
}
