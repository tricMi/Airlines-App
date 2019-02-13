package aircompany;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;
import model.User.Role;

public class UsersViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUsername = (String)request.getSession().getAttribute("loggedInUsername");
		if(loggedInUsername == null)
		{
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		User loggedInUser = UserDAO.get(loggedInUsername);
		if(loggedInUser == null)
		{
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		if (loggedInUser.getRole() != Role.ADMIN) {
			request.getRequestDispatcher("./UnauthorizedServlet").forward(request, response);
			return;
		}
		
		String username = request.getParameter("usernameFilter");
		username = (username != null ? username : "");
		String roleString = request.getParameter("role");
		if(roleString == null ) {
			roleString = "ADMIN";
		}
		Role role = Role.valueOf(roleString);
		List<User> users = UserDAO.getUsersId(username, role);
		
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("users", users);
		System.out.println(data);
		request.setAttribute("data", data);
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
