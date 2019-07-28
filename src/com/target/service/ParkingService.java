package com.target.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.target.constants.Const;
import com.target.constants.VehicleOwnerPrivilege;
import com.target.constants.VehicleType;
import com.target.customeException.ParkingLotFullException;
import com.target.customeException.VehicleNotFoundException;
import com.target.entity.ParkingSpot;

public class ParkingService {
	private static final Logger logger = Logger.getLogger(ParkingService.class.getName());

	private ParkingSpot[][][][] parkingLot;

	private ParkingSpot nextNormal;
	private ParkingSpot nextElder;
	private ParkingSpot nextRoyal;
	private ParkingSpot nextBike;// assuming bikes have only normal priority. No
									// Bike have royal or elder priority.
	private int freeParkingSpots;

	Map<String, ParkingSpot> carMap = new HashMap<>();
	Map<String, ParkingSpot> bikeMap = new HashMap<>();

	public ParkingService(ParkingSpot[][][][] parkingLot) {
		this.parkingLot = parkingLot;
		this.freeParkingSpots = Const.FLOOR * Const.ROW * Const.COLUMN * Const.STACK;
		this.nextNormal = parkingLot[0][0][0][0];
		this.nextElder = parkingLot[0][0][0][0];
		this.nextBike = parkingLot[0][0][0][0];
		this.nextRoyal = null;
	}

	public void makeRoyalParkingSpot(int f, int r, int c, int s) {
		parkingLot[f][r][c][s].setRoyal(true);
		logger.info(parkingLot[f][r][c][s] + " is reserved for Royal Family");
		if (nextRoyal == null || (parkingLot[f][r][c][s].compareTo(nextRoyal) < 0)) {
			nextRoyal = parkingLot[f][r][c][s];
		}
		logger.info("Next Royal car Pointer is : " + nextRoyal);
	}

	public ParkingSpot addVehicleInParkingLot(String vehicleRCNo, VehicleType vehicleType) {
		return addVehicleInParkingLot(vehicleRCNo, vehicleType, VehicleOwnerPrivilege.NORMAL);
	}

	public ParkingSpot addVehicleInParkingLot(String vehicleRCNo, VehicleType vehicleType,
			VehicleOwnerPrivilege vehicleOwnerPrivilege) {
		logger.info("Adding vehicle RC Number : " + vehicleRCNo + " Vehicle Type: " + vehicleType
				+ " with Vehicle Owner Privilege : " + vehicleOwnerPrivilege);
		if (vehicleType == VehicleType.CAR) {
			return addCarInParkingLot(vehicleRCNo, vehicleOwnerPrivilege);
		} else {
			return addBikeInParkingLot(vehicleRCNo);
		}
	}

	private ParkingSpot addCarInParkingLot(String vehicleRCNo, VehicleOwnerPrivilege vehicleOwnerPrivilege) {
		ParkingSpot allotedSpot = null;
		if (vehicleOwnerPrivilege == VehicleOwnerPrivilege.ROYAL) {
			if (nextRoyal == null) {
				// Allot a normal parking spot in case of all royal spots are
				// filled
				logger.info("Parking lot is full for Car with reserved Royal parking spot");
				return addCarInParkingLot(vehicleRCNo, VehicleOwnerPrivilege.NORMAL);
			}
			allotedSpot = nextRoyal;
		} else if (vehicleOwnerPrivilege == VehicleOwnerPrivilege.ELDER) {
			if(nextElder == null){
				logger.info("Parking lot is full for Car for Elders");
				return null;
			}
			allotedSpot = nextElder;
		} else if (vehicleOwnerPrivilege == VehicleOwnerPrivilege.NORMAL) {
			if(nextNormal == null){
				logger.info("Parking lot is full for Car");
				return null;
			}
			allotedSpot = nextNormal;
		}

		// set flag as Car parked in the parking spot
		allotedSpot.setCarParked(true);

		// add vehiicleRCNumber key and parking spot as value in the map
		carMap.put(vehicleRCNo, allotedSpot);

		// Reducing free parking spots
		this.freeParkingSpots--;

		// adjust all next pointers if they are not empty
		adjustAllNextPointersAfterAdd();

		return allotedSpot;
	}

