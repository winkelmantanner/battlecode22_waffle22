package tannerplayer1;

import battlecode.common.*;

abstract public class Building extends Robot {
    public Building(RobotController rc) {
        super(rc);
    }
    
    public void runMobilitySpecific() throws GameActionException {
        
        runTypeSpecific();
        
    }
}
