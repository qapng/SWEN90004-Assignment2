import java.io.IOException;
import java.util.ArrayList;
import java.lang.Math;

public class Driver {
    public int tick;
    public double totalMuscleMass;
    public double meanAnabolicHormone;
    public double meanCatabolicHormone;
    public Recorder recorder;
    public Patch[][] patches;

    public Driver() {
        tick = 0;
        totalMuscleMass = 0;
        recorder = new Recorder();
        patches = new Patch[Params.GRID_SIZE][Params.GRID_SIZE];
        setup();
    }

    public void drive() throws IOException {
        recorder.clearFileContent(Params.OUTPUT_FILE_NAME);
        String[] headers = { "Ticks", "Anabolic", "Catabolic", "Muscle Mass" };
        recorder.outputDataToCsv(Params.OUTPUT_FILE_NAME, headers);

        while (tick < Params.MAX_TICK) {
            updateTotalHormone();
            updateTotalMuscleMass();
            outputData();

            perform_daily_activity();
            if (Inputs.IS_LIFT && tick % Inputs.DAYS_BETWEEN_WORKOUTS == 0) {
                liftWeights();
            }
            sleep();
            developMuscle();
            tick++;
        }
    }

    public void setup() {
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                patches[x][y] = new Patch(x, y);
                regulateMuscleFiber(patches[x][y].getMuscle());
            }
        }
    }

    public void outputData() throws IOException {
        String[] outputString = { String.valueOf(tick), String.valueOf(meanAnabolicHormone),
                String.valueOf(meanCatabolicHormone), String.valueOf(totalMuscleMass) };
        recorder.outputDataToCsv(Params.OUTPUT_FILE_NAME, outputString);

    }

    public void updateTotalMuscleMass() {
        double newtotal = 0;
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                newtotal += patches[x][y].getMuscle().getFiberSize();
            }
        }
        totalMuscleMass = newtotal;
    }

    public void updateTotalHormone() {
        double anabolicTotal = 0;
        double catabolicTotal = 0;
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                anabolicTotal += patches[x][y].getHormones().getAnabolicHormones();
                catabolicTotal += patches[x][y].getHormones().getCatabolicHormones();
            }
        }
        meanAnabolicHormone = anabolicTotal / Params.NUM_PATCH;
        meanCatabolicHormone = catabolicTotal / Params.NUM_PATCH;
    }

    public void perform_daily_activity() {
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                Patch patch = patches[x][y];
                double curCatabolic = patch.getHormones().getCatabolicHormones();
                double curAnabolic = patch.getHormones().getAnabolicHormones();
                double curFiberSize = patch.getMuscle().getFiberSize();
                double newAnabolic = curAnabolic + 2.5 * Params.logBase10(curFiberSize);
                double newCatabolic = curCatabolic + 2.0 * Params.logBase10(curFiberSize);

                patch.getHormones().setAnabolicHormones(newAnabolic);
                patch.getHormones().setCatabolicHormones(newCatabolic);

            }
        }
    }

    public void liftWeights() {
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                Patch patch = patches[x][y];
                double curCatabolic = patch.getHormones().getCatabolicHormones();
                double curAnabolic = patch.getHormones().getAnabolicHormones();
                double curFiberSize = patch.getMuscle().getFiberSize();

                if (Math.random() < Inputs.INTENSITY / 100 * Inputs.INTENSITY / 100) {
                    double newAnabolic = curAnabolic
                            + Params.logBase10(curFiberSize) * 55;
                    double newCatabolic = curCatabolic
                            + Params.logBase10(curFiberSize) * 44;

                    patch.getHormones().setAnabolicHormones(newAnabolic);
                    patch.getHormones().setCatabolicHormones(newCatabolic);
                }

            }
        }
    }

    public void developMuscle() {
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                Patch patch = patches[x][y];
                grow(patch.getMuscle(), patch.getHormones());
                regulateMuscleFiber(patch.getMuscle());
            }
        }
    }

    public void regulateMuscleFiber(Muscle muscle) {

        if (muscle.getFiberSize() < 1) {
            muscle.setFiberSize(1);
        }
        if (muscle.getFiberSize() > muscle.getMaxSize()) {
            muscle.setFiberSize(muscle.getMaxSize());
        }

    }

    public void grow(Muscle muscle, Hormones hormones) {
        // to grow ;; turtle procedure
        // ;; catabolic hormones must prepare the fibers for growth before the
        // ;; anabolic hormones may add mass to the fibers
        // set fiber-size (fiber-size - 0.20 * (log catabolic-hormone 10))
        // set fiber-size (fiber-size + 0.20 * min (list (log anabolic-hormone 10)
        // (1.05 * log catabolic-hormone 10)))
        // end
        double curFiberSize = muscle.getFiberSize();
        double curAnabolic = hormones.getAnabolicHormones();
        double curCatabolic = hormones.getCatabolicHormones();
        double newFiberSize = curFiberSize - 0.20 * Params.logBase10(curCatabolic);
        newFiberSize = newFiberSize
                + 0.20 * Math.min(Params.logBase10(curAnabolic), 1.05 * Params.logBase10(curCatabolic));
        muscle.setFiberSize(newFiberSize);
    }

    public void sleep() {
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                Patch patch = patches[x][y];
                double curCatabolic = patch.getHormones().getCatabolicHormones();
                double curAnabolic = patch.getHormones().getAnabolicHormones();
                double newAnabolic = curAnabolic
                        - 0.48 * Params.logBase10(curAnabolic) * Inputs.HOURS_OF_SLEEP;
                double newCatabolic = curCatabolic
                        - 0.5 * Params.logBase10(curCatabolic) * Inputs.HOURS_OF_SLEEP;

                patch.getHormones().setAnabolicHormones(newAnabolic);
                patch.getHormones().setCatabolicHormones(newCatabolic);
            }
        }
    }
}
