package scoremanager.main;


import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

    //////////追記//////////
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher)session.getAttribute("user");
		
		String no = req.getParameter("no");
		String name = req.getParameter("name");
		String entYearStr = req.getParameter("ent_year");
		int entYear = Integer.parseInt(entYearStr);
		String classNum = req.getParameter("class_num");
		String isAttends = req.getParameter("is_attend");
		boolean isAttend;
		if(isAttends != null) {
			isAttend = true;
		}else {
			isAttend = false;
		}
		
		StudentDao studentDao = new StudentDao();
		//
		
		
		Student student = new Student();
		student.setNo(no);
		student.setName(name);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setAttend(isAttend);
		student.setSchool(teacher.getSchool());
		
		studentDao.save(student);
		
		// JSPへフォワード 7
		req.getRequestDispatcher("student_update_done.jsp").forward(req, res);
	}

}
