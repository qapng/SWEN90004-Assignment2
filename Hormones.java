/**
 * This class is used to keep track of catabolic and anabolic hormones.
 * Each patch will have their own hormones count.
 */

public class Hormones {
    private double anabolicHormones;
    private double catabolicHormones;

    public Hormones() {
        // Initialize the hormones with values given from NetLogo
        anabolicHormones = Params.INITIAL_ANABOLIC_HORMONE;
        catabolicHormones = Params.INITIAL_CATABOLIC_HORMONE;
    }

    // Below are getters and setters and functions to add and subtract hormones
    public void removeAnabolicHormones(double value) {
        anabolicHormones -= value;
    }

    public void removeCatabolicHormones(double value) {
        catabolicHormones -= value;
    }

    public void addAnabolicHormones(double value) {
        anabolicHormones += value;
    }

    public void addCatabolicHormones(double value) {
        catabolicHormones += value;
    }

    public double getAnabolicHormones() {
        return anabolicHormones;
    }

    public void setAnabolicHormones(double anabolic_hormones) {
        this.anabolicHormones = anabolic_hormones;
    }

    public double getCatabolicHormones() {
        return catabolicHormones;
    }

    public void setCatabolicHormones(double catabolic_hormones) {
        this.catabolicHormones = catabolic_hormones;
    }

    public String toString() {
        return "anabolic: " + anabolicHormones + " catabolic: " + catabolicHormones;
    }

}
