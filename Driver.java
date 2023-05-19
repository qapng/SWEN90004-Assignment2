import java.io.IOException;
import java.lang.Math;
import java.util.ArrayList;

public class Driver {
    public int tick;
    public double totalMuscleMass;
    public double meanAnabolicHormone;
    public double meanCatabolicHormone;
    public Recorder recorder;
    public Patch[][] patches;
    public int restDaysLeft;

    public Driver() throws IOException {
        tick = 0;
        totalMuscleMass = 0;
        recorder = new Recorder();
        patches = new Patch[Params.GRID_SIZE][Params.GRID_SIZE];
        restDaysLeft = 0;
        setup();
    }

    public void drive() throws IOException {
        recorder.clearFileContent(Params.OUTPUT_FILE_NAME);
        String[] headers = { "Ticks", "Anabolic", "Catabolic", "Muscle Mass" };
        recorder.outputDataToCsv(Params.OUTPUT_FILE_NAME, headers);
        updateTotalHormone();
        updateTotalMuscleMass();
        outputData();

        while (tick < Params.MAX_TICK) {

            perform_daily_activity();
            if (Inputs.IS_LIFT && restDaysLeft == 0) {
                liftWeights();
            }
            if (Inputs.VARIANCE > 0) {
                sleep(getVarianceSleepHours());
            } else {
                sleep(Inputs.HOURS_OF_SLEEP);
            }
            regulateHormones();
            developMuscle();
            upddateRestDaysLeft();
            tick++;
            outputData();
        }
    }

    public void setup() throws IOException {
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                patches[x][y] = new Patch(x, y);
                regulateMuscleFiber(patches[x][y].getMuscle());
            }
        }

    }

    public void outputData() throws IOException {
        updateTotalHormone();
        updateTotalMuscleMass();
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
        double curFiberSize = muscle.getFiberSize();
        double curAnabolic = hormones.getAnabolicHormones();
        double curCatabolic = hormones.getCatabolicHormones();
        double newFiberSize = curFiberSize - 0.20 * Params.logBase10(curCatabolic);
        newFiberSize = newFiberSize
                + 0.20 * Math.min(Params.logBase10(curAnabolic), 1.05 * Params.logBase10(curCatabolic));
        muscle.setFiberSize(newFiberSize);
    }

    public void sleep(double hoursOfSleep) {
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                Patch patch = patches[x][y];
                double curCatabolic = patch.getHormones().getCatabolicHormones();
                double curAnabolic = patch.getHormones().getAnabolicHormones();
                double newAnabolic = curAnabolic
                        - 0.48 * Params.logBase10(curAnabolic) * hoursOfSleep;
                double newCatabolic = curCatabolic
                        - 0.5 * Params.logBase10(curCatabolic) * hoursOfSleep;

                patch.getHormones().setAnabolicHormones(newAnabolic);
                patch.getHormones().setCatabolicHormones(newCatabolic);
            }
        }
    }

    public double getVarianceSleepHours() {

        double sleepTime = Inputs.HOURS_OF_SLEEP;
        if (Math.random() > Inputs.VARIANCE) {
            // reduce sleep time
            sleepTime = sleepTime * (1 - Params.SLEEP_PERCENTAGE_PENALTY);
        }
        return sleepTime;

    }

    public void upddateRestDaysLeft() {
        if (restDaysLeft == 0) {
            restDaysLeft = Inputs.DAYS_BETWEEN_WORKOUTS;
        } else {
            if (Inputs.VARIANCE > 0) {
                if (Math.random() > Inputs.VARIANCE) {
                    restDaysLeft--;
                }
            } else {
                restDaysLeft--;
            }
        }
    }

    public void regulateHormones() {
        diffuseHormones(patches, Params.HORMONE_DIFFUSE_RATE);
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                Hormones cuHormones = patches[x][y].getHormones();
                double curAnabolic = cuHormones.getAnabolicHormones();
                double curCatabolic = cuHormones.getCatabolicHormones();
                cuHormones.setAnabolicHormones(Math.max(curAnabolic, Params.ANABOLIC_HORMONE_MIN));
                cuHormones.setAnabolicHormones(Math.min(curAnabolic, Params.ANABOLIC_HORMONE_MAX));
                cuHormones.setCatabolicHormones(Math.max(curCatabolic, Params.CATABOLIC_HORMONE_MIN));
                cuHormones.setCatabolicHormones(Math.min(curCatabolic, Params.CATABOLIC_HORMONE_MAX));
            }
        }
    }

    public void diffuseHormones(Patch[][] patches, double diffuseRate) {
        for (int x = 0; x < patches.length; x++) {
            for (int y = 0; y < patches.length; y++) {
                Hormones curPatchHormones = patches[x][y].getHormones();
                double totalAnabolicShares = curPatchHormones.getAnabolicHormones() * diffuseRate;
                double totalCatabolicShares = curPatchHormones.getCatabolicHormones() * diffuseRate;
                ArrayList<Patch> neighbourPatches = getAdjacentPatch(patches, x, y);
                int numNeighbour = neighbourPatches.size();
                for (Patch patch : neighbourPatches) {
                    patch.getHormones().addAnabolicHormones(totalAnabolicShares / Params.MAX_NUM_PATCH_NEIGHBOUR);
                    patch.getHormones().addCatabolicHormones(totalCatabolicShares / Params.MAX_NUM_PATCH_NEIGHBOUR);
                }
                curPatchHormones
                        .removeAnabolicHormones(totalAnabolicShares / Params.MAX_NUM_PATCH_NEIGHBOUR * numNeighbour);
                curPatchHormones
                        .removeCatabolicHormones(totalCatabolicShares / Params.MAX_NUM_PATCH_NEIGHBOUR * numNeighbour);

            }
        }

    }

    public ArrayList<Patch> getAdjacentPatch(Patch[][] patches, int x, int y) {
        // Size of given 2d array
        int n = patches.length;

        // Initialising a array list where adjacent element
        // will be stored
        ArrayList<Patch> adjacentPatches = new ArrayList<Patch>();
        int i = 0;
        if (!isValidPos(x, y, n)) {
            return adjacentPatches;

        }

        // Checking for all the possible adjacent positions
        if (isValidPos(x - 1, y - 1, n)) {
            adjacentPatches.add(patches[x - 1][y - 1]);
        }
        if (isValidPos(x - 1, y, n)) {
            adjacentPatches.add(patches[x - 1][y]);
        }
        if (isValidPos(x - 1, y + 1, n)) {
            adjacentPatches.add(patches[x - 1][y + 1]);
        }
        if (isValidPos(x, y - 1, n)) {
            adjacentPatches.add(patches[x][y - 1]);
        }
        if (isValidPos(x, y + 1, n)) {
            adjacentPatches.add(patches[x][y + 1]);
        }
        if (isValidPos(x + 1, y - 1, n)) {
            adjacentPatches.add(patches[x + 1][y - 1]);
        }
        if (isValidPos(x + 1, y, n)) {
            adjacentPatches.add(patches[x + 1][y]);
        }
        if (isValidPos(x + 1, y + 1, n)) {
            adjacentPatches.add(patches[x + 1][y + 1]);
        }
        // Returning the arraylist
        return adjacentPatches;
    }

    public boolean isValidPos(int x, int y, int n) {
        if (x < 0 || y < 0 || x > n - 1 || y > n - 1) {
            return false;
        }
        return true;
    }

}