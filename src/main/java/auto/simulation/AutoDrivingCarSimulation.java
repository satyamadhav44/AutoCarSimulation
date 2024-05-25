package auto.simulation;

import auto.simulation.model.Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static auto.simulation.constants.UserMsgPrompts.*;

public class AutoDrivingCarSimulation {

    public static void main(String[] args) throws IOException {
        /* to interact through command line */
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        /* to store the user car details */
        List<Car> cars = new ArrayList<>();
        /* to track the user input status */
        boolean simRun = true;
        /* the loop will run as long as the user wants to continue */
        do {
            cars.clear();
            System.out.println(welcomeMsg);
            System.out.println(inputPrompt);
            String[] simField = userInput.readLine().split("\\s");
            /*create a simulation field*/
            System.out.println(inputMsg + simField[0] + " x " + simField[1]);
            int rows = Integer.parseInt(simField[0].strip());
            int columns = Integer.parseInt(simField[1].strip());
            int[][] simulationField = new int[rows][columns];
            /*initialising the 2D filed matrix with 0's  */
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    simulationField[i][j] = 0;
                }
            }
            boolean opSelect = true;
            do {
                System.out.println(selOptMsg);
                System.out.println(addCar);
                System.out.println(runSim);
                int optInput = Integer.parseInt(userInput.readLine().strip());
                switch (optInput) {
                    case 1:
                        cars.add(CarOperations.addUserCar(userInput));
                        System.out.println(carInfoDisp);
                        for (Car car : cars) {
                            System.out.println("- " + car.getName() + " " + "(" + car.getX_dx() + "," + car.getY_dy() + ")" +
                                    " " + car.getCarDir() + ", " + car.getRunSteps());
                            System.out.println();
                        }
                        break;
                    case 2:
                        int rerun = CarOperations.runCarSimulation(cars, userInput, simulationField, rows, columns);
                        if (rerun == 1) opSelect = false;
                        if (rerun == 2) {
                            System.out.println(exitMsg);
                            System.exit(0);
                        }
                        break;
                    default:
                        System.err.println("invalid selection , please select option 1 or 2 ");
                }
            } while (opSelect);

        } while (simRun);

    }
}