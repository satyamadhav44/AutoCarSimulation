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

    private int carId; // unique Car Id for tracking
    private String name; // name of the car
    private int x_direction; // initial position of the car in x coordinate
    private int y_direction; // initial position of the car in y coordinate
    private String carDir; //initial direction
    private String runSteps; // directional commands to run the car
    private int collideCarId; // name of the car with which current car collided.

    public int[][] move(int[][] simfiled, int field_x, int field_y, int move_x, int move_y) {
        simfiled[x_direction][y_direction] = 0;
        x_direction = move_x;
        y_direction = move_y;

        // check for out of bound and normalise
        x_direction = (x_direction + field_x) % field_x;
        y_direction = (y_direction + field_x) % field_y;
        if (simfiled[x_direction][y_direction] != 0) {
            collideCarId = simfiled[x_direction][y_direction];
        }
        return simfiled;
    }
}