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
 * Representation of the network of lines on the field
 *
 * @author Team BrickFire
 * @version 2.0
 */
class CircuitNetwork {

    private final Set<GraphNode> graph;

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

        // northWest.addAdjacent(north);
        northWest.addAdjacent(west);
        northEast.addAdjacent(north);
        northEast.addAdjacent(east);
        southWest.addAdjacent(south);
        southWest.addAdjacent(west);
        southEast.addAdjacent(south);
        southEast.addAdjacent(east);

        graph = new HashSet<>();
        insertNodes(graph, center, north, northEast, east, southEast, south, southWest, west, northWest);
        insertNodes(graph, east, eastClose, eastFar, greenClose, greenFar, redClose, redFar);
        insertNodes(graph, west, westClose, westFar, yellowClose, yellowFar, blueClose, blueFar);
    }

    private void insertNodes(Set<GraphNode> set,GraphNode... nodes) {
        set.addAll(Arrays.asList(nodes));
    }

    public CircuitPosition[] getPath(CircuitPosition startPosition, CircuitPosition endPosition,
                                     CircuitOrientation startOrientation, CircuitOrientation endOrientation) {
        List<GraphNode[]> allPaths = getAllPaths(getNodeFromIdentifier(startPosition), getNodeFromIdentifier(endPosition));
        GraphNode[] bestPath = getBestPath(allPaths, startOrientation.getAsVector(), endOrientation.getAsVector());
        System.out.println(Arrays.toString(bestPath));
        return makeDrivablePath(bestPath);

    }

    private GraphNode[] getBestPath(List<GraphNode[]> paths, Vector2D startOrientation, Vector2D goalOrientation) {
        if (paths == null || paths.isEmpty()) {
            return new GraphNode[0];
        }

        double[] angles = new double[paths.size()];
        boolean[] turnAtEnd = new boolean[paths.size()];
        int[] turnAmount = new int[paths.size()];
        int bestAmount = Integer.MAX_VALUE;
        double bestAngle = Double.POSITIVE_INFINITY;

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

        List<GraphNode[]> bestPaths1 = new ArrayList<>();
        for (int i = 0; i < angles.length; i++) {
            if (angles[i] <= bestAngle) {
                bestPaths1.add(paths.get(i));
            }
        }
        if (bestPaths1.size() == 1) {
            return bestPaths1.get(0);
        }

        List<GraphNode[]> bestPaths2 = new ArrayList<>();
        for (int i = 0; i < bestPaths1.size() ; i++) {
            if (!turnAtEnd[i]) {
                bestPaths2.add(bestPaths1.get(i));
            }
        }
        if (bestPaths2.size() == 1) {
            return bestPaths1.get(0);
        }
        if (bestPaths2.size() == 0) {
            bestPaths2 = bestPaths1;
        }

        List<GraphNode[]> bestPaths3 = new ArrayList<>();
        for (int i = 0; i < bestPaths2.size(); i++) {
            if (turnAmount[i] <= bestAmount) {
                bestPaths3.add(paths.get(i));
            }
        }
        if (bestPaths3.size() == 0) {
            bestPaths3 = bestPaths1;
        }
        return bestPaths3.get(0);
    }

    private Map<GraphNode, Integer> BFS(GraphNode startingNode) {
        Map<GraphNode, Integer> layers = new HashMap<>();
        Queue<GraphNode> q = new LinkedList<>();
        q.add(startingNode);
        layers.put(startingNode, 0);
        while (!q.isEmpty()) {
            GraphNode u = q.poll();
            for (GraphNode v : u.getAdjacent()) {
                if (!layers.containsKey(v)) {
                    q.add(v);
                    layers.put(v, layers.get(u) + 1);
                }
            }
        }
        return layers;
    }

    private List<GraphNode[]> getAllPaths(GraphNode startingNode, GraphNode endNode) {
        if (startingNode == null || endNode == null) {
            return new ArrayList<>();
        }

        Map<GraphNode, Integer> layers = BFS(startingNode);

        List<GraphNode[]> paths = new ArrayList<>();
        int curLayer = layers.get(endNode);
        paths.add(insertNode(new GraphNode[curLayer + 1], curLayer, endNode));

        while (curLayer > 0) {
            List<GraphNode[]> newPaths = new ArrayList<>();
            for (GraphNode[] path : paths) {
                for (GraphNode latestNode : path[curLayer].getAdjacent()) {
                    if (layers.get(latestNode) == curLayer - 1) {
                        newPaths.add(insertNode(copyPath(path), curLayer - 1, latestNode));
                    }
                }
            }
            paths = newPaths;
            curLayer--;
        }

        return paths;
    }

    private CircuitPosition[] makeDrivablePath(GraphNode[] path) {
        if (path.length <= 1) {
            return new CircuitPosition[0];
        }
        List<GraphNode> turningPath = new ArrayList<>();
        Vector2D lastOrientation = path[1].getPosition().subtract(path[0].getPosition()).normalized();
        for (int j = 1; j < path.length - 1; j++) {
            Vector2D curStep = path[j + 1].getPosition().subtract(path[j].getPosition());
            double angle = Math.abs(Vector2D.angle(lastOrientation, curStep));
            if (angle > 0) {
                turningPath.add(path[j]);
            }
            lastOrientation = curStep;
        }
        turningPath.add(path[path.length - 1]);

        CircuitPosition[] positions = new CircuitPosition[turningPath.size()];
        for (int i = 0; i < turningPath.size(); i++) {
            positions[i] = turningPath.get(i).getCircuitPosition();
        }
        return positions;
    }

    private GraphNode[] insertNode(GraphNode[] arr, int i, GraphNode n) {
        arr[i] = n;
        return arr;
    }

    private GraphNode[] copyPath(GraphNode[] org) {
        GraphNode[] copy = new GraphNode[org.length];
        System.arraycopy(org, 0, copy, 0, org.length);
        return copy;
    }


    public GraphNode getNodeFromIdentifier(CircuitPosition identifier) {
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
