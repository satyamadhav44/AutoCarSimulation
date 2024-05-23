package auto.simulation.model;

/*
* Model class to store information related to car.
* */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Car {

    private String name; // name of the car
    private int x_direction; // initial position of the car in x coordinate
    private int y_direction; // initial position of the car in y coordinate
    private String carInitialDir;
    private String runSteps; // directional commands to run the car
}