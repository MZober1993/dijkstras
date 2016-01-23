package datastructure.fibo;

import datastructure.AbstractElement;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 25.12.15 - 17:55
 */
public final class VertexFibo extends AbstractElement {

    private Double key;
    private boolean isMarked;
    private int deg;

    public VertexFibo(Integer id, Double key) {
        super(id);
        this.key = key;
        isMarked = false;
    }

    public Double getKey() {
        return key;
    }

    public void setKey(Double key) {
        this.key = key;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
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

    @Override
    public String toString() {
        return "(" + isMarked + ")" + super.toString();
    }
}
