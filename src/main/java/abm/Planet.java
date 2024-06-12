package abm;


public class Planet extends Agent {
    private final double angularVelocity;
    private final Star star;
    private final double orbitRadius;

    public Planet(double x, double y, double angularVelocity, Star star, double orbitRadius) {
        super(x, y);
        this.angularVelocity = angularVelocity;
        this.star = star;
        this.orbitRadius = orbitRadius;
    }

    @Override
    public void move() {
        double angle = angularVelocity * Simulation.time;
        x = 1 + star.getX() + orbitRadius * Math.cos(angle);
        y = 1 + star.getY() + orbitRadius * Math.sin(angle);
    }
}