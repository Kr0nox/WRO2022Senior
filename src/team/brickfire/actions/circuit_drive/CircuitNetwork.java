package team.brickfire.actions.circuit_drive;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

/**
 * <p>Representation of the network of lines on the field</p>
 *
 * @author Team BrickFire
 * @version 2.0
 */
class CircuitNetwork {

    private final Set<GraphNode> graph;

    /**
     * <p>Creates the network</p>
     */
    public CircuitNetwork() {
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

        /* Taken out because figure was too close
        northWest.addAdjacent(north); */
        northWest.addAdjacent(west);
        northEast.addAdjacent(north);
        northEast.addAdjacent(east);
        southWest.addAdjacent(south);
        southWest.addAdjacent(west);
        southEast.addAdjacent(south);
        southEast.addAdjacent(east);

        graph = new HashSet<>();
        graph.addAll(Arrays.asList(center, north, northEast, east, southEast, south, southWest, west, northWest));
        graph.addAll(Arrays.asList(east, eastClose, eastFar, greenClose, greenFar, redClose, redFar));
        graph.addAll(Arrays.asList(west, westClose, westFar, yellowClose, yellowFar, blueClose, blueFar));
    }

    /**
     * <p>Returns the fastest path the robot can take to get from start to goal being orientated as given</p>
     *
     * @param startPosition {@link CircuitPosition Position} the robots starts
     * @param endPosition {@link CircuitPosition Position} the robot stops in
     * @param startOrientation {@link CircuitOrientation Direction} the robot faces at start
     * @param endOrientation {@link CircuitOrientation Direction} the robot should face at the end
     * @return The positions on which the robot needs to stop/take a turn
     */
    public CircuitPosition[] getPath(CircuitPosition startPosition, CircuitPosition endPosition,
                                     CircuitOrientation startOrientation, CircuitOrientation endOrientation) {
        List<GraphNode[]> allPaths = getAllShortestPaths(getNodeFromIdentifier(startPosition), getNodeFromIdentifier(endPosition));
        GraphNode[] bestPath = getBestPath(allPaths, startOrientation.getAsVector(), endOrientation.getAsVector());
        System.out.println(Arrays.toString(bestPath));
        return makeDrivablePath(bestPath);
    }

    /**
     * <p>Calculate the fastest path the robot could take out of a List of given paths</p>
     * <p>Criterion are in the following priority: <ol>
     *     <li>Total amount of degrees turned in path</li>
     *     <li>Whether the path has a turn at the end</li>
     *     <li>Amount of turns done in the path</li>
     * </ol></p>
     * @param paths All paths of the shortest length
     * @param startOrientation {@link CircuitOrientation Direction} the robot faces at start
     * @param goalOrientation {@link CircuitOrientation Direction} the robot should face at the end
     * @return The best path
     */
    private GraphNode[] getBestPath(List<GraphNode[]> paths, Vector2D startOrientation, Vector2D goalOrientation) {
        if (paths == null || paths.isEmpty()) {
            return new GraphNode[0];
        }

        double[] angles = new double[paths.size()];
        boolean[] turnAtEnd = new boolean[paths.size()];
        int[] turnAmount = new int[paths.size()];
        int bestAmount = Integer.MAX_VALUE;
        double bestAngle = Double.POSITIVE_INFINITY;

        // Calculates: Amount of turns and degrees to turn for each path and whether a path has a turn as its last move
        // as well as the smallest degree and smallest amount of turns any path has
        for (int i = 0; i < paths.size(); i++) {
            GraphNode[] curPath = paths.get(i);
            Vector2D lastOrientation = startOrientation;
            for (int j = 0; j < curPath.length - 1; j++) {
                Vector2D curStep = curPath[j + 1].getPosition().subtract(curPath[j].getPosition());
                double angle = Math.abs(Vector2D.angle(lastOrientation, curStep));
                angles[i] += angle;
                if (angle > 0) {
                    turnAmount[i]++;
                }
                lastOrientation = curStep;
            }
            double angle = Math.abs(Vector2D.angle(goalOrientation, lastOrientation));
            angles[i] += angle;
            if (angle > 0) {
                turnAmount[i]++;
                turnAtEnd[i] = true;
            }

            if (angles[i] < bestAngle) {
                bestAngle = angles[i];
            }
            if (turnAmount[i] < bestAmount) {
                bestAmount = turnAmount[i];
            }
        }

        // Determine all paths with the smallest sum of degrees turned in its path
        // This is always at least one path
        List<GraphNode[]> pathsWithSmallestDegreeSum = new ArrayList<>();
        for (int i = 0; i < angles.length; i++) {
            if (angles[i] <= bestAngle) {
                pathsWithSmallestDegreeSum.add(paths.get(i));
            }
        }
        // If only one is left our best path is found
        if (pathsWithSmallestDegreeSum.size() == 1) {
            return pathsWithSmallestDegreeSum.get(0);
        }

        // Determine all paths without a turn at the end
        // This can be 0
        List<GraphNode[]> pathsWithoutTurnAtEnd = new ArrayList<>();
        for (int i = 0; i < pathsWithSmallestDegreeSum.size(); i++) {
            if (!turnAtEnd[i]) {
                pathsWithoutTurnAtEnd.add(pathsWithSmallestDegreeSum.get(i));
            }
        }
        // If only one is left our best path is found
        if (pathsWithoutTurnAtEnd.size() == 1) {
            return pathsWithSmallestDegreeSum.get(0);
        }
        // If every path has a turn at the end, then we do the next step with the results from the degree sum
        if (pathsWithoutTurnAtEnd.size() == 0) {
            pathsWithoutTurnAtEnd = pathsWithSmallestDegreeSum;
        }

        // Determine all paths with the smallest sum of turns in its path
        // This can be 0, because the one with the smallest amount may have been filtered out in a previous step
        List<GraphNode[]> bestPaths3 = new ArrayList<>();
        for (int i = 0; i < pathsWithoutTurnAtEnd.size(); i++) {
            if (turnAmount[i] <= bestAmount) {
                bestPaths3.add(paths.get(i));
            }
        }
        // If no path meets this criteria, then we do the next step with the results from the turn at end check
        if (bestPaths3.size() == 0) {
            bestPaths3 = pathsWithSmallestDegreeSum;
        }

        // If multiple paths are left, just choose the first one since they are all (nearly) equally good
        return bestPaths3.get(0);
    }

