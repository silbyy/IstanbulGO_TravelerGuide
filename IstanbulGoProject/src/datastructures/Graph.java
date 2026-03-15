package datastructures;

import java.util.*;

public class Graph {
    private final Map<String, List<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public static class Edge {
        String target;
        int distance; // Dakika veya km cinsinden
        String transportType;

        Edge(String target, int distance, String transportType) {
            this.target = target;
            this.distance = distance;
            this.transportType = transportType;
        }
    }

    // NEW ROUTE
    public void addRoute(String source, String destination, int time, String type) {
        adjacencyList.computeIfAbsent(source, k -> new ArrayList<>()).add(new Edge(destination, time, type));
        adjacencyList.computeIfAbsent(destination, k -> new ArrayList<>()).add(new Edge(source, time, type));
    }

    // DIJKSTRA ALGORITHM
    public String findShortestPath(String start, String end) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previous = new HashMap<>();
        
        pq.add(new Node(start, 0));
        distances.put(start, 0);

        while (!pq.isEmpty()) {
            Node current = pq.poll();

            if (current.name.equals(end)) break;

            for (Edge edge : adjacencyList.getOrDefault(current.name, new ArrayList<>())) {
                int newDist = distances.get(current.name) + edge.distance;
                if (newDist < distances.getOrDefault(edge.target, Integer.MAX_VALUE)) {
                    distances.put(edge.target, newDist);
                    previous.put(edge.target, current.name);
                    pq.add(new Node(edge.target, newDist));
                }
            }
        }

        return formatPath(previous, start, end, distances.get(end));
    }

    private String formatPath(Map<String, String> previous, String start, String end, Integer totalTime) {
        if (totalTime == null) return "No path found.";
        
        List<String> path = new LinkedList<>();
        String curr = end;
        while (curr != null) {
            path.add(0, curr);
            curr = previous.get(curr);
        }
        return "Best Route: " + String.join(" ➔ ", path) + "\nTotal Time: " + totalTime + " mins";
    }

    private static class Node {
        String name;
        int distance;
        Node(String name, int distance) { this.name = name; this.distance = distance; }
    }
}
