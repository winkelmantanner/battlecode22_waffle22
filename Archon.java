package tannerplayer1;

import battlecode.common.*;

public class Archon extends Building {
	public Archon(RobotController rc) {
		super(rc);
	}
	
	RobotType typeToBuild = null;
	
	public static RobotType [] ARCHON_BUILDABLE_TYPES = {
			RobotType.MINER,
			RobotType.BUILDER,
			RobotType.SAGE,
			RobotType.SOLDIER
	};
	
	@Override
	public void runTypeSpecific() throws GameActionException {
		System.out.println("ARCHON run() TANNER");
		if(rc.canBuildRobot(RobotType.MINER, Direction.NORTH)) {
			rc.buildRobot(RobotType.MINER, Direction.NORTH);
		}
	}
}
