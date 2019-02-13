package model;

public class Report {

	private String airportName;
	private int countFlights;
	private int countTickets;
	private double sumPrice;
	
	
	public Report(String airportName, int countFlights, int countTickets, double sumPrice) {
		super();
		this.airportName = airportName;
		this.countFlights = countFlights;
		this.countTickets = countTickets;
		this.sumPrice = sumPrice;
	}


	public String getAirportName() {
		return airportName;
	}


	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}


	public int getCountFlights() {
		return countFlights;
	}


	public void setCountFlights(int countFlights) {
		this.countFlights = countFlights;
	}


	public int getCountTickets() {
		return countTickets;
	}


	public void setCountTickets(int countTickets) {
		this.countTickets = countTickets;
	}


	public double getSumPrice() {
		return sumPrice;
	}


	public void setSumPrice(double sumPrice) {
		this.sumPrice = sumPrice;
	}
	
	
	
}
