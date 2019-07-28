package com.target.entity;

public class ParkingSpot implements Comparable<ParkingSpot>{
	
	private int floor;
	private int row;
	private int column;
	private int stack;
	
	private boolean isRoyal;
	
	private boolean isEmpty;
	private boolean isCarParked;
	private int numberOfBikes;
	
	@Override
	public int compareTo(ParkingSpot p) {
		if (p == null) {
			return 1;
		}
		if (this.floor != p.floor) {
			return this.floor - p.floor;
		} else if (this.row != p.row) {
			return this.row - p.row;
		} else if (this.column != p.column) {
			return this.column - p.column;
		} else {
			return this.stack - p.stack;
		}
	}

	public ParkingSpot(int floor, int row, int column, int stack) {
		this.floor = floor;
		this.row = row;
		this.column = column;
		this.stack = stack;
		this.isRoyal = false;
		this.isEmpty = true;
		this.isCarParked = false;
		this.numberOfBikes = 0;
	}

	@Override
	public String toString() {
		return "ParkingSpot [" + floor + "][" + row + "][" + column + "][" + stack + "]";
	}

	public boolean isRoyal() {
		return isRoyal;
	}

	public void setRoyal(boolean isRoyal) {
		this.isRoyal = isRoyal;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		if(isEmpty){
			this.isCarParked = false;
			this.numberOfBikes = 0;
		}
		this.isEmpty = isEmpty;
	}

	public boolean isCarParked() {
		return isCarParked;
	}

	public void setCarParked(boolean isCarParked) {
		this.isCarParked = isCarParked;
		if(isCarParked){
			this.isEmpty = false;
		}else{
			this.isEmpty = true;
		}
		
	}

	public int getNumberOfBikes() {
		return numberOfBikes;
	}

	public void setNumberOfBikes(int numberOfBikes) {
		this.numberOfBikes = numberOfBikes;
	}
	
	public int incrementNumberOfBikes(){
		return ++this.numberOfBikes;
	}
	
	public int decrementNumberOfBikes(){
		if(this.numberOfBikes>0){
			return --this.numberOfBikes;
		}else{
			return -1;
		}
		
	}

	public int getFloor() {
		return floor;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public int getStack() {
		return stack;
	}
	
	
	

}
