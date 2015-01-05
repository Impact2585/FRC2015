package org._2585robophiles.frc2015.systems;

import org._2585robophiles.frc2015.Environment;
import org._2585robophiles.frc2015.RobotMap;
import org._2585robophiles.frc2015.input.InputMethod;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * This system controls the movement of the robot
 */
public class WheelSystem implements RobotSystem, Runnable {
	
	private RobotDrive drivetrain;
	private SpeedController sidewaysMotor;
	private InputMethod input;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		drivetrain = new RobotDrive(RobotMap.FRONT_LEFT_DRIVE, RobotMap.REAR_LEFT_DRIVE, RobotMap.FRONT_RIGHT_DRIVE, RobotMap.REAR_RIGHT_DRIVE);
		sidewaysMotor = new Jaguar(RobotMap.SIDEWAYS_DRIVE);
		input = environment.getInput();
	}
	
	/**
	 * Drive the robot
	 * @param normalMovement forward back move value
	 * @param sidewaysMovement left right move value
	 * @param rotation turn value
	 */
	public void drive(double normalMovement, double sidewaysMovement, double rotation){
		drivetrain.arcadeDrive(normalMovement, rotation);
		sidewaysMotor.set(sidewaysMovement);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		drive(input.forwardMovement(), input.sidewaysMovement(), input.rotation());
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		drivetrain.free();
		if(sidewaysMotor instanceof SensorBase){
			SensorBase motor = (SensorBase) sidewaysMotor;
			motor.free();
		}
	}

}
