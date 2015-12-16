package algorithm.standard;

import algorithm.Dijkstra;
import datastructure.Edge;
import datastructure.Graph;
import datastructure.Vertex;
import datastructure.standard.GraphImpl;

import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 2015 - 01.11.15 - 18:40
 */
public class DijkstraImpl implements Dijkstra {

    @Override
    public List<Integer> shortestPath(Graph graph, Vertex start, Vertex finish) {
        PriorityQueue<Vertex> nodes = new PriorityQueue<>();

        for (Vertex vertex : graph.getVertices().values()) {
            if (vertex.equals(start)) {
                vertex.setGAndUpdateF(0.0);
            } else {
                vertex.setGAndUpdateF(Double.MAX_VALUE);
            }
            nodes.add(vertex);
        }

        while (!nodes.isEmpty()) {
            Vertex smallest = nodes.poll();

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
                    nodes.remove(connectedVertex);
                    nodes.add(connectedVertex);
                }
            }
        }
        return Collections.emptyList();
    }
}
