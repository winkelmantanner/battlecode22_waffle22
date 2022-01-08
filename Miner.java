package tannerplayer1;

import battlecode.common.*;

public class Miner extends Droid {
    public Miner(RobotController rc) {
        super(rc);
    }
    
    @Override
    public void runTypeSpecific() throws GameActionException {
    
        for(MapLocation locWithLead : rc.senseNearbyLocationsWithLead(rc.getType().actionRadiusSquared)) {
            while (rc.canMineLead(locWithLead)) {
                rc.mineLead(locWithLead);
            }
        }
        for(MapLocation locWithLead : rc.senseNearbyLocationsWithGold(rc.getType().actionRadiusSquared)) {
            while (rc.canMineGold(locWithLead)) {
                rc.mineGold(locWithLead);
            }
        }
    }
}
