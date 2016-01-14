package org.impact2585.frc2015.systems;

import org.impact2585.frc2015.Environment;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * This system finds the acceleration and speed of the robot
 */
public class AccelerometerSystem implements RobotSystem, Runnable {
	
	public static final double GRAVITATIONAL_ACCELERATION = 9.80665;
	public static final double METER_TO_FEET = 3.2808399;
	
	private Accelerometer accelerometer;
	private long lastUpdate;
	private double speedX;
	private double speedY;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		accelerometer = new BuiltInAccelerometer();
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
		if(lastUpdate != 0){
			double timeElapsed = (System.currentTimeMillis() - lastUpdate) / 1000d;
			// velocity = acceleration * time
			speedX += accelerationX() * timeElapsed;
			speedY += accelerationY() * timeElapsed;
		}
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
	public double getSpeedX() {
		return speedX;
	}

	/**
	 * Set meters per second speed
	 * @param speed the speed to set
	 */
	protected void setSpeedX(double speed) {
		this.speedX = speed;
	}
	
	/**
	 * The y-axis speed in meters per second
	 * @return the speedY
	 */
	public double getSpeedY() {
		return speedY;
	}

	/**
	 * Set the y-axis speed in meters per second
	 * @param speedY the speedY to set
	 */
	protected void setSpeedY(double speedY) {
		this.speedY = speedY;
	}

	/**
	 * X-axis speed in feet per second
	 * @return speed
	 */
	public double getSpeedXInFPS(){
		return speedX * METER_TO_FEET;
	}
	
	/**
	 * Y-axis speed in feet per second
	 * @return speed
	 */
	public double getSpeedYInFPS(){
		return speedY * METER_TO_FEET;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		
	}

	
}
