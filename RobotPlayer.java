package tannerplayer1;

import battlecode.common.*;

/**
 * RobotPlayer is the class that describes your main robot strategy.
 * The run() method inside this class is like your main function: this is what we'll call once your robot
 * is created!
 */
public strictfp class RobotPlayer {
	
	static Robot ai = null;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * It is like the main function for your robot. If this method returns, the robot dies!
     *
     * @param rc  The RobotController object. You use it to perform actions from this robot, and to get
     *            information on its current status. Essentially your portal to interacting with the world.
     **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // Hello world! Standard output is very useful for debugging.
        // Everything you say here will be directly viewable in your terminal when you run a match!
        System.out.println("I'm a " + rc.getType() + " and I just got created! I have health " + rc.getHealth());

        // You can also use indicators to save debug notes in replays.
        rc.setIndicatorString("Hello world!");
        
        switch (rc.getType()) {
	        case ARCHON:     ai = new Archon(rc);    break;
	        case MINER:      ai = new Miner(rc);     break;
	        case SOLDIER:    ai = new Soldier(rc);   break;
	        case LABORATORY: ai = new Laboratory(rc);break;
	        case WATCHTOWER: ai = new Watchtower(rc);break;
	        case BUILDER:	 ai = new Builder(rc);   break;
	        case SAGE:       ai = new Sage(rc);      break;
	    }

        while (true) {
            // This code runs during the entire lifespan of the robot, which is why it is in an infinite
            // loop. If we ever leave this loop and return from run(), the robot dies! At the end of the
            // loop, we call Clock.yield(), signifying that we've done everything we want to do.

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode.
            try {
                // The same run() function is called for every robot on your team, even if they are
                // different types. Here, we separate the control depending on the RobotType, so we can
                // use different strategies on different robots. If you wish, you are free to rewrite
                // this into a different control structure!
                ai.runRobot();
            } catch (GameActionException e) {
                // Oh no! It looks like we did something illegal in the Battlecode world. You should
                // handle GameActionExceptions judiciously, in case unexpected events occur in the game
                // world. Remember, uncaught exceptions cause your robot to explode!
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } catch (Exception e) {
                // Oh no! It looks like our code tried to do something bad. This isn't a
                // GameActionException, so it's more likely to be a bug in our code.
                System.out.println(rc.getType() + " Exception");
                e.printStackTrace();

            } finally {
                // Signify we've done everything we want to do, thereby ending our turn.
                // This will make our code wait until the next turn, and then perform this loop again.
                Clock.yield();
            }
            // End of loop: go back to the top. Clock.yield() has ended, so it's time for another turn!
        }

        // Your code should never reach here (unless it's intentional)! Self-destruction imminent...
    }

    /**
     * Run a single turn for an Archon.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
//    static void runArchon(RobotController rc) throws GameActionException {
//        // Pick a direction to build in.
//        Direction dir = directions[rng.nextInt(directions.length)];
//        if (rng.nextBoolean()) {
//            // Let's try to build a miner.
//            rc.setIndicatorString("Trying to build a miner");
//            if (rc.canBuildRobot(RobotType.MINER, dir)) {
//                rc.buildRobot(RobotType.MINER, dir);
//            }
//        } else {
//            // Let's try to build a soldier.
//            rc.setIndicatorString("Trying to build a soldier");
//            if (rc.canBuildRobot(RobotType.SOLDIER, dir)) {
//                rc.buildRobot(RobotType.SOLDIER, dir);
//            }
//        }
//    }

    /**
     * Run a single turn for a Miner.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
//    static void runMiner(RobotController rc) throws GameActionException {
//        // Try to mine on squares around us.
//        MapLocation me = rc.getLocation();
//        for (int dx = -1; dx <= 1; dx++) {
//            for (int dy = -1; dy <= 1; dy++) {
//                MapLocation mineLocation = new MapLocation(me.x + dx, me.y + dy);
//                // Notice that the Miner's action cooldown is very low.
//                // You can mine multiple times per turn!
//                while (rc.canMineGold(mineLocation)) {
//                    rc.mineGold(mineLocation);
//                }
//                while (rc.canMineLead(mineLocation)) {
//                    rc.mineLead(mineLocation);
//                }
//            }
//        }
//
//        // Also try to move randomly.
//        Direction dir = directions[rng.nextInt(directions.length)];
//        if (rc.canMove(dir)) {
//            rc.move(dir);
//            System.out.println("I moved!");
//        }
//    }

    /**
     * Run a single turn for a Soldier.
     * This code is wrapped inside the infinite loop in run(), so it is called once per turn.
     */
//    static void runSoldier(RobotController rc) throws GameActionException {
//        // Try to attack someone
//        int radius = rc.getType().actionRadiusSquared;
//        Team opponent = rc.getTeam().opponent();
//        RobotInfo[] enemies = rc.senseNearbyRobots(radius, opponent);
//        if (enemies.length > 0) {
//            MapLocation toAttack = enemies[0].location;
//            if (rc.canAttack(toAttack)) {
//                rc.attack(toAttack);
//            }
//        }
//
//        // Also try to move randomly.
//        Direction dir = directions[rng.nextInt(directions.length)];
//        if (rc.canMove(dir)) {
//            rc.move(dir);
//            System.out.println("I moved!");
//        }
//    }
}
