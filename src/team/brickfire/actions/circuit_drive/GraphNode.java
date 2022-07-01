package team.brickfire.actions.circuit_drive;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Representation of a position on the circuit as a graph node
 * All CircuitPositions are Nodes and nothing else is
 * Used in CircuitNetwork
 *
 * @author Team BrickFire
 * @version 1.0
 */
class GraphNode {

    private final List<GraphNode> adjacentNodes;

    private final CircuitPosition identifier;

    public GraphNode(CircuitPosition position) {
        this.identifier = position;
        adjacentNodes = new ArrayList<>();
    }

    public void addAdjacent(GraphNode node) {
        adjacentNodes.add(node);
        node.adjacentNodes.add(this);
    }

    public Vector2D getPosition() {
        return identifier.getAsVector();
    }

    public CircuitPosition getCircuitPosition() {
        return identifier;
    }

    public List<GraphNode> getAdjacent() {
        return adjacentNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof GraphNode) {
            return identifier == ((GraphNode)o).identifier;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return identifier.name();
    }
}
