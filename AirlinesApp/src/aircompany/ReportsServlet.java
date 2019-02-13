package aircompany;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AirportDAO;
import dao.UserDAO;
import model.Report;
import model.User;


public class ReportsServlet extends HttpServlet {
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
		
		String minDateString = request.getParameter("minDate");
		if(minDateString.trim().equals("")) {
			minDateString = "2019-01-01";
		}
		String maxDateString = request.getParameter("maxDate");
		if(maxDateString.trim().equals("")) {
			maxDateString = "2020-01-01";
		}
		String minTime = request.getParameter("minTime");
		if(minTime.trim().equals("")) {
			minTime = "00:00";
		}
		String maxTime = request.getParameter("maxTime");
		if(maxTime.trim().equals("")) {
			maxTime = "00:00";
		}
		
		String minDateTimeString = minDateString + " " + minTime;
		String maxDateTimeString = maxDateString + " " + maxTime;
			

		Timestamp minDateTime  = null;
		Timestamp maxDateTime = null;
		try {
			minDateTime = new Timestamp(AirportDAO.sdf.parse(minDateTimeString).getTime());
			maxDateTime= new Timestamp(AirportDAO.sdf.parse(maxDateTimeString).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
			
		List<Report> report = AirportDAO.getReports(minDateTime, maxDateTime);
		
		List<Integer> allFlights = new ArrayList<Integer>();
		List<Integer> allTickets = new ArrayList<Integer>();
		List<Double> sumPrice = new ArrayList<>();
		
		int total = 0;
		for(Report r : report)
		{
			total += r.getCountFlights();
			allFlights.add(total);
					
		}

		int totalCard = 0;
		for(Report a : report) {
			totalCard += a.getCountTickets();
			allTickets.add(totalCard);
		}
		
		double totalSum = 0;
		for(Report s : report) {
			totalSum += s.getSumPrice();
			sumPrice.add(totalSum);
		}
		
		
		Map<String, Object> data = new LinkedHashMap<>();
		
		data.put("report", report);
		data.put("allFlights", allFlights);
		data.put("allTickets", allTickets);
		data.put("sumPrice", sumPrice);
		
		request.setAttribute("data", data);

		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
