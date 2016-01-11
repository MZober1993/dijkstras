package algorithm.binary;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.binary.BEntry;
import datastructure.binary.BinaryHeap;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 14:00
 */
public class DijkstraImplBinary implements Dijkstra<BEntry<Element>> {

    @Override
    public <G extends Graph<BEntry<Element>>> List<Integer> shortestPath(G graph, BEntry<Element> start
            , BEntry<Element> finish) {
        BinaryHeap<Element> nodes = new BinaryHeap<>();
        for (BEntry<Element> entry : graph.getElements().values()) {
            entry.setKey(Double.MAX_VALUE);
            nodes.insert(entry);
        }
        nodes.decreaseKey(start, 0.0);

        while (!nodes.isEmpty()) {
            final BEntry<Element> smallest = nodes.extractMin();
            smallest.setClosed(true);

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getKey() == Double.MAX_VALUE) {
                continue;
            }

            for (Edge<BEntry<Element>> edge : graph.<Edge<BEntry<Element>>>getEdgesFromNode(smallest.getId())) {
                final BEntry<Element> connected = edge.getConnected(smallest);
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
