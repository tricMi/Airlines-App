package aircompany;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDAO;
import model.User;
import model.User.Role;


public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String username = request.getParameter("username");
			if(UserDAO.get(username) != null)
			{
				throw new Exception("Username already exists");
				
			}
			if("".equals(username))
			{
				throw new Exception("Username can't be empty");
			}
			
			String password = request.getParameter("password");
			if("".equals(password))
			{
				throw new Exception("Password can't be empty");
			}
			
			String dateString = LocalDate.now().toString();
			Date registrationDate = new Date(UserDAO.DATE_FORMAT.parse(dateString).getTime());
			
			User user = new User(username, password, registrationDate, Role.USER, false);
			UserDAO.addUser(user);
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
			
		}catch(Exception ex)
		{
			String message = ex.getMessage();
			if(message == null)
			{
				message = "Unpredictable error";
				ex.printStackTrace();
			}
			
			Map<String, Object> data = new LinkedHashMap<>();
			data.put("message", message);
			
			request.setAttribute("data", data);
			request.getRequestDispatcher("./FailureServlet").forward(request, response);
			
		}
	}

}
