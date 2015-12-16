package datastructure.fibo;

import datastructure.Graph;
import datastructure.Vertex;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         17.11.15 - 14:22
 */
public interface FiboGraph extends Graph {

    List<FiboEdge> getEdgesFromNode(Integer identifier);

    default List<FiboEdge> getEdgesFromNode(Vertex vertex) {
        return getEdgesFromNode(vertex.getId());
    }

    List<FiboEdge> getEdges();

    Map<Integer, List<FiboEdge>> getOutgoingEdges();
}
