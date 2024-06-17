package abm;

import java.util.ArrayList;
import java.util.List;

/**
 * The InteractionManager class handles agent movement, collision detection and out-of-bounds removal
 * within the simulation area.
 */
public class InteractionManager {
    private final double minX, minY, maxX, maxY;
    private int removedMeteoritesCount = 0;

    // Initializing the bounds of the simulation area

    /**
     * This constructor initializes the InteractionManager with the boundaries of the simulation area.
     *
     * @param minX The minimum X coordinate.
     * @param minY The minimum Y coordinate.
     * @param maxX The maximum X coordinate.
     * @param maxY The maximum Y coordinate.
     */
    public InteractionManager(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }


    /**
     * This function moves all agents in the provided list and removes any agents that go out
     * of the simulation area boundaries.
     *
     * @param agents The list of agents to move and check.
     */
    public void move(List<Agent> agents) {
        List<Agent> agentsToRemove = new ArrayList<>();
        for (Agent agent : agents) {
            agent.move();
            if (!isWithinBounds(agent)) { // Check if the agent is within bounds
                agentsToRemove.add(agent); // Marking the agent for removal
                if (agent instanceof Meteorite) {
                    removedMeteoritesCount++; // Increase the count of removed meteorites
                }
            }
        }
        agents.removeAll(agentsToRemove); // Remove the agents that are out of bounds
    }

    /**
     * This function checks if an agent's current position is within the defined simulation area boundaries.
     *
     * @param agent The agent to check.
     * @return True if the agent is within bounds, False otherwise.
     */
    private boolean isWithinBounds(Agent agent) {
        return agent.getX() >= minX && agent.getX() <= maxX && agent.getY() >= minY && agent.getY() <= maxY;
    }

    /**
     * This function returns the number of meteorites that have been removed from the simulation
     * because they went outside the boundaries.
     *
     * @return The number of removed meteorites.
     */
    public int getRemovedMeteoritesCount() {
        return removedMeteoritesCount;
    }

    // Handling collisions between agents

    /**
     * This function detects collisions between all pairs of agents in the provided list. If a collision
     * is detected, the 'handleCollision' method is called on both agents to handle the collision effects.
     * Agents marked for removal during collision handling are then removed from the list.
     *
     * @param agents The list of agents to check for collisions.
     * @return A list of agents that were removed due to collisions.
     */
    public List<Agent> collide(List<Agent> agents) {
        List<Agent> agentsToRemove = new ArrayList<>();
        for (int i = 0; i < agents.size(); i++) {
            for (int j = i + 1; j < agents.size(); j++) {
                Agent a1 = agents.get(i);
                Agent a2 = agents.get(j);

                // Check if two agents collide
                if (a1.collide(a2)) {
                    // Handle collision effects for both agents
                    a1.handleCollision(a2, agentsToRemove);
                    a2.handleCollision(a1, agentsToRemove);
                }
            }
        }
        agents.removeAll(agentsToRemove); // Remove the agents that were affected by collisions
        return agentsToRemove; // Return the list of removed agents
    }
}