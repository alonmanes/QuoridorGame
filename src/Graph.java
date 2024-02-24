import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
    private Map<Point, List<Point>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addVertex(Point p) {
        adjacencyList.putIfAbsent(p, new ArrayList<>());
    }

    public void addEdge(Point source, Point destination) {
        // Ensure both source and destination vertices are added before creating an edge
        addVertex(source);
        addVertex(destination);

        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source); // Since it's an undirected graph
    }

    public List<Point> getAdjacentPoints(Point p) {
        return adjacencyList.getOrDefault(p, new ArrayList<>());
    }

    public boolean hasEdge(Point source, Point destination) {
        List<Point> edges = adjacencyList.get(source);
        if (edges != null) { // Check if the list of edges for 'source' is not null
            return edges.contains(destination);
        }
        return false; // Return false if there are no edges for 'source' (i.e., 'source' vertex does not exist in the graph)
    }

    public void removeEdge(Point source, Point destination) {
        List<Point> sourceAdjacency = adjacencyList.get(source);
        List<Point> destinationAdjacency = adjacencyList.get(destination);

        if (sourceAdjacency != null) {
            sourceAdjacency.remove(destination);
        }

        if (destinationAdjacency != null) {
            destinationAdjacency.remove(source);
        }
    }
}