package algorithm;

import datastructure.Graph;
import datastructure.standard.StandardGraph;
import datastructure.Vertex;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         18.11.15 - 14:02
 */
public interface Dijkstra {
    /**
     * @param graph     The graph, where the shortestPath must be calculated.
     * @param startNode The character of the start-vertex.
     * @param endNode   The character of the end-vertex.
     * @return a list of characters, representing a shortestPath in an example
     */
    List<Integer> shortestPath(Graph graph, Vertex startNode, Vertex endNode);
}
