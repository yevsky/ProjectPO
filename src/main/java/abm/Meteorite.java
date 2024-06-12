package abm;

import java.util.List;
import java.util.Random;

public class Meteorite extends Agent {
    double speed;
    double vectorX;
    double vectorY;

    public Meteorite(double x, double y, double speed) {
        super(x, y);
        this.speed = speed;
        Random rand = new Random();
        double angle = rand.nextDouble() * 2 * Math.PI;
        this.vectorX = Math.cos(angle);
        this.vectorY = Math.sin(angle);
    }

    @Override
    public void move() {
        x += vectorX * speed;
        y += vectorY * speed;
    }

    @Override
    public void handleCollision(Agent other, List<Agent> agentsToRemove) {
        if (other instanceof Planet) {
            agentsToRemove.add(other);
            createNewMeteorites((Planet) other, agentsToRemove);
        }
    }

    // Doesn't work
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