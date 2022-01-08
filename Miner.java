package tannerplayer1;

import battlecode.common.*;

public class Miner extends Droid {
	public Miner(RobotController rc) {
		super(rc);
	}
	
	@Override
	public void runTypeSpecific() throws GameActionException {
		System.out.println("MINER run() TANNER");
	}
}
