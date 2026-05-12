package org.example;

import java.util.Set;

public class Elevator {
    private ElevatorState elevatorState;
    private int currentFloor;
    private Request[] requests;

    Elevator() {
        elevatorState = ElevatorState.IDLE;
        currentFloor = 0;
        requests = new Request[ElevatorController.getNumFloors()];
    }

    public void step() {
        /*
        CORE LOGIC: continue in current direction until no more requests in that direction. then decide to go idle or reverse.
            1. if current floor has a request, open and clear the request.
            2. if idle and requests is not empty, serve the next request - up then down,
            3. if going_down: check if there are requests down - if not check if there are requests up and reverse
            4. if going up: check if there are requests up - if not check if there are requests down and reverse.

        EDGE CASES
            1. topmost floor and going up
            2. down floor and going down.
         */

        if (requests[currentFloor] != null) {
            open();
            requests[currentFloor] = null;
            return;
        }

        if (elevatorState == ElevatorState.IDLE) {
            for (int i = currentFloor + 1; i < ElevatorController.getNumFloors(); i++) {
                if (requests[i] != null) {
                    elevatorState = ElevatorState.GOING_UP;
                    currentFloor++;
                    return;
                }
            }
            for (int i = currentFloor - 1; i >= 0; i--) {
                if (requests[i] != null) {
                    elevatorState = ElevatorState.GOING_DOWN;
                    currentFloor--;
                    return;
                }
            }
        }
        else if (elevatorState == ElevatorState.GOING_DOWN) {
            for (int i = currentFloor - 1; i >= 0; i--) {
                if (requests[i] != null) {
                    elevatorState = ElevatorState.GOING_DOWN;
                    currentFloor--;
                    return;
                }
            }
            for (int i = currentFloor + 1; i < ElevatorController.getNumFloors(); i++) {
                if (requests[i] != null) {
                    elevatorState = ElevatorState.GOING_UP;
                    currentFloor++;
                    return;
                }
            }
            elevatorState = ElevatorState.IDLE;
        }
        else {
            for (int i = currentFloor + 1; i < ElevatorController.getNumFloors(); i++) {
                if (requests[i] != null) {
                    elevatorState = ElevatorState.GOING_UP;
                    currentFloor++;
                    return;
                }
            }
            for (int i = currentFloor - 1; i >= 0; i--) {
                if (requests[i] != null) {
                    elevatorState = ElevatorState.GOING_DOWN;
                    currentFloor--;
                    return;
                }
            }
            elevatorState = ElevatorState.IDLE;
        }
    }

    public void open() {

    }

    // this is for DESTINATION requests on panel AND called by Elevator Controller.
    public boolean add(Request request) {
        /*
        Core logic:
        1. Add to requests[floor] = request
        Edge cases
        1. if request.floor == current floor -> reject insane.
        2. if request already present, ignore, we are already going there.
         */
        if (request.floor == currentFloor) return false;
        if (requests[request.floor] != null) return false;
        requests[request.floor] = request;
        return true;
    }
}
