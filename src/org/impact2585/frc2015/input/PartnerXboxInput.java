package org.impact2585.frc2015.input;

import org._2585robophiles.lib2585.XboxConstants;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Input with two xbox 360 controllers
 */
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
		return -controller1.getRawAxis(1);// left y
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#sidewaysMovement()
	 */
	@Override
	public double sidewaysMovement() {
		return controller1.getRawAxis(0);// left x
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
		return controller1.getRawButton(XboxConstants.LEFT_BUMPER);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#analogLiftUp()
	 */
	@Override
	public double analogLiftUp() {
		return Math.max(0, -controller2.getRawAxis(1));// left y lift driver when going up
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#analogLiftDown()
	 */
	@Override
	public double analogLiftDown() {
		return -Math.min(0, -controller2.getRawAxis(1));// left y lift driver when going down
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#digitalLiftDown()
	 */
	@Override
	public boolean digitalLiftDown() {
		return controller2.getRawAxis(2) > 0;// left trigger
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#digitalLiftUp()
	 */
	@Override
	public boolean digitalLiftUp() {
		return controller2.getRawButton(XboxConstants.LEFT_BUMPER);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpointDown()
	 */
	@Override
	public boolean liftSetpointDown() {
		return controller2.getRawAxis(3) > 0;// right trigger
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpointUp()
	 */
	@Override
	public boolean liftSetpointUp() {
		return controller2.getRawButton(XboxConstants.RIGHT_BUMPER);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint1()
	 */
	@Override
	public boolean liftSetpoint1() {
		return controller2.getRawButton(XboxConstants.A_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint2()
	 */
	@Override
	public boolean liftSetpoint2() {
		return controller2.getRawButton(XboxConstants.X_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint3()
	 */
	@Override
	public boolean liftSetpoint3() {
		return controller2.getRawButton(XboxConstants.B_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint4()
	 */
	@Override
	public boolean liftSetpoint4() {
		return controller2.getRawButton(XboxConstants.Y_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#groundLift()
	 */
	@Override
	public boolean groundLift() {
		return controller2.getRawButton(XboxConstants.START_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#stopStraightDrive()
	 */
	@Override
	public boolean stopStraightDrive() {
		return controller1.getRawButton(XboxConstants.RIGHT_BUMPER);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#changeSensitivity()
	 */
	@Override
	public boolean changeSensitivity() {
		return controller1.getRawButton(XboxConstants.A_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#joysticks()
	 */
	public Joystick[] joysticks() {
		return new Joystick[] { controller1, controller2 };
	}

}
