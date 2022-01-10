package tannerplayer;

import battlecode.common.*;

public class Archon extends Building {
    public Archon(RobotController rc) {
        super(rc);
    }
    
    RobotType typeToBuild = RobotType.MINER /* miner first */;
    
    int numRobotsBuilt = 0;
    
    RobotType [] ARCHON_BUILD_CHOOSER = {
        RobotType.MINER,
        RobotType.MINER,
        RobotType.SOLDIER,
        RobotType.SOLDIER,
        RobotType.SOLDIER,
        RobotType.BUILDER
    };
    
    @Override
    public void runTypeSpecific() throws GameActionException {
        
        for(int k = 0; k < 10; k++) {
            final Direction randomDir = Utils.choice(rng, Direction.allDirections());
            if(rc.getTeamLeadAmount(rc.getTeam()) > typeToBuild.buildCostLead + (5 * numRobotsBuilt)
                && rc.canBuildRobot(typeToBuild, randomDir)
            ) {
                rc.buildRobot(typeToBuild, randomDir);
                numRobotsBuilt++;
                typeToBuild = Utils.choice(rng, ARCHON_BUILD_CHOOSER);
                break;
            }
        }
        
        for(RobotInfo rbt : rc.senseNearbyRobots(rc.getType().actionRadiusSquared, rc.getTeam())) {
            if(rbt.health < rbt.type.health + rc.getType().getDamage/*returns negative*/(rc.getLevel())
                && rc.canRepair(rbt.location)
            ) {
                rc.repair(rbt.location);
                break;
            }
        }
    }
}
