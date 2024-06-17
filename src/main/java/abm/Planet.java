package abm;

/**
 * The Planet class represents a planet agent in the simulation. Planets orbit a star
 * following a circular path defined by their angular velocity and orbit radius.
 */
public class Planet extends Agent {
    private final double angularVelocity;
    private final Star star;
    private final double orbitRadius;

    /**
     * This constructor initializes a Planet object with its initial position (X and Y coordinates),
     * angular velocity, reference to the Star it orbits, and orbit radius.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param angularVelocity The angular velocity of the planet's orbit.
     * @param star The Star object that the planet orbits.
     * @param orbitRadius The radius of the planet's orbit around the star.
     */
    public Planet(double x, double y, double angularVelocity, Star star, double orbitRadius) {
        super(x, y);
        this.angularVelocity = angularVelocity;
        this.star = star;
        this.orbitRadius = orbitRadius;
    }

    /**
     * This function implements the movement behavior for planets. It calculates the planet's new
     * position based on its angular velocity, orbit radius, and the position of the central star.
     */
    @Override
    public void move() {
        double angle = angularVelocity * Simulation.time;
        x = 1 + star.getX() + orbitRadius * Math.cos(angle);
        y = 1 + star.getY() + orbitRadius * Math.sin(angle);
    }
}