package edu.desu.cis.robot.control;

import edu.desu.cis.robot.commands.MBot2;

/**
 * Component: Sample Identification (Sia's Role)
 * Logic: Monitor sensors for Red Sample, signal the find, and stop movement.
 */
public class SampleDetection {

    // Threshold for detecting "Red" - adjust this based on lab testing
    private final int RED_THRESHOLD = 200; 
    public boolean isSampleFound = false;

    /**
     * Executes the detection logic.
     * @param mbot The active mBot instance from the controller.
     */
    public void execute(MBot2 mbot) {
        
        // 1. Read the color sensor intensity
        int redValue = mbot.getColorSensorValue(); 

        // 2. TRIGGER: If red intensity is high and we haven't flagged it yet
        if (redValue > RED_THRESHOLD && !isSampleFound) {
            isSampleFound = true;

            // 3. ACTION: Halt movement
            mbot.stop();

            // 4. SIGNAL: Trigger the required audible/visual cue
            triggerStarfleetSignal(mbot);
            
            System.out.println("Mission Success: Red Sample Identified by Sia.");
        }
    }

    /**
     * Fulfills the requirement for an audible and visual signal upon discovery.
     */
    private void triggerStarfleetSignal(MBot2 mbot) {
        // Visual: Set the board/sensor LEDs to Red
        mbot.setDisplayColor(255, 0, 0); 
        
        // Audible: Play a success tone (A4 note)
        mbot.playTone(440, 500);         
    }
}
