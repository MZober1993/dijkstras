package datastructure.fibo;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.12.15 - 17:03
 */
public final class FibonacciHeap<T> {

    public final static class Entry<T> {

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

        public boolean isMarked() {
            return isMarked;
        }

        public double getPriority() {
            return priority;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "priority=" + priority +
                    ", value=" + value +
                    ", isMarked=" + isMarked +
                    ", deg=" + deg +
                    '}';
        }
    }

    private Entry<T> minimum = null;
    private Integer size = 0;

    public void insert(T value, Double priority) {
        Entry<T> entry = new Entry<>(value, priority);
        cyclicListConcat(minimum, entry);
        if (minimum == null || entry.priority < minimum.priority) {
            minimum = entry;
        }
        size++;
    }


    public FibonacciHeap<T> merge(FibonacciHeap<T> heap1, FibonacciHeap<T> heap2) {
        if (heap2.minimum != null) {
            if (heap1.minimum == null) {
                heap1.minimum = heap2.minimum;
            } else {
                cyclicListConcat(heap1.minimum, heap2.minimum);
                if (heap2.minimum.priority < heap1.minimum.priority) {
                    heap1.minimum = heap2.minimum;
                }
                heap1.size = heap1.size + heap2.size;
            }
        }
        return heap1;
    }

    /**
     * Concat the root-list of y next to x's root-list:
     * -x<=>x.next- & -y.previous<=>y- ==>
     * -x<=>y- & -y.previous<=>x.next-
     *
     * @param x left-heap
     * @param y right-heap
     */
    private void cyclicListConcat(Entry<T> x, Entry<T> y) {
        if (x == null) {
            x = y;
        } else {
            Entry<T> tmpNext = x.getNext();
            Entry<T> tmpPrevious = y.getPrevious();

            x.setNext(y);
            y.setPrevious(x);
/*

            tmpNext.setPrevious(tmpPrevious);
            tmpPrevious.setNext(tmpPrevious);
            //TODO:fix pointer-arithmetic
*/

            y.setNext(tmpNext);
            tmpNext.setPrevious(y);

            x.setPrevious(tmpPrevious);
            tmpPrevious.setNext(x);
        }
    }

    public Entry<T> deleteMin(FibonacciHeap<T> heap) {
        Entry<T> entry = heap.minimum;
        if (entry != null) {
            if (entry.child != null) {
                Entry<T> curr = entry.child;
                do {
                    curr.parent = null;
                    curr = curr.next;
                    cyclicListConcat(curr, entry);
                } while (curr != entry.child);
            }
        }
        return null;
    }

    public Entry<T> getMinimum() {
        return minimum;
    }

    public Integer getSize() {
        return size;
    }
}
