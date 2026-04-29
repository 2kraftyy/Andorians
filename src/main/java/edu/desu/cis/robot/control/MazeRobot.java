package edu.desu.cis.robot.control;

import edu.desu.cis.robot.service.SensorSnapshot;

/**
 * A specific implementation of a robot controller that navigates a maze,
 * identifies objects, and performs actions based on the object's color.
 *
 */

public class MazeRobot extends RobotController {
    // Fields
    public boolean sampleFound;
    RobotState robotState;
    int stopThreshold;

    // Constructor
    public MazeRobot(String robotName) {
        super(robotName);

        sampleFound = false;
        robotState = RobotState.CRUISING;
        stopThreshold = 10;
    }

    public void switchState(RobotState newState){
        this.robotState = newState;
    }

    // FULLY FUNCTIONAL
    public String detectObject(){
        String detectedColor = mbot.getColorObjectFromCamera();

        if (detectedColor == "GREEN") {
            return "Movable Object";
        }
        else if (detectedColor == "BLUE") {
            return "Immovable Object";
        }
        else if (detectedColor == "RED") {
            return "Sample";
        }
        else if (detectedColor == "YELLOW") {
            return "Insertion Point";
        }

        return null;
    }

    public void run(){
        SensorSnapshot sensorData = awaitNewData();
        //-- TODO: IMPLEMENT BEHAVIORS INSIDE STATES WHEN THEY ARE COMPLETED

        while (true){
             String detectedObject = this.detectObject();
             if (detectedObject != null) {

             }
        }

        /*
        if (robotState == RobotState.CRUISING) {
            mbot.avoidCrashing(this.stopThreshold);
            mbot.forward(50);

            // Bot is close to an object
            if (sensorData.distance() < this.stopThreshold){
                // mbot.stop();
                // Disable behaviors running in background

                // Transition into analyzing object state
                // Added extra state for cleaner code & more straightforward FSM implementation
                this.switchState(RobotState.ANALYZING_OBJECT);
            }
        }

        else if (robotState == RobotState.ANALYZING_OBJECT) {
            // If obj is a movable object transition to MOVING_OBJECT
            // If obj is an immovable object transition to AVOIDING_OBJECT
            // If obj is the sample,
            // this.switchState(RobotState.AVOIDING_OBJECT) or
            // this.switchState(RobotState.MOVING_OBJECT)
        }

        else if (robotState == RobotState.AVOIDING_OBJECT) {
            // Do a procedure to avoid the object (rotate either L or R and move forward)
            // Go back to CRUISING
            this.switchState(RobotState.CRUISING);
        }
        else if (robotState == RobotState.MOVING_OBJECT) {
            // Do a procedure that moves the object
            // Once done moving the object and no obj is infront go back to CRUISING

            this.switchState(RobotState.CRUISING);
        }
        else {
            // Returning

            // First turn 180
            // Then go back to CRUISING
        }

         */
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
