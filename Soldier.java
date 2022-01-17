package tannerplayer;

import battlecode.common.*;
import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

public class Soldier extends Droid {
    public Soldier(RobotController rc) {
        super(rc);
    }
    
    int totalDamageDone = 0;
    MapLocation lastTargetRbtLoc = null;
    int lastTargetRbtRound = -12345;
    
    final int BEST_SOLDIER_EXPIRATION_ROUNDS = 10;
    
    RobotInfo [] friendlySoldiers = new RobotInfo[100];
    int friendlySoldiersLength = 0;
    RobotInfo [] enemySoldiers = new RobotInfo[100];
    int enemySoldiersLength = 0;
    RobotInfo [] friendlyNonsoldiers = new RobotInfo[100];
    int friendlyNonsoldiersLength = 0;
    RobotInfo [] enemyNonsoldiers = new RobotInfo[100];
    int enemyNonsoldiersLength = 0;
    
    MapLocation getBestSoldierLocOrNull() throws GameActionException {
        if(rc.readSharedArray(BEST_SOLDIER_DAMAGE_DONE_IDX) > totalDamageDone
            && rc.getRoundNum() - rc.readSharedArray(BEST_SOLDIER_ROUND_IDX) < BEST_SOLDIER_EXPIRATION_ROUNDS
        ) {
            return new MapLocation(
                rc.readSharedArray(BEST_SOLDIER_LOC_X_IDX),
                rc.readSharedArray(BEST_SOLDIER_LOC_Y_IDX)
            );
        } else {
            return null;
        }
    }
    
    public static double amountCloser(MapLocation targetLocation, MapLocation measuredLocation, MapLocation myLocation) {
        return (double)(myLocation.distanceSquaredTo(targetLocation) - measuredLocation.distanceSquaredTo(targetLocation))
            / (1 + myLocation.distanceSquaredTo(targetLocation));
    }
    
    protected double evaluateDirForMove(Direction d) throws GameActionException {
        double value = 0;
        MapLocation adjLoc = rc.adjacentLocation(d);
        final double adjLocRubbleScalar = Utils.rubbleFormulaWithoutFloor(rc.senseRubble(adjLoc));
        for(int enemySoldierIdx = 0; enemySoldierIdx < enemySoldiersLength; enemySoldierIdx++) {
            RobotInfo enemySoldier = enemySoldiers[enemySoldierIdx];
            if(enemySoldier.location.distanceSquaredTo(adjLoc) <= enemySoldier.type.actionRadiusSquared) {
                if(friendlySoldiersLength < enemySoldiersLength) { // The calling soldier is not included in friendlySoldiersLength
                    value -= adjLocRubbleScalar
                        / Utils.rubbleFormulaWithoutFloor(rc.senseRubble(enemySoldier.location));
                } else {
                    value += Utils.rubbleFormulaWithoutFloor(rc.senseRubble(enemySoldier.location))
                        / adjLocRubbleScalar;
                }
            }
        }
        
        for(int friendlySoldierIdx = 0; friendlySoldierIdx < friendlySoldiersLength; friendlySoldierIdx++) {
            value += (double)1
                / adjLoc.distanceSquaredTo(friendlySoldiers[friendlySoldierIdx].location)
                / adjLocRubbleScalar;
        }
        
    //                final double ratioOfHealthLost = (double)(rc.getType().health - rc.getHealth()) / rc.getType().health;
    //                for(int friendlyNonsoldierIdx = 0; friendlyNonsoldierIdx < friendlyNonsoldiersLength; friendlyNonsoldierIdx++) {
    //                    RobotInfo friendlyNonsoldier = friendlyNonsoldiers[friendlyNonsoldierIdx];
    //                    if(friendlyNonsoldier.type.equals(RobotType.ARCHON)
    //                            && adjLoc.distanceSquaredTo(friendlyNonsoldier.location) > friendlyNonsoldier.type.actionRadiusSquared
    //                    ) {
    //                        value += 10 * ratioOfHealthLost * amountCloser(spawnLoc, adjLoc, rc.getLocation());
    //                    }
    //                }
    //                
        for(int enemyNonsoldierIdx = 0; enemyNonsoldierIdx < enemyNonsoldiersLength; enemyNonsoldierIdx++) {
            RobotInfo enemyNonsoldier = enemyNonsoldiers[enemyNonsoldierIdx];
            value += enemyNonsoldier.type.health * 0.1 / adjLoc.distanceSquaredTo(enemyNonsoldier.location);
        }
        
        value -= adjLocRubbleScalar;
        
        value -= (double)1 / (10 + adjLoc.distanceSquaredTo(exploreTarget));
        
        MapLocation targetLocOrNull = getBestSoldierLocOrNull();
        if(targetLocOrNull != null) {
            value += (double)10 * amountCloser(targetLocOrNull, adjLoc, rc.getLocation());
        }
        
        return value;
    }
    
