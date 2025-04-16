package org.example.maze;

import java.util.*;

public class PathFinder {
    private final Maze maze;

    public PathFinder(Maze maze) {
        this.maze = maze;
    }

    public List<Maze.Position> findPath() {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Map<Maze.Position, Node> allNodes = new HashMap<>();

        Maze.Position start = maze.getStart();
        Maze.Position end = maze.getEnd();

        Node startNode = new Node(start, null, 0, heuristic(start, end));
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.position.equals(end)) {
                return reconstructPath(current);
            }

            for (Maze.Position neighbor : getNeighbors(current.position)) {
                if (maze.isWall(neighbor.getX(), neighbor.getY())) {
                    continue;
                }

                double tentativeGScore = current.gScore + 1;

                Node neighborNode = allNodes.getOrDefault(neighbor,
                        new Node(neighbor, null, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY));
                allNodes.putIfAbsent(neighbor, neighborNode);

                if (tentativeGScore < neighborNode.gScore) {
                    neighborNode.parent = current;
                    neighborNode.gScore = tentativeGScore;
                    neighborNode.fScore = tentativeGScore + heuristic(neighbor, end);

                    if (!openSet.contains(neighborNode)) {
                        openSet.add(neighborNode);
                    }
                }
            }
        }

        return Collections.emptyList();
    }

    private List<Maze.Position> reconstructPath(Node endNode) {
        List<Maze.Position> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(0, current.position);
            current = current.parent;
        }

        return path;
    }

    private List<Maze.Position> getNeighbors(Maze.Position position) {
        List<Maze.Position> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        for (int[] dir : directions) {
            int newX = position.getX() + dir[0];
            int newY = position.getY() + dir[1];

            if (maze.isValidPosition(newX, newY)) {
                neighbors.add(new Maze.Position(newX, newY));
            }
        }

        return neighbors;
    }

    private double heuristic(Maze.Position a, Maze.Position b) {
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
    }

    private static class Node implements Comparable<Node> {
        private final Maze.Position position;
        private Node parent;
        private double gScore;
        private double fScore;

        public Node(Maze.Position position, Node parent, double gScore, double fScore) {
            this.position = position;
            this.parent = parent;
            this.gScore = gScore;
            this.fScore = fScore;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.fScore, other.fScore);
        }
    }
} 