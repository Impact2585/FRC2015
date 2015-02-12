package org._2585robophiles.frc2015;

import org._2585robophiles.lib2585.Executer;

/**
 * Executer for autonomous mode
 */
public enum AutonomousExecuter implements Executer, Initializable {

	/**
	 * Basic auton that just pushes a tote into the auto zone
	 */
	BASIC;
	
	private Environment environment;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		this.environment = environment;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Executer#execute()
	 */
	@Override
	public void execute() {
		switch(this){
		case BASIC:
			environment.getWheelSystem().driveDistance(3, false);// just drive forward
			break;
		}
	}

}
