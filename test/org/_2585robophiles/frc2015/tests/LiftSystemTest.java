package org._2585robophiles.frc2015.tests;

import org._2585robophiles.frc2015.input.InputMethod;
import org._2585robophiles.frc2015.systems.LiftSystem;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * Unit test for the lift system
 */
public class LiftSystemTest {
	
	private TestLiftSystem lift;
	private double motorSpeed;
	private double analogUpInput;
	private double analogDownInput;
	private boolean setpointUpInput;
	private boolean setpointDownInput;
	private boolean enabledPID;

	/**
	 * Set up before the test
	 */
	@Before
	public void setUp() {
		lift = new TestLiftSystem();
		lift.setInput(new InputMethod() {
			
			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.input.InputMethod#straightDrive()
			 */
			@Override
			public boolean straightDrive() {
				return false;
			}
			
			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.input.InputMethod#sidewaysMovement()
			 */
			@Override
			public double sidewaysMovement() {
				return 0;
			}
			
			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.input.InputMethod#rotation()
			 */
			@Override
			public double rotation() {
				return 0;
			}
			
			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpointUp()
			 */
			@Override
			public boolean liftSetpointUp() {
				return setpointUpInput;
			}
			
			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.input.InputMethod#liftSetpointDown()
			 */
			@Override
			public boolean liftSetpointDown() {
				return setpointDownInput;
			}
			
			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.input.InputMethod#joysticks()
			 */
			@Override
			public Joystick[] joysticks() {
				return null;
			}
			
			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.input.InputMethod#forwardMovement()
			 */
			@Override
			public double forwardMovement() {
				return 0;
			}
			
			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.input.InputMethod#analogLiftUp()
			 */
			@Override
			public double analogLiftUp() {
				return analogUpInput;
			}
			
			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.input.InputMethod#analogLiftDown()
			 */
			@Override
			public double analogLiftDown() {
				return analogDownInput;
			}
		});
	}

	/**
	 * test the run() method
	 */
	@Test
	public void testRun() {
		// we shouldn't be doing anything yet
		Assert.assertThat(motorSpeed, CoreMatchers.equalTo(0d));
		Assert.assertThat(enabledPID, CoreMatchers.equalTo(false));
		
		// test analog input
		analogUpInput = 1;
		lift.run();
		Assert.assertThat(motorSpeed, CoreMatchers.equalTo(1d));
		Assert.assertThat(enabledPID, CoreMatchers.equalTo(false));
		analogUpInput = 0;
		analogDownInput = 1;
		lift.run();
		Assert.assertThat(motorSpeed, CoreMatchers.equalTo(-1d));
		Assert.assertThat(enabledPID, CoreMatchers.equalTo(false));
		
		// test setpoint input
		setpointUpInput = true;
		analogDownInput = 0;
		lift.run();
		Assert.assertThat(enabledPID, CoreMatchers.equalTo(true));
		// PID should be disabled as we take manual control once again
		analogDownInput = 0.5;
		setpointUpInput = false;
		lift.run();
		Assert.assertThat(enabledPID, CoreMatchers.equalTo(false));
		Assert.assertThat(motorSpeed, CoreMatchers.equalTo(-0.5));
		// we start using the setpoints again and PID should again be enabled
		analogDownInput = 0;
		setpointDownInput = false;
		setpointUpInput = true;
		lift.run();
		Assert.assertThat(enabledPID, CoreMatchers.equalTo(true));
		
		// test holding down buttons
		double previousSetpoint = lift.getSetpoint();
		lift.run();
		Assert.assertThat(lift.getSetpoint(), CoreMatchers.equalTo(previousSetpoint));
		setpointUpInput = false;
		setpointDownInput = true;
		lift.run();
		previousSetpoint = lift.getSetpoint();
		lift.run();
		Assert.assertThat(lift.getSetpoint(), CoreMatchers.equalTo(previousSetpoint));
		
		// go through the setpoints
		lift.setSetpoint(LiftSystem.GROUND_SETPOINT);
		setpointDownInput = true;
		setpointUpInput = false;
		Assert.assertThat(enabledPID, CoreMatchers.equalTo(true));
		Assert.assertThat(lift.getSetpoint(), CoreMatchers.equalTo(LiftSystem.GROUND_SETPOINT));// we are already at lowest point
		setpointDownInput = false;
		setpointUpInput = true;
	}
	
	private class TestLiftSystem extends LiftSystem {

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.LiftSystem#enablePID()
		 */
		@Override
		protected synchronized void enablePID() {
			enabledPID = true;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.LiftSystem#disablePID()
		 */
		@Override
		protected synchronized void disablePID() {
			enabledPID = false;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.LiftSystem#setMotors(double)
		 */
		@Override
		public void setMotors(double speed) {
			motorSpeed = speed;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.LiftSystem#setInput(org._2585robophiles.frc2015.input.InputMethod)
		 */
		@Override
		protected synchronized void setInput(InputMethod input) {
			super.setInput(input);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.LiftSystem#setLeftMotor(edu.wpi.first.wpilibj.SpeedController)
		 */
		@Override
		protected synchronized void setLeftMotor(SpeedController leftMotor) {
			super.setLeftMotor(leftMotor);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.LiftSystem#setRightMotor(edu.wpi.first.wpilibj.SpeedController)
		 */
		@Override
		protected synchronized void setRightMotor(SpeedController rightMotor) {
			super.setRightMotor(rightMotor);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.LiftSystem#setLiftPID(edu.wpi.first.wpilibj.command.PIDSubsystem)
		 */
		@Override
		protected synchronized void setLiftPID(PIDSubsystem liftPID) {
			super.setLiftPID(liftPID);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.LiftSystem#setSetpoint(double)
		 */
		@Override
		protected synchronized void setSetpoint(double setpoint) {
			super.setSetpoint(setpoint);
		}
		
	}

}
