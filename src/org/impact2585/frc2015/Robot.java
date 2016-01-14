package org.impact2585.frc2015;

import org._2585robophiles.lib2585.ExecuterBasedRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends ExecuterBasedRobot {
	
	private static final long serialVersionUID = -4946955294002768824L;
	
	private Environment environment;
    
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#robotInit()
	 */
	@Override
	public void robotInit() {
		environment = new Environment(this);
	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#autonomousInit()
	 */
	@Override
	public void autonomousInit() {
		AutonomousExecuter auton = RobotMap.CURRENT_AUTON;
		auton.init(environment);
		setExecuter(auton);
	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#teleopInit()
	 */
	@Override
	public void teleopInit() {
		setExecuter(new TeleopExecuter(environment));
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#testInit()
	 */
	@Override
	public void testInit() {
		setExecuter(new TestExecuter(environment));
	}

	/**
	 * @return the environment
	 */
	public synchronized Environment getEnvironment() {
		return environment;
	}

	/**
	 * @param environment the environment to set
	 */
	public synchronized void setEnvironment(Environment environment) {
		this.environment = environment;
	}
    
}
