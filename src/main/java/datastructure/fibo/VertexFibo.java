package datastructure.fibo;

import datastructure.Element;
import datastructure.ElementContainer;
import datastructure.PrintHelper;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 25.12.15 - 17:55
 */
public final class VertexFibo extends ElementContainer implements Element {

    private int deg = 0;
    private boolean isMarked;
    private VertexFibo next;
    private VertexFibo previous;
    private VertexFibo parent;
    private VertexFibo child;
    private Double key;

    public VertexFibo(Element value, Double key) {
        super(value);
        deg = 0;
        parent = null;
        child = null;
        next = this;
        previous = this;
        this.key = key;
        isMarked = false;
    }

    public VertexFibo getNext() {
        return next;
    }

    public void setNext(VertexFibo next) {
        this.next = next;
    }

    public VertexFibo getPrevious() {
        return previous;
    }

    public void setPrevious(VertexFibo previous) {
        this.previous = previous;
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

    public VertexFibo getParent() {
        return parent;
    }

    public void setParent(VertexFibo parent) {
        this.parent = parent;
    }

    public VertexFibo getChild() {
        return child;
    }

    public void setChild(VertexFibo child) {
        this.child = child;
    }

    @Override
    public String toString() {
        return PrintHelper.transformEntry(this);
    }
}
