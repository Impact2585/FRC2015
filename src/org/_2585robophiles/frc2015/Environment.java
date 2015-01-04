package org._2585robophiles.frc2015;

import org._2585robophiles.frc2015.systems.WheelSystem;
import org._2585robophiles.lib2585.RobotEnvironment;

public class Environment extends RobotEnvironment {
	
	private WheelSystem wheelSystem;
	
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

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		
	}

}
