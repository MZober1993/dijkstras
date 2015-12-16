package datastructure.standard;

import datastructure.EdgeBuilder;
import datastructure.Graph;
import datastructure.Vertex;
import util.MathHelper;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         17.11.15 - 14:22
 */
public interface StandardGraph extends Graph{

    List<StandardEdge> getEdgesFromNode(Integer identifier);

    default List<StandardEdge> getEdgesFromNode(Vertex vertex) {
        return getEdgesFromNode(vertex.getId());
    }

    List<StandardEdge> getEdges();

    Map<Integer, List<StandardEdge>> getOutgoingEdges();
}
