package tannerplayer1;

import battlecode.common.*;

public class Sage extends Droid {
	public Sage(RobotController rc) {
		super(rc);
	}
	
	@Override
	public void runTypeSpecific() throws GameActionException {
		System.out.println("SAGE run() TANNER");
	}
}
