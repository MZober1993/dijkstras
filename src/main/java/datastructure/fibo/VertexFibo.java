package datastructure.fibo;

import datastructure.Element;
import datastructure.ElementContainer;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 25.12.15 - 17:55
 */
public final class VertexFibo extends ElementContainer implements Element {

    private int deg = 0;
    private boolean isMarked;

    public VertexFibo(Element value, Double key) {
        super(value);
        value.setKey(key);
        deg = 0;
        isMarked = false;
    }


    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }

    public void incrementDeg() {
        this.deg++;
    }

    public void decrementDeg() {
        this.deg--;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    @Override
    public String toString() {
        return "(" + deg +
                ", " + isMarked + ")" + getValue();
    }
}
