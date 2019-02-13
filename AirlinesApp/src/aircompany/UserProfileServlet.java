package aircompany;

import java.io.IOException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.TicketDAO;
import dao.UserDAO;
import model.Ticket;
import model.User;
import model.User.Role;


public class UserProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loggedInUsername = (String)request.getSession().getAttribute("loggedInUsername");
		if(loggedInUsername == null)
		{
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		User loggedInUser = UserDAO.get(loggedInUsername);
		System.out.println(loggedInUser);
		if(loggedInUser == null)
		{
			request.getRequestDispatcher("./LogoutServlet").forward(request, response);
			return;
		}
		
		Map<String, Object> data = new LinkedHashMap<>();
		
		String id = request.getParameter("id");
		System.out.println(id);
		int Id = Integer.parseInt(id);
		User user = UserDAO.getUserId(Id);
		
		List<Ticket> reservedTickets = TicketDAO.getUserReservations(user);
		List<Ticket> purchasedTickets = TicketDAO.getUserPurchases(user);
		
		
		data.put("user", user);
		data.put("reservedTickets", reservedTickets);
		data.put("purchasedTickets", purchasedTickets);
		request.setAttribute("data", data);
		
		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
		try {
			String action = request.getParameter("action");
			switch(action) {
				case "editByAdmin": {
					String id = request.getParameter("id");
					int Id = Integer.parseInt(id);
					User user = UserDAO.getUserId(Id);
					
					
					String password = request.getParameter("password");
					password = (!"".equals(password) ? password : user.getPassword());
					
					
					String roleString = request.getParameter("role");
					if(roleString.trim().equals("")) {
						roleString = "USER";
					}
					
					Role role = Role.valueOf(roleString);
					
					String blockedUserString = request.getParameter("blockedUser");
					boolean returnValue = false;
					if(blockedUserString.trim().equals("Active")) {
						returnValue = false;
					}else {
						returnValue = true;
					}
					boolean blockedUser = returnValue;
					

					user.setPassword(password);
					user.setRole(role);
					user.setBlockedUser(blockedUser);
					UserDAO.updateUserByAdmin(user);
					break;
				}
				case "editByUser": {
					String id = request.getParameter("id");
					int Id = Integer.parseInt(id);
					User user = UserDAO.getUserId(Id);
					
					
					String password = request.getParameter("password");
					password = (!"".equals(password) ? password : user.getPassword());
					

					
					String roleString = request.getParameter("role");
					if(roleString.trim().equals("")) {
						roleString = "USER";
					}
					
					Role role = Role.valueOf(roleString);
					
					String blockedUserString = request.getParameter("blockedUser");
					boolean returnValue = false;
					if(blockedUserString.trim().equals("Active")) {
						returnValue = false;
					}else {
						returnValue = true;
					}
					boolean blockedUser = returnValue;
					
					user.setPassword(password);
					user.setRole(role);
					user.setBlockedUser(blockedUser);
					UserDAO.updateUserByAdmin(user);
					break;
				}
				case "delete": {
					String id = request.getParameter("id");
					int Id = Integer.parseInt(id);
					boolean ifPurchaseExists = TicketDAO.doesUserHavePurchases(Id);
					
					if(ifPurchaseExists) {
						UserDAO.deleteUserLogical(Id);
					}else {
						UserDAO.deleteUser(Id);
					}
					break;
				}
			}
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
		}
	}

}
