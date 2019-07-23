package com.bbs.dao;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Scanner;

import com.bbs.beans.Bus;
import com.bbs.beans.Ticket;
import com.bbs.beans.User;

public class DaoUserImpl implements DAOUser {
	String url="jdbc:mysql://localhost:3306/busbookingsystem_db?user=root&password=root";
	Connection conn;
	java.sql.Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs=null;
	User user=new User();
	Scanner sc=new Scanner(System.in);




	@Override
	public Boolean createUser(User user) {
		try {
			//load Driver
			java.sql.Driver div=new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);

			//GetConnection
			conn=DriverManager.getConnection(url);

			//iSSUE SQL Query
			String q="INSERT INTO USERS_INFO VALUES(?,?,?,?,?)";
			pstmt=conn.prepareStatement(q);
			pstmt.setInt(1,user.getUserId());
			pstmt.setString(2,user.getUsername());
			pstmt.setString(3,user.getEmail());
			pstmt.setString(4, user.getPassword());
			pstmt.setLong(5,user.getContact());
			int i=pstmt.executeUpdate();
			if(i>0)
			{
				System.out.println("Account Created");
				return true;
			}
			else
			{
				System.out.println("Something went wrong");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean updateUser(Integer userId,String oldPassword,String newPassword) {
		try {
			//load Driver
			java.sql.Driver div=new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);

			//GetConnection
			conn=DriverManager.getConnection(url);

			//Issue SQL quERY
String q2 = "update users_info set password=? where user_id=? and password=?";
			
			pstmt = conn.prepareStatement(q2);
			
			pstmt.setInt(2,userId);
			pstmt.setString(3,oldPassword);
			pstmt.setString(1,newPassword);

			int j = pstmt.executeUpdate();
			if(j>0)
			{
				return true;
			}
			else
			{
				return false;
			}

		}
		catch(Exception e) {
			e.printStackTrace();

		}


		return false;
	}

	public Boolean deleteUser(Integer userId,String password) {

		try {
			//load Driver
			java.sql.Driver div=new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);

			//GetConnection
			conn=DriverManager.getConnection(url);

			//issue sql query
			String q3="DELETE FROM USERS_INFO WHERE user_ID=? and password=?";
			pstmt=conn.prepareStatement(q3);

			pstmt.setInt(1,userId);

			pstmt.setString(2,password);
			int k=pstmt.executeUpdate();
			if(k>0)
			{
				System.out.println("Account Deleted");
				return true;
			}
			else
			{
				System.out.println("Something went wrong");
			}


		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;




	}

	@Override
	public Boolean loginUser(Integer userId, String password) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//GetConnection
			conn=DriverManager.getConnection(url);

			//Issue SQL Query
			String q4="SELECT * FROM USERS_INFO WHERE USER_ID=? AND PASSWORD=?";
			pstmt=conn.prepareStatement(q4);
			pstmt.setInt(1, userId);
			pstmt.setString(2, password);
			rs=pstmt.executeQuery();

			if(rs.next())
			{

				return true;
			}
		}


