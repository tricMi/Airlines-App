package model;

public class Airport {
	
	private int id;
	private String airportName;
	

	
	

	public Airport(int id, String airportName) {
		
		this.id = id;
		this.airportName = airportName;
	}
	


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getAirportName() {
		return airportName;
	}


	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}
	



	@Override
	public String toString() {
		return airportName;
	}



}
