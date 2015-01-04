package org._2585robophiles.frc2015;

import org._2585robophiles.lib2585.RunnableExecuter;


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
	@SuppressWarnings("unchecked")// not using generics necessary for Lib2585 to remain compatible with Java ME
	@Override
	public void init(Environment environment) {
		getRunnables().add(environment.getWheelSystem());
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Executer#execute()
	 */
	@Override
	public void execute() {
		
	}

}