    protected void soldierMove(Direction [] directionsToConsider) throws GameActionException {
        Direction bestDir = null;
        double bestDirValue = -12345;
        for(Direction d : directionsToConsider) {
            if(rc.canMove(d)) {
                final double value = evaluateDirForMove(d);
                if(value > bestDirValue) {
                    bestDir = d;
                    bestDirValue = value;
                }
            }
        }
        if(bestDir != null) {
            rc.move(bestDir);
        }
    }
    
    protected void soldierAttack() throws GameActionException {
        RobotInfo targetRbt = null;
        double bestTargetValue = -12345;
        for(RobotInfo enemyRbt : rc.senseNearbyRobots(
            rc.getType().actionRadiusSquared,
            rc.getTeam().opponent()
        )) {
            if(rc.canAttack(enemyRbt.location)) {
                double value = (double)enemyRbt.getType().health / enemyRbt.getHealth();
                if(enemyRbt.type.canAttack()) {
                    value *= 10;
                }
                if(value > bestTargetValue) {
                    targetRbt = enemyRbt;
                    bestTargetValue = value;
                }
            }
        }
        if (targetRbt != null) {
            lastTargetRbtLoc = targetRbt.location;
            lastTargetRbtRound = rc.getRoundNum();
            rc.attack(targetRbt.getLocation());
            totalDamageDone += rc.getType().damage;
            if(totalDamageDone > 0
                && (
                    totalDamageDone > rc.readSharedArray(BEST_SOLDIER_DAMAGE_DONE_IDX)
                    || rc.getRoundNum() - rc.readSharedArray(BEST_SOLDIER_ROUND_IDX) > BEST_SOLDIER_EXPIRATION_ROUNDS
                )
            ) {
                rc.writeSharedArray(BEST_SOLDIER_DAMAGE_DONE_IDX, totalDamageDone);
                rc.writeSharedArray(BEST_SOLDIER_LOC_X_IDX, rc.getLocation().x);
                rc.writeSharedArray(BEST_SOLDIER_LOC_Y_IDX, rc.getLocation().y);
                rc.writeSharedArray(BEST_SOLDIER_ROUND_IDX, rc.getRoundNum());
            }
        }
    }
    
    @Override
    public void runTypeSpecific() throws GameActionException {
        friendlySoldiersLength = 0;
        enemySoldiersLength = 0;
        friendlyNonsoldiersLength = 0;
        enemyNonsoldiersLength = 0;
        for(RobotInfo rbt : rc.senseNearbyRobots()) {
            if(rbt.getType().canAttack()) {
                if(rbt.getTeam().equals(rc.getTeam())) {
                    friendlySoldiers[friendlySoldiersLength] = rbt;
                    friendlySoldiersLength++;
                } else {
                    enemySoldiers[enemySoldiersLength] = rbt;
                    enemySoldiersLength++;
                }
            } else {
                if(rbt.getTeam().equals(rc.getTeam())) {
                    friendlyNonsoldiers[friendlyNonsoldiersLength] = rbt;
                    friendlyNonsoldiersLength++;
                } else {
                    enemyNonsoldiers[enemyNonsoldiersLength] = rbt;
                    enemyNonsoldiersLength++;
                }
            }
        }
        updateExploreTarget();
        
        final Direction[] allDirections = Direction.allDirections(); // including CENTER
        boolean[] safeDirs = new boolean[allDirections.length];
        for(Direction d : allDirections) {safeDirs[d.ordinal()] = true;}
        int safeDirCount = allDirections.length;
        if(enemySoldiersLength <= 10) {
            for(int enemySoldierIdx = 0; enemySoldierIdx < enemySoldiersLength; enemySoldierIdx++) {
                if(safeDirCount > 0) {
                    RobotInfo enemySoldier = enemySoldiers[enemySoldierIdx];
                    for(Direction d : allDirections) {
                        if(safeDirs[d.ordinal()]
                            && rc.adjacentLocation(d).distanceSquaredTo(enemySoldier.location) <= enemySoldier.type.actionRadiusSquared
                        ) {
                            safeDirs[d.ordinal()] = false;
                            safeDirCount--;
                        }
                    }
                }
            }
        }
        
        if(safeDirCount > 0 && !safeDirs[Direction.CENTER.ordinal()]) {
            soldierAttack();
            Direction [] safeDirArray = new Direction[safeDirCount];
            int safeDirArrayLength = 0;
            for(Direction d : allDirections) {
                if(safeDirs[d.ordinal()]) {
                    safeDirArray[safeDirArrayLength] = d;
                    safeDirArrayLength++;
                }
            }
            soldierMove(safeDirArray);
        } else {
            soldierMove(directions);
            soldierAttack();
        }
        
    }
}
