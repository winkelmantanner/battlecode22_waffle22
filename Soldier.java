package tannerplayer1;

import battlecode.common.*;

public class Soldier extends Droid {
    public Soldier(RobotController rc) {
        super(rc);
    }
    
    @Override
    public void runTypeSpecific() throws GameActionException {
        for(RobotInfo enemyRbt : rc.senseNearbyRobots(
            rc.getType().actionRadiusSquared,
            rc.getTeam().opponent()
        )) {
            if (rc.canAttack(enemyRbt.getLocation())) {
                rc.attack(enemyRbt.getLocation());
                break;
            }
        }
    }
}
