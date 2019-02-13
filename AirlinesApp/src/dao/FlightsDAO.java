package dao;

import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Airport;
import model.Flight;



public class FlightsDAO {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static SimpleDateFormat sd = new SimpleDateFormat("dd.MM.yyyy.");
	public static SimpleDateFormat st = new SimpleDateFormat("HH:mm");
	
	public static Flight get(int id) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice FROM flights "
					+ "WHERE id = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
				String flightNum = rset.getString(index++);
				Timestamp departureTime = rset.getTimestamp(index++);
				Timestamp arrivalTime = rset.getTimestamp(index++);
				Airport departures = AirportDAO.get(rset.getInt(index++));
				Airport arrivals = AirportDAO.get(rset.getInt(index++));
				int seatNum = rset.getInt(index++);
				Double ticketPrice = rset.getDouble(index++);
				
				return new Flight(id, flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice);
				
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
	
	public static List<Flight> getFlights(String flightNum, double minTicketPrice, double maxTicketPrice, Timestamp departureTime, Timestamp arrivalTime, Airport departures, Airport arrivals) {
		List<Flight> flights = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		
		
		try {
			if(departures != null && arrivals != null) {
				String query = "SELECT id, flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice FROM flights "
						+ "WHERE flightNum LIKE ? AND ticketPrice >= ? AND ticketPrice <= ? AND departureTime >= ? AND arrivalTime <= ? AND departures LIKE ? AND arrivals LIKE ? AND active = 0 ";
				
				pstmt = conn.prepareStatement(query);
				int index = 1;
				pstmt.setString(index++, "%" + flightNum + "%");
				pstmt.setDouble(index++, minTicketPrice);
				pstmt.setDouble(index++, maxTicketPrice);
				pstmt.setTimestamp(index++, departureTime);
				pstmt.setTimestamp(index++, arrivalTime);
				pstmt.setInt(index++, departures.getId());
				pstmt.setInt(index++, arrivals.getId());
				System.out.println(pstmt);
			
				
				
				rset = pstmt.executeQuery();
				
				while(rset.next())
				{
					index = 1;
					
					int flightId = rset.getInt(index++);
					String flightFlightNum = rset.getString(index++);
					Timestamp flightDepartureTime = rset.getTimestamp(index++);
					System.out.println(flightDepartureTime);
					Timestamp flightArrivalTime = rset.getTimestamp(index++);
					Airport flightDepartures = AirportDAO.get(rset.getInt(index++));
					System.out.println(flightDepartures);
					Airport flightArrivals = AirportDAO.get(rset.getInt(index++));
					int flightSeatNum = rset.getInt(index++);
					Double flightTicketPrice = rset.getDouble(index++);
					
					Flight flight = new Flight(flightId, flightFlightNum, flightDepartureTime, flightArrivalTime,
									flightDepartures, flightArrivals, flightSeatNum, flightTicketPrice);
					
					flights.add(flight);
					
				}
			}else {
				String query = "SELECT id, flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice FROM flights " +
						 "WHERE flightNum LIKE ? AND ticketPrice >= ? AND ticketPrice <= ? AND departureTime >= ? AND arrivalTime <= ? AND active = 0 ";
			
			
				pstmt = conn.prepareStatement(query);
				int index = 1;
				pstmt.setString(index++, "%" + flightNum + "%");
				pstmt.setDouble(index++, minTicketPrice);
				pstmt.setDouble(index++, maxTicketPrice);
				pstmt.setTimestamp(index++, departureTime);
				pstmt.setTimestamp(index++, arrivalTime);
				System.out.println(pstmt);
			
				
				
				rset = pstmt.executeQuery();
				
				while(rset.next())
				{
					index = 1;
					
					int flightId = rset.getInt(index++);
					String flightFlightNum = rset.getString(index++);
					Timestamp flightDepartureTime = rset.getTimestamp(index++);
					System.out.println(flightDepartureTime);
					Timestamp flightArrivalTime = rset.getTimestamp(index++);
					Airport flightDepartures = AirportDAO.get(rset.getInt(index++));
					System.out.println(flightDepartures);
					Airport flightArrivals = AirportDAO.get(rset.getInt(index++));
					int flightSeatNum = rset.getInt(index++);
					Double flightTicketPrice = rset.getDouble(index++);
					
					Flight flight = new Flight(flightId, flightFlightNum, flightDepartureTime, flightArrivalTime,
									flightDepartures, flightArrivals, flightSeatNum, flightTicketPrice);
					
					flights.add(flight);
					
				}
			}
				
			
		}catch(Exception ex)
		{
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
		
		return flights;
	}
	
	public static boolean addFlight(Flight flight) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO flights (flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice) "
							+ "VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;

			pstmt.setString(index++, flight.getFlightNum());
			pstmt.setTimestamp(index++, flight.getDepartureTime());
			pstmt.setTimestamp(index++, flight.getArrivalTime());
			pstmt.setInt(index++, flight.getDepartures().getId());
			pstmt.setInt(index++, flight.getArrivals().getId());;
			pstmt.setInt(index++, flight.getSeatNum());
			pstmt.setDouble(index++, flight.getTicketPrice());;
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
	
	public static boolean updateFlight(Flight flight) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			String query = "UPDATE flights SET arrivalTime = ?, departures = ?, arrivals = ?, seatNum = ?, ticketPrice = ? WHERE id = ?";

			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setTimestamp(index++, flight.getArrivalTime());
			pstmt.setInt(index++, flight.getDepartures().getId());
			pstmt.setInt(index++, flight.getArrivals().getId());
			pstmt.setInt(index++, flight.getSeatNum());
			pstmt.setDouble(index++, flight.getTicketPrice());
			pstmt.setInt(index++, flight.getId());
			
			
			
			System.out.println(pstmt);

			return pstmt.executeUpdate() == 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
		
		return false;
	}
	
