package datastructure;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class VertexImpl implements Vertex {

    private Integer id;
    private Double g;
    private VertexImpl previous;
    private boolean closed;

    public VertexImpl(Integer id) {
        super();
        checkNotNull(id);
        this.id = id;
        this.g = 0.0;
        previous = null;
        closed = false;
    }

    @Override
    public Integer getId() {
        return id;
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
    public VertexImpl getPrevious() {
        return previous;
    }

    @Override
    public void setPrevious(Vertex previous) {
        this.previous = (VertexImpl) previous;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
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
                ",g=" + PrintHelper.transformDouble(g) +
                '}';
    }
}
