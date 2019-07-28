package com.target.service;

import java.util.logging.Logger;

import com.target.constants.Const;
import com.target.entity.ParkingSpot;

public class ParkingLotSetup {
	private static final Logger logger = Logger.getLogger(ParkingLotSetup.class.getName());
	
	private ParkingSpot[][][][] parkingLot;

	public ParkingSpot[][][][] createParkingLot(int floor){
		logger.info("Parking lot is been created using number of floors : " + floor);
		Const.setFLOOR(floor);
		parkingLot = new ParkingSpot[floor][Const.ROW][Const.COLUMN][Const.STACK];
		
		for(int f=0; f<floor; f++){
			for(int r=0; r<Const.ROW; r++){
				for(int c=0; c<Const.COLUMN; c++){
					parkingLot[f][r][c][0] = new ParkingSpot(f,r,c,0);
					parkingLot[f][r][c][1] = new ParkingSpot(f,r,c,1);
				}
			}
		}
		logger.info("Parking lot has been created ");
		return parkingLot;
	}
}
