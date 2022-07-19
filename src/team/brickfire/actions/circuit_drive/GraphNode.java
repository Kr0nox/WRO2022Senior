package team.brickfire.actions.circuit_drive;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

/**
 * <p>Representation of a position on the circuit as a graph node <br>
 * All {@link CircuitPosition CircuitPositions} are Nodes and nothing else is <br>
 * Used in {@link CircuitNetwork}</p>
 *
 * @author Team BrickFire
 * @version 1.0
 */
class GraphNode {

    private final List<GraphNode> adjacentNodes;

    private final CircuitPosition identifier;

    /**
     * <p>Creates a graph node</p>
     *
     * @param position Position of this node
     */
    public GraphNode(CircuitPosition position) {
        this.identifier = position;
        adjacentNodes = new ArrayList<>();
    }

    /**
     * <p>Adds a node as adjacent to this node. Also adds this node as adjacent to the given one</p>
     *
     * @param node Node to add
     */
    public void addAdjacent(GraphNode node) {
        adjacentNodes.add(node);
        node.adjacentNodes.add(this);
    }

    /**
     * <p>Gets the {@link Vector2D vector} for the {@link CircuitPosition position} of this node</p>
     *
     * @return Vector of this node
     */
    public Vector2D getPosition() {
        return identifier.getAsVector();
    }

    /**
     * <p>Gets the {@link CircuitPosition position} of this node</p>
     *
     * @return Position of this node
     */
    public CircuitPosition getCircuitPosition() {
        return identifier;
    }

    /**
     * <p>Gets all nodes adjacent to this one</p>
     *
     * @return All adjacent nodes
     */
    public List<GraphNode> getAdjacent() {
        return adjacentNodes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof GraphNode) {
            return identifier == ((GraphNode) o).identifier;
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
