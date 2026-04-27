package scoremanager.main;

import java.util.List;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    //////////追記//////////
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String stuNo = req.getParameter("no");
		
		ClassNumDao classNumDao = new ClassNumDao();
		StudentDao studentDao =new StudentDao();
		
		Student stu = studentDao.get(stuNo);
		
		List<String> list = classNumDao.filter(teacher.getSchool());
		
		req.setAttribute("is_attend", stu.isAttend());
		req.setAttribute("no", stu.getNo());
		req.setAttribute("name", stu.getName());
		req.setAttribute("ent_year", stu.getEntYear());
		req.setAttribute("class_num_set", list);

		// JSPへフォワード 7
		req.getRequestDispatcher("student_update.jsp").forward(req, res);
	}

}
