package org.impact2585.frc2015.systems;

import org.impact2585.lib2585.MultiMotor;
import org.impact2585.frc2015.Environment;
import org.impact2585.frc2015.RobotMap;
import org.impact2585.frc2015.input.InputMethod;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * This system controls our lift for picking up game pieces
 */
public class LiftSystem implements RobotSystem, Runnable {

	public static final double GROUND_SETPOINT = 0;
	public static final double TOTE_PICKUP_1 = 3;
	public static final double TOTE_PICKUP_2 = 4;
	public static final double TOTE_PICKUP_3 = 5;
	public static final double TOTE_PICKUP_4 = 6;
	
	public static final double DIGITAL_LIFT_SPEED = 0.5;

	private InputMethod input;
	private SpeedController leftMotor;
	private SpeedController rightMotor;
	private Encoder leftEncoder,rightEncoder;
	private PIDSubsystem liftPID;
	private PIDSubsystem rightPID;
	private double setpoint;
	private boolean upPressed;
	private boolean downPressed;
	private boolean groundPressed;
	private boolean setpoint1Pressed;
	private boolean setpoint2Pressed;
	private boolean setpoint3Pressed;
	private boolean setpoint4Pressed;
	private boolean manual;
	private boolean disabledPID;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		input = environment.getInput();
		leftMotor = new Victor(RobotMap.LEFT_LIFT);
		rightMotor = new MultiMotor(new Victor[]{new Victor(RobotMap.RIGHT_LIFT_1), new Victor(RobotMap.RIGHT_LIFT_2)});
		leftEncoder = new Encoder(RobotMap.LEFT_ENCODER_A_CHANNEL, RobotMap.LEFT_ENCODER_B_CHANNEL, false , CounterBase.EncodingType.k4X);
		leftEncoder.setDistancePerPulse(0.01 / 12);
		leftEncoder.reset();
		rightEncoder = new Encoder(RobotMap.RIGHT_ENCODER_A_CHANNEL, RobotMap.RIGHT_ENCODER_B_CHANNEL, true , CounterBase.EncodingType.k4X); // invert right encoder
		rightEncoder.setDistancePerPulse(0.01 / 12);
		rightEncoder.reset();
		disabledPID = true;

