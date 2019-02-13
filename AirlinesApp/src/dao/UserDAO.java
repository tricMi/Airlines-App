package dao;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.User;
import model.User.Role;

public class UserDAO {

	public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	
	public static User getUser(String username, String password) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			String query = "SELECT role FROM users WHERE username = ? AND password = ? and active = 0 ";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, username);
			pstmt.setString(index++, password);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()){

				Role role = Role.valueOf(rset.getString(1));
				return new User(username, password, role);
			}
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			try{
				pstmt.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try{
				rset.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try{
				conn.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static List<User> getUsersId(String username, Role role) {
		ArrayList<User> users = new ArrayList<>();
		
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try {
			if(role != null) {
				String query = "SELECT id, username, password, registeredDate, role, blockedUser, active FROM users WHERE username LIKE ? AND role LIKE ? AND active = 0 ";
				
				pstmt = conn.prepareStatement(query);
				int index = 1;
				pstmt.setString(index++, "%" + username + "%");
				pstmt.setString(index++, role.toString());
				System.out.println(pstmt);
				
				rset = pstmt.executeQuery();
				
				while(rset.next()) {
					index = 1;
					int id = rset.getInt(index++);
					String usernameInput = rset.getString(index++);
					String password = rset.getString(index++);
					Date registeredDate = rset.getDate(index++);
					Role roleInput = Role.valueOf(rset.getString(index++));
					boolean blockedUser = rset.getBoolean(index++);
					boolean active = rset.getBoolean(index++);
					
					User u =  new User(id, usernameInput, password, registeredDate, roleInput, blockedUser, active);
					users.add(u);
				
			}
			}else {
				String query = "SELECT id, username, password, registeredDate, role, blockedUser, active FROM users WHERE username LIKE ? AND active = 0 ";
		
				pstmt = conn.prepareStatement(query);
				int index = 1;
				pstmt.setString(index++, "%" + username + "%");
				System.out.println(pstmt);
				
				rset = pstmt.executeQuery();
				
				while(rset.next()) {
					index = 1;
					int id = rset.getInt(index++);
					String usernameInput = rset.getString(index++);
					String password = rset.getString(index++);
					Date registeredDate = rset.getDate(index++);
					Role roleInput = Role.valueOf(rset.getString(index++));
					boolean blockedUser = rset.getBoolean(index++);
					boolean active = rset.getBoolean(index++);
					
					User u =  new User(id, usernameInput, password, registeredDate, roleInput, blockedUser, active);
					users.add(u);

			}
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}finally {
			try{
				pstmt.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try{
				rset.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
			
			try{
				conn.close();
			}catch(Exception ex1)
			{
				ex1.printStackTrace();
			}
		}
		return users;
	}
	
	public static User get(String username) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			String query = "SELECT id, password, registeredDate, role, blockedUser, active FROM users WHERE username = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, username);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			if(rset.next())
			{
				int index = 1;
				int id = rset.getInt(index++);
				System.out.println("This is iddd" + id);
				String password = rset.getString(index++);
				Date registrationDate = rset.getDate(index++);
				Role role = Role.valueOf(rset.getString(index++));
				boolean blockedUser = rset.getBoolean(index++);
				boolean active = rset.getBoolean(index++);
				
				return new User(id, username, password,registrationDate, role, blockedUser, active);
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			
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
	
	public static User getUserId(int id) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		try
		{
			String query = "SELECT username, password, registeredDate, role, blockedUser, active FROM users WHERE id = ?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, id);
			System.out.println(pstmt);
			
			rset = pstmt.executeQuery();
			if(rset.next())
			{
				int index = 1;
				String username = rset.getString(index++);
				String password = rset.getString(index++);
				Date registrationDate = rset.getDate(index++);
				Role role = Role.valueOf(rset.getString(index++));
				boolean blockedUser = rset.getBoolean(index++);
				boolean active = rset.getBoolean(index++);
				
				return new User(id, username, password,registrationDate, role, blockedUser, active);
				
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally {
			
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
	
	public static boolean addUser(User user) {
		Connection conn = ConnectionManager.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			String query = "INSERT INTO users (username, password, registeredDate, role, blockedUser, active) "
							+ "VALUES (?, ?, ?, ?, ?, ?)";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;

			pstmt.setString(index++, user.getUsername());
			pstmt.setString(index++, user.getPassword());
			pstmt.setDate(index++, user.getRegistrationDate());
			pstmt.setString(index++, user.getRole().toString());
			pstmt.setBoolean(index++, user.isBlockedUser());;
			pstmt.setBoolean(index++, user.isActive());
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

	
	
	public static boolean updateUserByAdmin(User user) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null; 
		try {
			String query = "UPDATE users SET password = ?, role = ?, blockedUser = ? WHERE id = ?";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, user.getPassword());
			pstmt.setString(index++, user.getRole().toString());
			pstmt.setBoolean(index++, user.isBlockedUser());
			pstmt.setInt(index++, user.getId());

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
	
	
	public static boolean deleteUser(int id) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null; 
		try {
			String query = "DELETE users, tickets FROM users LEFT JOIN tickets ON users.id = tickets.username WHERE tickets.purchaseDateTime IS NULL AND  users.id = ?";
			
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
	
	public static boolean deleteUserLogical(int id) {
		Connection conn = ConnectionManager.getConnection();

		PreparedStatement pstmt = null; 
		try {
			conn.setAutoCommit(false);
			conn.commit();
			String query = "UPDATE users SET active = 1 WHERE id = ?";
			
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setInt(index++, id);
			System.out.println(pstmt);

			pstmt.executeUpdate();
			pstmt.close();
			
			query = "DELETE FROM tickets WHERE purchaseDateTime IS NULL AND username = ?  ";
			
			index = 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(index++, id);
			
			pstmt.executeUpdate();
			
			conn.commit();
			return pstmt.executeUpdate() == 1;
			
		}catch (Exception ex) {
			try {
				conn.rollback();
			}catch(Exception ex1) {
				ex1.printStackTrace();
			}
		}finally 
		{
			try {
				conn.setAutoCommit(true);
			} catch (Exception ex1) {
				ex1.printStackTrace();
			}
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
