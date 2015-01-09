package org._2585robophiles.frc2015.tests;

import org._2585robophiles.frc2015.systems.AccelerometerSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the accelerometer system
 */
public class AccelerometerTest {
	
	private TestAccelerometerSystem accelerometer;
	private double accelerationX;

	/**
	 * Set up before test is run
	 */
	@Before
	public void setUp() {
		accelerometer = new TestAccelerometerSystem();
		
		
	}

	/**
	 * Run the test
	 */
	@Test
	public void test() {
		// we just started so speed should be 0
		accelerationX = AccelerometerSystem.GRAVITATIONAL_ACCELERATION;
		accelerometer.setLastUpdate(System.currentTimeMillis());
		accelerometer.run();
		Assert.assertEquals(0, accelerometer.getSpeed(), 0.001);
		
		// accelerate forward to half of acceleration due to gravity
		accelerometer.setLastUpdate(System.currentTimeMillis() - 500);
		accelerometer.run();
		Assert.assertEquals(AccelerometerSystem.GRAVITATIONAL_ACCELERATION / 2, accelerometer.getSpeed(), 0.001);
		
		// decelerate back down to 0
		accelerationX = -AccelerometerSystem.GRAVITATIONAL_ACCELERATION / 2;
		accelerometer.setLastUpdate(System.currentTimeMillis());
		accelerometer.run();
		accelerometer.setLastUpdate(System.currentTimeMillis() - 1000);
		accelerometer.run();
		Assert.assertEquals(0, accelerometer.getSpeed(), 0.001);
	}

	private class TestAccelerometerSystem extends AccelerometerSystem{

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.AccelerometerSystem#accelerationX()
		 */
		@Override
		public double accelerationX() {
			return accelerationX;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.AccelerometerSystem#accelerationY()
		 */
		@Override
		public double accelerationY() {
			return 0;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.AccelerometerSystem#accelerationZ()
		 */
		@Override
		public double accelerationZ() {
			return 0;
		}

		/* (non-Javadoc)
		 * @see org._2585robophiles.frc2015.systems.AccelerometerSystem#setLastUpdate(long)
		 */
		@Override
		protected void setLastUpdate(long lastUpdate) {
			super.setLastUpdate(lastUpdate);
		}
		
	}
}
