
/**
 * This class stores all the constant parameters for the simulation.
 * These parameters can not be changed by the user.
 * All the numerical values are given by NetLogo
 */

import java.util.Random;

class Params {

    // Simulation constants
    static final int MAX_TICK = 400; // Specifies how long the simulation will run for

    // Patch constants
    static final int GRID_SIZE = 17;
    static final int NUM_PATCH = GRID_SIZE * GRID_SIZE; // 17*17 grid
    static final int MAX_NUM_PATCH_NEIGHBOUR = 8;

    // Hormones constants
    static final double HORMONE_DIFFUSE_RATE = 0.75;
    static final int ANABOLIC_HORMONE_MAX = 200;
    static final int ANABOLIC_HORMONE_MIN = 50;
    static final int CATABOLIC_HORMONE_MAX = 250;
    static final int CATABOLIC_HORMONE_MIN = 52;
    static final int INITIAL_ANABOLIC_HORMONE = 50;
    static final int INITIAL_CATABOLIC_HORMONE = 52;

    // Muscle constants
    static final int BASE_FIBER_MAX_SIZE = 4;

    // Varience extension constants
    static final double SLEEP_PERCENTAGE_PENALTY = 0.2;

    static final String OUTPUT_FILE_NAME = "output.csv";

    /**
     * @param max_size
     * @return double
     *         The base size of a fiber is calculated as a random percentage of the
     *         maximum size.
     *         Each fiber will have a different starting size to simulate natural
     *         variance.
     */
    static double getBaseFiberSize(double max_size) {

        Random random = new Random();
        double rand_num = 0.2 + random.nextDouble() * 0.4;
        return rand_num * max_size;
    }

    /**
     * @param
     * @return double
     *         The maximum size of a fiber will vary based on the percentage of slow
     *         twitch muscle.
     *         The higher the percentage the max size.
     */
    static double getFiberMaxSize() {
        double max_size = BASE_FIBER_MAX_SIZE;
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            if (random.nextDouble() * 100 > Inputs.PERCENTAGE_SLOW_TWITCH_MUSCLE) {
                max_size += 1;
            }
        }
        return max_size;
    }

    /**
     * @param
     * @return double
     *         Log base 10 is used to calculate muscle growth.
     */
    static double logBase10(double x) {
        return Math.log(x) / Math.log(10);
    }

}