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
    private int x_dx; // position of the car in x coordinate
    private int y_dy; // position of the car in y coordinate
    private String carDir; // car direction
    private String runSteps; // directional commands to run the car
    private int collideCarId; // name of the car with which current car collided.

    public int[][] move(int[][] simfiled, int field_x, int field_y, int move_x, int move_y) {
        simfiled[x_dx][y_dy] = 0;
        x_dx = move_x;
        y_dy = move_y;

        // check for out of bound and normalise
        x_dx = (x_dx + field_x) % field_x;
        y_dy = (y_dy + field_x) % field_y;
        if (simfiled[x_dx][y_dy] != 0) {
            collideCarId = simfiled[x_dx][y_dy];
        }
        return simfiled;
    }
}