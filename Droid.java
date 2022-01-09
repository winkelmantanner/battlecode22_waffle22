package tannerplayer;

import battlecode.common.*;

abstract public class Droid extends Robot {
    public Droid(RobotController rc) {
        super(rc);
    }
    
    protected boolean simpleTryMoveToward(MapLocation l) throws GameActionException {
        boolean didMove = false;
        Direction d = rc.getLocation().directionTo(l);
        if(rc.canMove(d)) {
            rc.move(d);
            didMove = true;
        }
        return didMove;
    }
    
    MapLocation exploreTarget = null;
    protected boolean simpleExploreMove() throws GameActionException {
        if(exploreTarget == null
            || rc.canSenseLocation(exploreTarget)
            || (
                rc.isMovementReady()
                && !rc.canMove(rc.getLocation().directionTo(exploreTarget))
            )
        ) {
            exploreTarget = new MapLocation(
                rng.nextInt(2 * rc.getMapWidth()) - (rc.getMapWidth() / 2),
                rng.nextInt(2 * rc.getMapHeight()) - (rc.getMapHeight() / 2)
            );
        }
        return simpleTryMoveToward(exploreTarget);
    }

    public void runMobilitySpecific() throws GameActionException {
        
        runTypeSpecific();
        
    }
}
