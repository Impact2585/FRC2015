package org.impact2585.frc2015.systems;

import org._2585robophiles.lib2585.MultiMotor;
import org.impact2585.frc2015.Environment;
import org.impact2585.frc2015.RobotMap;
import org.impact2585.frc2015.input.InputMethod;

import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * This system controls the movement of the robot
 */
public class WheelSystem implements RobotSystem, Runnable {

	private RobotDrive drivetrain;
	private SpeedController sidewaysMotor;
	private InputMethod input;
	private AccelerometerSystem accelerometer;
	private GyroSystem gyro;
	private double previousNormalMovement;
	private double currentRampForward;
	private double currentRampSideways;
	private double rotationValue;
	private double forwardDistanceDriven, sidewaysDistanceDriven;
	private long lastForwardDistanceUpdate, lastSidewaysDistanceUpdate;
	private boolean straightDriveDisabled;
	private boolean straightDrivePressed;
	private boolean changeSensitivityPressed;
	private boolean secondarySensitivity;
	private double correctRotate;
	private PIDSubsystem forwardDistancePID, sidewaysDistancePID, straightDrivePID;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		drivetrain = new RobotDrive(new Talon(RobotMap.FRONT_LEFT_DRIVE), new Talon(RobotMap.REAR_LEFT_DRIVE), new Talon(RobotMap.FRONT_RIGHT_DRIVE),  new Jaguar(RobotMap.REAR_RIGHT_DRIVE));
		drivetrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight , true );
		drivetrain.setInvertedMotor(RobotDrive.MotorType.kRearRight , true );
		sidewaysMotor = new MultiMotor(new SpeedController[]{new Victor(RobotMap.SIDEWAYS_DRIVE), new Victor(RobotMap.SIDEWAYS_DRIVE_2)});
		accelerometer = environment.getAccelerometerSystem();
		gyro = environment.getGyroSystem();
		input = environment.getInput();
		straightDriveDisabled = true;

		forwardDistancePID = new PIDSubsystem(0.2, 0.03, 0) {

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
				return forwardDistanceDriven;
			}
		};

		sidewaysDistancePID = new PIDSubsystem(0.2, 0.03, 0) {

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
				drive(0, output, 0);
			}

			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#returnPIDInput()
			 */
			@Override
			protected double returnPIDInput() {
				return sidewaysDistanceDriven;
			}
		};

		straightDrivePID = new PIDSubsystem(0.1, 0.03, 0) {

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
				correctRotate = output;
			}

			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#returnPIDInput()
			 */
			@Override
			protected double returnPIDInput() {
				return gyro.angle();
			}
		};
	}

	/**
	 * Drive a certain distance in meters or feet
	 * @param forwardDistance the distance to drive forward
	 * @param sidewaysDistance the distance to drive sideways
	 * @param usingMeters true if using meters false if using feet
	 * @return if driveDistance is done
	 */
	public boolean driveDistance(double forwardDistance, double sidewaysDistance, boolean usingMeters){
		if(usingMeters){
			return driveDistance(forwardDistance, sidewaysDistance);
		}else{
			return driveDistance(forwardDistance / AccelerometerSystem.METER_TO_FEET, sidewaysDistance / AccelerometerSystem.METER_TO_FEET);
		}
	}

	/**
	 * Drive the robot
	 * @param forwardMovement forward back move value
	 * @param sidewaysMovement left right move value
	 * @param rotation turn value
	 * @return if driveDistance is done
	 */
	public void drive(double forwardMovement, double sidewaysMovement, double rotation){
		drivetrain.arcadeDrive(forwardMovement, rotation);
		sidewaysMotor.set(sidewaysMovement);// we need to invert the sideways motor
	}

	/**
	 * Drives a certain distance using the accelerometer and PID
	 * @param forwardDistance the distance to drive forward
	 * @param sidewaysDistance the distance to drive sideways
	 * @return if distance drive is done
	 */
	public boolean driveDistance(double forwardMeters , double sidewaysMeters) {
		if(forwardMeters != 0){
			if(lastForwardDistanceUpdate == 0){
				enableForwardDistancePID(forwardMeters);
				lastForwardDistanceUpdate = System.currentTimeMillis();
			}else if(forwardDistanceDriven == forwardMeters){
				disableForwardDistancePID();
				forwardDistanceDriven = 0;
				lastForwardDistanceUpdate = 0;
			}else{
				// use the accelerometer to find the forward distance we have driven
				forwardDistanceDriven += accelerometer.getSpeedX() * (System.currentTimeMillis() - lastForwardDistanceUpdate) /1000;
				lastForwardDistanceUpdate = System.currentTimeMillis();
			}
		}

		if(sidewaysMeters != 0){
			if(lastSidewaysDistanceUpdate == 0){
				enableSidewaysDistancePID(sidewaysMeters);
				lastSidewaysDistanceUpdate = System.currentTimeMillis();
			}else if(sidewaysDistanceDriven == sidewaysMeters){
				disableSidewaysDistancePID();
				sidewaysDistanceDriven = 0;
				lastSidewaysDistanceUpdate = 0;
			}else{
				// use the accelerometer to find the sideways distance we have driven
				sidewaysDistanceDriven += accelerometer.getSpeedY() * (System.currentTimeMillis()-lastSidewaysDistanceUpdate) /1000;
				lastSidewaysDistanceUpdate = System.currentTimeMillis();
			}
		}
		return forwardDistanceDriven == forwardMeters && sidewaysDistanceDriven == sidewaysMeters;
	}

	/**
	 * Drive straight using gyro and PID
	 * @param forwardMovement the forward movement value
	 * @param sidewaysMovement the left/right movement value
	 */
	public void straightDrive(double forwardMovement, double sidewaysMovement){
		if(!straightDriving()){
			enableStraightDrivePID();
		}
		drive(forwardMovement, sidewaysMovement, correctRotate);
	}

	/**
	 * Enable and set the setpoint of the forward distance drive PID
	 * @param setpoint forward distance to drive in meters
	 */
	protected synchronized void enableForwardDistancePID(double setpoint) {
		forwardDistancePID.setSetpoint(setpoint);
		forwardDistancePID.enable();
	}

	/**
	 * Disable forward distance drive PID
	 */
	protected synchronized void disableForwardDistancePID(){
		forwardDistancePID.getPIDController().reset();
		forwardDistancePID.disable();
		disableSraightDrivePID();
	}

	/**
	 * Enable and set the setpoint of the sideways distance drive PID
	 * @param setpoint sideways distance to drive in meters
	 */
	protected synchronized void enableSidewaysDistancePID(double setpoint) {
		sidewaysDistancePID.setSetpoint(setpoint);
		sidewaysDistancePID.enable();
	}

	/**
	 * Disable sideways distance drive PID
	 */
	protected synchronized void disableSidewaysDistancePID(){
		sidewaysDistancePID.getPIDController().reset();
		sidewaysDistancePID.disable();
		disableSraightDrivePID();
	}   

	/**
	 * Enable straight driving
	 * @param setpoint target angle
	 */
	protected synchronized void enableStraightDrivePID(){
		straightDrivePID.setSetpoint(gyro.angle());
		straightDrivePID.setAbsoluteTolerance(2);// it's OK if we're 2 degrees off
		straightDrivePID.enable();
	}

	/**
	 * Stop straight driving
	 * @param setpoint target angle
	 */
	protected synchronized void disableSraightDrivePID(){
		straightDrivePID.getPIDController().reset();
		straightDrivePID.disable();
	}

	/**
	 * @return whether or not the robot is straight driving
	 */
	public synchronized boolean straightDriving(){
		return straightDrivePID.getPIDController().isEnabled();
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
		if(currentRampSideways < .175 && currentRampSideways > -.175)
			currentRampSideways = 0.0;
		rotationValue = input.rotation();
		if(rotationValue < .15 && rotationValue > -.15)
			rotationValue = 0.0;
		else
			rotationValue = Math.signum(input.rotation()) * Math.pow(Math.abs(input.rotation()), secondarySensitivity ? RobotMap.SECONDARY_ROTATION_EXPONENT : RobotMap.ROTATION_EXPONENT);
		if(rotationValue == 0 && !straightDriveDisabled && !input.stopStraightDrive()){
			straightDrive(currentRampForward, currentRampSideways);// straight drive when not turning
		}else{
			disableSraightDrivePID();
			drive(currentRampForward, currentRampSideways, rotationValue);
		}

		// toggle between straight driving enabled and disabled
		if(input.straightDrive() && !straightDrivePressed)
			straightDriveDisabled =! straightDriveDisabled;
		straightDrivePressed = input.straightDrive();
		
		// toggle sensitivities so people don't die
		if(input.changeSensitivity() && !changeSensitivityPressed)
			secondarySensitivity =! secondarySensitivity;
		changeSensitivityPressed = input.changeSensitivity();
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
	public InputMethod getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	protected synchronized void setInput(InputMethod input) {
		this.input = input;
	}

	/**
	 * @return the accelerometer
	 */
	public synchronized AccelerometerSystem getAccelerometer() {
		return accelerometer;
	}

	/**
	 * @param accelerometer the accelerometer to set
	 */
	protected synchronized void setAccelerometer(AccelerometerSystem accelerometer) {
		this.accelerometer = accelerometer;
	}

	/**
	 * @return the forwardDistanceDriven
	 */
	public synchronized double getForwardDistanceDriven() {
		return forwardDistanceDriven;
	}

	/**
	 * @return the sidewaysDistanceDriven
	 */
	public synchronized double getSidewaysDistanceDriven() {
		return sidewaysDistanceDriven;
	}

	/**
	 * @param forwardDistanceDriven the forwardDistanceDriven to set
	 */
	protected synchronized void setForwardDistanceDriven(double forwardDistanceDriven) {
		this.forwardDistanceDriven = forwardDistanceDriven;
	}

	/**
	 * @param sidewaysDistanceDriven the sidewaysDistanceDriven to set
	 */
	protected synchronized void setSidewaysDistanceDriven(double sidewaysDistanceDriven) {
		this.sidewaysDistanceDriven = sidewaysDistanceDriven;
	}

	/**
	 * @return the lastForwardDistanceUpdate
	 */
	public synchronized long getLastForwardDistanceUpdate() {
		return lastForwardDistanceUpdate;
	}

	/**
	 * @return the lastSidewaysDistanceUpdate
	 */
	public synchronized long getLastSidewaysDistanceUpdate() {
		return lastSidewaysDistanceUpdate;
	}

	/**
	 * @param lastForwardDistanceUpdate the lastForwardDistanceUpdate to set
	 */
	protected synchronized void setLastForwardDistanceUpdate(long lastForwardDistanceUpdate) {
		this.lastForwardDistanceUpdate = lastForwardDistanceUpdate;
	}

	/**
	 * @param lastSidewaysDistanceUpdate the lastSidewaysDistanceUpdate to set
	 */
	protected synchronized void setLastSidewaysDistanceUpdate(long lastSidewaysDistanceUpdate) {
		this.lastSidewaysDistanceUpdate = lastSidewaysDistanceUpdate;
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
