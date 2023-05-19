public class Patch {
    private Muscle muscle;
    private Hormones hormones;
    private Coordinate coordinate;

    public Patch(int x, int y) {
        this.muscle = new Muscle();
        this.hormones = new Hormones();
        this.coordinate = new Coordinate(x, y);
    }

    public Muscle getMuscle() {
        return muscle;
    }

    public void setMuscle(Muscle muscle) {
        this.muscle = muscle;
    }

    public Hormones getHormones() {
        return hormones;
    }

    public void setHormones(Hormones hormones) {
        this.hormones = hormones;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    @Override
    public String toString() {
        return coordinate.toString() + " " + muscle.toString() + " " + hormones.toString();
    }
}
