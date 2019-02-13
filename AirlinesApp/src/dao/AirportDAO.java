package dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Airport;
import model.Report;


public class AirportDAO {
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	public static List<Airport> getAirport() {
		ArrayList<Airport> airport = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id, airportName FROM airports";
			
			pstmt = conn.prepareStatement(query);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				int index = 1;
				int idA = rset.getInt(index++);
				String airportNameA = rset.getString(index++);
				
				Airport a = new Airport(idA, airportNameA);
				airport.add(a);
				System.out.println(airport);
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
		return airport;
	}
	
	public static Airport get(int id) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT airportName FROM airports WHERE id = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
				String airportName = rset.getString(index++);
				
				return new Airport(id,airportName);
				
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

	public static Airport getAirportName(String name) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT id FROM airports WHERE airportName = ?";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, name);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				int index = 1;
				int id = rset.getInt(index++);
				
				return new Airport(id,name);
				
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

	public static List<Report> getReports(Timestamp minDate, Timestamp maxDate){
		ArrayList<Report> report = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			int index = 1;
			String query = "SELECT airportName, " + 
					"(SELECT COUNT(DISTINCT f.id) FROM flights f WHERE a.id = f.arrivals " + 
					"AND f.departureTime >= ? AND f.departureTime <= ?) AS A, " + 
					"(SELECT COUNT(DISTINCT t.id) FROM flights f1 LEFT OUTER JOIN tickets t " + 
					"ON f1.id = t.departureFlight " + 
					"AND t.purchaseDateTime >= ? AND t.purchaseDateTime <= ? " + 
					"AND t.purchaseDateTime IS NOT NULL " + 
					"OR f1.id = t.roundtripFlight " + 
					"AND t.purchaseDateTime >= ? AND t.purchaseDateTime <= ? " + 
					"AND t.purchaseDateTime IS NOT NULL WHERE a.id = f1.arrivals " + 
					"AND f1.departureTime >= ? AND f1.departureTime <= ?) AS B, " + 
					"(SELECT SUM(f2.ticketPrice) FROM flights f2 LEFT OUTER JOIN tickets t1 ON f2.id = t1.departureFlight " + 
					"OR f2.id = t1.roundtripFlight WHERE t1.purchaseDateTime IS NOT NULL " + 
					"AND a.id = f2.arrivals " + 
					"AND f2.departureTime >= ? AND f2.departureTime <= ?) AS C " + 
					"FROM airports a " + 
					"GROUP BY airportName";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setTimestamp(index++, minDate);
			pstmt.setTimestamp(index++, maxDate);
			pstmt.setTimestamp(index++, minDate);
			pstmt.setTimestamp(index++, maxDate);
			pstmt.setTimestamp(index++, minDate);
			pstmt.setTimestamp(index++, maxDate);
			pstmt.setTimestamp(index++, minDate);
			pstmt.setTimestamp(index++, maxDate);
			pstmt.setTimestamp(index++, minDate);
			pstmt.setTimestamp(index++, maxDate);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				index = 1;
				String airportNameA = rset.getString(index++);
				int countFlightsF = rset.getInt(index++);
				int countTicketsT = rset.getInt(index++);
				double sumPriceP = rset.getDouble(index++);
				
				Report r = new Report(airportNameA, countFlightsF, countTicketsT, sumPriceP);
				report.add(r);
			
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
		return report;
	}
	
	

}
