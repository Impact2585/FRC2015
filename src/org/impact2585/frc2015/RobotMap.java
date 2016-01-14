package org.impact2585.frc2015;

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
	public static final int LEFT_LIFT = 6;
	public static final int RIGHT_LIFT_1 = 8;
	public static final int RIGHT_LIFT_2 = 7;
	
	public static final int GYRO = 1;
	
	public static final int LEFT_ENCODER_A_CHANNEL = 2;
	public static final int LEFT_ENCODER_B_CHANNEL = 3;
	
	public static final int RIGHT_ENCODER_A_CHANNEL = 5;
	public static final int RIGHT_ENCODER_B_CHANNEL = 6;
	
	public static final double FORWARD_RAMPING = .8;
	public static final double SIDEWAYS_RAMPING = .8;
	public static final double ROTATION_EXPONENT = 0.5;
	public static final double SECONDARY_ROTATION_EXPONENT = 2;
	
	public static final AutonomousExecuter CURRENT_AUTON = AutonomousExecuter.BASIC;
	
}
