package org.impact2585.frc2015.tests;

import org.hamcrest.CoreMatchers;
import org.impact2585.frc2015.systems.AccelerometerSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the accelerometer system
 */
public class AccelerometerTest {
	
	private TestAccelerometerSystem accelerometer;
	private double accelerationX;
	private double accelerationY;

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
		accelerometer.setLastUpdate(System.currentTimeMillis());
		accelerometer.run();
		Assert.assertThat(accelerometer.getSpeedX(), CoreMatchers.equalTo(0.0));
		Assert.assertThat(accelerometer.getSpeedY(), CoreMatchers.equalTo(0.0));
		
		// accelerate forward to half of acceleration due to gravity
		accelerationX = accelerationY = AccelerometerSystem.GRAVITATIONAL_ACCELERATION;
		accelerometer.setLastUpdate(System.currentTimeMillis() - 500);
		accelerometer.run();
		Assert.assertThat(accelerometer.getSpeedX(), CoreMatchers.equalTo(AccelerometerSystem.GRAVITATIONAL_ACCELERATION / 2));
		Assert.assertThat(accelerometer.getSpeedY(), CoreMatchers.equalTo(AccelerometerSystem.GRAVITATIONAL_ACCELERATION / 2));
		
		// decelerate back down to 0
		accelerationX = accelerationY = -AccelerometerSystem.GRAVITATIONAL_ACCELERATION / 2;
		accelerometer.setLastUpdate(System.currentTimeMillis());
		accelerometer.run();
		accelerometer.setLastUpdate(System.currentTimeMillis() - 1000);
		accelerometer.run();
		Assert.assertThat(accelerometer.getSpeedX(), CoreMatchers.equalTo(0.0));
		Assert.assertThat(accelerometer.getSpeedY(), CoreMatchers.equalTo(0.0));
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
			return accelerationY;
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
