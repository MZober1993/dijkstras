package algorithm.sample;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.sample.FiboHeap;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 07.01.16 - 19:37
 */
public class DijkstraFibo implements Dijkstra<FiboHeap.Node<Element>> {

    @Override
    public <G extends Graph<FiboHeap.Node<Element>>> List<Integer> shortestPath(G graph
            , FiboHeap.Node<Element> start, FiboHeap.Node<Element> finish) {
        FiboHeap<Element> nodes = new FiboHeap<>();
        for (Element entry : graph.getElements().values()) {
            if (entry.equals(start)) {
                entry.setG(0.0);
            } else {
                entry.setG(Double.MAX_VALUE);
            }
            nodes.enqueue(entry, entry.getG());
        }

        while (!nodes.isEmpty()) {
            final FiboHeap.Node<Element> smallest = nodes.dequeueMin();
            smallest.setClosed(true);

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getG() == Double.MAX_VALUE) {
                continue;
            }

            for (Edge<FiboHeap.Node<Element>> edge : graph
                    .<Edge<FiboHeap.Node<Element>>>getEdgesFromNode(smallest.getId())) {
                final FiboHeap.Node<Element> connected = edge.getConnected(smallest);
                if (!connected.isClosed()) {
                    final Double alt = smallest.getG() + edge.getDistance();
                    if (nodes.min().selfPointed()) {
                        System.out.println("minimum is selfpointed");
                    }
                    if (alt < connected.getG()) {
                        connected.setG(alt);
                        connected.setAnchor(smallest);
                        nodes.decreaseKey(connected, alt);
                    }
                }
            }
        }
        return Collections.emptyList();
    }
}

