package org._2585robophiles.frc2015.systems;

import org._2585robophiles.frc2015.Environment;
import org._2585robophiles.frc2015.Initializable;
import org._2585robophiles.lib2585.Executer;

public class AutonomousExecuter implements Executer, Initializable {

	/**
	 * Doesn't initialize anything
	 */
	public AutonomousExecuter() {
		
	}
	
	/**
	 * Just calls init
	 * @param environment the environment to initialize with
	 */
	public AutonomousExecuter(Environment environment){
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
		
	}

}
