package tannerplayer1;

import battlecode.common.*;

public class Watchtower extends Building {
	public Watchtower(RobotController rc) {
		super(rc);
	}
	
	@Override
	public void runTypeSpecific() throws GameActionException {
		System.out.println("WATCHTOWER run() TANNER");
	}
}
