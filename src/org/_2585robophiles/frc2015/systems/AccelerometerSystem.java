package org._2585robophiles.frc2015.systems;

import org._2585robophiles.frc2015.Environment;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * This system finds the acceleration and speed of the robot
 */
public class AccelerometerSystem implements RobotSystem, Runnable {
	
	public static final double GRAVITATIONAL_ACCELERATION = 9.80665;
	public static final double METER_TO_FEET = 3.2808399;
	
	private ADXL345_I2C accelerometer;
	private long lastUpdate;
	private double speed;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
	    accelerometer = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
	}
	
	/**
	 * @return x-axis acceleration in meters per second squared
	 */
	public double accelerationX(){
		return accelerometer.getX() * GRAVITATIONAL_ACCELERATION;
	}
	
	/**
	 * @return y-axis acceleration in meters per second squared
	 */
	public double accelerationY() {
		return accelerometer.getY() * GRAVITATIONAL_ACCELERATION;
	}
	
	/**
	 * @return z-axis acceleration in meters per second squared
	 */
	public double accelerationZ() {
		return accelerometer.getZ() * GRAVITATIONAL_ACCELERATION;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		if(lastUpdate != 0)
			speed += accelerationX() * (System.currentTimeMillis() - lastUpdate) / 1000;
		lastUpdate = System.currentTimeMillis();
	}


	/**
	 * @return the lastUpdate
	 */
	public long getLastUpdate() {
		return lastUpdate;
	}

	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	protected void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	/**
	 * Speed in meters per second
	 * @return the speed
	 */
	public double getSpeed() {
		return speed;
	}

	/**
	 * Set meters per second speed
	 * @param speed the speed to set
	 */
	protected void setSpeed(double speed) {
		this.speed = speed;
	}
	
	/**
	 * Speed in feet per second
	 * @return speed
	 */
	public double getSpeedInFPS(){
		return speed * METER_TO_FEET;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		
	}

	
}
