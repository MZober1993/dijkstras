package algorithm.standard;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.Vertex;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class DijkstraImpl implements Dijkstra<Vertex> {

    @Override
    public <G extends Graph<Vertex>> List<Integer> shortestPath(G graph, Vertex start, Vertex finish) {
        PriorityQueue<Vertex> nodes = new PriorityQueue<>();

        for (Vertex vertex : graph.getElements().values()) {
            if (vertex.equals(start)) {
                vertex.setG(0.0);
            } else {
                vertex.setG(Double.MAX_VALUE);
            }
            nodes.add(vertex);
        }

        while (!nodes.isEmpty()) {
            final Vertex smallest = nodes.poll();
            smallest.setClosed(true);

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getG() == Double.MAX_VALUE) {
                continue;
            }

            for (Edge<Vertex> edge : graph.<Edge<Vertex>>getEdgesFromNode(smallest.getId())) {
                final Double alt = smallest.getG() + edge.getDistance();
                final Vertex connectedVertex = edge.getConnected(smallest);

                if (!connectedVertex.isClosed() && alt < connectedVertex.getG()) {
                    connectedVertex.setG(alt);
                    connectedVertex.setPrevious(smallest);
                    nodes.remove(connectedVertex);
                    nodes.add(connectedVertex);
                }
            }
        }
        return Collections.emptyList();
    }
}
