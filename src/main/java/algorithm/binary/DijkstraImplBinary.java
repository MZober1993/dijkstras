package algorithm.binary;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.binary.BinaryHeap;
import datastructure.binary.VertexBinary;

import java.util.Collections;
import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 14:00
 */
public class DijkstraImplBinary implements Dijkstra<VertexBinary> {

    @Override
    public <G extends Graph<VertexBinary, ? extends Edge<VertexBinary>>> List<Integer>
    shortestPath(G graph, VertexBinary start
            , VertexBinary finish) {
        BinaryHeap nodes = new BinaryHeap();
        for (VertexBinary entry : graph.getElements().values()) {
            entry.setKey(Double.MAX_VALUE);
            nodes.insert(entry);
        }
        nodes.decreaseKey(start, 0.0);

        while (!nodes.isEmpty()) {
            final VertexBinary smallest = nodes.extractMin();
            smallest.setClosed(true);

            if (smallest.equals(finish)) {
                return GraphHelper.reconstructPath(smallest);
            }

            if (smallest.getKey() == Double.MAX_VALUE) {
                continue;
            }

            for (Edge<VertexBinary> edge : graph.<Edge<VertexBinary>>getConnectedElements(smallest)) {
                final VertexBinary connected = edge.getConnected();
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