	private void adjustAllNextPointersAfterAdd() {
		try {
			if (nextRoyal != null && !nextRoyal.isEmpty()) {
				nextRoyal = getNextCarParkingSpot(nextRoyal, VehicleOwnerPrivilege.ROYAL);
			}
			if (!nextElder.isEmpty()) {
				nextElder = getNextCarParkingSpot(nextElder, VehicleOwnerPrivilege.ELDER);
			}
			if (!nextNormal.isEmpty()) {
				nextNormal = getNextCarParkingSpot(nextNormal, VehicleOwnerPrivilege.NORMAL);
			}
			if (!nextBike.isEmpty()) {
				nextBike = getNextBikeParkingSpot(nextBike);
			}
		} catch (ParkingLotFullException e) {
			e.printStackTrace();
		} finally {

			logger.info("Next Normal Car Pointer is : " + nextNormal);
			logger.info("Next Bike Pointer is : " + nextBike);
			logger.info("Next Royal Car Pointer is : " + nextRoyal);
			logger.info("Next Elder Car Pointer is : " + nextElder);
		}

	}

	private ParkingSpot getNextCarParkingSpot(ParkingSpot currentParkingSpot,
			VehicleOwnerPrivilege vehicleOwnerPrivilege) throws ParkingLotFullException {
		int f = currentParkingSpot.getFloor();
		int r = currentParkingSpot.getRow();
		int c = currentParkingSpot.getColumn();
		int s = currentParkingSpot.getStack();
		// System.out.println(currentParkingSpot);
		if (s < Const.STACK - 1) {
			s++;
		} else {
			s = 0;
			if (c < Const.COLUMN - 1) {
				c++;
			} else {
				c = 0;
				if (r < Const.ROW - 1) {
					r++;
				} else {
					r = 0;
					if (f < Const.FLOOR - 1) {
						f++;
					} else {
						logger.info("Parking Lot is Full, could not add More Vehicle");
						return null;
						//throw new ParkingLotFullException("Parking Lot is Full, could not add More Vehicle");
					}
				}
			}

		}
		if ((parkingLot[f][r][c][s].isEmpty())
				&& ((parkingLot[f][r][c][s].isRoyal() && vehicleOwnerPrivilege == VehicleOwnerPrivilege.ROYAL)
						|| (vehicleOwnerPrivilege == VehicleOwnerPrivilege.ELDER && s == 0)
						|| (vehicleOwnerPrivilege == VehicleOwnerPrivilege.NORMAL))) {
			return parkingLot[f][r][c][s];

		} else {
			return getNextCarParkingSpot(parkingLot[f][r][c][s], vehicleOwnerPrivilege);
		}

		// return null;
	}

	private ParkingSpot addBikeInParkingLot(String vehicleRCNo) {
		ParkingSpot allotedSpot = null;
		if(nextBike == null){
			logger.info("No Space available for bike");
			return null;
		}
		allotedSpot = nextBike;

		// set is empty flag to false
		allotedSpot.setEmpty(false);

		// add vehiicleRCNumber key and parking spot as value in the map
		bikeMap.put(vehicleRCNo, allotedSpot);

		if (allotedSpot.getNumberOfBikes() == 0) {
			// Reducing free parking spots
			this.freeParkingSpots--;
		}

		// incrementing number of bikes in parking spot
		allotedSpot.incrementNumberOfBikes();

		// adjust all next pointers if they are not empty
		adjustAllNextPointersAfterAdd();

		return allotedSpot;
	}

