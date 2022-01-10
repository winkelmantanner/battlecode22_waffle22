package tannerplayer;

import battlecode.common.*;

public class Soldier extends Droid {
    public Soldier(RobotController rc) {
        super(rc);
    }
    
    int totalDamageDone = 0;
    MapLocation lastTargetRbtLoc = null;
    int lastTargetRbtRound = -12345;
    
    final int BEST_SOLDIER_EXPIRATION_ROUNDS = 50;
    
    @Override
    public void runTypeSpecific() throws GameActionException {
        RobotInfo targetRbt = null;
        double bestTargetValue = -12345;
        for(RobotInfo enemyRbt : rc.senseNearbyRobots(
            rc.getType().visionRadiusSquared,
            rc.getTeam().opponent()
        )) {
            if(rc.canAttack(enemyRbt.location)) {
                final double value = (double)enemyRbt.getType().health / enemyRbt.getHealth();
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
        if(rc.getRoundNum() - lastTargetRbtRound < 10) {
            stepAvoidingRubble(lastTargetRbtLoc);
        }

        if(rc.readSharedArray(BEST_SOLDIER_DAMAGE_DONE_IDX) > totalDamageDone
            && rc.getRoundNum() - rc.readSharedArray(BEST_SOLDIER_ROUND_IDX) < BEST_SOLDIER_EXPIRATION_ROUNDS
        ) {
            stepAvoidingRubble(new MapLocation(
                rc.readSharedArray(BEST_SOLDIER_LOC_X_IDX),
                rc.readSharedArray(BEST_SOLDIER_LOC_Y_IDX)
            ));
//            rc.setIndicatorString("GOING TOWARD " + rc.readSharedArray(BEST_SOLDIER_LOC_X_IDX) + ", " + rc.readSharedArray(BEST_SOLDIER_LOC_Y_IDX)
//                + " WHERE SOLDIER WHO HAD DONE " + rc.readSharedArray(BEST_SOLDIER_DAMAGE_DONE_IDX)
//                + " WAS " + (rc.getRoundNum() - rc.readSharedArray(BEST_SOLDIER_ROUND_IDX)) + " ROUNDS AGO"
//            );
        }
        simpleExploreMove();
    }
}
