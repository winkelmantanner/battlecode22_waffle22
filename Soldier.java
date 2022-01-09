package tannerplayer;

import battlecode.common.*;

public class Soldier extends Droid {
    public Soldier(RobotController rc) {
        super(rc);
    }
    
    @Override
    public void runTypeSpecific() throws GameActionException {
        RobotInfo leader = null;
        for(RobotInfo friendlyRbt : rc.senseNearbyRobots(
            rc.getType().visionRadiusSquared,
            rc.getTeam()
        )) {
            if(friendlyRbt.type.equals(RobotType.SOLDIER)
                && (leader == null
                    || friendlyRbt.ID < leader.ID
                )
            ) {
                leader = friendlyRbt;
            }
        }
        if(leader != null) {
            stepAvoidingRubble(leader.getLocation());
        }
        
        for(RobotInfo enemyRbt : rc.senseNearbyRobots(
            rc.getType().visionRadiusSquared,
            rc.getTeam().opponent()
        )) {
            stepAvoidingRubble(enemyRbt.getLocation());
            if (rc.canAttack(enemyRbt.getLocation())) {
                rc.attack(enemyRbt.getLocation());
                break;
            }
        }
        
        simpleExploreMove();
    }
}
