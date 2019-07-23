package com.bbs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import com.bbs.beans.Bus;


public class AdminDaoImpl implements AdminDaoBBS{
	String url="jdbc:mysql://localhost:3306/busbookingsystem_db?user=root&password=root";
	Connection conn;
	Statement stmt;
	PreparedStatement pstmt;
	ResultSet rs=null;
	Bus bus = new Bus();

	Scanner sc=new Scanner(System.in);

	@Override
	public Boolean createBus(Bus bus) {
		try {
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			conn = DriverManager.getConnection(url);
			System.out.println("Connection Established");
			String sql = "INSERT INTO BUS_INFO VALUES(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			System.out.println("Enter the bus id");
			pstmt.setInt(1,Integer.parseInt(sc.nextLine()));
			System.out.println("Enter the busname");
			pstmt.setString(2, sc.nextLine());
			System.out.println("Enter the source");
			pstmt.setString(3, sc.nextLine());
			System.out.println("Enter the destination");
			pstmt.setString(4, sc.nextLine());
			System.out.println("Bus type");
			pstmt.setString(5, sc.nextLine());
			System.out.println("Enter the Total seats");
			pstmt.setInt(6, sc.nextInt());
			System.out.println("Enter the Price");
			pstmt.setDouble(7,sc.nextDouble() );
			int i = pstmt.executeUpdate();
			if(i>0){
				System.out.println("Done");
				return true;
			}
			else {
				System.out.println("error");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
					if(pstmt!=null) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	@Override
	public Boolean updateBus(Bus bus) {
		try {
			//load Driver
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			//GetConnection
			conn = DriverManager.getConnection(url);
			String sql = "UPDATE BUS_INFO SET SOURCE=?,DESTINATION=?,PRICE=? WHERE BUS_ID=?";
			pstmt = conn.prepareStatement(sql);
			System.out.println("Enter the source");
			pstmt.setString(1,sc.nextLine() );
			System.out.println("Enter the destination");
			pstmt.setString(2, sc.nextLine());
			System.out.println("Enter the price");
			pstmt.setDouble(3, sc.nextDouble());
			System.out.println("Enter the bus id");
			pstmt.setInt(4, sc.nextInt());
			int i = pstmt.executeUpdate();
			if(i>0) {
				System.out.println("Updated");
				return true;
			}
			else {
				System.out.println("Not done");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
					if(pstmt!=null) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	@Override
	public Bus searchBus(int busId) {
		try {
			//load Driver
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			//GetConnection
			conn = DriverManager.getConnection(url);
			String sql = "SELECT * FROM BUS_INFO WHERE BUS_ID=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, busId);
			rs = pstmt.executeQuery();
			if(rs.next()){
				
				int busid = rs.getInt("bus_id");
				String busname = rs.getString("busname");
				String source = rs.getString("source");
				String destination = rs.getString("destination");
				String bus_type = rs.getString("bus_type");
				int total_seats = rs.getInt("total_seats");
				double price = rs.getDouble("price");
				bus.setBusId(busid);
				bus.setBusName(busname);
				bus.setSource(source);
				bus.setDestination(destination);
				bus.setBusType(bus_type);
				bus.setTotalSeats(total_seats);
				bus.setPrice(price);
				System.out.println(bus);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
					if(pstmt!=null) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return bus;
	}

	@Override
	public Boolean deletebus(int busId) {
		try {
			//load Driver
			java.sql.Driver div=new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			//GetConnection
			conn=DriverManager.getConnection(url);
			//Issue SQL quERY
			String q2="delete from bus_info where bus_id=?";
			pstmt=conn.prepareStatement(q2);
			pstmt.setInt(1,busId);
			
			int k=pstmt.executeUpdate();
			if(k>0)
			{
				return true;
			}
			else
			{
				System.out.println("Something went wrong");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
					if(pstmt!=null) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return false;
	}

	@Override
	public HashMap<Integer, Bus> busBetween(String source, String destination) {
		Bus bus=new Bus();
		HashMap<Integer, Bus> map=new HashMap<>();

			try{
				java.sql.Driver div = new com.mysql.jdbc.Driver();
				DriverManager.registerDriver(div);
				//GetConnection
				conn = DriverManager.getConnection(url);
				//IssueSql query
				String query="Select * from bus_info where source=? and destination=? ";
				pstmt=conn.prepareStatement(query);
				pstmt.setString(1, source);
				pstmt.setString(2, destination);
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
					bus.setBusId(busId);
					bus.setBusName(busName);
					bus.setSource(busSource);
					bus.setDestination(busDestination);
					bus.setBusType(busType);
					bus.setPrice(price);
					map.put(i, bus);
					i++;
					

				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			finally {
				if(conn != null) {
					try {
						conn.close();
						if(pstmt!=null) {
							pstmt.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		
		return map;
	}

	@Override
	public Boolean adminLogin(Integer adminId, String password) {
		try {
			//load Driver
			java.sql.Driver div = new com.mysql.jdbc.Driver();
			DriverManager.registerDriver(div);
			System.out.println("Driver Loaded");
			//GetConnection
			conn = DriverManager.getConnection(url);
			System.out.println("Connection Established");
			String query = "SELECT * FROM USERS_INFO WHERE ADMIN_ID=? AND PASSWORD=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, adminId);
			pstmt.setString(2, password);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if(conn != null) {
				try {
					conn.close();
					if(pstmt!=null) {
						pstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return false;

	}
}