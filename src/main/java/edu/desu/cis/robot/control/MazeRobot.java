package edu.desu.cis.robot.control;

import edu.desu.cis.robot.service.SensorSnapshot;

/**
 * A specific implementation of a robot controller that navigates a maze,
 * identifies objects, and performs actions based on the object's color.
 *
 */

public class MazeRobot extends RobotController {
    // Fields
    int stopThreshold;
    RobotState robotState;

    // Constructor
    public MazeRobot(String robotName) {
        super(robotName);

        robotState = RobotState.CRUISING;
        stopThreshold = 10;
    }

    public void switchState(RobotState newState){
        this.robotState = newState;
    }

    public void run(){
        SensorSnapshot sensorData = awaitNewData();
        //-- TODO: IMPLEMENT BEHAVIORS INSIDE STATES WHEN THEY ARE COMPLETED
        if (robotState == RobotState.CRUISING) {
            System.out.println("Forward");
            mbot.avoidCrashing(this.stopThreshold);
            mbot.forward(50);

            // Bot is close to an object
            if (sensorData.distance() < this.stopThreshold){
                // mbot.stop();

                // Check type of object to determine which state to transition to
                // if Obj is a movable object transition to MOVING_OBJECT
                // if Obj is an immovable object transition to AVOIDING_OBJECT
            }
        }

        else if (robotState == RobotState.AVOIDING_OBJECT) {
            // Do a procedure to avoid the object (rotate either L or R and move forward)
            // Go back to CRUISING

        }
        else if (robotState == RobotState.MOVING_OBJECT) {
            // Run through the object without crashing behavior
            // Once done running through and no obj is infront go back to CRUISING

        }
        else {
            // Returning

            // First turn 180
            // Then go back to CRUISING
        }
    }

    /**
     * The main entry point for the MazeRobot application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try (MazeRobot amazin = new MazeRobot("Sieh")) {
            // System.out.print("pls work");
            amazin.run();
        }
    }
}
