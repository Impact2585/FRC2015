package org.impact2585.frc2015.input;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Input from the joysticks
 */
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
	 * @see org._2585robophiles.frc2015.input.InputMethod#analogLiftUp()
	 */
	@Override
	public double analogLiftUp() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#analogLiftDown()
	 */
	@Override
	public double analogLiftDown() {
		return 0;
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#digitalLiftDown()
	 */
	@Override
	public boolean digitalLiftDown() {
		return false;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#digitalLiftUp()
	 */
	@Override
	public boolean digitalLiftUp() {
		return false;
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
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint2()
	 */
	@Override
	public boolean liftSetpoint2() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint3()
	 */
	@Override
	public boolean liftSetpoint3() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpoint4()
	 */
	@Override
	public boolean liftSetpoint4() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#groundLift()
	 */
	@Override
	public boolean groundLift() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#stopStraightDrive()
	 */
	@Override
	public boolean stopStraightDrive() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.input.InputMethod#changeSensitivity()
	 */
	@Override
	public boolean changeSensitivity() {
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
