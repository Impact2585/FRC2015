/**
 * 
 */
package org._2585robophiles.frc2015;

import org._2585robophiles.lib2585.RobotEnvironment;

/**
 * @author michael
 *
 */
public class Environment extends RobotEnvironment {
	
	/**
	 * Just a default constructor
	 */
	public Environment() {
		super();
	}
	
	/**
	 * Initialize the systems
	 * @param robot the robot which the environment belongs to
	 */
	public Environment(Robot robot) {
		super(robot);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		
	}

}
