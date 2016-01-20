package org.impact2585.frc2015;

import org.impact2585.lib2585.Executer;
import org.impact2585.frc2015.systems.LiftSystem;

/**
 * Executer for autonomous mode
 */
public enum AutonomousExecuter implements Executer, Initializable {

	/**
	 * Basic auton that just moves forward / pushes a tote into the auto zone
	 */
	BASIC,
	
	/**
	 * Slight variation of BASIC
	 */
	TURN_DOWN,

	/**
	 * Score a tote stack (stack the three yellow totes and drop them off in auto zone)
	 */
	TOTE_STACK,

	/**
	 * Auton that does nothing so basically auton is disabled
	 */
	NONE;

	private Environment environment;
	private boolean done;
	private int phase;
	private long start;

	/* (non-Javadoc)
	 * @see org._2585robophiles.frc2015.Initializable#init(org._2585robophiles.frc2015.Environment)
	 */
	@Override
	public void init(Environment environment) {
		this.environment = environment;
		phase = 0;
		start = -1;
	}

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Executer#execute()
	 */
	@Override
	public void execute() {
		if(start == -1)
			start = System.currentTimeMillis() / 1000;
		if(!done){
			switch(this){
			case BASIC:
				// move forward a couple feet
				if(System.currentTimeMillis() / 1000.0 - start < 2.1)
					environment.getWheelSystem().drive(1, 0, .5); //1  forward, 2 center, 3 rotation
				else
					done = true;
				break;
			case TURN_DOWN:
				long time = (System.currentTimeMillis() / 1000) - start;
				if(time < 1){
					done = environment.getWheelSystem().driveDistance(5, 0 ,false);// just drive forward 8 feet 11 inches
				}
				break;
			case TOTE_STACK:
				if(phase == 0){
					if(environment.getLiftSystem().moveToHeight(LiftSystem.TOTE_PICKUP_1))
						phase++;
				}else if(phase == 1){
					if(environment.getWheelSystem().driveDistance(0, 1, false))// move out of the way of the bin
						phase++;
				}else if(phase == 2){
					// now move forward
					if(environment.getWheelSystem().driveDistance(3, 0, false))
						phase++;
				}else if(phase == 3){
					// we've manuevered around the bin and we're now in front of the tote
					if(environment.getWheelSystem().driveDistance(0, -1, false))
						phase++;
				}else if(phase == 4){
					// now move forward to pick up the second tote
					if(environment.getWheelSystem().driveDistance(1, 0, false))
						phase++;
				}else if(phase == 5){
					// pick up the second tote
					if(environment.getLiftSystem().moveToHeight(LiftSystem.TOTE_PICKUP_2))
						phase++;
				}else if(phase == 6){
					// move out of the way of the second bin
					if(environment.getWheelSystem().driveDistance(0, 1, false))
						phase++;
				}else if(phase == 7){
					// move toward the third and last tote
					if(environment.getWheelSystem().driveDistance(3, 0, false))
						phase++;
				}else if(phase == 8){
					// get back in front of the tote
					if(environment.getWheelSystem().driveDistance(0, -1, false))
						phase++;
				}else if(phase == 9){
					// move forward to pick up the last tote
					if(environment.getWheelSystem().driveDistance(1, 0, false))
						phase++;
				}else if(phase == 10){
					// pick up the last tote
					if(environment.getLiftSystem().moveToHeight(LiftSystem.TOTE_PICKUP_3))
						phase++;
				}else{
					done = environment.getWheelSystem().driveDistance(0, 3, false);
				}
				break;
			case NONE:
				done = true;
				break;
			}
		}
	}

}