	public static boolean deleteFlight(int id) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			
			String query = "DELETE flights, tickets FROM flights LEFT JOIN tickets ON flights.id = tickets.departureFlight OR flights.id = tickets.roundtripFlight WHERE tickets.purchaseDateTime IS NULL and flights.id = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			
			return pstmt.executeUpdate() == 1;
		}catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
		
		return false;
	}
	
	public static boolean deleteFlightLogical(int id) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null;
		try {
			conn.setAutoCommit(false);
			conn.commit();
			String query = "DELETE FROM tickets WHERE purchaseDateTime IS NULL AND departureFlight = ? OR roundtripFlight = ? ";
			
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, id);
			pstmt.setInt(index++, id);
			System.out.println(pstmt);
			
			pstmt.executeUpdate();
			pstmt.close();
			
			query = "UPDATE flights SET active = 1 WHERE id = ? ";
			
			pstmt = conn.prepareStatement(query);
			index = 1;
			pstmt.setInt(index++, id);
			System.out.println(pstmt);
			
			pstmt.executeUpdate();
			
			conn.commit();
			return pstmt.executeUpdate() == 1;
				
				
			
		}catch (Exception ex) {
			try {
				conn.rollback();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		} finally {
			try {
				conn.setAutoCommit(true);
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				pstmt.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
			try {
				conn.close();
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
		}
		
		return false;
	}
	
	public static List<Flight> getFlightByCurrentDate(Timestamp departureTime) {
		List<Flight> flights = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice FROM flights "
					+ "WHERE departureTime >= ?";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setTimestamp(index++, departureTime);
			System.out.println("Current date statement" + pstmt);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				index = 1;
				
				int flightId = rset.getInt(index++);
				String flightFlightNum = rset.getString(index++);
				Timestamp flightDepartureTime = rset.getTimestamp(index++);
				System.out.println(flightDepartureTime);
				Timestamp flightArrivalTime = rset.getTimestamp(index++);
				Airport flightDepartures = AirportDAO.get(rset.getInt(index++));
				System.out.println(flightDepartures);
				Airport flightArrivals = AirportDAO.get(rset.getInt(index++));
				int flightSeatNum = rset.getInt(index++);
				Double flightTicketPrice = rset.getDouble(index++);
				
				Flight flight = new Flight(flightId, flightFlightNum, flightDepartureTime, flightArrivalTime,
								flightDepartures, flightArrivals, flightSeatNum, flightTicketPrice);
				
				flights.add(flight);
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
		
		return flights;
	}
	
	public static List<Flight> getRoundtripFlight(Airport departure, Airport arrival, Timestamp arrivalDateTime) {
		List<Flight> flights = new ArrayList<>();
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice FROM flights "
					+ "WHERE departures = ? AND arrivals = ? AND  departureTime > ?";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, departure.getId());
			pstmt.setInt(index++, arrival.getId());
			pstmt.setTimestamp(index++, arrivalDateTime);
			System.out.println("Current date statement" + pstmt);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				
				index = 1;
				
				int flightId = rset.getInt(index++);
				String flightFlightNum = rset.getString(index++);
				Timestamp flightDepartureTime = rset.getTimestamp(index++);
				System.out.println(flightDepartureTime);
				Timestamp flightArrivalTime = rset.getTimestamp(index++);
				Airport flightDepartures = AirportDAO.get(rset.getInt(index++));
				System.out.println(flightDepartures);
				Airport flightArrivals = AirportDAO.get(rset.getInt(index++));
				int flightSeatNum = rset.getInt(index++);
				Double flightTicketPrice = rset.getDouble(index++);
				
				Flight flight = new Flight(flightId, flightFlightNum, flightDepartureTime, flightArrivalTime,
								flightDepartures, flightArrivals, flightSeatNum, flightTicketPrice);
				
				
				flights.add(flight);
				System.out.println(flight);
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
		
		return flights;
	}
	
	public static int getNumberOfAvailableSeats(int id, int idR, int idD) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			
			String query = "SELECT " + 
					"(SELECT f.seatNum FROM flights f WHERE id = ?) - " + 
					"((SELECT COUNT(t.roundtripSeat) FROM tickets t WHERE  t.roundtripFlight = ?) + " + 
					"(SELECT COUNT(t1.departureSeat) FROM tickets t1 WHERE t1.departureFlight = ?)) ";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, id);
			pstmt.setInt(index++, idR);
			pstmt.setInt(index++, idD);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				return rset.getInt(1);
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
		return -1;
	}
	
	public static List<Flight> getAllFlights() {
		List<Flight> flights = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
			
		try {
				String query = "SELECT id, flightNum, departureTime, arrivalTime, departures, arrivals, seatNum, ticketPrice FROM flights WHERE active = 0 ORDER BY flightNum ASC";
									
				pstmt = conn.prepareStatement(query);
				int index = 1;
				System.out.println(pstmt);		
				
				rset = pstmt.executeQuery();
				
				while(rset.next())
				{
					index = 1;
					
					int flightId = rset.getInt(index++);
					String flightFlightNum = rset.getString(index++);
					Timestamp flightDepartureTime = rset.getTimestamp(index++);
					System.out.println(flightDepartureTime);
					Timestamp flightArrivalTime = rset.getTimestamp(index++);
					Airport flightDepartures = AirportDAO.get(rset.getInt(index++));
					System.out.println(flightDepartures);
					Airport flightArrivals = AirportDAO.get(rset.getInt(index++));
					int flightSeatNum = rset.getInt(index++);
					Double flightTicketPrice = rset.getDouble(index++);
					
					Flight flight = new Flight(flightId, flightFlightNum, flightDepartureTime, flightArrivalTime,
									flightDepartures, flightArrivals, flightSeatNum, flightTicketPrice);
					
					flights.add(flight);
					
				}
			
				
			
		}catch(Exception ex)
		{
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
		
		return flights;
	}

}
