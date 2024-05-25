package auto.simulation;

import auto.simulation.model.Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class CarOperationsTest {

    /**
     * Method under test:
     * {@link CarOperations#runCarSimulation(List, BufferedReader, int[][], int, int)}
     */

    // filed of 10 X 10
    int simFiled[][]={
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0}
    };
    // simulation with one car
    @Test
    void testRunCarSimulation1() throws IOException {

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(Car.builder()
                .carDir("N")
                .carId(1)
                .collideCarId(0)
                .name("A")
                .runSteps("FFRFFFFRRL")
                .x_dx(1)
                .y_dy(2)
                .build());
        CarOperations.runCarSimulation(cars, new BufferedReader(new StringReader("2"), 1),
               simFiled, 10, 10);
    }
    // simulation with 2 cars A and B , to check for collision
    @Test
    void testRunCarSimulation2() throws IOException {

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(Car.builder()
                .carDir("N")
                .carId(1)
                .collideCarId(0)
                .name("A")
                .runSteps("FFRFFFFRRL")
                .x_dx(1)
                .y_dy(2)
                .build());
        cars.add(Car.builder()
                .carDir("W")
                .carId(2)
                .collideCarId(0)
                .name("B")
                .runSteps("FFLFFFFFFF")
                .x_dx(7)
                .y_dy(8)
                .build());
        CarOperations.runCarSimulation(cars, new BufferedReader(new StringReader("2"), 1),
                simFiled, 10, 10);
    }

    // simulation with 3 cars where car A,C collide and B completes its route
    @Test
    void testRunCarSimulation3() throws IOException {

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(Car.builder()
                .carDir("N")
                .carId(1)
                .collideCarId(0)
                .name("A")
                .runSteps("FFRFFFFRRL")
                .x_dx(1)
                .y_dy(2)
                .build());
        cars.add(Car.builder()
                .carDir("W")
                .carId(2)
                .collideCarId(0)
                .name("B")
                .runSteps("FFLFFFFFFF")
                .x_dx(7)
                .y_dy(8)
                .build());
        cars.add(Car.builder()
                .carDir("S")
                .carId(3)
                .collideCarId(0)
                .name("C")
                .runSteps("FFFF")
                .x_dx(3)
                .y_dy(8)
                .build());
        CarOperations.runCarSimulation(cars, new BufferedReader(new StringReader("2"), 1),
                simFiled, 10, 10);
    }

    // test example for commands beyond the boundary of the filed
    @Test
    void testRunCarSimulation4() throws IOException {

        ArrayList<Car> cars = new ArrayList<>();
        cars.add(Car.builder()
                .carDir("S")
                .carId(1)
                .collideCarId(0)
                .name("A")
                .runSteps("FFR")
                .x_dx(6)
                .y_dy(1)
                .build());
        cars.add(Car.builder()
                .carDir("E")
                .carId(2)
                .collideCarId(0)
                .name("B")
                .runSteps("FR")
                .x_dx(9)
                .y_dy(3)
                .build());
        cars.add(Car.builder()
                .carDir("N")
                .carId(3)
                .collideCarId(0)
                .name("C")
                .runSteps("FFL")
                .x_dx(9)
                .y_dy(9)
                .build());
        CarOperations.runCarSimulation(cars, new BufferedReader(new StringReader("2"), 1),
                simFiled, 10, 10);
    }

}
