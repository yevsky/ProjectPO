package abm;

import javafx.scene.shape.Circle;
import java.util.List;

public class Agent {
    protected double x;
    protected double y;
    private Circle shape;

    public Agent(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void move() {
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setShape(Circle shape) {
        this.shape = shape;
    }

    public Circle getShape() {
        return shape;
    }

    public boolean collide(Agent other) {
        return shape.getBoundsInParent().intersects(other.getShape().getBoundsInParent());
    }


    public void handleCollision(Agent other, List<Agent> agentsToRemove) {
        if (other instanceof Meteorite) {
            agentsToRemove.add(this);
        }
    }
}