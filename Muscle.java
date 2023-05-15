public class Muscle {
    private double fiber_size;
    private double max_size;

    public Muscle() {
        max_size = Params.get_fiber_max_size();
        fiber_size = Params.get_base_fiber_size(max_size);
    }

    public double get_fiber_size() {
        return fiber_size;
    }

    public void set_fiber_size(double fiber_size) {
        this.fiber_size = fiber_size;
    }

    public double get_max_size() {
        return max_size;
    }

}
