package org._2585robophiles.frc2015.systems;

import org._2585robophiles.frc2015.Environment;

import edu.wpi.first.wpilibj.Gyro;

public class GyroSystem implements RobotSystem {
	
	private Gyro gyro;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		gyro = new Gyro(3);
		gyro.initGyro();
	}
	
	/**
	 * The output of this method will be from 0 to 360
	 * @return the heading of the gyro in degrees
	 */
	public double heading(){
		return angle();
	}
	
	/**
	 * This method returns the accumulative value in degree
	 * @return angle from the gyro
	 */
	public double angle(){
		return gyro.getAngle();
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		gyro.free();
	}

}
