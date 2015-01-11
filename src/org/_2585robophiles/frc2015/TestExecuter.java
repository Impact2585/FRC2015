package org._2585robophiles.frc2015;

import org._2585robophiles.lib2585.Executer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Just a test with the smart dashboard
 */
public class TestExecuter implements Initializable, Executer {

	/**
	 * Doesn't initialize anything
	 */
	public TestExecuter() {
		
	}
	
	/**
	 * Calls init
	 * @param environment the environment to initialize with
	 */
	public TestExecuter(Environment environment){
		init(environment);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Executer#execute()
	 */
	@Override
	public void execute() {
		SmartDashboard.putString("Test", "world P.S. Andrew is the push engineer");
	}

}
