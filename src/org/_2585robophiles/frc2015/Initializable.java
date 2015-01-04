package org._2585robophiles.frc2015;

/**
 * SAM interface with an init method
 */
public @FunctionalInterface interface Initializable {
	
	/**
	 * Initialize object
	 * @param environment the environment of the systems
	 */
	public void init(Environment environment);
}
