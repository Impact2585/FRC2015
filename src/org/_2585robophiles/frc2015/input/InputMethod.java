package org._2585robophiles.frc2015.input;

/**
 * Input for the robot
 */
public interface InputMethod {

	/**
	 * Positive means forward movement and negative means backwards movement
	 * @return value from -1 to 1 representing how fast the robot should move forward
	 */
	public abstract double forwardMovement();
	
	/**
	 * @return value -1 to 1 representing sideways movement of the robot
	 */
	public abstract double sidewaysMovement();
	
	/**
	 * @return turning value of robot from -1 to 1
	 */
	public abstract double rotation();
}
