package algorithm.fibo;

import algorithm.Dijkstra;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.Vertex;
import datastructure.fibo.Entry;
import datastructure.fibo.FiboEdge;
import datastructure.fibo.FibonacciHeap;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class DijkstraImplFibo implements Dijkstra {

    @Override
    public List<Integer> shortestPath(Graph graph, Vertex start, Vertex finish) {
        FibonacciHeap<Vertex> nodes = new FibonacciHeap<>();
        for (Entry<Vertex> entry : graph.getEntryVertices().values()) {
            if (entry.getValue().equals(start)) {
                entry.getValue().setG(0.0);
                entry.setKey(0.0);
            } else {
                entry.getValue().setG(Double.MAX_VALUE);
                entry.setKey(Double.MAX_VALUE);
            }
            nodes.insert(entry);
        }

        while (!nodes.isEmpty()) {
            final Entry<Vertex> smallestEntry = nodes.extractMin();
            final Vertex smallest = smallestEntry.getValue();
            smallest.setClosed(true);

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getG() == Double.MAX_VALUE) {
                continue;
            }

            for (FiboEdge edge : graph.<FiboEdge>getEdgesFromNode(smallest.getId())) {
                final Double alt = smallest.getG() + edge.getDistance();
                final Entry<Vertex> connectedEntry = edge.getConnectedEntry(smallestEntry);
                final Vertex connectedVertex = connectedEntry.getValue();

                if (!connectedVertex.isClosed() && alt < connectedVertex.getG()) {
                    connectedVertex.setG(alt);
                    connectedVertex.setPrevious(smallest);
                    nodes.insertIfNotExist(connectedEntry);
                    nodes.decreaseKey(connectedEntry, connectedVertex.getG());
                }
            }
        }
        return Collections.emptyList();
    }
}
