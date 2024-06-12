package abm;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Agent> agents;
    private InteractionManager interactionManager;

    public Board(int width, int height) {
        this.agents = new ArrayList<>();
        this.interactionManager = new InteractionManager(0, 0, width, height);
    }

    public void addAgent(Agent agent) {
        agents.add(agent);
    }

    public void removeAgent(Agent agent) {
        agents.remove(agent);
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public int getMeteoriteCount() {
        int count = 0;
        for (Agent agent : agents) {
            if (agent instanceof Meteorite) {
                count++;
            }
        }
        return count;
    }

    public int getPlanetCount() {
        int count = 0;
        for (Agent agent : agents) {
            if (agent instanceof Planet) {
                count++;
            }
        }
        return count;
    }

    // Rewrite the logic
    public void moveAgents() {
        interactionManager.move(agents);
    }

    public List<Agent> checkCollisions() {
        return interactionManager.collide(agents);
    }

    public void updateCounts() {
        // Smth
    }
}