package datastructure.fibo;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.12.15 - 17:03
 */
public final class FibonacciHeap<T> {

    public final static class Entry<T> {

        private int deg = 0;       // Number of children
        private boolean isMarked = false; // Whether this node is marked

        private Entry<T> next;   // Next and previous elements in the list
        private Entry<T> previous;

        private Entry<T> parent; // Parent in the tree, if any.

        private Entry<T> child;  // Child node, if any.

        private T value;     // Element being stored here
        private double priority; // Its priority

        public Entry(T value, double priority) {
            next = this;
            previous = this;
            this.value = value;
            this.priority = priority;
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

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public double getPriority() {
            return priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }
    }

    Entry<T> minimum;
    Integer n;

    public void insert(T value, Double priority) {
        Entry<T> entry = new Entry<>(value, priority);
        if (minimum == null) {
            minimum = entry;
        } else {
            insertEntryLeftToMinimum(entry);
            if (entry.getPriority() < minimum.getPriority()) {
                minimum = entry;
            }
        }
        n++;
    }

    private void insertEntryLeftToMinimum(Entry<T> entry) {
        Entry<T> tmpNext=minimum.getNext();

        entry.setNext(tmpNext);
        tmpNext.setPrevious(entry);

        minimum.setNext(entry);
        entry.setPrevious(minimum);
    }
}
