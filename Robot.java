package tannerplayer1;

import java.util.Random;

import battlecode.common.*;

abstract public class Robot {
    
    protected final RobotController rc;
    protected Random rng = null;
    public Robot(RobotController rc) {
        this.rc = rc;
        this.rng = new Random(rc.getID());
    }
    
    abstract public void runMobilitySpecific() throws GameActionException;
    abstract public void runTypeSpecific() throws GameActionException;
    
    public void runRobot() throws GameActionException {
        
        runMobilitySpecific();
        
    }
    
}
