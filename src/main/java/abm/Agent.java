package abm;

import javafx.scene.shape.Circle;
import java.util.List;

/**
 * The Agent class is the base class for all objects of this simulation
 * (stars, planets, and meteorites).
 * It provides basic properties and functionalities common to all agents.
 */
public class Agent {
    protected double x;
    protected double y;
    private Circle shape;

    /**
     * This constructor initializes an Agent object with its initial position (X and Y coordinates).
     *
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    public Agent(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * This function defines the movement behavior of the agent. Subclasses (Star, Planet, Meteorite)
     * should override this function to implement their specific movement logic.
     */
    public void move() {
    }

    /**
     * This function returns the X coordinate of the agent's position.
     *
     * @return The X coordinate.
     */
    public double getX() {
        return x;
    }

    /**
     * This function returns the Y coordinate of the agent's position.
     *
     * @return The Y coordinate.
     */
    public double getY() {
        return y;
    }

    /**
     * This function sets the Circle object used for visual representation of the agent.
     *
     * @param shape The Circle object representing the agent.
     */
    public void setShape(Circle shape) {
        this.shape = shape;
    }

    /**
     * This function returns the Circle object used for visual representation of the agent.
     *
     * @return The Circle object representing the agent.
     */
    public Circle getShape() {
        return shape;
    }

    /**
     * This function checks for collision between the current agent and another agent.
     * It utilizes the 'getBoundsInParent' method of the Circle objects to check for intersection.
     *
     * @param other The other agent to check for collision with.
     * @return True if there's a collision, False otherwise.
     */
    public boolean collide(Agent other) {
        return shape.getBoundsInParent().intersects(other.getShape().getBoundsInParent());
    }

    /**
     * This function defines how the agent handles a collision with another agent.
     * The default behavior is to remove the current agent if the other agent is a Meteorite.
     * Subclasses override this function to implement collision handling logic.
     *
     * @param other The other agent involved in the collision.
     * @param agentsToRemove A list to add agents for removal during collision handling.
     */
    public void handleCollision(Agent other, List<Agent> agentsToRemove) {
        if (other instanceof Meteorite) {
            agentsToRemove.add(this);
        }
    }
}