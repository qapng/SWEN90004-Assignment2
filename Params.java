import java.util.Random;

class Params {
    static final double HORMONE_DIFFUSE_RATE = 0.75;
    static final int ANABOLIC_HORMONE_MAX = 200;
    static final int ANABOLIC_HORMONE_MIN = 50;
    static final int CATABOLIC_HORMONE_MAX = 250;
    static final int CATABOLIC_HORMONE_MIN = 52;
    static final int INITIAL_ANABOLIC_HORMONE = 50;
    static final int INITIAL_CATABOLIC_HORMONE = 52;
    static final String OUTPUT_FILE_NAME = "output.csv";
    static final int MAX_TICK = 400;
    static final int GRID_SIZE = 17;
    static final int NUM_PATCH = GRID_SIZE * GRID_SIZE; // 17*17 grid
    static final int BASE_FIBER_MAX_SIZE = 4;

    static double getBaseFiberSize(double max_size) {

        Random random = new Random();
        double rand_num = 0.2 + random.nextDouble() * 0.4;
        return rand_num * max_size;
    }

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

    static double logBase10(double x) {
        return Math.log(x) / Math.log(10);
    }

}