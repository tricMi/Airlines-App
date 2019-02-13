package model;

import java.sql.Timestamp;

public class Flight {
	
	private int id;
	private String flightNum;
	private Timestamp departureTime;
	private Timestamp arrivalTime;
	private Airport departures;
	private Airport arrivals;
	private int seatNum;
	private double ticketPrice;
	private int active;
	
	public Flight(int id, String flightNum, Timestamp departureTime, Timestamp arrivalTime, Airport departures,
			Airport arrivals, int seatNum, double ticketPrice) {
		this.id = id;
		this.flightNum = flightNum;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.departures = departures;
		this.arrivals = arrivals;
		this.seatNum = seatNum;
		this.ticketPrice = ticketPrice;
	}
	
	

	public Flight(String flightNum, Timestamp departureTime, Timestamp arrivalTime, Airport departures,
			Airport arrivals, int seatNum, double ticketPrice) {
	
		this.flightNum = flightNum;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.departures = departures;
		this.arrivals = arrivals;
		this.seatNum = seatNum;
		this.ticketPrice = ticketPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public Timestamp getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Timestamp departureTime) {
		this.departureTime = departureTime;
	}

	public Timestamp getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Timestamp arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Airport getDepartures() {
		return departures;
	}

	public void setDepartures(Airport departures) {
		this.departures = departures;
	}

	public Airport getArrivals() {
		return arrivals;
	}

	public void setArrivals(Airport arrivals) {
		this.arrivals = arrivals;
	}

	public int getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(int seatNum) {
		this.seatNum = seatNum;
	}

	public double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}



	@Override
	public String toString() {
		return "Flight [id=" + id + ", flightNum=" + flightNum + ", departureTime=" + departureTime + ", arrivalTime="
				+ arrivalTime + ", departures=" + departures + ", arrivals=" + arrivals + ", seatNum=" + seatNum
				+ ", ticketPrice=" + ticketPrice + ", active=" + active + "]";
	}
	
	
	
}