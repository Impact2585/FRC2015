package org.impact2585.frc2015.input;

import edu.wpi.first.wpilibj.Joystick;

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
	
	/**
	 * @return enable/disable straight driving
	 */
	public abstract boolean straightDrive();
	
	/**
	 * Analog input to make the lift go up
	 * @return value from 0 to 1 for how fast the lift should go up
	 */
	public abstract double analogLiftUp();
	
	/**
	 * Analog input to make the lift go down
	 * @return value from 0 to 1 for how fast the lift should go down
	 */
	public abstract double analogLiftDown();
	
	/**
	 * Digital input to make the lift go down
	 * @return boolean if the lift need to go down
	 */
	public abstract boolean digitalLiftDown();
	
	/**
	 * Digital input to make the lift go up
	 * @return boolean if the lift need to go up
	 */
	public abstract boolean digitalLiftUp();
	
	/**
	 * Move the lift to the setpoint below it's current setpoint
	 * @return input to make the setpoint the next lowest setpoint
	 */
	public abstract boolean liftSetpointDown();
	
	/**
	 * Same as liftSetpointDown() basically except for going up
	 * @return input to make the lift setpoint the next higher one
	 */
	public abstract boolean liftSetpointUp();
	
	/**
	 * @return input to make the lift setpoint the first one
	 */
	public abstract boolean liftSetpoint1();
	
	/**
	 * @return input to make the lift setpoint the second one
	 */
	public abstract boolean liftSetpoint2();
	
	/**
	 * @return input to make lift go to 3rd setpoint
	 */
	public abstract boolean liftSetpoint3();
	
	/**
	 * @return input to make lift go to 4th setpoint
	 */
	public abstract boolean liftSetpoint4();
	
	/**
	 * @return input to make the lift go to the ground setpoints
	 */
	public abstract boolean groundLift();
	
	/**
	 * @return input to stop a straight drive
	 */
	public abstract boolean stopStraightDrive();
	
	/**
	 * @return input to change the rotation sensitivity
	 */
	public abstract boolean changeSensitivity();
	
	/**
	 * @return an array of joysticks used for input
	 */
	public abstract Joystick[] joysticks();
	
}
