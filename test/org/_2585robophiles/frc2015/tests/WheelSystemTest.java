package org._2585robophiles.frc2015.tests;

import org._2585robophiles.frc2015.RobotMap;
import org._2585robophiles.frc2015.input.InputMethod;
import org._2585robophiles.frc2015.systems.WheelSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.Joystick;

/**
 * Unit test for the WheelSystem class
 */
public class WheelSystemTest {

	private TestWheelSystem wheelSystem;
	private double currentForwardMovement, currentSidewaysMovement,
			currentRotation;

	/**
	 * Set up the unit test
	 */
	@Before
	public void setUp() {
		wheelSystem = new TestWheelSystem();
	}

	/**
	 * Test the run() method of the WheelSystem class
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
		});
		wheelSystem.run();
		Assert.assertEquals(0.25, currentRotation, 0);
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

	}
}
