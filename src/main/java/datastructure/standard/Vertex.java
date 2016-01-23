package datastructure.standard;

import datastructure.Element;
import datastructure.PrintHelper;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class Vertex implements Element {

    private Integer id;
    private Double key;
    private Element anchor;
    private boolean closed;
    private Map<Integer, Boolean> connectionTo;

    public Vertex(Integer id) {
        super();
        checkNotNull(id);
        this.id = id;
        this.key = 0.0;
        anchor = null;
        closed = false;
        connectionTo = new HashMap<>();
    }

    @Override
    public Boolean hasConnectionTo(Integer id) {
        return connectionTo.get(id) != null;
    }

    @Override
    public void isConnectionTo(Integer id) {
        connectionTo.put(id, true);
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setKey(Double key) {
        this.key = key;
    }

    @Override
    public Double getKey() {
        return key;
    }

    @Override
    public Element getAnchor() {
        return anchor;
    }

    @Override
    public void setAnchor(Element element) {
        this.anchor = element;
    }

    @Override
    public boolean isClosed() {
        return closed;
    }

    @Override
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
        return "[" +
                "id=" + id +
                ",key=" + PrintHelper.transformDouble(key) +
                "]";
    }
}