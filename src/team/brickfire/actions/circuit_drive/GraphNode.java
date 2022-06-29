package team.brickfire.actions.circuit_drive;

import java.util.*;

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

    public CircuitPosition getPosition() {
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
}
