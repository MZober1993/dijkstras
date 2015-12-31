package datastructure;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         17.11.15 - 14:24
 */
public interface Vertex extends Comparable<Vertex> {

    Integer getId();

    void setG(Double g);

    Double getG();

    Vertex getPrevious();

    void setPrevious(Vertex previous);

    boolean isClosed();

    void setClosed(boolean close);

    @Override
    default int compareTo(Vertex vertex) {
        checkNotNull(vertex);
        return this.getG() < vertex.getG()
                ? -1 : this.getG() == vertex.getG()
                ? 0 : 1;
    }
}
