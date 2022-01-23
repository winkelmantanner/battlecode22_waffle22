package tannerplayer;

import java.util.Random;

public class Utils {
    public static <T> T choice(Random rng, T[] arr) {
        return arr[rng.nextInt(arr.length)];
    }
    
    public static double rubbleFormulaWithoutFloor(int rubble) {
        return 1 + (((double)rubble) / 10); // ignoring the floor does not make much difference
    }
    
    final public static double SQRT_2 = Math.sqrt(2);
    
    /**
     * Approximates the square root using linear interpolation between square roots of powers of 2.
     * @param v number to take the square root of
     * @return an approximation to the square root of v
     */
    public static double fastSqrt(int v) {
        // I've unit tested this method and it works.
        if(v == 0) {
            return 0;
        } else {
            final int p = Integer.SIZE - Integer.numberOfLeadingZeros(v);
            double lower_sqrt; final int lower_power_of_2 = 1<<(p-1);
            double upper_sqrt; final int upper_power_of_2 = 1<<p;
            if(p % 2 == 1) {
                lower_sqrt = (1 << (p >> 1));
                upper_sqrt = lower_sqrt * SQRT_2;
            } else {
                upper_sqrt = (1 << (p >> 1));
                lower_sqrt = upper_sqrt / SQRT_2;
            }
            return lower_sqrt + (
                (double)(upper_sqrt - lower_sqrt)
                / (upper_power_of_2 - lower_power_of_2)
                * (v - lower_power_of_2)
            );
        }
    }
}
