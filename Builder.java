package tannerplayer;

import battlecode.common.*;

public class Builder extends Droid {
    public Builder(RobotController rc) {
        super(rc);
    }
    
    @Override
    public void runTypeSpecific() throws GameActionException {
        exploreMove();
        if(rc.canSenseLocation(rc.getLocation())
            && rc.senseLead(rc.getLocation()) == 0
        ) {
            rc.disintegrate();
        }
    }
}
