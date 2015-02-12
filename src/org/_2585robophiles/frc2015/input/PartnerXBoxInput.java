package org._2585robophiles.frc2015.input;

import org._2585robophiles.lib2585.XboxConstants;

import edu.wpi.first.wpilibj.Joystick;

public class PartnerXBoxInput implements InputMethod {
	private Joystick controller1, controller2;

	public PartnerXBoxInput() {
		controller1 = new Joystick(0);
		controller2 = new Joystick(1);
	}

	public PartnerXBoxInput(Joystick joystick1, Joystick joystick2) {
		controller1 = joystick1;
		controller2 = joystick2;
	}
	
	@Override
	public double forwardMovement() {
		return controller1.getRawAxis(XboxConstants.LEFT_Y_AXIS);
	}

	@Override
	public double sidewaysMovement() {
		return controller1.getRawAxis(XboxConstants.LEFT_X_AXIS);
	}

	@Override
	public double rotation() {
		return controller1.getRawAxis(XboxConstants.RIGHT_X_AXIS);
	}

	@Override
	public boolean straightDrive() {
		return controller1.getRawButton(XboxConstants.BACK_BUTTON);
	}

	public Joystick[] joysticks() {
		return new Joystick[] { controller1, controller2 };
	}
}
