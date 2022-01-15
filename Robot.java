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
    
    abstract public void runMobilitySpecific() throws GameActionException;
    abstract public void runTypeSpecific() throws GameActionException;
    
//    void doEnemyArchonComm() throws GameActionException {
//        if(rc.getRoundNum() <= 3) {
//            rc.writeSharedArray(ENEMY_ARCHON_X_IDX, GameConstants.MAX_SHARED_ARRAY_VALUE);
//            rc.writeSharedArray(ENEMY_ARCHON_Y_IDX, GameConstants.MAX_SHARED_ARRAY_VALUE);
//        }
//        for(RobotInfo rbt : rc.senseNearbyRobots(
//            rc.getType().visionRadiusSquared,
//            rc.getTeam().opponent()
//        )) {
//            if(rbt.type.equals(RobotType.ARCHON)) {
//                rc.writeSharedArray(ENEMY_ARCHON_X_IDX, rbt.location.x);
//                rc.writeSharedArray(ENEMY_ARCHON_Y_IDX, rbt.location.y);
//                break;
//            }
//        }
//        MapLocation enemyArchonLocFromSharedArray = new MapLocation(
//            rc.readSharedArray(ENEMY_ARCHON_X_IDX),
//            rc.readSharedArray(ENEMY_ARCHON_Y_IDX)
//        );
//        if(rc.canSenseLocation(enemyArchonLocFromSharedArray)) {
//            RobotInfo probablyEnemyArchon = rc.senseRobotAtLocation(enemyArchonLocFromSharedArray);
//            if(probablyEnemyArchon == null || 
//                !(probablyEnemyArchon.type.equals(RobotType.ARCHON)
//                    && probablyEnemyArchon.team.equals(rc.getTeam().opponent())
//                )
//            ) {
//                rc.writeSharedArray(ENEMY_ARCHON_X_IDX, GameConstants.MAX_SHARED_ARRAY_VALUE);
//                rc.writeSharedArray(ENEMY_ARCHON_Y_IDX, GameConstants.MAX_SHARED_ARRAY_VALUE);
//            }
//        }
//    }
//    MapLocation getEnemyArchonLocOrNullFromComms() throws GameActionException {
//        final int x = rc.readSharedArray(ENEMY_ARCHON_X_IDX);
//        final int y = rc.readSharedArray(ENEMY_ARCHON_Y_IDX);
//        if(x != GameConstants.MAX_SHARED_ARRAY_VALUE && y != GameConstants.MAX_SHARED_ARRAY_VALUE) {
//            return new MapLocation(x, y);
//        } else {
//            return null;
//        }
//    }
    
    public void runRobot() throws GameActionException {
//        doEnemyArchonComm();
            
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
    
    static final int BEST_SOLDIER_DAMAGE_DONE_IDX = 0;
    static final int BEST_SOLDIER_LOC_X_IDX = 1;
    static final int BEST_SOLDIER_LOC_Y_IDX = 2;
    static final int BEST_SOLDIER_ROUND_IDX = 3;
}
