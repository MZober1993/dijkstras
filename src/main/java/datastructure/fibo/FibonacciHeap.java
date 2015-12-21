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

    public Entry<T> extractMin() {
        Entry<T> entry = minimum;
        if (entry != null) {
            if (entry.child != null) {
                Entry<T> curr = entry.child;
                cutChildsAndAddToList(entry, curr);
            }
            cutConnection(entry);
            if (entry == entry.getNext()) {
                minimum = null;
            } else {
                minimum = entry.getNext();
                consolidate();
            }
            size--;
            return entry;
        }
        return null;
    }

    private void consolidate() {

    }

    private void cutChildsAndAddToList(Entry<T> entry, Entry<T> curr) {
        do {
            curr.parent = null;
            curr = curr.next;
            if (curr != entry.child) {
                cyclicListConcat(entry, curr);
            }
        } while (curr != entry.child);
    }

    private void cutConnection(Entry<T> entry) {
        entry.next.previous = entry.previous;
        entry.previous.next = entry.next;
    }

    /**
     * Concat the root-list of y next to x's root-list:
     * -x<=>x.next- & -y.previous<=>y- ==>
     * -x<=>y- & -y.previous<=>x.next-
     *
     * @param x left-heap
     * @param y right-heap
     * @return minimum
     */
    private Entry<T> cyclicListConcat(Entry<T> x, Entry<T> y) {
        if (checkEntriesWithCorrectlyOverride(x, y)) {
            Entry<T> tmpNext = x.getNext();

            x.setNext(y.next);
            x.getNext().setPrevious(x);

            y.setNext(tmpNext);
            y.getNext().setPrevious(y);

            return x.getPriority() < y.getPriority() ? x : y;
        } else {
            return x;
        }
    }

    private boolean checkEntriesWithCorrectlyOverride(Entry<T> x, Entry<T> y) {
        if (x == null && y == null) {
            throw new IllegalArgumentException("both Entries are null," +
                    " in checkEntriesAndOverride");
        } else if (y == null) {
            y = x;
            return false;
        } else if (x == null) {
            x = y;
            return false;
        }
        return true;
    }


    private boolean checkEntries(Entry<T> x, Entry<T> y) {
        if (x == null && y == null) {
            return false;
        } else if (y == null) {
            return false;
        } else if (x == null) {
            return false;
        }
        return true;
    }

    public Entry<T> getMinimum() {
        return minimum;
    }

    public Integer getSize() {
        return size;
    }

    @Override
    public String toString() {

        return "FibonacciHeap{" +
                "minimum=" + minimum +
                ", size=" + size +
                ", elems="+//TODO:implement this
                '}';
    }
}
