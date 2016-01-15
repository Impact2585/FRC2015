package org.impact2585.frc2015.systems;

import org.impact2585.frc2015.Environment;
import org.impact2585.frc2015.RobotMap;

import edu.wpi.first.wpilibj.AnalogGyro;

public class GyroSystem implements RobotSystem {
	
	private AnalogGyro gyro;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		gyro = new AnalogGyro(RobotMap.GYRO);
		gyro.initGyro();
	}
	
	/**
	 * The output of this method will be from 0 to 360
	 * @return the heading of the gyro in degrees
	 */
	public double heading(){
		if(angle() < 0){
			return 360 + (angle() % 360);
		}
		return angle() % 360;
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