    /**
     * <p>Implementation of the <a href="https://en.wikipedia.org/wiki/Breadth-first_search">Breadth-first search</a>
     * to determine distance of every node from the given source</p>
     *
     * @param source Source for this algorithm. {@link CircuitPosition Position} the robot starts in
     * @return Map containing every {@link GraphNode node} reachable from the source mapped to its distance from the
     *          source
     */
    private Map<GraphNode, Integer> bfs(GraphNode source) {
        if (source == null) {
            return new HashMap<>();
        }

        Map<GraphNode, Integer> layers = new HashMap<>();
        Queue<GraphNode> nextNodes = new LinkedList<>();
        nextNodes.add(source);
        layers.put(source, 0);
        while (!nextNodes.isEmpty()) {
            GraphNode u = nextNodes.poll();
            for (GraphNode v : u.getAdjacent()) {
                if (!layers.containsKey(v)) {
                    nextNodes.add(v);
                    layers.put(v, layers.get(u) + 1);
                }
            }
        }
        return layers;
    }

    /**
     * <p>Calculates all shortest paths from one {@link GraphNode node} to another in the graph</p>
     *
     * @param startingNode {@link GraphNode Node} of the {@link CircuitPosition position} the robot starts in.
     * @param endNode {@link GraphNode Node} of the {@link CircuitPosition position} the robot should stop in.
     * @return All shortest paths from startingNode to endNode
     */
    private List<GraphNode[]> getAllShortestPaths(GraphNode startingNode, GraphNode endNode) {
        if (startingNode == null || endNode == null) {
            return new ArrayList<>();
        }

        // Determine distances for every node from the starting point
        Map<GraphNode, Integer> layers = bfs(startingNode);
        if (!layers.containsKey(startingNode) || !layers.containsKey(endNode)) {
            return new ArrayList<>();
        }

        List<GraphNode[]> paths = new ArrayList<>();
        int curLayer = layers.get(endNode);
        paths.add(insertNodes(new GraphNode[curLayer + 1], curLayer, endNode));

        while (curLayer > 0) {
            List<GraphNode[]> newPaths = new ArrayList<>();
            for (GraphNode[] path : paths) {
                // Crete a new path in the list for every node adjacenent to the current one that is closer to the
                // source than the node itself
                for (GraphNode latestNode : path[curLayer].getAdjacent()) {
                    if (layers.get(latestNode) == curLayer - 1) {
                        newPaths.add(insertNodes(copyPath(path), curLayer - 1, latestNode));
                    }
                }
            }
            paths = newPaths;
            curLayer--;
        }

        return paths;

    }

    /**
     * <p>Inserts a node into the given array and returns it</p>
     *
     * @param arr Array to insert into
     * @param i Index to insert at
     * @param n Node to insert
     * @return The array with the given value
     */
    private GraphNode[] insertNodes(GraphNode[] arr, int i, GraphNode n) {
        arr[i] = n;
        return arr;
    }

    /**
     * <p>Copies an array and returns it</p>
     *
     * @param org Array to copy
     * @return Copy of this array
     */
    private GraphNode[] copyPath(GraphNode[] org) {
        GraphNode[] copy = new GraphNode[org.length];
        System.arraycopy(org, 0, copy, 0, org.length);
        return copy;
    }


    /**
     * <p>Simplifies a given path, so the robot can drive it the fastest way possible</p>
     *
     * @param path Path to simplify
     * @return An array of the {@link CircuitPosition positions} the robot needs to stop in because it either has to turn
     *          or it's the end position
     */
    private CircuitPosition[] makeDrivablePath(GraphNode[] path) {
        if (path.length <= 1) {
            return new CircuitPosition[0];
        }
        List<GraphNode> simplifiedPath = new ArrayList<>();
        Vector2D lastOrientation = path[1].getPosition().subtract(path[0].getPosition()).normalized();
        for (int j = 1; j < path.length - 1; j++) {
            // When the robot needs to turn, add node to simplified path
            Vector2D curStep = path[j + 1].getPosition().subtract(path[j].getPosition());
            double angle = Math.abs(Vector2D.angle(lastOrientation, curStep));
            if (angle > 0) {
                simplifiedPath.add(path[j]);
            }
            lastOrientation = curStep;
        }
        // Add last node, so robot knows it needs to stop
        simplifiedPath.add(path[path.length - 1]);

        // List to array
        CircuitPosition[] positions = new CircuitPosition[simplifiedPath.size()];
        for (int i = 0; i < simplifiedPath.size(); i++) {
            positions[i] = simplifiedPath.get(i).getCircuitPosition();
        }
        return positions;
    }

    /**
     * <p>Returns the node with the given identifier if it exists</p>
     *
     * @param identifier Identifier to search for
     * @return The desired node or null if it doesn't exists
     */
    private GraphNode getNodeFromIdentifier(CircuitPosition identifier) {
        if (identifier == null) {
            return null;
        }

        for (GraphNode node : graph) {
            if (node.getPosition().equals(identifier.getAsVector())) {
                return node;
            }
        }

        return null;
    }
}
