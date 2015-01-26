package org._2585robophiles.frc2015.systems;

import org._2585robophiles.frc2015.Environment;
import org._2585robophiles.frc2015.RobotMap;
import org._2585robophiles.frc2015.input.InputMethod;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

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
	private double distanceDriven;
	private long lastDistanceUpdate;
	
	private PIDSubsystem distancePID;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		drivetrain = new RobotDrive(RobotMap.FRONT_LEFT_DRIVE, RobotMap.REAR_LEFT_DRIVE, RobotMap.FRONT_RIGHT_DRIVE, RobotMap.REAR_RIGHT_DRIVE);
		drivetrain.setInvertedMotor(RobotDrive.MotorType.kFrontLeft , true );
		drivetrain.setInvertedMotor(RobotDrive.MotorType.kRearLeft , true );
		sidewaysMotor = new Jaguar(RobotMap.SIDEWAYS_DRIVE);
		input = environment.getInput();
		
		distancePID = new PIDSubsystem(0.3, 0.3, 0.3) {
				
				/* (non-Javadoc)
				 * @see edu.wpi.first.wpilibj.command.Subsystem#initDefaultCommand()
				 */
				@Override
				protected void initDefaultCommand() {
					
				}
				
				/* (non-Javadoc)
				 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#usePIDOutput(double)
				 */
				@Override
				protected void usePIDOutput(double output) {
					drive(output, 0, 0);
				}
				
				/* (non-Javadoc)
				 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#returnPIDInput()
				 */
				@Override
				protected double returnPIDInput() {
					return distanceDriven;
				}
			};
	}
	
	/**
	 * Drive a certain distance in meters or feet
	 * @param distance the distance to drive
	 * @param usingMeters true if using meters false if using feet
	 */
	public void driveDistance(double distance, boolean usingMeters){
		driveDistance(usingMeters ? distance : AccelerometerSystem.METER_TO_FEET * distance);
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
	
	/**
	 * Drives a certain distance using the accelerometer and PID
	 * @param meters distance to drive in meters
	 */
	public void driveDistance(double meters) {
		if(lastDistanceUpdate == 0){
			enableDistancePID(meters);
			lastDistanceUpdate = System.currentTimeMillis();
		}else if(distanceDriven == meters){
			disableDistancePID();
			lastDistanceUpdate = 0;
		}else{
			lastDistanceUpdate = System.currentTimeMillis();
			
		}
	}

	/**
	 * Enable and set the setpoint of the distance drive PID
	 * @param setpoint distance to drive in meters
	 */
	protected void enableDistancePID(double setpoint) {
		distancePID.setSetpoint(setpoint);
		distancePID.enable();
	}
	
	protected void disableDistancePID(){
		distancePID.getPIDController().reset();
		distancePID.disable();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		currentRampForward += (input.forwardMovement()-currentRampForward) * RobotMap.FORWARD_RAMPING;
		currentRampSideways += (input.sidewaysMovement()-currentRampSideways) * RobotMap.SIDEWAYS_RAMPING;
		if(currentRampForward < .15 && currentRampForward > -.15)
			currentRampForward = 0.0;
		if(currentRampSideways < .15 && currentRampSideways > -.15)
			currentRampSideways = 0.0;
		if(input.rotation() < 0){
			rotationValue = Math.pow(input.rotation(),RobotMap.ROTATION_EXPONENT) * -1.0; // keeps the negative value
			if(rotationValue > 0){
				rotationValue = rotationValue * -1; // if the ROTATION_EXPONENT is an odd number it keeps the positive
			}
		}else{
			rotationValue = Math.pow(input.rotation(),RobotMap.ROTATION_EXPONENT);
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

	/**
	 * @return the distanceDriven
	 */
	public synchronized double getDistanceDriven() {
		return distanceDriven;
	}

	/**
	 * @param distanceDriven the distanceDriven to set
	 */
	protected synchronized void setDistanceDriven(double distanceDriven) {
		this.distanceDriven = distanceDriven;
	}

	/**
	 * @return the lastDistanceUpdate
	 */
	public synchronized long getLastDistanceUpdate() {
		return lastDistanceUpdate;
	}

	/**
	 * @param lastDistanceUpdate the lastDistanceUpdate to set
	 */
	protected synchronized void setLastDistanceUpdate(long lastDistanceUpdate) {
		this.lastDistanceUpdate = lastDistanceUpdate;
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
