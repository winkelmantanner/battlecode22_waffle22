package tannerplayer1;

import battlecode.common.*;

abstract public class Droid extends Robot {
    public Droid(RobotController rc) {
        super(rc);
    }
    
    protected void simpleTryMoveToward(MapLocation l) throws GameActionException {
        Direction d = rc.getLocation().directionTo(l);
        if(rc.canMove(d)) {
            rc.move(d);
        }
    }

    public void runMobilitySpecific() throws GameActionException {
        
        runTypeSpecific();
        
        for(int k = 0; k < 10; k++) {
            Direction dir = Utils.choice(rng, Direction.allDirections());
            if (rc.canMove(dir)) {
                rc.move(dir);
                break;
            }
        }
    }
}
