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
    static final int MAX_TICK = 100;
    static final int NUM_MUSCLE = 289; // 17*17 grid
    static final int BASE_FIBER_MAX_SIZE = 4;

    static double get_base_fiber_size(double max_size) {

        Random random = new Random();
        double rand_num = 0.2 + random.nextDouble() * 0.4;
        return rand_num * max_size;
    }

    static double get_fiber_max_size() {
        double max_size = BASE_FIBER_MAX_SIZE;
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            if (random.nextDouble() > Inputs.PERCENTAGE_SLOW_TWITCH_MUSCLE) {
                max_size += 1;
            }
        }
        return max_size;
    }

}