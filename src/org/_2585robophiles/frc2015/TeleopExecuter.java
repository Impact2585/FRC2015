package org._2585robophiles.frc2015;

import org._2585robophiles.lib2585.RunnableExecuter;


/**
 * Executer for teleop (user operated) mode
 */
public class TeleopExecuter extends RunnableExecuter implements Initializable {
	
	private static final long serialVersionUID = 2506244240272489185L;

	/**
	 * Doesn't initialize anything
	 */
	public TeleopExecuter(){
		
	}
	
	/**
	 * Calls init
	 * @param environment the environment to initialize with
	 */
	public TeleopExecuter(Environment environment){
		init(environment);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@SuppressWarnings("unchecked")// not using generics so Lib2585 can remain compatible with Java ME
	@Override
	public void init(Environment environment) {
		getRunnables().add(environment.getWheelSystem());
	}

}
