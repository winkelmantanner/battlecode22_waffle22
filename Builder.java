package tannerplayer1;

import battlecode.common.*;

public class Builder extends Droid {
	public Builder(RobotController rc) {
		super(rc);
	}
	
	@Override
	public void runTypeSpecific() throws GameActionException {
		System.out.println("BUILDER run() TANNER");
	}
}
