package algorithm;

import datastructure.Element;
import datastructure.Graph;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         18.11.15 - 14:02
 */
public interface Dijkstra<T extends Element> {
    /**
     * @param graph     The graph, where the shortestPath must be calculated.
     * @param start     The element of the start-point.
     * @param finish    The element of the end-point.
     * @return a list of characters, representing a shortestPath in an example
     */
    <G extends Graph<T>> List<Integer> shortestPath(G graph, T start, T finish);
}
