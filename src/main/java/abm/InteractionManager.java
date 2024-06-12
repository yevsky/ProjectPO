package abm;

import java.util.ArrayList;
import java.util.List;

public class InteractionManager {
    private final double minX, minY, maxX, maxY;
    private int removedMeteoritesCount = 0;

    // Initializing the bounds of the simulation area
    public InteractionManager(double minX, double minY, double maxX, double maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    // Moving all agents and removing those that go out of bounds
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

    // Checking if an agent is within the bounds of the simulation area
    private boolean isWithinBounds(Agent agent) {
        return agent.getX() >= minX && agent.getX() <= maxX && agent.getY() >= minY && agent.getY() <= maxY;
    }

    // Getter for the count of removed meteorites
    public int getRemovedMeteoritesCount() {
        return removedMeteoritesCount;
    }

    // Handling collisions between agents
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