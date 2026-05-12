package org.example;

import java.util.List;

public class NearestSameDirectionFirstStrategy implements ElevatorSelectionStrategy{

    @Override
    public Elevator chooseBestElevator(Request request, List<Elevator> elevators) {
        /*
        CORE LOGIC:
        a. See if there is nearest elevator going in same direction to reach this floor.
        b. If not, choose nearest idle elevator
        c. If not, choose nearest elevator.
         */
        if (request.requestType == RequestType.DESTINATION) return null;

        Elevator best = findNearestSameDirection(request.floor, request.requestType, elevators);
        if (best == null) {
            best = findNearestIdle(request.floor, elevators);
        }
        if (best == null) {
            best = findNearest(request.floor, elevators);
        }
        return best;
    }

    private Elevator findNearest(int floor, List<Elevator> elevators) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            if (minDistance > Math.abs(elevator.currentFloor - floor)) {
                minDistance = Math.abs(elevator.currentFloor - floor);
                bestElevator = elevator;
            }
        }
        return bestElevator;
    }

    private Elevator findNearestIdle(int floor, List<Elevator> elevators) {
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            if (elevator.elevatorState == ElevatorState.IDLE && minDistance > Math.abs(elevator.currentFloor - floor)) {
                minDistance = Math.abs(elevator.currentFloor - floor);
                bestElevator = elevator;
            }
        }
        return bestElevator;
    }

    private Elevator findNearestSameDirection(int floor, RequestType requestType, List<Elevator> elevators) {
        /*
        Core logic:
        1. Go through each elevator and continue if elevator state and request type are not equal.
        2. If elevator state and request type is UP and currentfloor > floor -> this passed us.
        3. If elevator state and request type is DOWN and currentfloor < floor -> this passed us.
        4. Keep track of min elevator
        Edge cases:
        1. Destination request type is not served here.
         */
        Elevator bestElevator = null;
        int minDistance = Integer.MAX_VALUE;
        for (Elevator elevator : elevators) {
            if (elevator.elevatorState == ElevatorState.GOING_UP && requestType == RequestType.GO_DOWN) continue;
            if (elevator.elevatorState == ElevatorState.GOING_DOWN && requestType == RequestType.GO_UP) continue;
            if (elevator.elevatorState == ElevatorState.GOING_UP && requestType == RequestType.GO_UP) {
                if (elevator.currentFloor > floor) continue;
                else {
                    if (minDistance > floor - elevator.currentFloor) {
                        minDistance = floor - elevator.currentFloor;
                        bestElevator = elevator;
                    }
                }
            }
            if (elevator.elevatorState == ElevatorState.GOING_DOWN && requestType == RequestType.GO_DOWN) {
                if (elevator.currentFloor < floor) continue;
                else {
                    if (minDistance > elevator.currentFloor - floor) {
                        minDistance = elevator.currentFloor - floor;
                        bestElevator = elevator;
                    }
                }
            }

        }
        return bestElevator;
    }


}
