package team.brickfire.actions.circuit_drive;

import java.util.*;

class CircuitGraph {

    private final Set<GraphNode> nodes;

    public CircuitGraph() {
        nodes = new HashSet<>();

        GraphNode center = new GraphNode(CircuitPosition.CENTER);
        GraphNode north = new GraphNode(CircuitPosition.NORTH);
        GraphNode northEast = new GraphNode(CircuitPosition.NORTH_EAST);
        GraphNode east = new GraphNode(CircuitPosition.EAST);
        GraphNode southEast = new GraphNode(CircuitPosition.SOUTH_EAST);
        GraphNode south = new GraphNode(CircuitPosition.SOUTH);
        GraphNode southWest = new GraphNode(CircuitPosition.SOUTH_WEST);
        GraphNode west = new GraphNode(CircuitPosition.WEST);
        GraphNode northWest = new GraphNode(CircuitPosition.NORTH_WEST);

        GraphNode eastClose = new GraphNode(CircuitPosition.EAST_ROOMS_CLOSE);
        GraphNode eastFar = new GraphNode(CircuitPosition.EAST_ROOMS_FAR);
        GraphNode westClose = new GraphNode(CircuitPosition.WEST_ROOMS_CLOSE);
        GraphNode westFar = new GraphNode(CircuitPosition.WEST_ROOMS_FAR);

        GraphNode redClose = new GraphNode(CircuitPosition.RED_CLOSE);
        GraphNode redFar = new GraphNode(CircuitPosition.RED_FAR);
        GraphNode greenClose = new GraphNode(CircuitPosition.GREEN_CLOSE);
        GraphNode greenFar = new GraphNode(CircuitPosition.GREEN_FAR);
        GraphNode blueClose = new GraphNode(CircuitPosition.BLUE_CLOSE);
        GraphNode blueFar = new GraphNode(CircuitPosition.BLUE_FAR);
        GraphNode yellowClose = new GraphNode(CircuitPosition.YELLOW_CLOSE);
        GraphNode yellowFar = new GraphNode(CircuitPosition.YELLOW_FAR);

        yellowFar.addAdjacent(westFar);
        yellowClose.addAdjacent(westClose);
        blueFar.addAdjacent(westFar);
        blueClose.addAdjacent(westClose);
        westClose.addAdjacent(westFar);
        westClose.addAdjacent(west);

        redFar.addAdjacent(eastFar);
        redClose.addAdjacent(eastClose);
        greenFar.addAdjacent(eastFar);
        greenClose.addAdjacent(eastClose);
        eastClose.addAdjacent(eastFar);
        eastClose.addAdjacent(east);

        center.addAdjacent(north);
        center.addAdjacent(east);
        center.addAdjacent(west);
        center.addAdjacent(south);

        northWest.addAdjacent(north);
        northWest.addAdjacent(west);
        northEast.addAdjacent(north);
        northEast.addAdjacent(east);
        southWest.addAdjacent(south);
        southWest.addAdjacent(west);
        southEast.addAdjacent(south);
        southEast.addAdjacent(east);

        insetNodes(center, north, northEast, east, southEast, south, southWest, west, northWest);
        insetNodes(eastClose, eastFar, greenClose, greenFar, redClose, redFar);
        insetNodes(westClose, westFar, yellowClose, yellowFar, blueClose, blueFar);
    }

    private void insetNodes(GraphNode... nodes) {
        this.nodes.addAll(Arrays.asList(nodes));
    }

    public List<GraphNode> shortestPath(GraphNode startingNode, GraphNode endNode) {
        if (startingNode == null || endNode == null) {
            return new ArrayList<>();
        }

        Queue<GraphNode> q = new LinkedList<>();
        Set<GraphNode> colored = new HashSet<>();
        Map<GraphNode, GraphNode> previous = new HashMap<>();
        q.add(startingNode);
        colored.add(startingNode);
        previous.put(startingNode, null);

        while (!q.isEmpty()) {
            GraphNode u = q.poll();
            for (GraphNode v : u.getAdjacent()) {
                if (!colored.contains(v)) {
                    q.add(v);
                    colored.add(v);
                    previous.put(v, u);
                }
            }
        }

        List<GraphNode> path = new ArrayList<>();
        path.add(0, endNode);

        while (!path.get(0).equals(startingNode)) {
            path.add(0, previous.get(path.get(0)));
        }
        return path;
    }

    public GraphNode getNodeFromIdentifier(CircuitPosition identifier) {
        for (GraphNode node : nodes) {
            if (node.getPosition() == identifier) {
                return node;
            }
        }

        return null;
    }

}
