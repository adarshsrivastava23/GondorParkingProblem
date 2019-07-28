package com.target.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.target.constants.VehicleOwnerPrivilege;
import com.target.constants.VehicleType;
import com.target.entity.ParkingSpot;
import com.target.service.ParkingLotSetup;
import com.target.service.ParkingService;

public class TestParkingLotApp {

	public static void main(String[] args) {
		ParkingLotSetup parkingLotSetup = new ParkingLotSetup();
		
		//Created the Parking lot with 10 Floors
		ParkingSpot[][][][] parkingLot = parkingLotSetup.createParkingLot(10);
		
//		if (parkingLot[3][1][1][1].equals(parkingLot[3][1][1][1])) {
//			System.out.println("True");
//		}else{
//			System.out.println("False");
//		}
		
		
		
		
		ParkingService parkingService = new ParkingService(parkingLot);
		
		parkingService.makeRoyalParkingSpot(0, 1, 1, 0);
		parkingService.makeRoyalParkingSpot(0, 1, 2, 0);
		parkingService.makeRoyalParkingSpot(0, 1, 3, 0);
		parkingService.makeRoyalParkingSpot(0, 2, 1, 0);
		parkingService.makeRoyalParkingSpot(0, 2, 2, 0);
		parkingService.makeRoyalParkingSpot(0, 3, 3, 0);
		
		
		List<String> listOfParkedCarRCNo = new ArrayList<>();
		
		ParkingSpot s = null;
		Random rand = new Random(); 
		int startingNo = 1000;
		for(int i=0; i<30; i++){
			String vehicleNo = "KA" +  startingNo++; //(rand.nextInt(10000) + 1000); 
			s = parkingService.addVehicleInParkingLot(vehicleNo, VehicleType.CAR);
			if(s!=null){
				System.out.println("+++ Parking Slot : " + s+ " assigned for vehicle : " + vehicleNo + " +++");
				listOfParkedCarRCNo.add(vehicleNo);
			}			
		}
		for(int i=0; i<4; i++){
			for(int j=0; j<5; j++){
				String vehicleNo = "KA" +  startingNo++; //(rand.nextInt(10000) + 1000); 
				s = parkingService.addVehicleInParkingLot(vehicleNo, VehicleType.CAR);
				if(s!=null){
					System.out.println("+++ Parking Slot : " + s+ " assigned for vehicle : " + vehicleNo + " +++");
					listOfParkedCarRCNo.add(vehicleNo);
				}
			}
			for(int k=0; k<5; k++){
				int randomNo = rand.nextInt(listOfParkedCarRCNo.size());
				String removedVehicle = listOfParkedCarRCNo.remove(randomNo);
				s = parkingService.removeVehicleFromParkingLot(removedVehicle,  VehicleType.CAR);
				System.out.println("--- Parking Slot : " + s+ " is Free after removal : " + removedVehicle + " ---");
			}
			
		}
		
		for(int i=0; i<2; i++){
			for(int j=0; j<5; j++){
				String vehicleNo = "KA" +  startingNo++; //(rand.nextInt(10000) + 1000); 
				s = parkingService.addVehicleInParkingLot(vehicleNo, VehicleType.CAR, VehicleOwnerPrivilege.ELDER);
				if(s!=null){
					System.out.println("+++ Parking Slot : " + s+ " assigned for vehicle : " + vehicleNo + " +++");
					listOfParkedCarRCNo.add(vehicleNo);
				}
			}
			for(int k=0; k<5; k++){
				int randomNo = rand.nextInt(listOfParkedCarRCNo.size());
				String removedVehicle = listOfParkedCarRCNo.remove(randomNo);
				s = parkingService.removeVehicleFromParkingLot(removedVehicle,  VehicleType.CAR);
				System.out.println("--- Parking Slot : " + s+ " is Free after removal : " + removedVehicle + " ---");
			}
			
		}
		
		for(int i=0; i<2; i++){
			for(int j=0; j<5; j++){
				String vehicleNo = "KA" +  startingNo++; //(rand.nextInt(10000) + 1000); 
				s = parkingService.addVehicleInParkingLot(vehicleNo, VehicleType.CAR, VehicleOwnerPrivilege.ROYAL);
				if(s!=null){
					System.out.println("+++ Parking Slot : " + s+ " assigned for vehicle : " + vehicleNo + " +++");
					listOfParkedCarRCNo.add(vehicleNo);
				}
			}
			for(int k=0; k<5; k++){
				int randomNo = rand.nextInt(listOfParkedCarRCNo.size());
				String removedVehicle = listOfParkedCarRCNo.remove(randomNo);
				s = parkingService.removeVehicleFromParkingLot(removedVehicle,  VehicleType.CAR);
				System.out.println("--- Parking Slot : " + s+ " is Free after removal : " + removedVehicle + " ---");
			}
			
		}
		
		List<String> listOfParkedBikeRCNo = new ArrayList<>();
		for(int j=0; j<20; j++){
			String vehicleNo = "KA" +  startingNo++; //(rand.nextInt(10000) + 1000); 
			s = parkingService.addVehicleInParkingLot(vehicleNo, VehicleType.BIKE);
			if(s!=null){
				System.out.println("+++ Parking Slot : " + s+ " assigned for vehicle : " + vehicleNo + " +++");
				listOfParkedBikeRCNo.add(vehicleNo);
			}
		}
		
		for(int i=0; i<10; i++){
			for(int j=0; j<5; j++){
				String vehicleNo = "KA" +  startingNo++; //(rand.nextInt(10000) + 1000); 
				s = parkingService.addVehicleInParkingLot(vehicleNo, VehicleType.BIKE);
				if(s!=null){
					System.out.println("+++ Parking Slot : " + s+ " assigned for vehicle : " + vehicleNo + " +++");
					listOfParkedBikeRCNo.add(vehicleNo);
				}
			}
			for(int k=0; k<5; k++){
				int randomNo = rand.nextInt(listOfParkedBikeRCNo.size());
				String removedVehicle = listOfParkedBikeRCNo.remove(randomNo);
				s = parkingService.removeVehicleFromParkingLot(removedVehicle,  VehicleType.BIKE);
				System.out.println("--- Parking Slot : " + s+ " is Free after removal : " + removedVehicle + " ---");
			}
			
		}
		
		parkingService.printSummary();
	}

}
