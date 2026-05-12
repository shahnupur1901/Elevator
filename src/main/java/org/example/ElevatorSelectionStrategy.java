package org.example;

import java.util.List;

public interface ElevatorSelectionStrategy {

    public Elevator chooseBestElevator(Request request, List<Elevator> elevators);

}
