package datastructure.fibo;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 25.12.15 - 17:55
 */
public final class Entry<T> {

    private int deg = 0;
    private boolean isMarked;
    private Entry<T> next;
    private Entry<T> previous;
    private Entry<T> parent;
    private Entry<T> child;
    private T value;
    private double priority;

    public Entry(T value, double priority) {
        deg = 0;
        parent = null;
        child = null;
        next = this;
        previous = this;
        this.value = value;
        this.priority = priority;
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

    public double getPriority() {
        return priority;
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
    public String toString() {
        return "Entry{" +
                "deg=" + deg +
                ", isMarked=" + isMarked +
                ", next=" + nullOrPriority(next) +
                ", previous=" + nullOrPriority(previous) +
                ", parent=" + nullOrPriority(parent) +
                ", child=" + nullOrPriority(child) +
                ", value=" + value +
                ", priority=" + priority +
                '}';
    }

    private String nullOrPriority(Entry<T> entry){
        return String.valueOf(entry==null?"null":entry.getPriority());
    }
}
