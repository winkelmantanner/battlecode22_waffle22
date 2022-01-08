package tannerplayer1;

import battlecode.common.*;

public class Soldier extends Droid {
	public Soldier(RobotController rc) {
		super(rc);
	}
	
	@Override
	public void runTypeSpecific() throws GameActionException {
		System.out.println("SOLDIER run() TANNER");
	}
}
