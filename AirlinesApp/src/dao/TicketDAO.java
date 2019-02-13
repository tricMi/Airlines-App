package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import model.Flight;
import model.Ticket;
import model.User;


public class TicketDAO {

	public static List<Ticket> getUserReservations(User username) {
		List<Ticket> tickets = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, departureFlight, roundtripFlight, departureSeat , roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname FROM tickets "
					+ "WHERE username LIKE ? AND purchaseDateTime IS NULL ORDER BY purchaseDateTime DESC";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, username.getId());
			System.out.println(pstmt);
		
			
			
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
				index = 1;
				
				int ticketId = rset.getInt(index++);
				Flight ticketDepartureFlight = FlightsDAO.get(rset.getInt(index++));
				Flight ticketRoundtripFlight = FlightsDAO.get(rset.getInt(index++));
				int ticketDepartureSeat = rset.getInt(index++);
				int ticketRoundtripSeat = rset.getInt(index++);
				Timestamp ticketReservationDateTime = rset.getTimestamp(index++);
				Timestamp ticketPurchaseDateTime = rset.getTimestamp(index++);
				User ticketUsername = UserDAO.getUserId(rset.getInt(index++));
				String ticketName = rset.getString(index++);
				String ticketSurname = rset.getString(index++);
				
				Ticket ticket = new Ticket(ticketId, ticketDepartureFlight, ticketRoundtripFlight, ticketDepartureSeat
						,ticketRoundtripSeat, ticketReservationDateTime, ticketPurchaseDateTime, ticketUsername, ticketName, ticketSurname);
				
				tickets.add(ticket);
				
