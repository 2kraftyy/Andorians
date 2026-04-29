package edu.desu.cis.robot.control;

import edu.desu.cis.robot.commands.MBot2;

/**
 *
 * Logic: Monitor camera for Red Sample, signal the find, and stop movement.
 */
public class ObjectDetection {
    /**
     *
     * @param mbot The active robot instance.
     */
    public void execute(MBot2 mbot) {
        
        // 1. Read the color name from the camera
        // The MBot2 library returns a String like "red", "green", or "none"
        String detectedColor = mbot.getColorObjectFromCamera();

        // 2. TRIGGER: Check if the camera sees "red"
        if (detectedColor != null && detectedColor.equalsIgnoreCase("red")) {

            // 3. ACTION: Stop the robot using the validated stop() method
            mbot.stop();

            // 4. SIGNAL: Turn on the LEDs for a visual cue
            triggerStarfleetSignal(mbot);

            System.out.println("Mission Success: Red Sample Identified by Sia.");
        }
    }


    /**
     * Uses validated MBot2 methods for signaling.
     */
    private void triggerStarfleetSignal(MBot2 mbot) {
        // Visual: Turn on LEDs 1 and 2 to Red (RGB: 255, 0, 0)
        // Based on MBot2.java: turnLedOn(int id, int red, int green, int blue)
        mbot.turnLedOn(1, 255, 0, 0); 
        mbot.turnLedOn(2, 255, 0, 0); 
        
         
        // so we are using the LEDs as the primary signal.
    }

}
