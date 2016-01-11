package datastructure.binary;

import datastructure.Element;


/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 11:43
 */
public class BEntry<T extends Element> implements Element {

    private T value;
    private Double key;
    private Integer position;

    public BEntry(T value, Double key) {
        this.value = value;
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BEntry<T> entry = (BEntry<T>) o;

        return value.equals(entry.value);
    }

    @Override
    public Integer getId() {
        return value.getId();
    }

    @Override
    public void setG(Double g) {
        value.setG(g);
    }

    @Override
    public Double getG() {
        return value.getG();
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

    public Double getKey() {
        return key;
    }

    public void setKey(Double key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "(id=" + getId() +
                ", key=" + key +
                ", pos=" + position +
                ')';
    }
}