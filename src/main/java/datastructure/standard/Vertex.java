package datastructure.standard;

import datastructure.Element;
import datastructure.PrintHelper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class Vertex implements Element {

    private Integer id;
    private Double g;
    private Element anchor;
    private boolean closed;

    public Vertex(Integer id) {
        super();
        checkNotNull(id);
        this.id = id;
        this.g = 0.0;
        anchor = null;
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
    public Element getAnchor() {
        return anchor;
    }

    @Override
    public void setAnchor(Element element) {
        this.anchor = element;
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
        if (!(o instanceof Vertex)) return false;

        Vertex vertex = (Vertex) o;

        return !(id != null ? !id.equals(vertex.id) : vertex.id != null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ",g=" + PrintHelper.transformDouble(g) +
                '}';
    }
}
