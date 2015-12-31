package algorithm;

import datastructure.Graph;
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
    <G extends Graph> List<Integer> shortestPath(G graph, Vertex startNode, Vertex endNode);
}
