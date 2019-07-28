package com.target.customeException;

public class ParkingLotFullException extends RuntimeException{
	public ParkingLotFullException(String msg) {
		super(msg);
	}
}
