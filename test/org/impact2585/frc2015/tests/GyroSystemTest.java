/**
 * 
 */
package org.impact2585.frc2015.tests;

import org.impact2585.frc2015.systems.GyroSystem;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the Gyro system
 */
public class GyroSystemTest {
	
	private TestGyroSystem gyro;
	private double angle;
	
	/**
	 * setup gyro for the test
	 */
	@Before
	public void setUp() {
		gyro = new TestGyroSystem();
	}

	/**
	 * test the gyro
	 */
	@Test
	public void test() {
		//normal test
		angle = 62;
		Assert.assertEquals(62, gyro.heading(), 0);
		//test negative angle
		angle = -361;
		Assert.assertEquals(359, gyro.heading(), 0);
		//test angle over 360
		angle = 361.0;
		Assert.assertEquals(1, gyro.heading(), 0);
		//test big negative angle
		angle = -1*(360*56+67);
		Assert.assertEquals(360-67, gyro.heading(), 0);
		//test big angles
		angle = 360*56+67;
		Assert.assertEquals(67, gyro.heading(), 0);
		
	}
	
	private class TestGyroSystem extends GyroSystem{

		@Override
		public double angle() {
			return angle;
		}
		
	}

}
