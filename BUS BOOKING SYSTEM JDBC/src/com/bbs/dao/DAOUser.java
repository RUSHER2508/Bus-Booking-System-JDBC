package com.bbs.dao;

import java.util.ArrayList;
import java.util.Date;

import com.bbs.beans.Bus;
import com.bbs.beans.Ticket;
import com.bbs.beans.User;

public interface DAOUser {
	//user manipulation
		public Boolean createUser(User user);
		public Boolean updateUser(Integer userId, String oldPassword, String newPassword);		public Boolean deleteUser(Integer userId,String password);
		public Boolean loginUser(Integer userId,String password);
		public User searchUser(Integer userId);
		
		
		//ticket booking
		public Boolean bookTicket(Ticket ticket);
		public Boolean cancelTicket(Integer bookingId);
		public Ticket getTicket(Integer bookingId);
		public Integer checkAvailability(Integer busId,java.sql.Date availdate);

}
