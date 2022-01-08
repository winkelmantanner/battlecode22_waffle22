package tannerplayer1;

import battlecode.common.*;

abstract public class Droid extends Robot {
	public Droid(RobotController rc) {
		super(rc);
	}

	public void runMobilitySpecific() throws GameActionException {
		
		runTypeSpecific();
		
	}
}
