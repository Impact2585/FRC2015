package org._2585robophiles.frc2015.systems;

import org._2585robophiles.frc2015.Environment;
import org._2585robophiles.frc2015.RobotMap;

import edu.wpi.first.wpilibj.RobotDrive;

/**
 * This system controls the movement of the robot
 */
public class WheelSystem implements RobotSystem, Runnable {
	
	private RobotDrive drivetrain;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		drivetrain = new RobotDrive(RobotMap.FRONT_LEFT_DRIVE, RobotMap.REAR_LEFT_DRIVE, RobotMap.FRONT_RIGHT_DRIVE, RobotMap.REAR_RIGHT_DRIVE);
	}
	
	/**
	 * Drive the robot
	 * @param normalMovement forward back move value
	 * @param sidewaysMovement left right move value
	 * @param rotation turn value
	 */
	public void drive(double normalMovement, double sidewaysMovement, double rotation){
		drivetrain.arcadeDrive(normalMovement, rotation);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		drivetrain.free();
	}

}
