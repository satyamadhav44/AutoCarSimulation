# Environment
- windows 11
- java 17
- apache maven-3.9.6

## About The Project

The project is about simulating Autonomous car driving based on the driving instructions , i have designed and build the given problem statement
using java 17 as programing language on maven as project package.

As with the given problem statement I have considered a 2-dimensional integer array as car field where coordinates [x,y] indicate location of car at any given point.
with each car having a unique integer Id.

- **Assumptions**:
   - the field ranges from [0,0] < [n,m] where n and m are maximum limits of the field.
   - car execution commands as below
      - L: rotate car by 90 degree to left from current direction.
      - R: rotate car by 90 degree to right from current direction.
      - F: move forward by 1 grid point from current position in current direction.
   - move the car on F command as long as the car is not going beyond the boundary of field.
   - at each step all cars execute each instruction in order until either the cars reaches it's destination and/or cars encounter collision.

- **Explaination**

   - After gathering car details as described in the problem statement from the user using input stream console documented in the code.
   - method "runCarSimulation" with signature as List of cars and a 2D array representing as car field under class CarOperation performing the logic as listed in assumptions.
        - It begins by placing all the cars in the field using for loop, where each car is identified with a unique car id (integer)
        - then finding the longest route among the list of cars to simulate steps
        - At each step perform check for collision (checkCollision method) between all the cars if any 2 car id's are found to be at same location they are said to be collied and information is displayed
          and in further steps these ares are not proceeded further with instructions as by setting 'collideCarId' property of car with the collided car and vise versa in 'move' method (refer to code for detailed explanation)
        - at each step all the car are move with each instruction given, checking the boundary condition.( see the code for more details ) until the collision and/or route completion
        - at the end all the car information is displayed to the user containing final car location and direction it is facing
   above is a high level explanation of execution pls refer code for more details.
     
## Contact
- Satya Venkata Medhi Theegela
- satyamadhav44@rediffmail.com/satyamadhav44@gmail.com
