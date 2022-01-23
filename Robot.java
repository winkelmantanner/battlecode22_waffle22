package tannerplayer;

import java.util.Random;

import battlecode.common.*;

abstract public class Robot {
    
    protected final RobotController rc;
    protected Random rng = null;
    protected MapLocation spawnLoc = null;
    public Robot(RobotController rc) {
        this.rc = rc;
        this.rng = new Random(rc.getID());
        this.spawnLoc = rc.getLocation();
    }
    protected MapLocation parentLoc = null;
    
    abstract public void runMobilitySpecific() throws GameActionException;
    abstract public void runTypeSpecific() throws GameActionException;
    
    public void runRobot() throws GameActionException {
        if(parentLoc == null
            && !rc.getType().equals(RobotType.ARCHON)
        ) {
            for(Direction d : directions) {
                MapLocation l = rc.adjacentLocation(d);
                if(rc.onTheMap(l)) {
                    RobotInfo rbt = rc.senseRobotAtLocation(l);
                    if(rbt != null
                        && rbt.team.equals(rc.getTeam())
                        && rbt.type.equals(RobotType.ARCHON)
                    ) {
                        this.parentLoc = rbt.location;
                    }
                }
            }
        }
            
        runMobilitySpecific();
    }
    
    public static double amountCloser(MapLocation targetLocation, MapLocation measuredLocation, MapLocation myLocation) {
        return Utils.fastSqrt(myLocation.distanceSquaredTo(targetLocation))
            - Utils.fastSqrt(measuredLocation.distanceSquaredTo(targetLocation));
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
    
    static final int BEST_SOLDIER_DAMAGE_DONE_IDX = 0;
    static final int BEST_SOLDIER_LOC_X_IDX = 1;
    static final int BEST_SOLDIER_LOC_Y_IDX = 2;
    static final int BEST_SOLDIER_ROUND_IDX = 3;
}
