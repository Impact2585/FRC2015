package org._2585robophiles.frc2015.tests;

import org._2585robophiles.frc2015.RobotMap;
import org._2585robophiles.frc2015.input.InputMethod;
import org._2585robophiles.frc2015.systems.AccelerometerSystem;
import org._2585robophiles.frc2015.systems.WheelSystem;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * Unit test for the WheelSystem class
 */
public class WheelSystemTest {

	private TestWheelSystem wheelSystem;
	private double currentForwardMovement, currentSidewaysMovement,
			currentRotation;
	private boolean distancePIDEnabled, straightDrivePIDEnabled;

	/**
	 * Set up the unit test
	 */
	@Before
	public void setUp() {
		wheelSystem = new TestWheelSystem();
		wheelSystem.setAccelerometer(new AccelerometerSystem(){

			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.systems.AccelerometerSystem#getSpeedX()
			 */
			@Override
			public double getSpeedX() {
				return 0;
			}

			/* (non-Javadoc)
			 * @see org._2585robophiles.frc2015.systems.AccelerometerSystem#getSpeedY()
			 */
			@Override
			public double getSpeedY() {
				return 0;
			}
			
		});
	}

	/**
	 * Test the general things in the run() method of the WheelSystem class
	 */
	@Test
	public void testRun() {
		
		// first make sure we aren't moving without input
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(0, currentForwardMovement, 0);
		Assert.assertEquals(0, currentRotation, 0);

		// test forward ramping
		wheelSystem.setInput(new InputMethod() {

			@Override
			public double sidewaysMovement() {
				return 0;
			}

			@Override
			public double rotation() {
				return 0;
			}

			@Override
			public double forwardMovement() {
				return 1;
			}

			@Override
			public Joystick[] joysticks() {
				return null;
			}

			@Override
			public boolean straightDrive() {
				return false;
			}
		});
		wheelSystem.run();
		Assert.assertEquals(0, currentRotation, 0);
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(RobotMap.FORWARD_RAMPING, currentForwardMovement, 0);
		wheelSystem.setInput(new InputMethod() {

			@Override
			public double sidewaysMovement() {
				return 0;
			}

			@Override
			public double rotation() {
				return 0;
			}

			@Override
			public double forwardMovement() {
				return -1;
			}

			@Override
			public Joystick[] joysticks() {
				return null;
			}

			@Override
			public boolean straightDrive() {
				return false;
			}
		});
		wheelSystem.run();
		Assert.assertEquals(0, currentRotation, 0);
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(RobotMap.FORWARD_RAMPING
				+ (-1 - RobotMap.FORWARD_RAMPING) * RobotMap.FORWARD_RAMPING,
				currentForwardMovement, 0);
		
		// test sideways ramping
				wheelSystem.setInput(new InputMethod() {

					@Override
					public double sidewaysMovement() {
						return 1;
					}

					@Override
					public double rotation() {
						return 0;
					}

					@Override
					public double forwardMovement() {
						return 0;
					}

					@Override
					public Joystick[] joysticks() {
						return null;
					}

					@Override
					public boolean straightDrive() {
						return false;
					}
				});
				wheelSystem.run();
				Assert.assertEquals(0, currentRotation, 0);
				Assert.assertEquals(RobotMap.SIDEWAYS_RAMPING, currentSidewaysMovement, 0);
				wheelSystem.setInput(new InputMethod() {

					@Override
					public double sidewaysMovement() {
						return -1;
					}

					@Override
					public double rotation() {
						return 0;
					}

					@Override
					public double forwardMovement() {
						return 0;
					}

					@Override
					public Joystick[] joysticks() {
						return null;
					}

					@Override
					public boolean straightDrive() {
						return false;
					}
				});
				wheelSystem.run();
				Assert.assertEquals(0, currentRotation, 0);
				Assert.assertEquals(RobotMap.SIDEWAYS_RAMPING
						+ (-1 - RobotMap.SIDEWAYS_RAMPING) * RobotMap.SIDEWAYS_RAMPING,
						currentSidewaysMovement, 0);

		// test squared rotation
		wheelSystem.setInput(new InputMethod() {

			@Override
			public double sidewaysMovement() {
				return 0;
			}

			@Override
			public double rotation() {
				return -1;
			}

			@Override
			public Joystick[] joysticks() {
				return null;
			}

			@Override
			public double forwardMovement() {
				return 0;
			}

			@Override
			public boolean straightDrive() {
				return false;
			}
		});
		wheelSystem.run();
		Assert.assertEquals(-1, currentRotation, 0);
		wheelSystem.setInput(new InputMethod() {

			@Override
			public double sidewaysMovement() {
				return 0;
			}

			@Override
			public double rotation() {
				return 0.5;
			}

			@Override
			public Joystick[] joysticks() {
				return null;
			}

			@Override
			public double forwardMovement() {
				return 0;
			}

			@Override
			public boolean straightDrive() {
				return false;
			}
		});
		wheelSystem.run();
		Assert.assertEquals(Math.pow(0.5, RobotMap.ROTATION_EXPONENT), currentRotation, 0);
		
		// test deadzone
		wheelSystem.setInput(new InputMethod() {
			
			@Override
			public double forwardMovement() {
				return 0.05;
			}
			
			@Override
			public double sidewaysMovement() {
				return 0.3;
			}
			
			@Override
			public double rotation() {
				return 0;
			}
			
			@Override
			public Joystick[] joysticks() {
				return null;
			}

			@Override
			public boolean straightDrive() {
				return false;
			}
		});
		wheelSystem.run();
		Assert.assertTrue(currentSidewaysMovement > 0);
		Assert.assertEquals(0, currentForwardMovement, 0);
		
		wheelSystem.setInput(new InputMethod() {
			
			@Override
			public double sidewaysMovement() {
				return 0.07;
			}
			
			@Override
			public double rotation() {
				return 0;
			}
			
			@Override
			public Joystick[] joysticks() {
				return null;
			}
			
			@Override
			public double forwardMovement() {
				return 0.3;
			}

			@Override
			public boolean straightDrive() {
				return false;
			}
		});
		wheelSystem.run();
		Assert.assertTrue(currentForwardMovement > 0);
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		
		wheelSystem.setInput(new InputMethod() {
			
			@Override
			public double sidewaysMovement() {
				return 0.09;
			}
			
			@Override
			public double rotation() {
				return 0;
			}
			
			@Override
			public Joystick[] joysticks() {
				return null;
			}
			
			@Override
			public double forwardMovement() {
				return 0.09;
			}

			@Override
			public boolean straightDrive() {
				return false;
			}
		});
		wheelSystem.run();
		Assert.assertEquals(0, currentSidewaysMovement, 0);
		Assert.assertEquals(0, currentForwardMovement, 0);
	}
	
