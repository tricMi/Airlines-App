package model;

import java.sql.Timestamp;

public class Ticket {
	
	private int id;
	private Flight departureFlight;
	private Flight roundtripFlight;
	private int departureSeat;
	private int roundtripSeat;
	private Timestamp reservationDateTime;
	private Timestamp purchaseDateTime;
	private User username;
	private String firstName;
	private String surname;
	
	
	public Ticket(int id, Flight departureFlight, Flight roundtripFlight, int departureSeat, int roundtripSeat,
			Timestamp reservationDateTime, Timestamp purchaseDateTime, User username, String firstName, String surname) {
		
		this.id = id;
		this.departureFlight = departureFlight;
		this.roundtripFlight = roundtripFlight;
		this.departureSeat = departureSeat;
		this.roundtripSeat = roundtripSeat;
		this.reservationDateTime = reservationDateTime;
		this.purchaseDateTime = purchaseDateTime;
		this.username = username;
		this.firstName = firstName;
		this.surname = surname;
	}
	
	public Ticket(Flight departureFlight, Flight roundtripFlight, int departureSeat, int roundtripSeat,
			Timestamp reservationDateTime, Timestamp purchaseDateTime, User username, String firstName, String surname) {
		
	
		this.departureFlight = departureFlight;
		this.roundtripFlight = roundtripFlight;
		this.departureSeat = departureSeat;
		this.roundtripSeat = roundtripSeat;
		this.reservationDateTime = reservationDateTime;
		this.purchaseDateTime = purchaseDateTime;
		this.username = username;
		this.firstName = firstName;
		this.surname = surname;
	}
	
	public Ticket(Timestamp reservationDateTime, int departureSeat, int roundtripSeat,User username) {
		this.reservationDateTime = reservationDateTime;
		this.departureSeat = departureSeat;
		this.roundtripSeat = roundtripSeat;
		this.username = username;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Flight getDepartureFlight() {
		return departureFlight;
	}


	public void setDepartureFlight(Flight departureFlight) {
		this.departureFlight = departureFlight;
	}


	public Flight getRoundtripFlight() {
		return roundtripFlight;
	}


	public void setRoundtripFlight(Flight roundtripFlight) {
		this.roundtripFlight = roundtripFlight;
	}


	public int getDepartureSeat() {
		return departureSeat;
	}


	public void setDepartureSeat(int departureSeat) {
		this.departureSeat = departureSeat;
	}


	public int getRoundtripSeat() {
		return roundtripSeat;
	}


	public void setRoundtripSeat(int roundtripSeat) {
		this.roundtripSeat = roundtripSeat;
	}


	public Timestamp getReservationDateTime() {
		return reservationDateTime;
	}


	public void setReservationDateTime(Timestamp reservationDateTime) {
		this.reservationDateTime = reservationDateTime;
	}


	public Timestamp getPurchaseDateTime() {
		return purchaseDateTime;
	}


	public void setPurchaseDateTime(Timestamp purchaseDateTime) {
		this.purchaseDateTime = purchaseDateTime;
	}


	public User getUsername() {
		return username;
	}


	public void setUsername(User username) {
		this.username = username;
	}


	public String getName() {
		return firstName;
	}


	public void setName(String firstName) {
		this.firstName = firstName;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", departureFlight=" + departureFlight + ", roundtripFlight=" + roundtripFlight
				+ ", departureSeat=" + departureSeat + ", roundtripSeat=" + roundtripSeat + ", reservationDateTime="
				+ reservationDateTime + ", purchaseDateTime=" + purchaseDateTime + ", username=" + username
				+ ", firstName=" + firstName + ", surname=" + surname + "]";
	}
	
	
	
	

}
