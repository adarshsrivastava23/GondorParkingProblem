package com.target.customeException;

public class VehicleNotFoundException extends RuntimeException{
	public VehicleNotFoundException(String msg) {
		super(msg);
	}
}
