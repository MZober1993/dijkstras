package datastructure.standard;

import datastructure.Element;
import datastructure.PrintHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 19:03
 *         This class only delegates.
 */
public class Vertex implements Element, Comparable<Vertex> {

    private Integer id;
    private Double key;
    private Element anchor;
    private boolean closed;
    private Map<Integer, Boolean> connectionTo;

    public Vertex(Integer id, double key) {
        super();
        checkNotNull(id);
        this.key = key;
        this.id = id;
        anchor = null;
        closed = false;
        connectionTo = new HashMap<>();
    }

    public void setKey(Double key) {
        this.key = key;
    }

    public Double getKey() {
        return key;
    }

    @Override
    public Boolean hasConnectionTo(Integer id) {
        return connectionTo.get(id) != null;
    }

    @Override
    public void isConnectedTo(Integer id) {
        connectionTo.put(id, true);
    }

    @Override
    public Integer getId() {
        return id;
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
        if (!(o instanceof Element)) return false;

        Vertex vertex = (Vertex) o;

        return !(id != null ? !id.equals(vertex.id) : vertex.id != null);
    }

    @Override
    public int compareTo(Vertex element) {
        checkNotNull(element);
        return this.getKey() < element.getKey()
                ? -1 : Objects.equals(this.getKey(), element.getKey())
                ? 0 : 1;
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
