# GondorParkingProblem

Design of the case study:
To know exact relative position of the parking spots in the parking lot I have used a multidimensional matrix to create parkingLot object.
parkingLot[f][r][c][s]
All the indicies are zero based index
f: number of floor
r: row number
c: column number
s: stack (0 stands for lower, 1 stands for upper)

To achieve nearest possible parking spot to be allotted:
Assumption: Exit is near parkingLot[0][0][0][0] that means the nearest parking spot near exit is at floor number 0, row number 0, column number 0 and stack 0 (lower stack).
The next nearest parking spot is when we increment stack. If stack is highest (s==1) then make s=0 and increment column, if column is max then reset c=0 and increment row, if row is highest then increment floor, if floor is highest then show parking is full.

To achieve this I created some pointers (nextNormal, nextElder, nextRoyal, nextBike)
These pointers are having available parking spot  of Car for Normal privilege, Elder privilege or Royal Privilege and next parking spot for bike.
These pointers will be updated after every addition/removal of vehicle to/from the parking lot.

For bike, parking every upper stack can have at most 2 bikes and every lover stack at most 3 bikes if upper stack have bikes only. If upper stack have car then lower stack at most 2 bikes. Therefore, we can achieve 5 bikes with stacking and 2 bikes in upper and lower each without stacking.
 
To store cars and bikes in parkingLot I have used Hashmap<String, ParkingSpot> for cars and bikes separately. Where Vehicle RC Number is key and allotted parking spot is value.

