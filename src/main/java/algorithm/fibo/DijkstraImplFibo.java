package algorithm.fibo;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.fibo.Entry;
import datastructure.fibo.FibonacciHeap;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class DijkstraImplFibo implements Dijkstra<Entry<Element>> {

    @Override
    public <G extends Graph<Entry<Element>>> List<Integer> shortestPath(G graph
            , Entry<Element> start, Entry<Element> finish) {
        FibonacciHeap<Element> nodes = new FibonacciHeap<>();
        for (Entry<Element> entry : graph.getElements().values()) {
            entry.setKey(Double.MAX_VALUE);
            nodes.insert(entry);
        }
        nodes.decreaseKey(start, 0.0);

        while (!nodes.isEmpty()) {
            final Entry<Element> smallest = nodes.extractMin();
            smallest.setClosed(true);

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getKey() == Double.MAX_VALUE) {
                continue;
            }

            for (Edge<Entry<Element>> edge : graph.<Edge<Entry<Element>>>getEdgesFromNode(smallest.getId())) {
                final Entry<Element> connected = edge.getConnected(smallest);
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
