package algorithm.standard;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.standard.Vertex;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class DijkstraImpl implements Dijkstra<Vertex> {

    @Override
    public <G extends Graph<Vertex, ? extends Edge<Vertex>>> List<Integer>
    shortestPath(G graph, Vertex start, Vertex finish) {
        PriorityQueue<Vertex> nodes = new PriorityQueue<>();

        for (Vertex vertex : graph.getVs().values()) {
            if (vertex.equals(start)) {
                vertex.setKey(0.0);
            } else {
                vertex.setKey(Double.MAX_VALUE);
            }
            nodes.add(vertex);
        }

        while (!nodes.isEmpty()) {
            final Vertex smallest = nodes.poll();
            smallest.setClosed(true);

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getKey() == Double.MAX_VALUE) {
                continue;
            }

            for (Edge<Vertex> edge : graph.getConnectedElements(smallest)) {
                final Vertex connected = edge.getConnected();
                if (!connected.isClosed()) {
                    final Double alt = smallest.getKey() + edge.getDistance();

                    if (alt < connected.getKey()) {
                        connected.setKey(alt);
                        connected.setAnchor(smallest);
                        nodes.remove(connected);
                        nodes.add(connected);
                    }
                }
            }
        }
        return Collections.emptyList();
    }
}
