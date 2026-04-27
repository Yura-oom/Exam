package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class LogoutAction extends Action{
	public void execute(
			HttpServletRequest request, HttpServletResponse response
			)throws Exception{
		
		String url = "";
		HttpSession session = request.getSession();
		
		if(session.getAttribute("user")!=null) {
			session.removeAttribute("user");
		}
		
		url = "logout.jsp";
		request.getRequestDispatcher(url).forward(request, response);
	}

}
