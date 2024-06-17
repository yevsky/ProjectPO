package abm;

import java.util.List;
import java.util.Random;

/**
 * The Meteorite class represents a meteorite agent within the simulation.
 * Meteorites move with a random direction and speed, and upon collision with a planet,
 * they break into smaller fragments (the fragmentation is still in work).
 */
public class Meteorite extends Agent {
    double speed;
    double vectorX;
    double vectorY;

    /**
     * This constructor initializes a Meteorite object with its initial position (X and Y coordinates)
     * and speed. It also generates a random direction vector for the meteorite's movement.
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     * @param speed The speed of the meteorite.
     */
    public Meteorite(double x, double y, double speed) {
        super(x, y);
        this.speed = speed;
        Random rand = new Random();
        double angle = rand.nextDouble() * 2 * Math.PI;
        this.vectorX = Math.cos(angle);
        this.vectorY = Math.sin(angle);
    }

    /**
     * This function implements the movement behavior for meteorites. It updates the meteorite's position
     * based on its speed and movement vector.
     */
    @Override
    public void move() {
        x += vectorX * speed;
        y += vectorY * speed;
    }

    /**
     * This function defines how a meteorite handles a collision with another agent.
     * The default behavior of 'Agent' is to be removed upon collision with a meteorite.
     * Here, we override that behavior to remove the collided planet and create new meteorite fragments
     * at the planet's location.
     *
     * @param other The other agent involved in the collision.
     * @param agentsToRemove A list to add agents for removal during collision handling.
     */
    @Override
    public void handleCollision(Agent other, List<Agent> agentsToRemove) {
        if (other instanceof Planet) {
            agentsToRemove.add(other);
            createNewMeteorites((Planet) other, agentsToRemove);
        }
    }

    /**
     * This function should create new meteorite fragments after a collision between this meteorite
     * and a planet.
     * It generates a random number of fragments (between 1 and 5) with the same speed as the original
     * meteorite but with random directions. The new fragments are added to the provided agents list.
     * It is not working now and needs to be reworked.
     *
     * @param planet The planet collided with.
     * @param agents The list of agents in the simulation.
     */
    private void createNewMeteorites(Planet planet, List<Agent> agents) {
        Random rand = new Random();
        int newMeteoritesCount = rand.nextInt(5) + 1;
        for (int i = 0; i < newMeteoritesCount; i++) {
            // double angle = rand.nextDouble() * 2 * Math.PI;
            double fragmentSpeed = speed;
            Meteorite fragment = new Meteorite(planet.getX(), planet.getY(), fragmentSpeed);
            agents.add(fragment);
        }
    }
}