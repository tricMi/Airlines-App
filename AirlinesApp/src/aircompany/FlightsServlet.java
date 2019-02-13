package aircompany;

import java.io.IOException;


import java.sql.Timestamp;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AirportDAO;
import dao.FlightsDAO;
import model.Airport;
import model.Flight;



public class FlightsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String flightNum = request.getParameter("flightNumFilter");
		flightNum = (flightNum != null? flightNum: "");
		
		double minTicketPrice = 0.0;
		try {
			String minTicketPriceFilter = request.getParameter("minTicketPriceFilter");
			minTicketPriceFilter = (!"".equals(minTicketPriceFilter)? minTicketPriceFilter : "0.0");
			minTicketPrice = Double.parseDouble(minTicketPriceFilter);
			minTicketPrice = (minTicketPrice >= 0.0 ? minTicketPrice : 0.0);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		double maxTicketPrice = Double.MAX_VALUE;
		try {
			String maxTicketPriceFilter = request.getParameter("maxTicketPriceFilter");
			maxTicketPriceFilter = (!"".equals(maxTicketPriceFilter) ? maxTicketPriceFilter : "1000000");
			maxTicketPrice = Double.parseDouble(maxTicketPriceFilter);
			maxTicketPrice = (maxTicketPrice >= 0.0 ? maxTicketPrice : Double.MAX_VALUE);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
				
		String departureTimeString = request.getParameter("departureTimeFilter");
		String arrivalTimeString = request.getParameter("arrivalTimeFilter");
		String departureDateString = request.getParameter("departureDateFilter");
		String arrivalDateString = request.getParameter("arrivalDateFilter");
		
		String departureDateTimeString = departureDateString + " " + departureTimeString;
		String arrivalDateTimeString = arrivalDateString + " " + arrivalTimeString;
			
		if(departureDateTimeString.trim().equals("")) {
			departureDateTimeString = "2019-01-01 00:00:00";
		}
		
		if(arrivalDateTimeString.trim().equals("")) {
			arrivalDateTimeString = "2020-01-01 00:00:00";
		}
		
		Timestamp departureTime = null;
		Timestamp arrivalTime = null;
		try {
			departureTime = new Timestamp(FlightsDAO.sdf.parse(departureDateTimeString).getTime());
			arrivalTime = new Timestamp(FlightsDAO.sdf.parse(arrivalDateTimeString).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		String departures = request.getParameter("departureAirport");
		String arrivals = request.getParameter("arrivalAirport");

		
		Airport departureAirport = AirportDAO.getAirportName(departures);
		Airport arrivalAirport = AirportDAO.getAirportName(arrivals);
			
		List<Flight> filteredFlight = FlightsDAO.getFlights(flightNum, minTicketPrice, maxTicketPrice, departureTime, arrivalTime, departureAirport, arrivalAirport);
		
		List<Airport> airport = AirportDAO.getAirport();
	
		
		Map<String, Object> data = new LinkedHashMap<>();
		data.put("filteredFlight", filteredFlight);
		data.put("airport", airport);

		System.out.println(data);
		request.setAttribute("data", data);

		request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}
