package datastructure;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class VertexImpl implements Vertex {

    private Integer id;
    private Double g;
    private Double h;
    private Double f;
    private VertexImpl previous;
    private boolean close;
    private Double x;
    private Double y;

    public VertexImpl(Integer id) {
        super();
        checkNotNull(id);
        this.id = id;
        this.g = 0.0;
        this.h = 0.0;
        this.f = 0.0;
        previous = null;
        close = false;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        checkNotNull(id);
        this.id = id;
    }

    @Override
    public void setG(Double g) {
        this.g = g;
    }

    @Override
    public Double getG() {
        return g;
    }

    @Override
    public void setGAndUpdateF(Double g) {
        this.g = g;
        this.f = h + g;
    }

    @Override
    public Double getH() {
        return h;
    }

    @Override
    public void setHAndUpdateF(Double h) {
        this.h = h;
        this.f = g + h;
    }

    @Override
    public Double getF() {
        return f;
    }

    @Override
    public void setF(Double f) {
        this.f = f;
    }

    @Override
    public VertexImpl getPrevious() {
        return previous;
    }

    @Override
    public void setPrevious(Vertex previous) {
        this.previous = (VertexImpl) previous;
    }

    @Override
    public boolean isClose() {
        return close;
    }

    @Override
    public void setClose(boolean close) {
        this.close = close;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public void setX(Double x) {
        this.x = x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VertexImpl)) return false;

        VertexImpl vertex = (VertexImpl) o;

        return !(id != null ? !id.equals(vertex.id) : vertex.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "id=" + id +
                ",g=" + g +
                ",f=" + f +
                '}';
    }
}
