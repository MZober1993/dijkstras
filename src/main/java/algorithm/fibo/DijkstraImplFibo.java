package algorithm.fibo;

import algorithm.Dijkstra;
import datastructure.Graph;
import datastructure.Vertex;
import datastructure.fibo.FiboEdge;
import datastructure.fibo.FibonacciHeapBig;
import util.GraphHelper;
import util.LoggingHelper;

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
        FibonacciHeapBig<Vertex> nodes = new FibonacciHeapBig<>();
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
        LoggingHelper.logNewLine(LOGGER);
        while (!nodes.isEmpty()) {
            final FibonacciHeapBig.Entry<Vertex> smallestEntry = nodes.dequeueMin();
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
            LoggingHelper.logNewLine(LOGGER);
            for (FiboEdge edge : graph.<FiboEdge>getEdgesFromNode(smallest.getId())) {
                LoggingHelper.logMinusLine(LOGGER);
                LOGGER.info("edge = " + edge);
                final Double alt = smallest.getG() + edge.getDistance();
                LOGGER.info("alt = " + alt);
                final FibonacciHeapBig.Entry<Vertex> connectedEntry = edge.getConnectedEntry(smallestEntry);
                LOGGER.info("old connectedEntry = " + connectedEntry);
                final Vertex connectedVertex = connectedEntry.getValue();
                LOGGER.info("connectedVertex = " + connectedVertex);

                if (alt < connectedVertex.getG()) {
                    connectedVertex.setGAndUpdateF(alt);
                    connectedVertex.setPrevious(smallest);
                    nodes.decreaseKey(connectedEntry, connectedVertex.getG());
                    LOGGER.info("new connectedEntry = " + connectedEntry);
                }
                LoggingHelper.logNewLine(LOGGER);
            }
        }
        return Collections.emptyList();
    }
}
