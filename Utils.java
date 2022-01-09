package tannerplayer;

import java.util.Random;

public class Utils {
    public static <T> T choice(Random rng, T[] arr) {
        return arr[rng.nextInt(arr.length)];
    }
    
    public static double rubbleFormulaWithoutFloor(int rubble) {
        return 1 + (((double)rubble) / 10); // ignoring the floor does not make much difference
    }
}
