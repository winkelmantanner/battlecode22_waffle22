package tannerplayer;

import battlecode.common.*;

public class Miner extends Droid {
    public Miner(RobotController rc) {
        super(rc);
    }
    
    public static double LEAD_VALUE_PER_GOLD_VALUE = GameConstants.ALCHEMIST_LONELINESS_A;
    
    @Override
    public void runTypeSpecific() throws GameActionException {

        // MOVE TOWARD MINEABLES
        double minDistSqr = 12345;
        MapLocation bestLoc = null;
        // senseNearbyLocationsWithGold is implemented at https://github.com/battlecode/battlecode22/blob/6318ff4e853afff50e97a90611bd595a68afb5fc/engine/src/main/battlecode/world/RobotControllerImpl.java#L358
        for(MapLocation locWithGold : rc.senseNearbyLocationsWithGold(rc.getType().visionRadiusSquared)) {
            if(rc.getLocation().distanceSquaredTo(locWithGold) < minDistSqr) {
                minDistSqr = rc.getLocation().distanceSquaredTo(locWithGold);
                bestLoc = locWithGold;
            }
        }
        if(bestLoc == null) { // go for lead only if there is no gold
            for(MapLocation locWithLead : rc.senseNearbyLocationsWithLead(rc.getType().visionRadiusSquared)) {
                if(rc.canSenseLocation(locWithLead)
                    && rc.senseLead(locWithLead) > GameConstants.ADD_LEAD
                    && rc.getLocation().distanceSquaredTo(locWithLead) < minDistSqr
                ) {
                    minDistSqr = rc.getLocation().distanceSquaredTo(locWithLead);
                    bestLoc = locWithLead;
                }
            }
        }
        if(bestLoc != null) {
            stepAvoidingRubble(bestLoc);
        }
        
        
        // MINE
        boolean didMine = false;
        for(MapLocation locWithLead : rc.senseNearbyLocationsWithLead(rc.getType().actionRadiusSquared)) {
            while (
                rc.canSenseLocation(locWithLead)
                && rc.senseLead(locWithLead) > 1
                && rc.canMineLead(locWithLead)
            ) {
                rc.mineLead(locWithLead);
                didMine = true;
            }
        }
        for(MapLocation locWithGold : rc.senseNearbyLocationsWithGold(rc.getType().actionRadiusSquared)) {
            while (rc.canMineGold(locWithGold)) {
                rc.mineGold(locWithGold);
                didMine = true;
            }
        }
        
        
        // EXPLORE IF NO MINEABLES
        if(!didMine) {
            exploreMove();
        }
    }
}
