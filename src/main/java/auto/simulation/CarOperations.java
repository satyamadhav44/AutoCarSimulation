package auto.simulation;

import auto.simulation.constants.Direction;
import auto.simulation.model.Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static auto.simulation.constants.UserMsgPrompts.*;

public class CarOperations {

    public static Car addUserCar(BufferedReader input) throws IOException {
        Car userCar = new Car();
        System.out.println(carNamePrompt);
        userCar.setName(input.readLine().strip());
        System.out.println(carInitPosPrompt.replace("{}", userCar.getName()));
        String[] position = input.readLine().split("\\s");
        userCar.setX_dx(Integer.parseInt(position[0].strip()));
        userCar.setY_dy(Integer.parseInt(position[1].strip()));
        userCar.setCarDir(String.valueOf(Direction.valueOf(position[2].strip())));
        System.out.println(carCommand.replace("{}", userCar.getName()));
        userCar.setRunSteps(input.readLine().strip());
        return userCar;
    }

    /*
     * Method signature:-
     * cars     : a list of cars details based on the user entry
     * input    : input for user
     * simField : a 2D array depicting simulation filed
     * rows     : max row of field
     * columns  : max column of filed
     * */
    public static int runCarSimulation(List<Car> cars, BufferedReader input, int[][] simFiled, int rows, int columns) throws IOException {
        int reRunChoice = 0;
        /* Begin simulation */
        /* start by placing the car in the fields to mark the car's initial positions */
        int uniqueCarId = 1;
        for (Car car : cars) {
            // assigning a unique integer id to each car
            car.setCarId(uniqueCarId++);
            // the below property tracks the carId of collided car
            car.setCollideCarId(0);
            simFiled[car.getX_dx()][car.getY_dy()] = car.getCarId();
        }
        // find the longest route among all the cars to keep the steps running
        int longestRoute = cars.stream().map(Car::getRunSteps).max(Comparator.comparingInt(String::length)).get().length();
        int steps = 0; // to progress car movement for each step
        // step zero to last step of route to complete the simulation until destination and to identify collision between cars
        while (steps < longestRoute) {

            // check for collision between cars for each step.
            checkCollision(simFiled, cars, (steps));

            //now move the car and update the field position based on car direction.
            for (Car car : cars) {
                //update the collide status of the other car
                int colloideCarId = car.getCollideCarId();
                /* the below statements sre setting collide property of each car with carID of it's collided car,
                   which is used later to track in check collision method */
                Optional<Car> colloidCar = cars.stream().filter(c -> c.getCarId() == colloideCarId).findAny();
                colloidCar.ifPresent(value -> value.setCollideCarId(car.getCarId()));
                /*below condition checks the step count against each car route to avoid over run and
                 *  if either the car route is exhausted or car met with collision further execution is skipped for those cars
                 * */
                if (steps < car.getRunSteps().length() && colloideCarId == 0) {
                    // moving instruction per car
                    char instruction = car.getRunSteps().charAt(steps);
                    // to identify and execute the car movement of each instruction type.
                    switch (instruction) {
                        case 'F':
                            if (car.getCarDir().equals(Direction.N.getValue())) {
                                // North : incrementing Y axis
                                int increment = ((car.getY_dy() + 1) <= (columns - 1)) ? car.getY_dy() + 1 : car.getY_dy();
                                simFiled = car.move(simFiled, rows, columns, car.getX_dx(), increment);
                                simFiled[car.getX_dx()][car.getY_dy()] = car.getCarId();
                            } else if (car.getCarDir().equals(Direction.S.getValue())) {
                                // South : decrement Y axis
                                int decrement = (car.getY_dy() - 1 < 0) ? car.getY_dy() : car.getY_dy() - 1;
                                simFiled = car.move(simFiled, rows, columns, car.getX_dx(), decrement);
                                simFiled[car.getX_dx()][car.getY_dy()] = car.getCarId();
                            } else if (car.getCarDir().equals(Direction.E.getValue())) {
                                // East : increment X axis
                                int increment = ((car.getX_dx() + 1) <= (rows - 1)) ? (car.getX_dx() + 1) : car.getX_dx();
                                simFiled = car.move(simFiled, rows, columns, increment, car.getY_dy());
                                simFiled[car.getX_dx()][car.getY_dy()] = car.getCarId();
                            } else {
                                // West : decrement X axis
                                int decrement = (car.getX_dx() - 1 < 0) ? car.getX_dx() : car.getX_dx() - 1;
                                simFiled = car.move(simFiled, rows, columns, decrement, car.getY_dy());
                                simFiled[car.getX_dx()][car.getY_dy()] = car.getCarId();
                            }
                            break;
                        case 'R':
                            // turning clockwise checking the current car direction
                            if (car.getCarDir().equals(Direction.N.getValue())) {
                                car.setCarDir(Direction.E.getValue());
                            } else if (car.getCarDir().equals(Direction.E.getValue())) {
                                car.setCarDir(Direction.S.getValue());
                            } else if (car.getCarDir().equals(Direction.S.getValue())) {
                                car.setCarDir(Direction.W.getValue());
                            } else {
                                car.setCarDir(Direction.N.getValue());
                            }
                            break;
                        case 'L':
                            // turning counterclockwise  checking the current car direction
                            if (car.getCarDir().equals(Direction.N.getValue())) {
                                car.setCarDir(Direction.W.getValue());
                            } else if (car.getCarDir().equals(Direction.W.getValue())) {
                                car.setCarDir(Direction.S.getValue());
                            } else if (car.getCarDir().equals(Direction.S.getValue())) {
                                car.setCarDir(Direction.E.getValue());
                            } else {
                                car.setCarDir(Direction.N.getValue());
                            }
                            break;
                    }
                }
            }
            ++steps;
        }
        // print the final location with coordinates and direction of each car
        System.out.println(resultMsg);
        for (Car car : cars) {
            System.out.println("- " + car.getName() + ", (" + car.getX_dx() + "," + car.getY_dy() + ") " + car.getCarDir());
        }
        System.out.println(); // new line
        System.out.println(selOptMsg);
        System.out.println(continueSim);
        System.out.println(exit);
        reRunChoice = Integer.parseInt(input.readLine().strip());
        return reRunChoice;
    }

    public static void checkCollision(int[][] field, List<Car> cars, int step) {
        for (Car car : cars) {
            int car_x = car.getX_dx();
            int car_y = car.getY_dy();
            int carId = field[car_x][car_y]; // get the carID of a car already at this location if any
            // cars are said to be collided when field having an exiting carId is overwritten by a new carId .
            if (field[car_x][car_y] != car.getCarId() && car.getCollideCarId() == 0) {
                //finding the car property of existing carID
                Optional<Car> otherCar = cars.stream().filter(c -> c.getCarId() == carId).findAny();
                otherCar.ifPresent(value -> System.out.println("- " + value.getName() + ", collides with " + car.getName() + " at (" + car_x + "," + car_y + ") at step " + step));
                System.out.println();// new line
            }
        }
    }
}
