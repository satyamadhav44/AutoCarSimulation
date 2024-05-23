package auto.simulation;

import auto.simulation.constants.Direction;
import auto.simulation.model.Car;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static auto.simulation.constants.UserMsgPrompts.*;

public class CarOperations {

    public static Car addUserCar(BufferedReader input) throws IOException {
        Car userCar = new Car();
        System.out.println(carNamePrompt);
        userCar.setName(input.readLine().strip());
        System.out.println(carInitPosPrompt.replace("{}", userCar.getName()));
        String[] position=input.readLine().split("\\s");
        userCar.setX_direction(Integer.parseInt(position[0].strip()));
        userCar.setY_direction(Integer.parseInt(position[1].strip()));
        userCar.setCarInitialDir(String.valueOf(Direction.valueOf(position[2].strip())));
        System.out.println(carCommand.replace("{}", userCar.getName()));
        userCar.setRunSteps(input.readLine().strip());
        return userCar;
    }

    public static int runCarSimulation(List<Car> cars,BufferedReader input,int[][] simFiled) throws IOException{
        int reRunChoice =0;
        System.out.println("the simulation is complete ");
        System.out.println(selOptMsg);
        System.out.println(continueSim);
        System.out.println(exit);
        reRunChoice=Integer.parseInt(input.readLine().strip());
        return reRunChoice;
    }
}