		catch(Exception e) {

		}
		return false;
	}

	@Override
	public User searchUser(Integer userId) {
		try
		{
			//load Driver
			java.sql.Driver div=new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);

			//GetConnection
			conn=DriverManager.getConnection(url);

			//Issue sql query
			String q1="Select * from users_info where user_id=?";
			pstmt=conn.prepareStatement(q1);
			pstmt.setInt(1, userId);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				int userid=rs.getInt("user_id");
				String userName=rs.getString("username");
				String email=rs.getString("email");
				String password=rs.getString("password");
				long contact=rs.getLong("contact");
				user.setUserId(userId);
				user.setUsername(userName);
				user.setEmail(email);
				user.setPassword(password);
				user.setContact(contact);

				if(user!=null)
				{
					return user;
				}
				else {
					return null;
				}

			}
		}
		catch(Exception e)
		{

		}
		return user;

	}

	@Override
	public Boolean bookTicket(Ticket ticket) {
		Bus bus=new Bus();
		HashMap<Integer, Integer> map=new HashMap<>();




		try {
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			//GetConnection
			conn = DriverManager.getConnection(url);
			//IssueSql query
			String query="Select * from bus_info where source=? and destination=? ";
			pstmt=conn.prepareStatement(query);
			System.out.println("Enter Source");
			pstmt.setString(1, sc.next());
			System.out.println("Enter Destination");
			pstmt.setString(2, sc.next());
			rs=pstmt.executeQuery();
			int i=1;
			while(rs.next())
			{
				int busId=rs.getInt("bus_id");
				String busName=rs.getString("busname");
				String busSource=rs.getString("source");
				String busDestination=rs.getString("destination");
				String busType=rs.getString("bus_type");
				Double price=rs.getDouble("price");
				map.put(i, busId);
				i++;

			}
			System.out.println(map);

			System.out.println(" Enter Selected Bus_id ");
			int choiceBus=sc.nextInt();
			System.out.println("Enter Date in yyyy-mm-dd Format");
			String date1=sc.next();
			java.util.Date date2=new SimpleDateFormat("yyyy-MM-dd").parse(date1);
			Date date= new Date(date2.getTime());
			int available=checkAvailability(choiceBus, date );
			System.out.println("available :"+available);
			if(available !=0)
			{
				System.out.println("Enter no. of tickets");
				int tickets=sc.nextInt();
				available=available-tickets;

				String q1="Update availability set avail_seats=? where bus_id=? and avail_date=?";
				pstmt=conn.prepareStatement(q1);
				pstmt.setInt(2, choiceBus);
				pstmt.setDate(3, date);
				pstmt.setInt(1, available);
				int update=pstmt.executeUpdate();

				if(update>0)
				{
					String q2="INSERT INTO BOOKING_INFO (bus_id,user_id,journey_date,numofseats,booking_datetime) VALUES(?,?,?,?,?)";
					PreparedStatement pstmt = conn.prepareStatement(q2, java.sql.Statement.RETURN_GENERATED_KEYS);
					pstmt.setInt(1,choiceBus );
					pstmt.setInt(2,ticket.getUserId());
					pstmt.setDate(3, date);
					pstmt.setInt(4, tickets);
					//				  pstmt.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
					pstmt.setTimestamp(5, new java.sql.Timestamp(new java.util.Date().getTime()));
					int k=pstmt.executeUpdate();
					if(k>0)
					{
						System.out.println("Booking Info Added");
					}
					else {
						System.out.println("Failed to add");
					}

					return true;
				}else {
					return false;
				}
			}
			else {
				System.out.println("No tickets available");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean cancelTicket(Integer bookingId) {
		Boolean state=false;
		try {
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			//GetConnection
			conn = DriverManager.getConnection(url);
			//Issue Query
			Ticket ticket=getTicket(bookingId);
			int num=ticket.getNumofSeats();
			int busId=ticket.getBusId();
			System.out.println(ticket);
			System.out.println("To confirm Delete press yes");
			
			if(sc.next().equalsIgnoreCase("yes"))
			{
			String query="Delete from booking_info where booking_id=?";
			pstmt=conn.prepareStatement(query);
			pstmt.setInt(1,bookingId);
			int update=pstmt.executeUpdate();
			int available;
			if(update>0)
			{
				String q1="Select * from availability where bus_id=?";
				pstmt=conn.prepareStatement(q1);
				pstmt.setInt(1, busId);
				rs=pstmt.executeQuery();
				if(rs.next())
				{
					 available=rs.getInt("avail_seats");
					 int availableNew=available+num;
					 String q2="Update availability set avail_seats=? where bus_id=?";
					 pstmt=conn.prepareStatement(q2);
					 pstmt.setInt(2, busId);
					 pstmt.setInt(1, availableNew);
					int inc= pstmt.executeUpdate();
					if(inc>0)
					{
						state=true;
					}
				}
				
				
			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return state;
	}

	@Override
	public Ticket getTicket(Integer bookingId) {
		try {
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			//GetConnection
			conn = DriverManager.getConnection(url);
			//Issue Query
			String query="select * from booking_info where booking_id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1,bookingId );
			rs = pstmt.executeQuery();
			Ticket ticket=null;
			while(rs.next()) {
				int bookingId1 = rs.getInt("booking_id");
				int busId = rs.getInt("bus_id");
				int userId=rs.getInt("user_id");
				Date date=rs.getDate("journey_Date");
				int seats=rs.getInt("numofseats");
				ticket=new Ticket();
				ticket.setUserId(userId);
				ticket.setJourneyDate(date);
				ticket.setBusId(busId);
				ticket.setNumofSeats(seats);
				ticket.setBookingId(bookingId1);
				return ticket;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer checkAvailability(Integer busId, Date date) {
		try {
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			//GetConnection
			conn = DriverManager.getConnection(url);

			//IssueSql
			String q1="Select * from availability where bus_id=? and avail_date=?";
			pstmt=conn.prepareStatement(q1);
			pstmt.setInt(1, busId);
			pstmt.setDate(2, date);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				int available=rs.getInt("avail_seats");
				return available;
			}


		} catch (SQLException e) {

			e.printStackTrace();
		}
		return null;



	}
	}
