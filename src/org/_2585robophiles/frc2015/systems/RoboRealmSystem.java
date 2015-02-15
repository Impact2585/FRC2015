package org._2585robophiles.frc2015.systems;

import org._2585robophiles.frc2015.Environment;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.tables.ITable;
import edu.wpi.first.wpilibj.tables.ITableListener;
/*
 * The robot vision is implemented in this class.
 */
public class RoboRealmSystem implements RobotSystem, ITableListener {
    
    private NetworkTable nt;
    
    /**
     * Nothing is done in this constructor
     */
    public RoboRealmSystem()
    {    
    }

    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.systems.RobotSystem#init(org._2585robophiles.aerbot.Environment)
     */
    @Override
	public void init(Environment environment)
    {
        nt = NetworkTable.getTable("VisionTable");
        nt.addTableListener(this);
    }

    /* (non-Javadoc)
     * @see org._2585robophiles.aerbot.systems.RobotSystem#destroy()
     */
    @Override
    public void destroy()
    {
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.tables.ITableListener#valueChanged(edu.wpi.first.wpilibj.tables.ITable, java.lang.String, java.lang.Object, boolean)
     */
    @Override
    public void valueChanged(ITable itable, String key, Object obj, boolean isNew)
    {
        SmartDashboard.putString(key, obj.toString());
    }
}