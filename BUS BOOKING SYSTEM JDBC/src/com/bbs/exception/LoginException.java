package com.bbs.exception;

public class LoginException extends RuntimeException{

	public String Login() {
		return "Invalid User id or Password";
	}
}
