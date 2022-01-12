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
    
    boolean stepAvoidingRubble(MapLocation target_loc) throws GameActionException {
        final MapLocation myLoc = rc.getLocation();
        boolean did_move = false;
        Direction best_dir = null;
        double max_value = -12345;
        final int start_dist = myLoc.distanceSquaredTo(target_loc);
        for(Direction dir : directions) {
            MapLocation landing_loc = rc.adjacentLocation(dir);
            if(rc.canMove(dir)
                && rc.canSenseLocation(landing_loc)
            ) {
                int end_dist = landing_loc.distanceSquaredTo(target_loc);
                double value = start_dist - end_dist;
                // Allow value to be negative, representing moving away from the target.
                // Minimize the increase in distance to the target and also minimize rubble.
                if(value > 0) {
                    value /= Utils.rubbleFormulaWithoutFloor(rc.senseRubble(landing_loc));
                } else {
                    value *= Utils.rubbleFormulaWithoutFloor(rc.senseRubble(landing_loc));
                }
                if(value > max_value) {
                    best_dir = dir;
                    max_value = value;
                }
            }
        }
        if(best_dir != null) {
            rc.move(best_dir);
            did_move = true;
        } // otherwise I'm stuck
        return did_move;
    }
    
    MapLocation exploreTarget = null;
    protected boolean exploreMove() throws GameActionException {
        if(exploreTarget == null
            || rc.canSenseLocation(exploreTarget)
            || !rc.onTheMap(rc.adjacentLocation(rc.getLocation().directionTo(exploreTarget)))
        ) {
            exploreTarget = new MapLocation(
                rng.nextInt(2 * rc.getMapWidth()) - (rc.getMapWidth() / 2),
                rng.nextInt(2 * rc.getMapHeight()) - (rc.getMapHeight() / 2)
            );
        }
        return stepAvoidingRubble(exploreTarget);
    }

    public void runMobilitySpecific() throws GameActionException {
        
        runTypeSpecific();
        
    }
}
