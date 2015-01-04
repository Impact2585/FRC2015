package org._2585robophiles.frc2015;

import org._2585robophiles.lib2585.ExecuterBasedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends ExecuterBasedRobot {
	
	private Environment environment;
    
	/* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#robotInit()
     */
	@Override
    public void robotInit() {
		environment = new Environment(this);
    }
    
}
