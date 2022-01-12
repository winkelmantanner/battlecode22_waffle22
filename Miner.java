package tannerplayer;

import battlecode.common.*;

public class Miner extends Droid {
    public Miner(RobotController rc) {
        super(rc);
    }
    
    public static double LEAD_VALUE_PER_GOLD_VALUE = GameConstants.ALCHEMIST_LONELINESS_A;
    
    @Override
    public void runTypeSpecific() throws GameActionException {

        double maxValue = 0;
        MapLocation bestLoc = null;
        for(MapLocation locWithGold : rc.senseNearbyLocationsWithGold(rc.getType().visionRadiusSquared)) {
            if(rc.canSenseLocation(locWithGold)
                && rc.senseGold(locWithGold) > 0
                && rc.senseGold(locWithGold) > maxValue / LEAD_VALUE_PER_GOLD_VALUE
            ) {
                bestLoc = locWithGold;
                maxValue = rc.senseGold(locWithGold) * LEAD_VALUE_PER_GOLD_VALUE;
            }
        }
        for(MapLocation locWithLead : rc.senseNearbyLocationsWithLead(rc.getType().visionRadiusSquared)) {
            if(rc.canSenseLocation(locWithLead)
                && rc.senseLead(locWithLead) > maxValue
                && rc.senseLead(locWithLead) > GameConstants.ADD_LEAD
            ) {
                bestLoc = locWithLead;
                maxValue = rc.senseLead(locWithLead);
            }
        }
        if(bestLoc != null) {
            stepAvoidingRubble(bestLoc);
        }
        
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
        
        if(!didMine) {
            exploreMove();
        }
    }
}
