package aircompany;

import java.io.IOException;


import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;



public class UserServlet extends HttpServlet {
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
		
		Map<String, Object> data = new LinkedHashMap<>();
		
		String action = request.getParameter("action");
		switch(action) {
			case "loggedInUserRole": {
				data.put("loggedInUserRole", loggedInUser.getRole());
				break;
			}
			case "loggedInUserStatus": {
				data.put("loggedInUserStatus", loggedInUser.isBlockedUser());
				break;
			}
		}
		

		request.setAttribute("data", data);
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
