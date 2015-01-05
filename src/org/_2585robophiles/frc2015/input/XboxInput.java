package org._2585robophiles.frc2015.input;

import org._2585robophiles.lib2585.XboxConstants;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Input from an xbox360 controller
 */
public class XboxInput implements InputMethod {
	
	private Joystick controller;
	
	/**
	 * Use joystick on port 1
	 */
	public XboxInput() {
		controller = new Joystick(1);
	}
	
	/**
	 * Use the given joystick
	 * @param joystick the xbox360 contoller
	 */
	public XboxInput(Joystick joystick){
		controller = joystick;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#forwardMovement()
	 */
	@Override
	public double forwardMovement() {
		return controller.getRawAxis(XboxConstants.LEFT_Y_AXIS);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#sidewaysMovement()
	 */
	@Override
	public double sidewaysMovement() {
		return controller.getRawAxis(XboxConstants.LEFT_X_AXIS);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#rotation()
	 */
	@Override
	public double rotation() {
		return controller.getRawAxis(XboxConstants.RIGHT_X_AXIS);
	}

}
