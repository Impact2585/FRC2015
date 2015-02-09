package org._2585robophiles.frc2015.input;

import edu.wpi.first.wpilibj.Joystick;

public class JoystickInput implements InputMethod {
	
	private Joystick left , right;
	
	/**
	 * Initialize a joystick
	 */
	public JoystickInput() {
		 left = new Joystick(0);
	     right = new Joystick(1);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#forwardMovement()
	 */
	@Override
	public double forwardMovement() {
		return left.getY();
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#sidewaysMovement()
	 */
	@Override
	public double sidewaysMovement() {
		return left.getX();
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#rotation()
	 */
	@Override
	public double rotation() {
		return right.getX();
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#straightDrive()
	 */
	@Override
	public boolean straightDrive() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#joysticks()
	 */
	@Override
	public Joystick[] joysticks() {
		return new Joystick[] {left , right};
	}

}
