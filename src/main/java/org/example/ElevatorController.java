package org.example;

import java.util.ArrayList;
import java.util.List;

public class ElevatorController {

    private List<Elevator> elevators;
    private ElevatorSelectionStrategy strategy;
    private static int NUM_FLOORS = 10, NUM_ELEVATORS = 3;

    public ElevatorController() {
        elevators = new ArrayList<>();
        for (int i = 0 ; i < NUM_ELEVATORS; i++) {
            elevators.add(new Elevator());
        }
        strategy = new NearestSameDirectionFirstStrategy();
    }

    // THIS IS FOR HALL CALL REQUESTS
    public boolean add(int floor, RequestType requestType) {
        /* CORE LOGIC
        1. Create a Request Type object.
        2. Choose an elevator to add this request to.
        3. Add this request in that elevator set.
            EDGE CASES
        1. Validate if request is valid [up and already at top floor type]
        */
        if (floor == NUM_FLOORS-1 && requestType == RequestType.GO_UP) return false;
        if (floor == 0 && requestType == RequestType.GO_DOWN) return false;
        Request request = new Request(floor, requestType);
        Elevator elevator = strategy.chooseBestElevator(request, elevators);
        return elevator.add(request);
    }

    public void step() {
        for (Elevator elevator : elevators) {
            elevator.step();
        }
    }

    public static int getNumFloors(){
        return NUM_FLOORS;
    }
    public static int getNumElevators(){
        return NUM_ELEVATORS;
    }

}
