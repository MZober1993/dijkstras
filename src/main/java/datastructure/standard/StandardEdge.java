package datastructure.standard;

import datastructure.Edge;
import datastructure.Vertex;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         17.11.15 - 14:24
 */
public interface StandardEdge extends Edge{

    default Vertex getConnectedVertex(Vertex vertex) {
        if (vertex.equals(getFirstVertex())) {
            return getSecondVertex();
        } else if (vertex.equals(getSecondVertex())) {
            return getFirstVertex();
        } else {
            return null;
        }
    }

    Vertex getFirstVertex();

    Vertex getSecondVertex();
}
