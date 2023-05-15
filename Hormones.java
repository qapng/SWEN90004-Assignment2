public class Hormones {
    private int anabolic_hormones;
    private int catabolic_hormones;

    public Hormones() {
        anabolic_hormones = Params.INITIAL_ANABOLIC_HORMONE;
        catabolic_hormones = Params.INITIAL_CATABOLIC_HORMONE;
    }

    public int get_anabolic_hormones() {
        return anabolic_hormones;
    }

    public void set_anabolic_hormones(int anabolic_hormones) {
        this.anabolic_hormones = anabolic_hormones;
    }

    public int get_catabolic_hormones() {
        return catabolic_hormones;
    }

    public void set_catabolic_hormones(int catabolic_hormones) {
        this.catabolic_hormones = catabolic_hormones;
    }

}
