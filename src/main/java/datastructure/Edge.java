package datastructure;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 2015 - 17.11.15 - 14:24
 */
public interface Edge extends Comparable<Edge> {

    boolean contains(datastructure.Vertex vertex);

    Double getDistance();

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

    @Override
    default int compareTo(Edge edge) {
        checkNotNull(edge);
        return this.getDistance() < edge.getDistance()
                ? -1 : this.getDistance() == edge.getDistance()
                ? 0 : 1;
    }
}
