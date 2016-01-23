package datastructure;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 19:03
 *         This class only delegates.
 */
public class AbstractElement implements Element {

    private Integer id;
    private Element anchor;
    private boolean closed;
    private Map<Integer, Boolean> connectionTo;

    public AbstractElement(Integer id) {
        super();
        checkNotNull(id);
        this.id = id;
        anchor = null;
        closed = false;
        connectionTo = new HashMap<>();
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

        AbstractElement vertex = (AbstractElement) o;

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
                "]";
    }
}
