package datastructure;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         17.11.15 - 14:24
 */
public interface Vertex extends Comparable<Vertex> {

    Double getF();

    Integer getId();

    void setId(Integer id);

    void setG(Double g);

    Double getG();

    default void setGAndUpdateF(Double g) {
        setG(g);
        setF(getH() + getG());
    }

    Double getH();

    void setHAndUpdateF(Double h);

    void setF(Double f);

    Vertex getPrevious();

    void setPrevious(Vertex previous);

    boolean isClose();

    void setClose(boolean close);

    Double getX();

    void setX(Double x);

    Double getY();

    void setY(Double y);

    @Override
    default int compareTo(Vertex vertex) {
        checkNotNull(vertex);
        return this.getF() < vertex.getF()
                ? -1 : this.getF() == vertex.getF()
                ? 0 : 1;
    }
}
