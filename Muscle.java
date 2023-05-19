public class Muscle {
    private double fiberSize;
    private double maxSize;

    public Muscle() {
        this.maxSize = Params.getFiberMaxSize();
        this.fiberSize = Params.getBaseFiberSize(maxSize);
    }

    public double getFiberSize() {
        return fiberSize;
    }

    public void setFiberSize(double fiberSize) {
        this.fiberSize = fiberSize;
    }

    public double getMaxSize() {
        return maxSize;
    }

    @Override
    public String toString() {
        return "fiber_size: " + fiberSize + " max_size: " + maxSize;
    }

}
