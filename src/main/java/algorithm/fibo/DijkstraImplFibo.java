package algorithm.fibo;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Graph;
import datastructure.Vertex;
import datastructure.fibo.FibonacciHeap;
import datastructure.standard.GraphImpl;

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
            Vertex smallest = nodes.dequeueMin().getValue();

            if (smallest.equals(finish)) {
                return GraphImpl.reconstructPath(smallest);
            }

            if (smallest.getG() == Double.MAX_VALUE) {
                continue;
            }

            for (Edge edge : graph.getEdgesFromNode(smallest.getId())) {
                Double alt = smallest.getG() + edge.getDistance();
                Vertex connectedVertex = edge.getConnectedVertex(smallest);

                if (alt < connectedVertex.getG()) {
                    connectedVertex.setGAndUpdateF(alt);
                    connectedVertex.setPrevious(smallest);
                    //TODO: nodes.decreaseKey(entry,connectedVertex.getG());
                }
            }
        }
        return Collections.emptyList();
    }
}
