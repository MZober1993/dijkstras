package algorithm.fibo;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.fibo.FibonacciHeap;
import datastructure.fibo.VertexFibo;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class DijkstraImplFibo implements Dijkstra<VertexFibo> {

    @Override
    public <G extends Graph<VertexFibo, ? extends Edge<VertexFibo>>> List<Integer> shortestPath(G graph, VertexFibo start, VertexFibo finish) {
        FibonacciHeap nodes = new FibonacciHeap(GraphHelper.box(graph));
        for (VertexFibo entry : graph.getVs().values()) {
            entry.setKey(Double.MAX_VALUE);
            nodes.insert(entry);
        }
        nodes.decreaseKey(start, 0.0);

        while (!nodes.isEmpty()) {
            final VertexFibo smallest = nodes.extractMin();
            smallest.setClosed(true);

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getKey() == Double.MAX_VALUE) {
                continue;
            }

            for (Edge<VertexFibo> edge : graph.<Edge<VertexFibo>>getConnectedElements(smallest)) {
                final VertexFibo connected = edge.getConnected();
                if (connected.isClosed()) {
                    continue;
                }
                final Double alt = smallest.getKey() + edge.getDistance();

                if (alt < connected.getKey()) {
                    connected.setAnchor(smallest);
                    nodes.decreaseKey(connected, alt);
                }
            }
        }

        return Collections.emptyList();
    }
}
