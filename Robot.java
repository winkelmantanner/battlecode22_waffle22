package tannerplayer1;

import battlecode.common.*;

abstract public class Robot {
	
	protected final RobotController rc;
	public Robot(RobotController rc) {
		this.rc = rc;
	}
	
	abstract public void runMobilitySpecific() throws GameActionException;
	abstract public void runTypeSpecific() throws GameActionException;
	
	public void runRobot() throws GameActionException {
		
		runMobilitySpecific();
		
	}
}
