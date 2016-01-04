package datastructure.fibo;

import datastructure.Element;
import datastructure.PrintHelper;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 25.12.15 - 17:55
 */
public final class Entry<T extends Element> implements Element {

    private int deg = 0;
    private boolean isMarked;
    private Entry<T> next;
    private Entry<T> previous;
    private Entry<T> parent;
    private Entry<T> child;
    private T value;
    private Double key;

    private Entry() {
    }

    public Entry(T value, Double key) {
        deg = 0;
        parent = null;
        child = null;
        next = this;
        previous = this;
        this.value = value;
        this.key = key;
        isMarked = false;
    }

    public Entry<T> getNext() {
        return next;
    }

    public void setNext(Entry<T> next) {
        this.next = next;
    }

    public Entry<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Entry<T> previous) {
        this.previous = previous;
    }

    public T getValue() {
        checkValueNotNull();
        return value;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public Double getKey() {
        return key;
    }

    public void setKey(Double key) {
        this.key = key;
    }

    public Entry<T> getParent() {
        return parent;
    }

    public void setParent(Entry<T> parent) {
        this.parent = parent;
    }

    public Entry<T> getChild() {
        return child;
    }

    public void setChild(Entry<T> child) {
        this.child = child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry<T> entry = (Entry<T>) o;

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

    private void checkValueNotNull() {
        if (value == null) {
            throw new NullPointerException("Value of Entry:" + this + " , is null.");
        }
    }

    @Override
    public String toString() {
        return PrintHelper.transformEntry(this);
    }
}
