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

        if (detectedColor.equals("GREEN")) {
            return "Movable Object";
        }
        else if (detectedColor.equals("BLUE")) {
            return "Immovable Object";
        }
        else if (detectedColor.equals("RED")) {
            return "Sample";
        }
        else if (detectedColor.equals("YELLOW")) {
            return "Insertion Point";
        }

        return null;
    }

    // FULLY FUNCTIONAL
    public void moveObject(){
        mbot.moveObject();
    }

    public void run(){

        //-- TODO: IMPLEMENT BEHAVIORS INSIDE STATES WHEN THEY ARE COMPLETED
        //mbot.followLine();
        boolean SCHEDULED_BEHAVIORS_ACTIVE = false;

        while (true) {
            if (robotState == RobotState.CRUISING) {

                if (!SCHEDULED_BEHAVIORS_ACTIVE) {
                    SCHEDULED_BEHAVIORS_ACTIVE = true;
                    mbot.avoidCrashing(this.stopThreshold);
                    System.out.println("Starting behaviors");
                }

                mbot.followLine();
                System.out.println("Cruising");

                // Bot is close to an object
                SensorSnapshot sensorData = awaitNewData();
                if (sensorData.distance() < this.stopThreshold){
                    System.out.println("Object closer than threshold");
                    mbot.stop();
                    // Disable behaviors running in background
                    mbot.stopAllBehaviors(); SCHEDULED_BEHAVIORS_ACTIVE = false;
                    System.out.println("Bot stopped & behaviors disabled");

                    // Transition into analyzing object state
                    // Added extra state for cleaner code & more straightforward FSM implementation
                    this.switchState(RobotState.ANALYZING_OBJECT);
                }
            }

            else if (robotState == RobotState.ANALYZING_OBJECT) {
                // If obj is a movable object transition to MOVING_OBJECT
                // If obj is an immovable object transition to AVOIDING_OBJECT etc

                String detectedObject = detectObject();
                System.out.println(detectedObject);
                if (detectedObject.equals("Immovable Object")){
                    this.switchState(RobotState.AVOIDING_OBJECT);
                }

                else if (detectedObject.equals("Movable Object")) {
                    this.switchState(RobotState.MOVING_OBJECT);
                }

                else if (detectedObject.equals("Sample")) {
                    this.switchState(RobotState.COLLECTING_SAMPLE);
                }

                else if (detectedObject.equals("Insertion Point")) {
                    this.switchState(RobotState.MISSION_COMPLETE);
                }
            }

            else if (robotState == RobotState.MOVING_OBJECT) {
                // Do a procedure to avoid the object (rotate either L or R and move forward)
                System.out.println("Moving Object");
                moveObject();
                // Go back to CRUISING
                System.out.println("Object Moved");
                this.switchState(RobotState.CRUISING);
            }
            else if (robotState == RobotState.AVOIDING_OBJECT) {
                // Do a procedure that moves the object
                // Once done moving the object and no obj is infront go back to CRUISING

                this.switchState(RobotState.CRUISING);
            }
            else if (robotState == RobotState.MISSION_COMPLETE)  {

                break; // Exit the loop
            }
        }
        System.out.println("Finished");
    }

    /**
     * The main entry point for the MazeRobot application.
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        try (MazeRobot amazin = new MazeRobot("Sieh")) {
            amazin.run();
        }
    }
}
