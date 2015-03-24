package org._2585robophiles.frc2015.systems;

import org._2585robophiles.frc2015.Environment;
import org._2585robophiles.frc2015.RobotMap;
import org._2585robophiles.frc2015.input.InputMethod;
import org._2585robophiles.lib2585.MultiMotor;

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
	public static final double TOTE_PICKUP_2 = 3;
	public static final double TOTE_PICKUP_3 = 3;
	public static final double TOTE_PICKUP_4 = 3;

	private InputMethod input;
	private SpeedController leftMotor;
	private SpeedController rightMotor;
	private Encoder encoder;
	private PIDSubsystem liftPID;
	private double setpoint;
	private boolean upPressed;
	private boolean downPressed;
	private boolean manual;
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		input = environment.getInput();
		leftMotor = new MultiMotor(new Victor[]{new Victor(RobotMap.LEFT_LIFT_1), new Victor(RobotMap.LEFT_LIFT_2)});
		rightMotor = new MultiMotor(new Victor[]{new Victor(RobotMap.RIGHT_LIFT_1), new Victor(RobotMap.RIGHT_LIFT_2)});
		encoder = new Encoder(RobotMap.ENCODER_A_CHANNEL, RobotMap.ENCODER_B_CHANNEL);
		encoder.setDistancePerPulse(3);
		liftPID = new PIDSubsystem(0.3, 0.3, 0.3) {
			
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
				setMotors(output);
			}
			
			/* (non-Javadoc)
			 * @see edu.wpi.first.wpilibj.command.PIDSubsystem#returnPIDInput()
			 */
			@Override
			protected double returnPIDInput() {
				return encoder.getDistance();
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
	}
	
	/**
	 * Disable PIDSubsystem of the lift
	 */
	protected synchronized void disablePID(){
		liftPID.disable();
	}
	
	/**
	 * Set the speed of the lift motors
	 * @param speed desired motor speed
	 */
	public void setMotors(double speed) {
		leftMotor.set(speed);
		rightMotor.set(speed);
	}
	
	/**
	 * @return distance from the encoder
	 */
	public double encoderDistance(){
		return encoder.getDistance();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// manual control of the lift
		if(input.analogLiftDown()>0){
			disablePID();
			setMotors(-input.analogLiftDown());
		}else if(input.analogLiftUp() > 0){
			disablePID();
			setMotors(input.analogLiftUp());
		}else if(manual){
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
		}
		upPressed = input.liftSetpointUp();
		downPressed = input.liftSetpointDown();
		manual = input.analogLiftDown() == input.analogLiftUp() && input.analogLiftDown() == 0;// user is controlling lift manually
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
		encoder.free();
	}

}
