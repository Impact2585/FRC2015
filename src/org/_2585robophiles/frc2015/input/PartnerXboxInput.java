package org._2585robophiles.frc2015.input;

import org._2585robophiles.lib2585.XboxConstants;

import edu.wpi.first.wpilibj.Joystick;

public class PartnerXboxInput implements InputMethod {
	private Joystick controller1, controller2;

	/**
	 * Initializes joysticks on 0 and 1
	 */
	public PartnerXboxInput() {
		controller1 = new Joystick(0);
		controller2 = new Joystick(1);
	}

	/**
	 * @param joystick1 the first joystick
	 * @param joystick2 the second joystick
	 */
	public PartnerXboxInput(Joystick joystick1, Joystick joystick2) {
		controller1 = joystick1;
		controller2 = joystick2;
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#forwardMovement()
	 */
	@Override
	public double forwardMovement() {
		return controller1.getRawAxis(XboxConstants.LEFT_Y_AXIS);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#sidewaysMovement()
	 */
	@Override
	public double sidewaysMovement() {
		return controller1.getRawAxis(XboxConstants.LEFT_X_AXIS);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#rotation()
	 */
	@Override
	public double rotation() {
		return controller1.getRawAxis(XboxConstants.RIGHT_X_AXIS);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#straightDrive()
	 */
	@Override
	public boolean straightDrive() {
		return controller1.getRawButton(XboxConstants.BACK_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#analogLiftUp()
	 */
	@Override
	public double analogLiftUp() {
		return controller2.getRawAxis(3);// right trigger of second controller
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#analogLiftDown()
	 */
	@Override
	public double analogLiftDown() {
		return controller2.getRawAxis(2);// left trigger of second controller
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpointDown()
	 */
	@Override
	public boolean liftSetpointDown() {
		return controller2.getRawButton(XboxConstants.LEFT_BUMPER);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpointUp()
	 */
	@Override
	public boolean liftSetpointUp() {
		return controller2.getRawButton(XboxConstants.RIGHT_BUMPER);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#joysticks()
	 */
	public Joystick[] joysticks() {
		return new Joystick[] { controller1, controller2 };
	}
}
