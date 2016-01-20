package algorithm.standard;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphHelper;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class DijkstraImpl implements Dijkstra<Element> {

    @Override
    public <G extends Graph<Element, ? extends Edge<Element>>> List<Integer>
    shortestPath(G graph, Element start, Element finish) {
        PriorityQueue<Element> nodes = new PriorityQueue<>();

        for (Element vertex : graph.getElements().values()) {
            if (vertex.equals(start)) {
                vertex.setG(0.0);
            } else {
                vertex.setG(Double.MAX_VALUE);
            }
            nodes.add(vertex);
        }

        while (!nodes.isEmpty()) {
            final Element smallest = nodes.poll();
            smallest.setClosed(true);

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getG() == Double.MAX_VALUE) {
                continue;
            }

            for (Edge<Element> edge : graph.getConnectedElements(smallest)) {
                final Element connected = edge.getConnected();
                if (!connected.isClosed()) {
                    final Double alt = smallest.getG() + edge.getDistance();

                    if (alt < connected.getG()) {
                        connected.setG(alt);
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
