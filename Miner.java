package tannerplayer1;

import battlecode.common.*;

public class Miner extends Droid {
    public Miner(RobotController rc) {
        super(rc);
    }
    
    @Override
    public void runTypeSpecific() throws GameActionException {
        for(MapLocation locWithLead : rc.senseNearbyLocationsWithLead(rc.getType().actionRadiusSquared)) {
            while (
                rc.canSenseLocation(locWithLead)
                && rc.senseLead(locWithLead) > 1
                && rc.canMineLead(locWithLead)
            ) {
                rc.mineLead(locWithLead);
            }
        }
        for(MapLocation locWithGold : rc.senseNearbyLocationsWithGold(rc.getType().actionRadiusSquared)) {
            while (rc.canMineGold(locWithGold)) {
                rc.mineGold(locWithGold);
            }
        }
        

        for(MapLocation locWithGold : rc.senseNearbyLocationsWithGold(rc.getType().visionRadiusSquared)) {
            simpleTryMoveToward(locWithGold);
        }
        for(MapLocation locWithLead : rc.senseNearbyLocationsWithLead(rc.getType().visionRadiusSquared)) {
            if(rc.canSenseLocation(locWithLead)
                && rc.senseLead(locWithLead) > GameConstants.ADD_LEAD
            ) {
                simpleTryMoveToward(locWithLead);
            }
        }
    }
}
