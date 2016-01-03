package util;

import algorithm.Dijkstra;
import com.google.common.collect.ImmutableList;
import datastructure.Vertex;
import datastructure.standard.GraphImpl;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         19.11.15 - 17:08
 */
public class MemberFinder {

    public List<Integer> findMemberForHighestShortestPathFromTheBeginningOfTheGraph(
            Dijkstra algorithm, GraphImpl graph) {
        Vertex start = graph.getElement(1);
        ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        builder.add(start.getId());
        Integer length = 0;
        Integer member = 0;

        for (Vertex vertex : graph.getElements().values()) {
            List<Integer> shortestPath = algorithm.shortestPath(graph, start, vertex);

            if (shortestPath.isEmpty()) {
                continue;
            }

            int size = shortestPath.size();
            if (length < size) {
                length = size;
                member = vertex.getId();
            }
        }

        builder.add(member);
        return builder.build();
    }
}
