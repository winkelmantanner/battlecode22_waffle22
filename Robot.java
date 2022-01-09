package tannerplayer;

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
    
    // Because Direction.allDirections() includes Direction.CENTER
    static final Direction[] directions = {
        Direction.NORTH,
        Direction.NORTHEAST,
        Direction.EAST,
        Direction.SOUTHEAST,
        Direction.SOUTH,
        Direction.SOUTHWEST,
        Direction.WEST,
        Direction.NORTHWEST,
    };
    
}
