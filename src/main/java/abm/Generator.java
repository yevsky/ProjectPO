package abm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Generator class is responsible for generating the initial set of agents
 * (stars, planets, and meteorites) for the space simulation.
 */
public class Generator {
    public int sizeX = 1000, sizeY = 800; // Tested, don't edit
    public double minStarDistance = 200;
    public double minPlanetDistance = 25;
    public double borderMargin = 100; // Margin from the border where stars cannot be placed

    /**
     * This function generates a list of agents (stars, planets, and meteorites) for the simulation.
     *
     * @param starCount The number of stars to generate.
     * @param planetCount The number of planets to generate per star.
     * @param meteoriteCount The number of meteorites to generate.
     * @param planetSpeed The speed of the planets around their stars.
     * @param meteoriteSpeed The speed of the meteorites.
     * @return A list of generated agents.
     */
    public List<Agent> generateObjects(int starCount, int planetCount, int meteoriteCount, double planetSpeed, double meteoriteSpeed) {
        List<Agent> agents = new ArrayList<>();
        Random rand = new Random();

        // Generate stars considering minimal distance between them and from borders
        for (int i = 0; i < starCount; i++) {
            double x, y;
            boolean tooClose;
            do {
                tooClose = false;
                x = borderMargin + rand.nextDouble() * (sizeX - 2 * borderMargin);
                y = borderMargin + rand.nextDouble() * (sizeY - 2 * borderMargin);
                for (Agent agent : agents) {
                    if (agent instanceof Star s) {
                        double dx = s.getX() - x;
                        double dy = s.getY() - y;
                        if (Math.sqrt(dx * dx + dy * dy) < minStarDistance) {
                            tooClose = true;
                            break;
                        }
                    }
                }
            } while (tooClose);

            Star star = new Star(x, y, rand.nextDouble() * 50);
            agents.add(star);

            // Generate planets for each star
            for (int j = 0; j < planetCount; j++) {
                double orbitRadius, angularVelocity;
                double planetX, planetY;
                boolean planetTooClose;
                do {
                    planetTooClose = false;
                    double minOrbitRadius = minPlanetDistance * (j + 2); //Extending minimal
                    // radius of a planet(depends on iteration)
                    double maxOrbitRadius = star.getOrbitRadius() * (0.5 + rand.nextDouble());
                    orbitRadius = minOrbitRadius + (maxOrbitRadius - minOrbitRadius) * rand.nextDouble();
                    angularVelocity = (planetSpeed * planetSpeed + 10) / orbitRadius; // If a planet is closer
                    // to the star then angVel will have a bigger number
                    double angle = rand.nextDouble() * 2 * Math.PI;
                    planetX = star.getX() + orbitRadius * Math.cos(angle);
                    planetY = star.getY() + orbitRadius * Math.sin(angle);

                    for (Agent agent : agents) {
                        if (agent instanceof Planet p) {
                            double dx = p.getX() - planetX;
                            double dy = p.getY() - planetY;
                            if (Math.sqrt(dx * dx + dy * dy) < minPlanetDistance) {
                                planetTooClose = true;
                                break;
                            }
                        }
                    }
                } while (planetTooClose);

                Planet planet = new Planet(planetX, planetY, angularVelocity, star, orbitRadius);
                agents.add(planet);
            }
        }

        // Generating meteorites
        for (int i = 0; i < meteoriteCount; i++) {
            double x, y;
            boolean tooCloseMet;
            do {
                tooCloseMet = false;
                x = rand.nextDouble() * sizeX;
                y = rand.nextDouble() * sizeY;

                // Checking if the position conflicts with existing stars or planets
                for (Agent agent : agents) {
                    double dx = agent.getX() - x;
                    double dy = agent.getY() - y;
                    if (agent instanceof Star || agent instanceof Planet) {
                        if (Math.sqrt(dx * dx + dy * dy) < minStarDistance) {
                            tooCloseMet = true;
                            break;
                        }
                    }
                }
            } while (tooCloseMet);

            Meteorite meteorite = new Meteorite(x, y, meteoriteSpeed);
            agents.add(meteorite);
        }
        return agents;
    }
}