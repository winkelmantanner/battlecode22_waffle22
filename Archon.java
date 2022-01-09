package tannerplayer;

import battlecode.common.*;

public class Archon extends Building {
    public Archon(RobotController rc) {
        super(rc);
    }
    
    RobotType typeToBuild = RobotType.MINER /* miner first */;
    
    int numRobotsBuilt = 0;
    
    @Override
    public void runTypeSpecific() throws GameActionException {
        
        for(int k = 0; k < 10; k++) {
            final Direction randomDir = Utils.choice(rng, Direction.allDirections());
            if(rc.getTeamLeadAmount(rc.getTeam()) > typeToBuild.buildCostLead + (5 * numRobotsBuilt)
                && rc.canBuildRobot(typeToBuild, randomDir)
            ) {
                rc.buildRobot(typeToBuild, randomDir);
                numRobotsBuilt++;
                typeToBuild = Utils.choice(rng, new RobotType[] {RobotType.MINER, RobotType.SOLDIER});
                break;
            }
        }
    }
}
