package tannerplayer1;

import battlecode.common.*;

abstract public class Droid extends Robot {
    public Droid(RobotController rc) {
        super(rc);
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
