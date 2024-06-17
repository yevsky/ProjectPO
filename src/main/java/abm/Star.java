package abm;

/**
 * The Star class represents a star agent within the simulation. Unlike planets, stars do not move
 * within the simulation and serve as fixed reference points for planets to orbit around.
 */
public class Star extends Agent {
    private final double orbitRadius;

    /**
     * This constructor initializes a Star object with its initial position (X and Y coordinates)
     * and an orbit radius.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param orbitRadius The orbit radius associated with the star.
     */
    public Star(double x, double y, double orbitRadius) {
        super(x, y);
        this.orbitRadius = orbitRadius;
    }

    /**
     * This function returns the orbit radius associated with the star.
     *
     * @return The orbit radius of the star.
     */
    public double getOrbitRadius() {
        return orbitRadius;
    }
}