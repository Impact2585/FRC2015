package org.impact2585.frc2015.systems;

import org.impact2585.frc2015.Environment;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;

/**
 * The robot vision is implemented in this class.
 */
public class RoboRealmSystem implements RobotSystem, ITableListener {
	private final double MIN_AREA = 0; // The value is 0 for testing purposes
										// only.
	private final double MAX_AREA = 1; // The value is 1 for testing purposes
										// only.
	private double distance;
	private final double DISTANCE_CONSTANT = 0; // The value is 0 for testing
												// purposes only.
	private double area;
	private double xCoord;
	private double yCoord;
	private NetworkTable nt;

	/**
	 * Nothing is done in this constructor
	 */
	public RoboRealmSystem() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.impact2585.frc2015.systems.aerbot.systems.RobotSystem#init(org.impact2585.frc2015.aerbot.Environment)
	 */
	@Override
	public void init(Environment environment) {
		nt = NetworkTable.getTable("VisionTable");
		nt.addTableListener(this);
		setNums();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see org.impact2585.frc2015.systems.aerbot.systems.RobotSystem#destroy()
	 */
	@Override
	public void destroy() {
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.tables.ITableListener#valueChanged(edu.wpi.first.wpilibj.tables.ITable, java.lang.String, java.lang.Object, boolean)
	 */
	@Override
	public void valueChanged(ITable itable, String key, Object obj, boolean isNew) {
		SmartDashboard.putString(key, obj.toString());
		setNums();
	}

	/**
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.tables.ITableListener#valueChanged(edu.wpi.first.wpilibj.tables.ITable, java.lang.String, java.lang.Object, boolean)
	 */
	public void setNums() {
		yCoord = nt.getNumber("COG_Y", 0);
		xCoord = nt.getNumber("COG_X", 0);
		area = nt.getNumber("COG_AREA", 0);
		if (MIN_AREA < area && area < MAX_AREA) {
			distance = area / DISTANCE_CONSTANT;
		} else {
			area = -5555;
		}
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.tables.ITableListener#valueChanged(edu.wpi.first.wpilibj.tables.ITable, java.lang.String, java.lang.Object, boolean)
	 */
	public double getDistance() {
		return distance;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.tables.ITableListener#valueChanged(edu.wpi.first.wpilibj.tables.ITable, java.lang.String, java.lang.Object, boolean)
	 */
	public double getX() {
		return xCoord;
	}
	
	/**
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.tables.ITableListener#valueChanged(edu.wpi.first.wpilibj.tables.ITable, java.lang.String, java.lang.Object, boolean)
	 */
	public double getY() {
		return yCoord;
	}
}
