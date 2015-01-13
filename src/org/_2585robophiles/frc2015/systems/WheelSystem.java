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
	private double previousNormalMovement;
	private double currentRampForward;
	private double currentRampSideways;
	private double rotationValue;

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
	public void drive(double forwardMovement, double sidewaysMovement, double rotation){
		drivetrain.arcadeDrive(forwardMovement, rotation);
		sidewaysMotor.set(sidewaysMovement);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		currentRampForward += (input.forwardMovement()-currentRampForward) * RobotMap.FORWARD_RAMPING;
		currentRampSideways += (input.sidewaysMovement()-currentRampSideways) * RobotMap.SIDEWAYS_RAMPING;
		if(input.rotation() < 0){
			rotationValue = Math.pow(input.rotation(),2) * -1.0; // keeps the negative value
		}else{
			rotationValue = Math.pow(input.rotation(),2);
		}
		drive(currentRampForward, currentRampSideways, rotationValue);
	}

	/**
	 * @return the previousNormalMovement
	 */
	public synchronized double getPreviousNormalMovement() {
		return previousNormalMovement;
	}

	/**
	 * @param previousNormalMovement the previousNormalMovement to set
	 */
	protected synchronized void setPreviousNormalMovement(double currentNormalMovement) {
		this.previousNormalMovement = currentNormalMovement;
	}
	
	/**
	 * @return the sidewaysMotor
	 */
	public synchronized SpeedController getSidewaysMotor() {
		return sidewaysMotor;
	}

	/**
	 * @param sidewaysMotor the sidewaysMotor to set
	 */
	protected synchronized void setSidewaysMotor(SpeedController sidewaysMotor) {
		this.sidewaysMotor = sidewaysMotor;
	}

	/**
	 * @return the input
	 */
	protected InputMethod getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	protected synchronized void setInput(InputMethod input) {
		this.input = input;
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