	/**
	 * Test the driveDistance method
	 */
	@Test
	public void testDriveDistance(){
		Assert.assertFalse(distancePIDEnabled);
		for(int i = 0; i < 3; i++){
			wheelSystem.driveDistance(3);
			Assert.assertTrue(distancePIDEnabled);
			Assert.assertTrue(wheelSystem.getLastDistanceUpdate() > 0);
		}
		wheelSystem.setDistanceDriven(3);
		wheelSystem.driveDistance(3);
		Assert.assertFalse(distancePIDEnabled);
		Assert.assertEquals(0, wheelSystem.getLastDistanceUpdate());
	}
	
	/**
	 * test straight driving
	 */
	@Test
	public void testStraightDrive(){
		wheelSystem.disableSraightDrivePID();
		Assert.assertThat(straightDrivePIDEnabled, CoreMatchers.equalTo(false));
		wheelSystem.straightDrive(0.3, 0.3);
		Assert.assertThat(currentForwardMovement, CoreMatchers.equalTo(0.3));
		Assert.assertThat(currentSidewaysMovement, CoreMatchers.equalTo(0.3));
		Assert.assertThat(straightDrivePIDEnabled, CoreMatchers.equalTo(true));
	}

	/**
	 * WheelSystem subclass for the purpose of unit testing
	 */
	private class TestWheelSystem extends WheelSystem {

		/*
		 * (non-Javadoc)
		 * 
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#drive(double,
		 * double, double)
		 */
		@Override
		public void drive(double normalMovement, double sidewaysMovement,
				double rotation) {
			currentForwardMovement = normalMovement;
			currentSidewaysMovement = sidewaysMovement;
			currentRotation = rotation;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setInput(org.
		 * _2585robophiles.frc2015.input.InputMethod)
		 */
		@Override
		protected synchronized void setInput(InputMethod input) {
			super.setInput(input);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setDistanceDriven(double)
		 */
		@Override
		protected synchronized void setDistanceDriven(double distanceDriven) {
			super.setDistanceDriven(distanceDriven);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setLastDistanceUpdate(long)
		 */
		@Override
		protected synchronized void setLastDistanceUpdate(
				long lastDistanceUpdate) {
			super.setLastDistanceUpdate(lastDistanceUpdate);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setPreviousNormalMovement(double)
		 */
		@Override
		protected synchronized void setPreviousNormalMovement(
				double currentNormalMovement) {
			super.setPreviousNormalMovement(currentNormalMovement);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setSidewaysMotor(edu.wpi.first.wpilibj.SpeedController)
		 */
		@Override
		protected synchronized void setSidewaysMotor(
				SpeedController sidewaysMotor) {
			super.setSidewaysMotor(sidewaysMotor);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#setAccelerometer(org._2585robophiles.frc2015.systems.AccelerometerSystem)
		 */
		@Override
		protected synchronized void setAccelerometer(
				AccelerometerSystem accelerometer) {
			super.setAccelerometer(accelerometer);
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#enableDistancePID(double)
		 */
		@Override
		protected synchronized void enableDistancePID(double setpoint) {
			distancePIDEnabled = true;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#disableDistancePID()
		 */
		@Override
		protected synchronized void disableDistancePID() {
			distancePIDEnabled = false;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#enableStraightDrivePID(double)
		 */
		@Override
		protected synchronized void enableStraightDrivePID() {
			straightDrivePIDEnabled = true;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.WheelSystem#disableSraightDrivePID()
		 */
		@Override
		protected synchronized void disableSraightDrivePID() {
			straightDrivePIDEnabled = false;
		}

	}
}
