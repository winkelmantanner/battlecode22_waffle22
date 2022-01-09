package tannerplayer;

import battlecode.common.*;

public class Soldier extends Droid {
    public Soldier(RobotController rc) {
        super(rc);
    }
    
    @Override
    public void runTypeSpecific() throws GameActionException {
        for(RobotInfo enemyRbt : rc.senseNearbyRobots(
            rc.getType().visionRadiusSquared,
            rc.getTeam().opponent()
        )) {
            simpleTryMoveToward(enemyRbt.getLocation());
            if (rc.canAttack(enemyRbt.getLocation())) {
                rc.attack(enemyRbt.getLocation());
                break;
            }
        }
        
        simpleExploreMove();
    }
}
