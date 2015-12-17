package algorithm.fibo;

import algorithm.Dijkstra;
import datastructure.Graph;
import datastructure.Vertex;
import datastructure.fibo.FiboEdge;
import datastructure.fibo.FibonacciHeap;
import util.GraphHelper;

import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

import static util.LoggingHelper.buildLoggerWithStandardOutputConfig;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class DijkstraImplFibo implements Dijkstra {

    private static final Logger LOGGER = buildLoggerWithStandardOutputConfig(DijkstraImplFibo.class);

    @Override
    public List<Integer> shortestPath(Graph graph, Vertex start, Vertex finish) {
        FibonacciHeap<Vertex> nodes = new FibonacciHeap<>();
        LOGGER.info("graph = [" + graph + "]\n, start = [" + start + "]\n, finish = [" + finish + "]\n");
        for (Vertex vertex : graph.getVertices().values()) {
            if (vertex.equals(start)) {
                vertex.setGAndUpdateF(0.0);
            } else {
                vertex.setGAndUpdateF(Double.MAX_VALUE);
            }
            nodes.enqueue(vertex, vertex.getG());
        }

        LOGGER.info(nodes.toString());

        while (!nodes.isEmpty()) {
            final FibonacciHeap.Entry<Vertex> smallestEntry = nodes.dequeueMin();
            final Vertex smallest = smallestEntry.getValue();
            LOGGER.info("smallestEntry = " + smallestEntry);
            LOGGER.info("smallest = " + smallest);

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getG() == Double.MAX_VALUE) {
                continue;
            }
            //TODO: fix dijkstra
            for (FiboEdge edge : graph.<FiboEdge>getEdgesFromNode(smallest.getId())) {
                LOGGER.info("edge = " + edge);
                final Double alt = smallest.getG() + edge.getDistance();
                LOGGER.info("alt = " + alt);
                final FibonacciHeap.Entry<Vertex> connectedEntry = edge.getConnectedEntry(smallestEntry);
                LOGGER.info("old connectedEntry = " + connectedEntry);
                final Vertex connectedVertex = connectedEntry.getValue();
                LOGGER.info("connectedVertex = " + connectedVertex);

                if (alt < connectedVertex.getG()) {
                    connectedVertex.setGAndUpdateF(alt);
                    connectedVertex.setPrevious(smallest);
                    nodes.decreaseKey(connectedEntry, connectedVertex.getG());
                    LOGGER.info("new connectedEntry = " + connectedEntry);
                }
            }
        }
        return Collections.emptyList();
    }
}
