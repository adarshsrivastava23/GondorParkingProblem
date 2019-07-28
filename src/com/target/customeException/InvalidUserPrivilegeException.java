package com.target.customeException;

public class InvalidUserPrivilegeException extends RuntimeException{
	public InvalidUserPrivilegeException(String msg) {
		super(msg);
	}
}