	private ParkingSpot getNextBikeParkingSpot(ParkingSpot currentBikeSpot) throws ParkingLotFullException {
		try {
			int totalBikes = currentBikeSpot.getNumberOfBikes();
			if (totalBikes < 2) {
				return currentBikeSpot;
			} else if (totalBikes >=3 || (totalBikes == 2 && currentBikeSpot.getStack() == 1)) {
				return getNextCarParkingSpot(currentBikeSpot, VehicleOwnerPrivilege.NORMAL);
			} else {
				// case when total bikes in parking spot is 2 and stack is 0
				// that
				// means lover stack
				if (!parkingLot[currentBikeSpot.getFloor()][currentBikeSpot.getRow()][currentBikeSpot
						.getColumn()][currentBikeSpot.getStack() + 1].isCarParked()) {
					return currentBikeSpot;
				}
				return getNextCarParkingSpot(currentBikeSpot, VehicleOwnerPrivilege.NORMAL);
			}
		} catch (ParkingLotFullException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ParkingSpot removeVehicleFromParkingLot(String vehicleRCNo, VehicleType vehicleType) {
		logger.info(
				"Removing vehicle RC Number : " + vehicleRCNo + " Vehicle Type: " + vehicleType + " from Parking lot");
		try {
			ParkingSpot removedParkingSlot = null;
			if (vehicleType == VehicleType.CAR) {
				removedParkingSlot = removeCarFromParkingLot(vehicleRCNo);
			} else if(vehicleType == VehicleType.BIKE){
				removedParkingSlot = removeBikeFromParkingLot(vehicleRCNo);
			}
			logger.info("Vehicle :" + vehicleRCNo + " Removed from : " + removedParkingSlot);
			return removedParkingSlot;
		} catch (VehicleNotFoundException e) {
			e.printStackTrace();
			return null;
		}

	}

	private ParkingSpot removeCarFromParkingLot(String vehicleRCNo) {
		if (carMap.get(vehicleRCNo) == null) {
			throw new VehicleNotFoundException(vehicleRCNo + " not parked in the parking lot");
		}
		ParkingSpot allotedSpot = carMap.remove(vehicleRCNo);
		allotedSpot.setCarParked(false);

		// Reducing free parking spots
		this.freeParkingSpots++;

		// adjust all next pointers if they are not empty
		adjustAllNextPointersAfterRemoval(allotedSpot);
		return allotedSpot;

	}

	private void adjustAllNextPointersAfterRemoval(ParkingSpot allotedSpot) {

		if (allotedSpot.compareTo(nextNormal) < 0) {
			nextNormal = allotedSpot;
		}
		if (allotedSpot.compareTo(nextElder) < 0) {
			nextElder = allotedSpot;
		}
		if (allotedSpot.isRoyal() && allotedSpot.compareTo(nextRoyal) < 0) {
			nextRoyal = allotedSpot;
		}
		if (allotedSpot.compareTo(nextBike) < 0 && nextBike.getNumberOfBikes() == 0) {
			nextBike = allotedSpot;
		}

	}

	private ParkingSpot removeBikeFromParkingLot(String vehicleRCNo) {
		if (bikeMap.get(vehicleRCNo) == null) {
			throw new VehicleNotFoundException(vehicleRCNo + " not parked in the parking lot");
		}
		ParkingSpot allotedSpot = bikeMap.remove(vehicleRCNo);
		int noOfbikes = allotedSpot.getNumberOfBikes();

		if (noOfbikes == 1) {
			if (allotedSpot.getStack() == 0 && allotedSpot.getNumberOfBikes() == 3) {
				// set is empty flag to true
				allotedSpot.setEmpty(true);
				ParkingSpot upperStack = parkingLot[allotedSpot.getFloor()][allotedSpot.getRow()][allotedSpot
						.getColumn()][allotedSpot.getStack() + 1];
				if (upperStack.getNumberOfBikes() == 0 & upperStack.isCarParked() == false) {
					// if lover stack have 2 bikes now and upper stack is not
					// having bikes or cars then it should be visible to car
					// parking
					upperStack.setEmpty(true);
				}
			} else if (allotedSpot.getStack() == 1
					&& parkingLot[allotedSpot.getFloor()][allotedSpot.getRow()][allotedSpot.getColumn()][allotedSpot
							.getStack() - 1].getNumberOfBikes() == 3) {
				// in this case lover stack have 3 bikes therefore we can't
				// release the upper stack for cars. It should not be visible to
				// car parking unless
				// there is 2 bikes in lower stack.
				allotedSpot.setEmpty(false);
			} else {
				allotedSpot.setEmpty(true);
			}
			adjustAllNextPointersAfterRemoval(allotedSpot);
			this.freeParkingSpots++;
		}

		// incrementing number of bikes in parking spot
		allotedSpot.decrementNumberOfBikes();
		return allotedSpot;

	}

	public int getFreeParkingSpots() {
		return this.freeParkingSpots;
	}

	public int getTotalCarParked() {
		return carMap.size();
	}

	public int getTotalBikeParked() {
		return bikeMap.size();
	}

	public void printAllParkedCar() {
		for (Map.Entry<String, ParkingSpot> e : this.carMap.entrySet()) {
			System.out.println("Car No " + e.getKey() + " is Parked at " + e.getValue());
		}
	}

	public void printAllParkedBike() {
		for (Map.Entry<String, ParkingSpot> e : this.bikeMap.entrySet()) {
			System.out.println("Bike No " + e.getKey() + " is Parked at " + e.getValue());
		}
	}

	public void printSummary() {
		logger.info("Parking Lot Summary");
		System.out.println("*****************************************************************************");
		System.out.println("Total Free parking spots " + getFreeParkingSpots());
		System.out.println("Total Car Parked " + getTotalCarParked());
		System.out.println("Total Bike Parked " + getTotalBikeParked());
		System.out.println("*****************************************************************************");
		printAllParkedCar();
		System.out.println("*****************************************************************************");
		printAllParkedBike();
		System.out.println("*****************************************************************************");

	}

}
