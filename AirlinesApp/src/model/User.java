package model;

import java.sql.Date;


public class User {
	
	public enum Role {USER, ADMIN}
	
	private int id;
	private String username;
	private String password;
	private Date registrationDate;
	private Role role;
	private boolean blockedUser;
	private boolean active;
	
	public User(String username, String password, Date registrationDate, Role role, boolean blockedUser) {

		this.username = username;
		this.password = password;
		this.registrationDate = registrationDate;
		this.role = role;
		this.blockedUser = blockedUser;
	}
	
	public User(int id, String username, String password, Date registrationDate, Role role, boolean blockedUser, boolean active) {

		this.id = id;
		this.username = username;
		this.password = password;
		this.registrationDate = registrationDate;
		this.role = role;
		this.blockedUser = blockedUser;
		this.active = active;
	}
	
	public User(String userName, String password, Role role) {
		this.username = userName;
		this.password = password;
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Date getRegistrationDate() {
		return registrationDate;
	}
	
	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	public boolean isBlockedUser() {
		return blockedUser;
	}
	
	public void setBlockedUser(boolean blockedUser) {
		this.blockedUser = blockedUser;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", registrationDate="
				+ registrationDate + ", role=" + role + ", blockedUser=" + blockedUser + "]";
	}

	
	
}
