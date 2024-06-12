package abm;

public class Star extends Agent {
    private final double orbitRadius;

    public Star(double x, double y, double orbitRadius) {
        super(x, y);
        this.orbitRadius = orbitRadius;
    }

    public double getOrbitRadius() {
        return orbitRadius;
    }
}