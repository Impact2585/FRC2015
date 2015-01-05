package org._2585robophiles.frc2015;

import org._2585robophiles.frc2015.input.InputMethod;
import org._2585robophiles.frc2015.input.XboxInput;
import org._2585robophiles.frc2015.systems.WheelSystem;
import org._2585robophiles.lib2585.RobotEnvironment;

/**
 * This class contains the robot's systems and input
 */
public class Environment extends RobotEnvironment {
	
	private static final long serialVersionUID = 8684301017988652791L;
	
	private WheelSystem wheelSystem;
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

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		wheelSystem.destroy();
	}

}
