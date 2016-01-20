package org.impact2585.frc2015;

import org.impact2585.lib2585.Executer;
import org.impact2585.frc2015.input.InputMethod;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
		LiveWindow.setEnabled(false);// we don't want the LiveWindow we want to use the SmartDashboard
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Executer#execute()
	 */
	@Override
	public void execute() {
		for(Joystick joystick : input.joysticks()){
			// put all the axis of joystick one on the dashboard
			for(int i = 0; i < joystick.getAxisCount(); i++)
				SmartDashboard.putNumber("Axis " + i, joystick.getRawAxis(i));
			// all the buttons
			for(int i = 1; i < input.joysticks()[0].getButtonCount(); i++)
				SmartDashboard.putBoolean("Button " + i, joystick.getRawButton(i));
			// the POV hat
			SmartDashboard.putNumber("POV ", joystick.getPOV());
			SmartDashboard.putNumber("X ", joystick.getX());
			SmartDashboard.putNumber("Y ", joystick.getY());
		}
	}

}
