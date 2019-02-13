package aircompany;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import dao.AirportDAO;
import dao.FlightsDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Airport;
import model.Flight;
import model.Ticket;
import model.User;
import model.User.Role;



public class FlightsViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> data = new LinkedHashMap<>();
		
		String id = request.getParameter("id");
		System.out.println(id);
		int Id = Integer.parseInt(id);
		Flight flight = FlightsDAO.get(Id);
		List<Integer> li = TicketDAO.takenSeats(flight.getId(), flight.getId());
		data.put("li" , li);
		
		List<Ticket> flightReservations = TicketDAO.getFlightsReservations(flight, flight);
		List<Ticket> flightPurchases = TicketDAO.getFlightsPurchases(flight, flight);
		
		List<Flight> roundtripFlights = FlightsDAO.getRoundtripFlight(flight.getArrivals(), flight.getDepartures(), flight.getArrivalTime());
		
		double seatsAvailable = FlightsDAO.getNumberOfAvailableSeats(flight.getId(), flight.getId(), flight.getId());
		
		Timestamp ts = flight.getArrivalTime();
		Date date = new Date();
		date.setTime(ts.getTime());	
		String flightDate = FlightsDAO.sd.format(date);

		

		data.put("flight", flight);
		data.put("flightReservations", flightReservations);
		data.put("flightPurchases", flightPurchases);
		data.put("roundtripFlights", roundtripFlights);
		data.put("seatsAvailable", seatsAvailable);
		data.put("flightDate", flightDate);
		System.out.println(data);
		
		
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
		
		if (loggedInUser.getRole() != Role.ADMIN) {
			request.getRequestDispatcher("./UnauthorizedServlet").forward(request, response);
			return;
		}
		
		try {
			String action = request.getParameter("action");
			switch(action) {
				case "add": {
					
					String flightNum = request.getParameter("flightNum");
					flightNum = (!"".equals(flightNum) ? flightNum : "no number");
					System.out.println(flightNum);
					
					String departureDateString = request.getParameter("departureDate");
					String departureTimeString = request.getParameter("departureTime");
					String departureDateTimeString = departureDateString + " " + departureTimeString;
					Timestamp departureDateTime = new Timestamp(FlightsDAO.sdf.parse(departureDateTimeString).getTime());
					
					String arrivalDateString = request.getParameter("arrivalDate");
					String arrivalTimeString = request.getParameter("arrivalTime");
					String arrivalDateTimeString = arrivalDateString + " " + arrivalTimeString;
					Timestamp arrivalDateTime = new Timestamp(FlightsDAO.sdf.parse(arrivalDateTimeString).getTime());
					
					List<Flight> allFlights = FlightsDAO.getAllFlights();
					for(Flight f: allFlights) {
						Timestamp t = new Timestamp(FlightsDAO.sdf.parse(f.getDepartureTime().toString()).getTime());
						if(flightNum.equals(f.getFlightNum()) && departureDateTime.equals(t)) {
							throw new Exception("There is already a flight with this number for this date");
						}
					}
					
					if(arrivalDateTime.before(departureDateTime)) {
						throw new Exception("Arrival date must be after departure date");
					}
					
					String departuresStr = request.getParameter("departureAirport");
					String arrivalsStr = request.getParameter("arrivalAirport");
					departuresStr = (departuresStr != null? departuresStr: "");
					arrivalsStr = (arrivalsStr != null? arrivalsStr: "");

					if(departuresStr.equals("")) {
						departuresStr = "LAX";
					}
					
					if(arrivalsStr.equals("")) {
						arrivalsStr = "Nikola Tesla";
					}
					Airport departures = AirportDAO.getAirportName(departuresStr);
					Airport arrivals = AirportDAO.getAirportName(arrivalsStr);
					
					int seatNum = Integer.parseInt(request.getParameter("seatNum"));
					seatNum = (seatNum > 0 ? seatNum : 50);
					
					double ticketPrice = Double.parseDouble(request.getParameter("ticketPrice"));
					ticketPrice = (ticketPrice > 0 ? ticketPrice : 99999999.00);
					
					Flight flight = new Flight(flightNum, departureDateTime, arrivalDateTime, departures, arrivals, seatNum, ticketPrice);
					FlightsDAO.addFlight(flight);
					break;
					
				}
				case "edit": {
					
						int id = Integer.parseInt(request.getParameter("id"));
						Flight flight = FlightsDAO.get(id);
						
						
						String arrivalDateString = request.getParameter("arrivalDate");
						String arrivalTimeString = request.getParameter("arrivalTime");
						String arrivalDateTimeString = arrivalDateString + " " + arrivalTimeString;				
						Timestamp arrivalTime = new Timestamp(FlightsDAO.sdf.parse(arrivalDateTimeString).getTime());
						
						String departuresStr = request.getParameter("departureAirport");
						String arrivalsStr = request.getParameter("arrivalAirport");
						departuresStr = (departuresStr != null? departuresStr: "");
						arrivalsStr = (arrivalsStr != null? arrivalsStr: "");
						
						
	
						if(departuresStr.equals("")) {
							departuresStr = "LAX";
						}
						
						if(arrivalsStr.equals("")) {
							arrivalsStr = "Nikola Tesla";
						}
						Airport departures = AirportDAO.getAirportName(departuresStr);
						Airport arrivals = AirportDAO.getAirportName(arrivalsStr);
						
						int seatNum = Integer.parseInt(request.getParameter("seatNum"));
						seatNum = (seatNum > 0 ? seatNum : flight.getSeatNum());
						
						List<Integer> flightSeats = TicketDAO.takenSeats(id, id);
						
						for(int i = 0; i < flightSeats.size();i++) {
							if(flightSeats.get(i) > seatNum) {
								throw new Exception("Number of seats must be greater");
								
							}
							
						}
						double ticketPrice = Double.parseDouble(request.getParameter("ticketPrice"));
						ticketPrice = (ticketPrice > 0 ? ticketPrice : flight.getTicketPrice());
						
						
						
						flight.setArrivalTime(arrivalTime);
						flight.setDepartures(departures);
						flight.setArrivals(arrivals);
						flight.setSeatNum(seatNum);
						flight.setTicketPrice(ticketPrice);
						FlightsDAO.updateFlight(flight);
					
					break;
				}
				case "delete": {
					int id = Integer.parseInt(request.getParameter("id"));
					boolean purchaseExists = TicketDAO.doesFlightHavePurchases(id, id);
					if(purchaseExists) {
						FlightsDAO.deleteFlightLogical(id);
					}else {
						FlightsDAO.deleteFlight(id);
					}
					break;
				}
			}
			
			request.getRequestDispatcher("./SuccessServlet").forward(request, response);
		} catch (Exception ex) {
			
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
