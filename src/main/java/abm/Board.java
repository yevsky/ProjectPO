package abm;

import java.util.ArrayList;
import java.util.List;

/**
 * The Board class represents the simulation area and manages the agents
 * (stars, planets, and meteorites) within it.
 */
public class Board {
    private List<Agent> agents;
    private InteractionManager interactionManager;

    /**
     * This constructor initializes a Board object with the width and height of the simulation area.
     * It creates an empty list to store agents and sets up an InteractionManager with the provided dimensions.
     *
     * @param width The width of the simulation area.
     * @param height The height of the simulation area.
     */
    public Board(int width, int height) {
        this.agents = new ArrayList<>();
        this.interactionManager = new InteractionManager(0, 0, width, height);
    }

    /**
     * This function adds an agent to the board's list of agents.
     *
     * @param agent The agent to add.
     */
    public void addAgent(Agent agent) {
        agents.add(agent);
    }

    /**
     * This function removes an agent from the board's list of agents.
     *
     * @param agent The agent to remove.
     */
    public void removeAgent(Agent agent) {
        agents.remove(agent);
    }

    /**
     * This function returns a list containing all the agents currently on the board.
     *
     * @return A list of agents.
     */
    public List<Agent> getAgents() {
        return agents;
    }

    /**
     * This function counts and returns the number of meteorites currently on the board.
     *
     * @return The number of meteorites.
     */
    public int getMeteoriteCount() {
        int count = 0;
        for (Agent agent : agents) {
            if (agent instanceof Meteorite) {
                count++;
            }
        }
        return count;
    }

    /**
     * This function counts and returns the number of planets currently on the board.
     *
     * @return The number of planets.
     */
    public int getPlanetCount() {
        int count = 0;
        for (Agent agent : agents) {
            if (agent instanceof Planet) {
                count++;
            }
        }
        return count;
    }

    /*
    // Edit logic
    public void moveAgents() {
        interactionManager.move(agents);
    }

    public List<Agent> checkCollisions() {
        return interactionManager.collide(agents);
    }

    public void updateCounts() {
        // Smth
    }*/
}