		// two independent PID subsystems for each side
		liftPID = new PIDSubsystem(1 / 9d, 0.03, 0) {

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
				leftMotor.set(output);
			}

			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#returnPIDInput()
			 */
			@Override
			protected double returnPIDInput() {
				return leftEncoder.getDistance();
			}
		};

		rightPID = new PIDSubsystem(1 / 9d, 0.03, 0) {

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
				rightMotor.set(output);
			}

			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#returnPIDInput()
			 */
			@Override
			protected double returnPIDInput() {
				return rightEncoder.getDistance();
			}
		};
	}

	/**
	 * Enable PIDSubsystem of the lift
	 * @param setpoint PID setpoint for the lift
	 */
	protected synchronized void enablePID(){
		liftPID.setSetpoint(setpoint);
		liftPID.enable();
		rightPID.setSetpoint(setpoint);
		rightPID.enable();
	}

	/**
	 * Disable PIDSubsystem of the lift
	 */
	protected synchronized void disablePID(){
		liftPID.disable();
		rightPID.disable();
	}

	/**
	 * Move the lift to a certain height with PID
	 * @param height the height that the lift should get to
	 * @return whether or not we have reached that height
	 */
	public boolean moveToHeight(double height){
		setpoint = height;
		enablePID();
		return encoderDistance() == height;
	}

	/**
	 * Set the speed of the lift motors
	 * @param speed desired motor speed
	 */
	public void setMotors(double speed) {
		leftMotor.set(speed); 
		rightMotor.set(-speed); // invert right motor
	}

	/**
	 * @return average distance of the encoders
	 */
	public double encoderDistance(){
		return (leftEncoder.getDistance() + rightEncoder.getDistance()) / 2;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// manual control of the lift
		if(input.analogLiftDown() > 0.15){
			disablePID();
			setMotors(-input.analogLiftDown());
		}else if(input.analogLiftUp() > 0.15){
			disablePID();
			setMotors(input.analogLiftUp());
		}else if(input.digitalLiftUp()){
			disablePID();
			setMotors(DIGITAL_LIFT_SPEED);
		}else if(input.digitalLiftDown()){
			disablePID();
			setMotors(-DIGITAL_LIFT_SPEED);
		}else{
			if(disabledPID){
				setMotors(0);
			}else if(manual){
				setMotors(0);
				// driver just stopped controlling lift manually so let's maintain this height
				setpoint = encoderDistance();
				enablePID();
			}else if(input.liftSetpointUp() && !upPressed){
				// not so manual control of the lift
				if(setpoint == GROUND_SETPOINT)
					setpoint = TOTE_PICKUP_1;
				else if(setpoint == TOTE_PICKUP_1)
					setpoint = TOTE_PICKUP_2;
				else if(setpoint == TOTE_PICKUP_2)
					setpoint = TOTE_PICKUP_3;
				else
					setpoint = TOTE_PICKUP_4;
				enablePID();
			}else if(input.liftSetpointDown() && !downPressed){
				if(setpoint == TOTE_PICKUP_4)
					setpoint = TOTE_PICKUP_3;
				else if(setpoint == TOTE_PICKUP_3)
					setpoint = TOTE_PICKUP_2;
				else if(setpoint == TOTE_PICKUP_2)
					setpoint = TOTE_PICKUP_1;
				else
					setpoint = GROUND_SETPOINT;
				enablePID();
			}else if(input.groundLift() && !groundPressed){
				setpoint = GROUND_SETPOINT;
				enablePID();
			}else if(input.liftSetpoint1() && !setpoint1Pressed){
				setpoint = TOTE_PICKUP_1;
				enablePID();
			}else if(input.liftSetpoint2() && !setpoint2Pressed){
				setpoint = TOTE_PICKUP_2;
				enablePID();
			}else if(input.liftSetpoint3() && !setpoint3Pressed){
				setpoint = TOTE_PICKUP_3;
				enablePID();
			}else if(input.liftSetpoint4() && !setpoint4Pressed){
				setpoint = TOTE_PICKUP_4;
				enablePID();
			}
		}
		upPressed = input.liftSetpointUp();
		downPressed = input.liftSetpointDown();
		groundPressed = input.groundLift();
		setpoint1Pressed = input.liftSetpoint1();
		setpoint2Pressed = input.liftSetpoint2();
		setpoint3Pressed = input.liftSetpoint3();
		setpoint4Pressed = input.liftSetpoint4();
		manual = input.analogLiftUp() > 0.15 || input.analogLiftDown() > 0.15 || input.digitalLiftUp() || input.digitalLiftDown() ;// user is controlling lift manually
	}

	/**
	 * @return the input
	 */
	public synchronized InputMethod getInput() {
		return input;
	}

	/**
	 * @param input the input to set
	 */
	protected synchronized void setInput(InputMethod input) {
		this.input = input;
	}

	/**
	 * @return the leftMotor
	 */
	public synchronized SpeedController getLeftMotor() {
		return leftMotor;
	}

	/**
	 * @param leftMotor the leftMotor to set
	 */
	protected synchronized void setLeftMotor(SpeedController leftMotor) {
		this.leftMotor = leftMotor;
	}

	/**
	 * @return the rightMotor
	 */
	public synchronized SpeedController getRightMotor() {
		return rightMotor;
	}

	/**
	 * @param rightMotor the rightMotor to set
	 */
	protected synchronized void setRightMotor(SpeedController rightMotor) {
		this.rightMotor = rightMotor;
	}

	/**
	 * @return the liftPID
	 */
	public synchronized PIDSubsystem getLiftPID() {
		return liftPID;
	}

	/**
	 * @param liftPID the liftPID to set
	 */
	protected synchronized void setLiftPID(PIDSubsystem liftPID) {
		this.liftPID = liftPID;
	}

	/**
	 * @return the setpoint
	 */
	public synchronized double getSetpoint() {
		return setpoint;
	}

	/**
	 * @param setpoint the setpoint to set
	 */
	protected synchronized void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}

	/**
	 * @return the upPressed
	 */
	public synchronized boolean isUpPressed() {
		return upPressed;
	}

	/**
	 * @param upPressed the upPressed to set
	 */
	protected synchronized void setUpPressed(boolean upPressed) {
		this.upPressed = upPressed;
	}

	/**
	 * @return the downPressed
	 */
	public synchronized boolean isDownPressed() {
		return downPressed;
	}

	/**
	 * @param downPressed the downPressed to set
	 */
	protected synchronized void setDownPressed(boolean downPressed) {
		this.downPressed = downPressed;
	}

	/**
	 * @return the groundPressed
	 */
	public synchronized boolean isGroundPressed() {
		return groundPressed;
	}

	/**
	 * @param groundPressed the groundPressed to set
	 */
	protected synchronized void setGroundPressed(boolean groundPressed) {
		this.groundPressed = groundPressed;
	}

	/**
	 * @return the setpoint1Pressed
	 */
	public synchronized boolean isSetpoint1Pressed() {
		return setpoint1Pressed;
	}

	/**
	 * @param setpoint1Pressed the setpoint1Pressed to set
	 */
	protected synchronized void setSetpoint1Pressed(boolean setpoint1Pressed) {
		this.setpoint1Pressed = setpoint1Pressed;
	}

	/**
	 * @return the setpoint2Pressed
	 */
	public synchronized boolean isSetpoint2Pressed() {
		return setpoint2Pressed;
	}

	/**
	 * @param setpoint2Pressed the setpoint2Pressed to set
	 */
	protected synchronized void setSetpoint2Pressed(boolean setpoint2Pressed) {
		this.setpoint2Pressed = setpoint2Pressed;
	}

	/**
	 * @return the setpoint3Pressed
	 */
	public synchronized boolean isSetpoint3Pressed() {
		return setpoint3Pressed;
	}

	/**
	 * @param setpoint3Pressed the setpoint3Pressed to set
	 */
	protected synchronized void setSetpoint3Pressed(boolean setpoint3Pressed) {
		this.setpoint3Pressed = setpoint3Pressed;
	}

	/**
	 * @return the setpoint4Pressed
	 */
	public synchronized boolean isSetpoint4Pressed() {
		return setpoint4Pressed;
	}

	/**
	 * @param setpoint4Pressed the setpoint4Pressed to set
	 */
	protected synchronized void setSetpoint4Pressed(boolean setpoint4Pressed) {
		this.setpoint4Pressed = setpoint4Pressed;
	}

	/**
	 * @return the disabledPID
	 */
	public synchronized boolean isDisabledPID() {
		return disabledPID;
	}
	
	/**
	 * @param disabledPID the disabledPID to set
	 */
	protected synchronized void setDisabledPID(boolean disabledPID) {
		this.disabledPID = disabledPID;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		// dynamic cast to destroy leftMotor
		if(leftMotor instanceof SensorBase){
			SensorBase motor = (SensorBase) leftMotor;
			motor.free();
		}
		// dynamic cast to destroy rightMotor
		if(rightMotor instanceof SensorBase){
			SensorBase motor = (SensorBase) rightMotor;
			motor.free();
		}
		rightEncoder.free();
		leftEncoder.free();
	}

}
