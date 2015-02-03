package org._2585robophiles.frc2015;

import org._2585robophiles.frc2015.input.InputMethod;
import org._2585robophiles.lib2585.Executer;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Just a test with the smart dashboard
 */
public class TestExecuter implements Initializable, Executer {
	
	private InputMethod input;

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
		input = environment.getInput();
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Executer#execute()
	 */
	@Override
	public void execute() {
		if(input.joysticks().length > 0){
			// put all the axis of joystick one on the dashboard
			for(int i = 0; i < input.joysticks()[0].getAxisCount(); i++)
				SmartDashboard.putNumber("Axis " + i, input.joysticks()[0].getRawAxis(i));
			// all the buttons
			for(int i = 1; i < input.joysticks()[0].getButtonCount(); i++)
				SmartDashboard.putBoolean("Button " + i, input.joysticks()[0].getRawButton(i));
			// the POV hat
			SmartDashboard.putNumber("POV ", input.joysticks()[0].getPOV());
		}
	}

}
