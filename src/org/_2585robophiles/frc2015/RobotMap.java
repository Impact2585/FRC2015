package org._2585robophiles.frc2015;

/**
 * This interface contains the constants such as port numbers
 */
public interface RobotMap {

	public static final int FRONT_RIGHT_DRIVE = 0,
							FRONT_LEFT_DRIVE = 1,
							REAR_RIGHT_DRIVE = 2,
							REAR_LEFT_DRIVE = 3,
							SIDEWAYS_DRIVE = 4,
							SIDEWAYS_DRIVE_2 = 5;
	public static final int LEFT_LIFT_1 = 6;
	public static final int LEFT_LIFT_2 = 7;
	public static final int RIGHT_LIFT_1 = 8;
	public static final int RIGHT_LIFT_2 = 9;
	
	public static final int GYRO = 1;
	public static final int ENCODER_A_CHANNEL = 2;
	public static final int ENCODER_B_CHANNEL = 3;
	
	public static final double FORWARD_RAMPING = .8;
	public static final double SIDEWAYS_RAMPING = .8;
	public static final int ROTATION_EXPONENT = 2;
	
}
