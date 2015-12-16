package algorithm.fibo;

import algorithm.Dijkstra;
import datastructure.Graph;
import datastructure.fibo.FiboEdge;
import datastructure.fibo.FiboGraph;
import datastructure.standard.StandardEdge;
import datastructure.standard.StandardGraph;
import datastructure.Vertex;
import datastructure.fibo.FibonacciHeap;
import util.GraphHelper;

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

        for (Vertex vertex : graph.getVertices().values()) {
            if (vertex.equals(start)) {
                vertex.setGAndUpdateF(0.0);
            } else {
                vertex.setGAndUpdateF(Double.MAX_VALUE);
            }
            nodes.enqueue(vertex,vertex.getG());
        }

        while (!nodes.isEmpty()) {
            final FibonacciHeap.Entry<Vertex> smallestEntry = nodes.dequeueMin();
            final Vertex smallest=smallestEntry.getValue();

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getG() == Double.MAX_VALUE) {
                continue;
            }
            //TODO: fix dijkstra
            for (FiboEdge edge : graph.<FiboEdge>getEdgesFromNode(smallest.getId())) {
                final Double alt = smallest.getG() + edge.getDistance();
                final FibonacciHeap.Entry<Vertex> connectedEntry = edge.getConnectedEntry(smallestEntry);
                final Vertex connectedVertex = connectedEntry.getValue();

                if (alt < connectedVertex.getG()) {
                    connectedVertex.setGAndUpdateF(alt);
                    connectedVertex.setPrevious(smallest);
                    nodes.decreaseKey(connectedEntry, connectedVertex.getG());
                }
            }
        }
        return Collections.emptyList();
    }
}
