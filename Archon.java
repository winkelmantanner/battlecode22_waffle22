package tannerplayer1;

import battlecode.common.*;

public class Archon extends Building {
    public Archon(RobotController rc) {
        super(rc);
    }
    
    RobotType typeToBuild = RobotType.MINER;
    public static RobotType [] ARCHON_LEAD_BUILDABLE_TYPES = {
        RobotType.MINER,
        RobotType.BUILDER,
        RobotType.SOLDIER
    };
    
    @Override
    public void runTypeSpecific() throws GameActionException {
        
        for(int k = 0; k < 10; k++) {
            final Direction randomDir = Utils.choice(rng, Direction.allDirections());
            if(rc.canBuildRobot(typeToBuild, randomDir)) {
                rc.buildRobot(typeToBuild, randomDir);
                typeToBuild = Utils.choice(rng, ARCHON_LEAD_BUILDABLE_TYPES);
                break;
            }
        }
    }
}
