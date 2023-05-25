/**
 * This class represents a patch in the simulation.
 * Each patch will have a muscle fiber, hormones.
 */

public class Patch {
    private Muscle muscle;
    private Hormones hormones;

    public Patch(int x, int y) {
        this.muscle = new Muscle();
        this.hormones = new Hormones();

    }

    public Muscle getMuscle() {
        return muscle;
    }

    public Hormones getHormones() {
        return hormones;
    }

}
