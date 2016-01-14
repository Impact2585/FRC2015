package org.impact2585.frc2015.systems;

import org.impact2585.frc2015.Environment;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DashboardSystem implements RobotSystem, Runnable {
	
	private AccelerometerSystem accelerometer;
	private GyroSystem gyro;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		accelerometer = environment.getAccelerometerSystem();
		gyro = environment.getGyroSystem();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		SmartDashboard.putNumber("X Acceleration", accelerometer.accelerationX());
		SmartDashboard.putNumber("Y Acceleration", accelerometer.accelerationY());
		SmartDashboard.putNumber("Z Acceleration", accelerometer.accelerationZ());
		SmartDashboard.putNumber("Heading", gyro.heading());
	}
	
	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		
	}

}
