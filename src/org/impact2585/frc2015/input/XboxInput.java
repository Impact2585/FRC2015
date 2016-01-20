package org.impact2585.frc2015.input;

import org.impact2585.lib2585.XboxConstants;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Input from an xbox360 controller
 */
public class XboxInput implements InputMethod {
	
	private Joystick controller;
	
	/**
	 * Initialize a joystick
	 */
	public XboxInput() {
		controller = new Joystick(0);
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
		return (-controller.getRawAxis(1));// left stick y-axis
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#sidewaysMovement()
	 */
	@Override
	public double sidewaysMovement() {
		return (controller.getRawAxis(0));// left stick x-axis
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#rotation()
	 */
	@Override
	public double rotation() {
		return controller.getRawAxis(XboxConstants.RIGHT_X_AXIS);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#straightDrive()
	 */
	@Override
	public boolean straightDrive() {
		return controller.getRawButton(XboxConstants.BACK_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#analogLiftUp()
	 */
	@Override
	public double analogLiftUp() {
		return controller.getRawAxis(3);// right trigger
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#analogLiftDown()
	 */
	@Override
	public double analogLiftDown() {
		return controller.getRawAxis(2);// left trigger
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#digitalLiftDown()
	 */
	@Override
	public boolean digitalLiftDown() {
		return controller.getRawButton(XboxConstants.LEFT_BUMPER);
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#digitalLiftUp()
	 */
	@Override
	public boolean digitalLiftUp() {
		return controller.getRawButton(XboxConstants.RIGHT_BUMPER);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpointDown()
	 */
	@Override
	public boolean liftSetpointDown() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpointUp()
	 */
	@Override
	public boolean liftSetpointUp() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint1()
	 */
	@Override
	public boolean liftSetpoint1() {
		return controller.getRawButton(XboxConstants.A_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint2()
	 */
	@Override
	public boolean liftSetpoint2() {
		return controller.getRawButton(XboxConstants.X_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint3()
	 */
	@Override
	public boolean liftSetpoint3() {
		return controller.getRawButton(XboxConstants.B_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint4()
	 */
	@Override
	public boolean liftSetpoint4() {
		return controller.getRawButton(XboxConstants.Y_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#groundLift()
	 */
	@Override
	public boolean groundLift() {
		return controller.getRawButton(XboxConstants.START_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#stopStraightDrive()
	 */
	@Override
	public boolean stopStraightDrive() {
		return controller.getRawButton(XboxConstants.LEFT_JOYSTICK_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#changeSensitivity()
	 */
	@Override
	public boolean changeSensitivity() {
		return controller.getRawButton(XboxConstants.RIGHT_JOYSTICK_BUTTON);
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#joysticks()
	 */
	@Override
	public Joystick[] joysticks() {
		return new Joystick[]{controller};
	}
}
