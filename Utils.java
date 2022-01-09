package tannerplayer;

import java.util.Random;

public class Utils {
    static <T> T choice(Random rng, T[] arr) {
        return arr[rng.nextInt(arr.length)];
    }
}
