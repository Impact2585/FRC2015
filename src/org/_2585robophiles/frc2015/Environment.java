package org._2585robophiles.frc2015;

import org._2585robophiles.frc2015.input.InputMethod;
import org._2585robophiles.frc2015.input.XboxInput;
import org._2585robophiles.frc2015.systems.AccelerometerSystem;
import org._2585robophiles.frc2015.systems.GyroSystem;
import org._2585robophiles.frc2015.systems.WheelSystem;
import org._2585robophiles.lib2585.RobotEnvironment;

/**
 * This class contains the robot's systems and input
 */
public class Environment extends RobotEnvironment {
	
	private static final long serialVersionUID = 8684301017988652791L;
	
	private WheelSystem wheelSystem;
	private GyroSystem gyroSystem;
	private AccelerometerSystem accelerometerSystem;
	private InputMethod input;
	
	/**
	 * Just a default constructor
	 */
	public Environment() {
		super();
	}
	
	/**
	 * Initialize the systems
	 * @param robot the robot which the environment belongs to
	 */
	public Environment(Robot robot) {
		super(robot);
		
		wheelSystem = new WheelSystem();
		wheelSystem.init(this);
		
		gyroSystem = new GyroSystem();
		gyroSystem.init(this);
		
		accelerometerSystem = new AccelerometerSystem();
		accelerometerSystem.init(this);
		
		input = new XboxInput();
	}

	/**
	 * @return the wheelSystem
	 */
	public synchronized WheelSystem getWheelSystem() {
		return wheelSystem;
	}

	/**
	 * @param wheelSystem the wheelSystem to set
	 */
	protected synchronized void setWheelSystem(WheelSystem wheelSystem) {
		this.wheelSystem = wheelSystem;
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
	 * @return the gyroSystem
	 */
	public synchronized GyroSystem getGyroSystem() {
		return gyroSystem;
	}

	/**
	 * @param gyroSystem the gyroSystem to set
	 */
	protected synchronized void setGyroSystem(GyroSystem gyroSystem) {
		this.gyroSystem = gyroSystem;
	}

	/**
	 * @return the accelerometerSystem
	 */
	public synchronized AccelerometerSystem getAccelerometerSystem() {
		return accelerometerSystem;
	}

	/**
	 * @param accelerometerSystem the accelerometerSystem to set
	 */
	protected synchronized void setAccelerometerSystem(
			AccelerometerSystem accelerometerSystem) {
		this.accelerometerSystem = accelerometerSystem;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		wheelSystem.destroy();
		gyroSystem.destroy();
		accelerometerSystem.destroy();
	}

}
