package aircompany;

import java.io.IOException;


import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FlightsDAO;
import dao.TicketDAO;
import dao.UserDAO;
import model.Flight;
import model.Ticket;
import model.User;

public class TicketsServlet extends HttpServlet {
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
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		String dateString = LocalDateTime.now().format(formatter).toString();
 
		Timestamp departureTime = null;
		try {
			departureTime = new Timestamp(FlightsDAO.sdf.parse(dateString).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Map<String, Object> data = new LinkedHashMap<>();
		List<Flight> currentDateFlight = FlightsDAO.getFlightByCurrentDate(departureTime);
		List<Ticket> purchasedTickets = TicketDAO.getPurchases();
		List<Ticket> reservedTickets = TicketDAO.getReservations();
		
		data.put("currentDateFlight", currentDateFlight);	
		data.put("purchasedTickets", purchasedTickets);
		data.put("reservedTickets", reservedTickets);
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
		
		try {
			String action = request.getParameter("action");
				
			switch(action) {
				case "booking" : {
					String id = request.getParameter("flightId");
					int Id = Integer.parseInt(id);
					Flight departureFlight = FlightsDAO.get(Id);
					
					String idRoundtrip = request.getParameter("roundtripId");
					int IdRoundtrip = Integer.parseInt(idRoundtrip);
					Flight roundtripFlight = FlightsDAO.get(IdRoundtrip);
					roundtripFlight = (roundtripFlight != null ? roundtripFlight : null);
					String departureSeatString = request.getParameter("departureSeat");
					departureSeatString = (!"".equals(departureSeatString) ? departureSeatString : "0");
					int departureSeat = Integer.parseInt(departureSeatString);
					
					String roundtripSeatString = request.getParameter("roundtripSeat");
					roundtripSeatString = (!"".equals(roundtripSeatString) ? roundtripSeatString : "0");
					int roundtripSeat = Integer.parseInt(roundtripSeatString);
					roundtripSeat = (roundtripSeat > 0 ? roundtripSeat : 0);
					
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					String dateString = LocalDateTime.now().format(formatter).toString();
					Timestamp date = null;
					try {
						date = new Timestamp(FlightsDAO.sdf.parse(dateString).getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					User username = UserDAO.getUserId(loggedInUser.getId());
					
					String firstName = request.getParameter("firstName");
					firstName = (!"".equals(firstName) ? firstName : "no name");
					String surname = request.getParameter("surname");
					surname = (!"".equals(surname) ? surname : "no name");
					
					Ticket ticket = new Ticket(departureFlight, roundtripFlight, departureSeat, roundtripSeat, date, null, username, firstName, surname);
					TicketDAO.addTicket(ticket);

					break;
				}
				case "purchase": {
					String idd = request.getParameter("flightId");
					int Idd = Integer.parseInt(idd);
					Flight departureFlightt = FlightsDAO.get(Idd);
					
					String idRoundtripp = request.getParameter("roundtripId");
					int IdRoundtripp = Integer.parseInt(idRoundtripp);
					Flight roundtripFlightt = FlightsDAO.get(IdRoundtripp);
					roundtripFlightt = (roundtripFlightt != null ? roundtripFlightt : null);
					String departureSeatStringg = request.getParameter("departureSeat");
					int departureSeatt = Integer.parseInt(departureSeatStringg);
					
					String roundtripSeatStringg = request.getParameter("roundtripSeat");
					roundtripSeatStringg = (!"".equals(roundtripSeatStringg) ? roundtripSeatStringg : "0");
					int roundtripSeatt = Integer.parseInt(roundtripSeatStringg);
					roundtripSeatt = (roundtripSeatt > 0 ? roundtripSeatt : 0);
					
					DateTimeFormatter formatterr = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
					String dateStringg = LocalDateTime.now().format(formatterr).toString();
					Timestamp datee = null;
					try {
						datee = new Timestamp(FlightsDAO.sdf.parse(dateStringg).getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					User usernamee = UserDAO.getUserId(loggedInUser.getId());
					
					String firstNamee = request.getParameter("firstName");
					firstNamee = (!"".equals(firstNamee) ? firstNamee : "no name");
					String surnamee = request.getParameter("surname");
					surnamee = (!"".equals(surnamee) ? surnamee : "no surname");
					
					Ticket ticketPurchase = new Ticket(departureFlightt, roundtripFlightt, departureSeatt, roundtripSeatt, null, datee, usernamee, firstNamee, surnamee);
					TicketDAO.addTicket(ticketPurchase);
					break;
				}
				case "buyReservation" : {
					int id = Integer.parseInt(request.getParameter("id"));
					Ticket ticket = TicketDAO.getTicket(id);
					
					
					Timestamp date = null;
					String datetime = request.getParameter("datetime");
					try {
						date = new Timestamp(FlightsDAO.sdf.parse(datetime).getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					

					TicketDAO.buyReservation(ticket, date);
					break;
					
				}
				case "edit" : {
					int id = Integer.parseInt(request.getParameter("id"));
					Ticket ticket = TicketDAO.getTicket(id);
					
					String departureSeatString = request.getParameter("departureSeat");
					int departureSeat = Integer.parseInt(departureSeatString);
					departureSeat = (departureSeat > 0 ? departureSeat : ticket.getDepartureSeat());
					
					String roundtripSeatString = request.getParameter("roundtripSeat");
					roundtripSeatString = (!"".equals(roundtripSeatString) ? roundtripSeatString : "0");
					int roundtripSeat = Integer.parseInt(roundtripSeatString);
					roundtripSeat = (roundtripSeat > 0 ? roundtripSeat : ticket.getRoundtripSeat());
					
					String firstName = request.getParameter("firstName");
					firstName = (!"".equals(firstName) ? firstName : ticket.getName());
					String surname = request.getParameter("surname");
					surname = (!"".equals(surname) ? surname : ticket.getSurname());
					
					ticket.setDepartureSeat(departureSeat);
					ticket.setRoundtripSeat(roundtripSeat);
					ticket.setName(firstName);
					ticket.setSurname(surname);
					TicketDAO.updateTickets(ticket);
					break;
				}
				case "delete" : {
					int id = Integer.parseInt(request.getParameter("id"));
					TicketDAO.deleteReservation(id);
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
