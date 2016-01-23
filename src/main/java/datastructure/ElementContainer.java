package datastructure;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 19:03
 *         This class only delegates.
 */
public class ElementContainer implements Element {

    private Element value;

    protected ElementContainer(Element value) {
        this.value = value;
    }

    private void checkValueNotNull() {
        if (value == null) {
            throw new NullPointerException("Value:" + this + " , is null.");
        }
    }

    public Element getValue() {
        checkValueNotNull();
        return value;
    }

    @Override
    public Boolean hasConnectionTo(Integer id) {
        return value.hasConnectionTo(id);
    }

    @Override
    public void isConnectionTo(Integer id) {
        value.isConnectionTo(id);
    }

    @Override
    public Integer getId() {
        return value.getId();
    }

    @Override
    public void setKey(Double g) {
        value.setKey(g);
    }

    @Override
    public Double getKey() {
        return value.getKey();
    }

    @Override
    public Element getAnchor() {
        return value.getAnchor();
    }

    @Override
    public void setAnchor(Element element) {
        value.setAnchor(element);
    }

    @Override
    public boolean isClosed() {
        return value.isClosed();
    }

    @Override
    public void setClosed(boolean close) {
        value.setClosed(close);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ElementContainer entry = (ElementContainer) o;

        return value.equals(entry.value);
    }
}
