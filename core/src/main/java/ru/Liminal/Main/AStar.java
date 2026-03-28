package ru.Liminal.Main;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class AStar {
    private TiledMapTileLayer collisionLayer;
    private int width, height;
    private Node[][] nodes;

    public AStar(TiledMapTileLayer collisionLayer) {
        this.collisionLayer = collisionLayer;
        this.width = collisionLayer.getWidth();
        this.height = collisionLayer.getHeight();

        nodes = new Node[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y] = new Node(x, y);
            }
        }
    }

    public List<Vector2> findPath(Vector2 start, Vector2 goal) {
        return findPath((int)start.x, (int)start.y, (int)goal.x, (int)goal.y);
    }

    public List<Vector2> findPath(int startX, int startY, int goalX, int goalY) {

        if (startX < 0 || startX >= width || startY < 0 || startY >= height ||
            goalX < 0 || goalX >= width || goalY < 0 || goalY >= height) {
            return null;
        }


        if (isBlocked(goalX, goalY) && !(goalX == startX && goalY == startY)) {
            return null;
        }


        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                nodes[x][y].reset();
            }
        }

        Node startNode = nodes[startX][startY];
        Node goalNode = nodes[goalX][goalY];

        PriorityQueue<Node> openList = new PriorityQueue<>((a, b) -> Integer.compare(a.fCost(), b.fCost()));
        Set<Node> closedList = new HashSet<>();

        startNode.gCost = 0;
        startNode.hCost = calculateHeuristic(startNode, goalNode);
        openList.add(startNode);

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!openList.isEmpty()) {
            Node current = openList.poll();

            if (current.equals(goalNode)) {
                return reconstructPath(current);
            }

            closedList.add(current);

            for (int[] dir : directions) {
                int neighborX = current.x + dir[0];
                int neighborY = current.y + dir[1];


                if (neighborX < 0 || neighborX >= width || neighborY < 0 || neighborY >= height) {
                    continue;
                }


                if (isBlocked(neighborX, neighborY)) {
                    continue;
                }

                Node neighbor = nodes[neighborX][neighborY];

                if (closedList.contains(neighbor)) {
                    continue;
                }

                int tentativeGCost = current.gCost + 1;

                if (tentativeGCost < neighbor.gCost) {
                    neighbor.parent = current;
                    neighbor.gCost = tentativeGCost;
                    neighbor.hCost = calculateHeuristic(neighbor, goalNode);

                    if (!openList.contains(neighbor)) {
                        openList.add(neighbor);
                    }
                }
            }
        }

        return null;
    }

    private int calculateHeuristic(Node a, Node b) {

        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private boolean isBlocked(int x, int y) {
        if (collisionLayer == null) return false;
        TiledMapTileLayer.Cell cell = collisionLayer.getCell(x, y);
        return cell != null && cell.getTile() != null &&
            cell.getTile().getProperties().containsKey("solid");
    }

    private List<Vector2> reconstructPath(Node node) {
        List<Vector2> path = new ArrayList<>();
        Node current = node;

        while (current != null) {
            path.add(0, new Vector2(current.x, current.y));
            current = current.parent;
        }

        return path;
    }

    private static class Node {
        int x, y;
        int gCost = Integer.MAX_VALUE;
        int hCost = 0;
        Node parent;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int fCost() {
            return gCost + hCost;
        }

        void reset() {
            gCost = Integer.MAX_VALUE;
            hCost = 0;
            parent = null;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Node node = (Node) obj;
            return x == node.x && y == node.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