				System.out.println(tickets);
				
			}
		}catch(Exception ex) {
				ex.printStackTrace();
		}finally {
				
			try {
				pstmt.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				rset.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
		}
		
		return tickets;
	}
	
	public static List<Ticket> getUserPurchases(User username) {
		List<Ticket> tickets = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, departureFlight, roundtripFlight, departureSeat , roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname FROM tickets "
					+ "WHERE username LIKE ? AND reservationDateTime IS NULL ORDER BY purchaseDateTime DESC";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, username.getId());
			System.out.println(pstmt);
		
			
			
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
				index = 1;
				
				int ticketId = rset.getInt(index++);
				Flight ticketDepartureFlight = FlightsDAO.get(rset.getInt(index++));
				Flight ticketRoundtripFlight = FlightsDAO.get(rset.getInt(index++));
				int ticketDepartureSeat = rset.getInt(index++);
				int ticketRoundtripSeat = rset.getInt(index++);
				Timestamp ticketReservationDateTime = rset.getTimestamp(index++);
				Timestamp ticketPurchaseDateTime = rset.getTimestamp(index++);
				User ticketUsername = UserDAO.getUserId(rset.getInt(index++));
				String ticketName = rset.getString(index++);
				String ticketSurname = rset.getString(index++);
				
				Ticket ticket = new Ticket(ticketId, ticketDepartureFlight, ticketRoundtripFlight, ticketDepartureSeat
						,ticketRoundtripSeat, ticketReservationDateTime, ticketPurchaseDateTime, ticketUsername, ticketName, ticketSurname);
				
				tickets.add(ticket);
				
			}
		}catch(Exception ex) {
				ex.printStackTrace();
		}finally {
				
			try {
				pstmt.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				rset.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
		}
		
		return tickets;
	}
	
	public static List<Ticket> getFlightsReservations(Flight flight, Flight roundtrip) {
		List<Ticket> tickets = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, departureFlight, roundtripFlight, departureSeat , roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname FROM tickets "
					+ "WHERE departureFlight LIKE ? AND purchaseDateTime IS NULL OR roundtripFlight LIKE ? AND reservationDateTime IS NULL";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, flight.getId());
			pstmt.setInt(index++, roundtrip.getId());
			System.out.println(pstmt);
		
			
			
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
				index = 1;
				
				int ticketId = rset.getInt(index++);
				Flight ticketDepartureFlight = FlightsDAO.get(rset.getInt(index++));
				Flight ticketRoundtripFlight = FlightsDAO.get(rset.getInt(index++));
				int ticketDepartureSeat = rset.getInt(index++);
				int ticketRoundtripSeat = rset.getInt(index++);
				Timestamp ticketReservationDateTime = rset.getTimestamp(index++);
				Timestamp ticketPurchaseDateTime = rset.getTimestamp(index++);
				User ticketUsername = UserDAO.getUserId(rset.getInt(index++));
				String ticketName = rset.getString(index++);
				String ticketSurname = rset.getString(index++);
				
				Ticket ticket = new Ticket(ticketId, ticketDepartureFlight, ticketRoundtripFlight, ticketDepartureSeat
						,ticketRoundtripSeat, ticketReservationDateTime, ticketPurchaseDateTime, ticketUsername, ticketName, ticketSurname);
				
				tickets.add(ticket);
				
			}
		}catch(Exception ex) {
				ex.printStackTrace();
		}finally {
				
			try {
				pstmt.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				rset.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
		}
		
		return tickets;
	}
	
	public static List<Ticket> getFlightsPurchases(Flight flight, Flight roundtrip) {
		List<Ticket> tickets = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, departureFlight, roundtripFlight, departureSeat , roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname FROM tickets "
					+ "WHERE departureFlight LIKE ? AND reservationDateTime IS NULL OR roundtripFlight LIKE ? AND reservationDateTime IS NULL";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, flight.getId());
			pstmt.setInt(index++, roundtrip.getId());
			System.out.println(pstmt);
		
			
			
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
				index = 1;
				
				int ticketId = rset.getInt(index++);
				Flight ticketDepartureFlight = FlightsDAO.get(rset.getInt(index++));
				Flight ticketRoundtripFlight = FlightsDAO.get(rset.getInt(index++));
				int ticketDepartureSeat = rset.getInt(index++);
				int ticketRoundtripSeat = rset.getInt(index++);
				Timestamp ticketReservationDateTime = rset.getTimestamp(index++);
				Timestamp ticketPurchaseDateTime = rset.getTimestamp(index++);
				User ticketUsername = UserDAO.getUserId(rset.getInt(index++));
				String ticketName = rset.getString(index++);
				String ticketSurname = rset.getString(index++);
				
				Ticket ticket = new Ticket(ticketId, ticketDepartureFlight, ticketRoundtripFlight, ticketDepartureSeat
						,ticketRoundtripSeat, ticketReservationDateTime, ticketPurchaseDateTime, ticketUsername, ticketName, ticketSurname);
				
				tickets.add(ticket);
				
			}
		}catch(Exception ex) {
				ex.printStackTrace();
		}finally {
				
			try {
				pstmt.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				rset.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
		}
		
		return tickets;
	}
	
	public static List<Ticket> getReservations() {
		List<Ticket> tickets = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, departureFlight, roundtripFlight, departureSeat , roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname FROM tickets "
					+ "WHERE purchaseDateTime IS NULL";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			System.out.println(pstmt);
		
			
			
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
				index = 1;
				
				int ticketId = rset.getInt(index++);
				Flight ticketDepartureFlight = FlightsDAO.get(rset.getInt(index++));
				Flight ticketRoundtripFlight = FlightsDAO.get(rset.getInt(index++));
				int ticketDepartureSeat = rset.getInt(index++);
				int ticketRoundtripSeat = rset.getInt(index++);
				Timestamp ticketReservationDateTime = rset.getTimestamp(index++);
				Timestamp ticketPurchaseDateTime = rset.getTimestamp(index++);
				User ticketUsername = UserDAO.getUserId(rset.getInt(index++));
				String ticketName = rset.getString(index++);
				String ticketSurname = rset.getString(index++);
				
				Ticket ticket = new Ticket(ticketId, ticketDepartureFlight, ticketRoundtripFlight, ticketDepartureSeat
						,ticketRoundtripSeat, ticketReservationDateTime, ticketPurchaseDateTime, ticketUsername, ticketName, ticketSurname);
				
				tickets.add(ticket);
				
				System.out.println(tickets);
				
			}
		}catch(Exception ex) {
				ex.printStackTrace();
		}finally {
				
			try {
				pstmt.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				rset.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
		}
		
		return tickets;
	}
	
	public static List<Ticket> getPurchases() {
		List<Ticket> tickets = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, departureFlight, roundtripFlight, departureSeat , roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname FROM tickets "
					+ "WHERE purchaseDateTime IS NOT NULL";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			System.out.println(pstmt);		
			
			rset = pstmt.executeQuery();
			
			while(rset.next())
			{
				index = 1;
				
				int ticketId = rset.getInt(index++);
				Flight ticketDepartureFlight = FlightsDAO.get(rset.getInt(index++));
				Flight ticketRoundtripFlight = FlightsDAO.get(rset.getInt(index++));
				int ticketDepartureSeat = rset.getInt(index++);
				int ticketRoundtripSeat = rset.getInt(index++);
				Timestamp ticketReservationDateTime = rset.getTimestamp(index++);
				Timestamp ticketPurchaseDateTime = rset.getTimestamp(index++);
				User ticketUsername = UserDAO.getUserId(rset.getInt(index++));
				String ticketName = rset.getString(index++);
				String ticketSurname = rset.getString(index++);
				
				Ticket ticket = new Ticket(ticketId, ticketDepartureFlight, ticketRoundtripFlight, ticketDepartureSeat
						,ticketRoundtripSeat, ticketReservationDateTime, ticketPurchaseDateTime, ticketUsername, ticketName, ticketSurname);
				
				tickets.add(ticket);
				
				System.out.println(tickets);
				
			}
		}catch(Exception ex) {
				ex.printStackTrace();
		}finally {
				
			try {
				pstmt.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				rset.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
		}
		
		return tickets;
	}
	
	public static Ticket getTicket(int id) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname FROM tickets "
					+ "WHERE id = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
		
				Flight departureFlight = FlightsDAO.get(rset.getInt(index++));
				Flight roundtripFlight = FlightsDAO.get(rset.getInt(index++));
				if(roundtripFlight == null) {
					roundtripFlight = FlightsDAO.get(0);
				}else {
					roundtripFlight = FlightsDAO.get(rset.getInt(index++));
				}
				int departureSeat = rset.getInt(index++);
				int roundtripSeat = rset.getInt(index++);
				Timestamp reservationDateTime = rset.getTimestamp(index++);
				Timestamp purchaseDateTime = rset.getTimestamp(index++);
				User username = UserDAO.getUserId(rset.getInt(index++));
				String firstName = rset.getString(index++);
				String surname = rset.getString(index++);
				
				return new Ticket(id, departureFlight, roundtripFlight, departureSeat, roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname);
				
			}
		}catch(Exception ex) {
				ex.printStackTrace();
		}finally {
				
			try {
				pstmt.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				rset.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
		}
		
		return null;
	}
	
	public static boolean addTicket(Ticket ticket) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO tickets (departureFlight, roundtripFlight, departureSeat , roundtripSeat, reservationDateTime, purchaseDateTime, username, firstName, surname) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;

			pstmt.setInt(index++, ticket.getDepartureFlight().getId());
			if(ticket.getRoundtripFlight() != null) {
				pstmt.setInt(index++, ticket.getRoundtripFlight().getId());
			}else {
				pstmt.setNull(index++, Types.NULL);
			}
			pstmt.setInt(index++, ticket.getDepartureSeat());
			if(ticket.getRoundtripSeat() != 0) {
				pstmt.setInt(index++, ticket.getRoundtripSeat());
				
			}else {
				pstmt.setNull(index++, Types.NULL);
			}
			pstmt.setTimestamp(index++, ticket.getReservationDateTime());;
			pstmt.setTimestamp(index++, ticket.getPurchaseDateTime());
			pstmt.setInt(index++, ticket.getUsername().getId());;
			pstmt.setString(index++, ticket.getName());
			pstmt.setString(index++, ticket.getSurname());
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally 
		{
			try {
				pstmt.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean doesUserHavePurchases(int id) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM tickets WHERE purchaseDateTime IS NOT NULL AND username = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				
				int index = 1;
				
				int ifPurchaseExists = rset.getInt(index++);
				
				if(ifPurchaseExists != 0) {
					return true;
				}
				
				return false;
			}

			
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally 
		{
			try {
				pstmt.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				rset.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean doesFlightHavePurchases(int id, int idd) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT COUNT(*) FROM tickets WHERE purchaseDateTime IS NOT NULL AND departureFlight = ? OR roundtripFlight = ?";
			
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setInt(index++, id);
			pstmt.setInt(index++, idd);
			System.out.println(pstmt);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				index = 1;
				
				int ifPurchaseExists = rset.getInt(index++);
				
				if(ifPurchaseExists != 0) {
					return true;
				}
				
				return false;
			}

			
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally 
		{
			try {
				pstmt.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				rset.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		return false;
	}
	
	public static List<Integer> takenSeats(int id, int idd){
		List<Integer> seats = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			String query = "SELECT t.departureSeat FROM tickets t WHERE t.departureFlight = ? UNION ALL " + 
					"SELECT t1.roundtripSeat FROM tickets t1 WHERE t1.roundtripFlight = ?";
			
			pstmt = conn.prepareStatement(query);
			
			int index = 1;
			pstmt.setInt(index++, id);
			pstmt.setInt(index++, idd);
			System.out.println(pstmt);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				index = 1;
				int seat = rset.getInt(index++);
				seats.add(seat);
				
				
			}

			
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally 
		{
			try {
				pstmt.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				rset.close();
			}catch (Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try {
				conn.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		
		return seats;
	}
	
	public static boolean buyReservation(Ticket ticket, Timestamp date) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String query = "UPDATE tickets SET reservationDateTime = NULL, purchaseDateTime = ? WHERE id = ?";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setTimestamp(index++, date);
			pstmt.setInt(index++, ticket.getId());
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally 
		{
			try {
				pstmt.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			
			try {
				conn.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		
		return false;
		
	}
	
	public static boolean updateTickets(Ticket ticket) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		
		try {
			String query = "UPDATE tickets SET departureSeat = ?, roundtripSeat = ?, firstName = ?, surname = ? WHERE id = ?";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, ticket.getDepartureSeat());
			if(ticket.getRoundtripSeat() != 0) {
				pstmt.setInt(index++, ticket.getRoundtripSeat());
				
			}else {
				pstmt.setNull(index++, Types.NULL);
			}
			pstmt.setString(index++, ticket.getName());
			pstmt.setString(index++, ticket.getSurname());
			pstmt.setInt(index++, ticket.getId());
			
			return pstmt.executeUpdate() == 1;
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally 
		{
			try {
				pstmt.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			
			try {
				conn.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		
		return false;
	}
	
	public static boolean deleteReservation(int id) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "DELETE FROM tickets WHERE id = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			
			
			return pstmt.executeUpdate() == 1;
			
		}catch (Exception ex) {
			ex.printStackTrace();
		}finally 
		{
			try {
				pstmt.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			
			try {
				conn.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		return false;
	}
	
	
}
