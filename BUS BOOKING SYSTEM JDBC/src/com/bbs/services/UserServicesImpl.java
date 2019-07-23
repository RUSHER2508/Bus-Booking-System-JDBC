package com.bbs.services;

import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bbs.beans.Ticket;
import com.bbs.beans.User;
import com.bbs.dao.DAOUser;
import com.bbs.dao.DaoUserImpl;

public class UserServicesImpl implements UserServices{
	Pattern pat = null;
	Matcher match = null;
	

	DAOUser dao = new DaoUserImpl();
	@Override
	public Boolean createUser(User user) {
		return dao.createUser(user);
	}

	@Override
	public Boolean updateUser(Integer userId, String oldPassword, String newPassword) {
		return dao.updateUser(userId, oldPassword,newPassword);
	}

	@Override
	public Boolean deleteUser(Integer userId,String password) {
		return dao.deleteUser(userId, password);
	}

	@Override
	public Boolean loginUser(Integer userId, String password) {
		return dao.loginUser(userId, password);
	}

	@Override
	public User searchUser(Integer userId) {
		return dao.searchUser(userId);
	}

	@Override
	public Boolean bookTicket(Ticket ticket) {
		return dao.bookTicket(ticket);
	}

	@Override
	public Boolean cancelTicket(Integer bookingId) {
		return dao.cancelTicket(bookingId);
	}

	@Override
	public Ticket getTicket(Integer bookingId) {
		return dao.getTicket(bookingId);
	}

	@Override
	public Integer checkAvailability(Integer busId, Date availdate) {
		return dao.checkAvailability(busId, availdate);
	}

	


	

}
