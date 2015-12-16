package datastructure;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         17.11.15 - 14:24
 */
public interface Vertex extends Comparable<Vertex> {

    public Double getF();

    public Integer getId();

    public void setId(Integer id);

    public void setG(Double g);

    public Double getG();

    public default void setGAndUpdateF(Double g) {
        setG(g);
        setF(getH() + getG());
    }

    public Double getH();

    public void setHAndUpdateF(Double h);

    public void setF(Double f);

    public Vertex getPrevious();

    public void setPrevious(Vertex previous);

    public boolean isClose();

    public void setClose(boolean close);

    public Double getX();

    public void setX(Double x);

    public Double getY();

    public void setY(Double y);

    @Override
    public default int compareTo(Vertex vertex) {
        checkNotNull(vertex);
        return this.getF() < vertex.getF()
                ? -1 : this.getF() == vertex.getF()
                ? 0 : 1;
    }
}
