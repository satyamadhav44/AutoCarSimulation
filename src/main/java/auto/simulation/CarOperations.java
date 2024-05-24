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
        userCar.setX_direction(Integer.parseInt(position[0].strip()));
        userCar.setY_direction(Integer.parseInt(position[1].strip()));
        userCar.setCarDir(String.valueOf(Direction.valueOf(position[2].strip())));
        System.out.println(carCommand.replace("{}", userCar.getName()));
        userCar.setRunSteps(input.readLine().strip());
        return userCar;
    }

    public static int runCarSimulation(List<Car> cars, BufferedReader input, int[][] simFiled, int rows, int columns) throws IOException {
        int reRunChoice = 0;
        /* Begin simulation */
        /* placing the cars on the filed
         * marking the location in the filed by carId */
        int uniqueCarId = 1;
        for (Car car : cars) {
            car.setCarId(uniqueCarId++);
            car.setCollideCarId(0);
            simFiled[car.getX_direction()][car.getY_direction()] = car.getCarId();
        }
        // find the longest route among all the cars
        int longestRoute = cars.stream().map(Car::getRunSteps).max(Comparator.comparingInt(String::length)).get().length();
        int steps = 0; // to track the loop for car movement
        boolean checkCar = false;
        while (steps < longestRoute) {

            checkCollision(simFiled, cars, (steps));

            //now move the car and update the field position based on car direction.
            for (Car car : cars) {
                //update the collide status of the other car
                int colloideCarId = car.getCollideCarId();
                Optional<Car> colloidCar = cars.stream().filter(c -> c.getCarId() == colloideCarId).findAny();
                colloidCar.ifPresent(value -> value.setCollideCarId(car.getCarId()));
                if (steps < car.getRunSteps().length() && colloideCarId == 0) {
                    char instruction = car.getRunSteps().charAt(steps);
                    switch (instruction) {
                        case 'F':

                            if (car.getCarDir().equals(Direction.N.getValue())) {
                                // north : incrementing Y axis
                                int increment = car.getY_direction() + 1;
                                simFiled = car.move(simFiled, rows, columns, car.getX_direction(), increment);
                                simFiled[car.getX_direction()][car.getY_direction()] = car.getCarId();
                            } else if (car.getCarDir().equals(Direction.S.getValue())) {
                                // south : decrement Y axis
                                int decrement = (car.getY_direction() - 1 <= 0) ? car.getY_direction() : car.getY_direction() - 1;
                                simFiled = car.move(simFiled, rows, columns, car.getX_direction(), decrement);
                                simFiled[car.getX_direction()][car.getY_direction()] = car.getCarId();
                            } else if (car.getCarDir().equals(Direction.E.getValue())) {
                                // East : increment X axis
                                int increment = car.getX_direction() + 1;
                                simFiled = car.move(simFiled, rows, columns, increment, car.getY_direction());
                                simFiled[car.getX_direction()][car.getY_direction()] = car.getCarId();
                            } else {
                                // West : decrement X axis
                                int decrement = (car.getX_direction() - 1 <= 0) ? car.getX_direction() : car.getX_direction() - 1;
                                simFiled = car.move(simFiled, rows, columns, decrement, car.getY_direction());
                                simFiled[car.getX_direction()][car.getY_direction()] = car.getCarId();
                            }
                            break;
                        case 'R':
                            // turning clockwise
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
                            // counter clockwise
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
            steps++;
        }

        System.out.println(resultMsg);
        for (Car car : cars) {
            System.out.println("- " + car.getName() + ", (" + car.getX_direction() + "," + car.getY_direction() + ") " + car.getCarDir());
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
            int car_x = car.getX_direction();
            int car_y = car.getY_direction();
            int carId = field[car_x][car_y];
            if (field[car_x][car_y] != car.getCarId() && car.getCollideCarId() == 0) {
                Optional<Car> otherCar = cars.stream().filter(c -> c.getCarId() == carId).findAny();
                otherCar.ifPresent(value -> System.out.println("- " + car.getName() + ", collides with " + value.getName() + " at (" + car_x + "," + car_y + ") at step " + step));
                System.out.println();// new line
            }
        }
    }
}
