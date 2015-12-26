package datastructure.fibo;

import java.util.stream.Stream;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.12.15 - 17:03
 */
public final class FibonacciHeap<T> {

    private Entry<T> minimum = null;
    private Integer size = 0;

    public void insert(T value, Double priority) {
        insert(new Entry<>(value, priority));
    }

    public void insert(Entry<T> entry) {
        cyclicListConcat(minimum, entry);
        if (minimum == null || entry.getPriority() < minimum.getPriority()) {
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
                if (heap2.minimum.getPriority() < heap1.minimum.getPriority()) {
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
            if (entry.getChild() != null) {
                Entry<T> curr = entry.getChild();
                cutChildsAndAddToList(entry, curr);
            }
            cutConnection(entry);
            if (entry == entry.getNext()) {
                minimum = null;
            } else {
                minimum = entry.getNext();
                consolidate();
            }
            this.size--;
            return entry;
        }
        return null;
    }

    private void consolidate() {
        int sizeOfArray = (int) Math.ceil(Math.log(size)) + size / 2;
        Entry<T>[] degreeVertices = initDegreeVertices(sizeOfArray);

        boolean isNotVisit = true;
        for (Entry<T> cur = minimum; cur != cur.getNext() || isNotVisit; cur = cur.getNext()) {
            if (cur == cur.getNext()) {
                isNotVisit = false;
            }
            Integer d = cur.getDeg();
            while (degreeVertices[d] != null) {
                if (cur.getPriority() > degreeVertices[d].getPriority()) {
                    swap(cur, degreeVertices[d]);
                }
                degreeVertices[d].setMarked(false);
                if (degreeVertices[d].getNext() == cur || degreeVertices[d].getPrevious() == cur) {
                    degreeVertices[d].setNext(degreeVertices[d]);
                    degreeVertices[d].setPrevious(degreeVertices[d]);
                    cyclicListConcat(degreeVertices[d], cur.getChild());
                }
                cur.setChild(degreeVertices[d]);
                degreeVertices[d].setParent(cur);
                cur.setDeg(cur.getDeg() + 1);
                degreeVertices[d] = null;
                d++;
            }

            degreeVertices[d] = cur;
            cutConnection(cur);
        }
        minimum = null;

        calcMinimum(sizeOfArray, degreeVertices);
    }

    public void swap(Entry<T> entryOne, Entry<T> entryTwo) {
        Entry<T> tmp = entryOne;
        entryOne = entryTwo;
        entryTwo = tmp;
    }

    private Entry<T>[] initDegreeVertices(int sizeOfArray) {
        Entry<T>[] degreeVertices = new Entry[sizeOfArray];
        for (int i = 0; i < sizeOfArray; i++) {
            degreeVertices[i] = null;
        }
        return degreeVertices;
    }

    private void calcMinimum(int sizeOfArray, Entry<T>[] degreeVertices) {
        for (int i = 0; i < sizeOfArray; i++) {
            if (degreeVertices[i] != null) {
                if (minimum == null) {
                    minimum = degreeVertices[i];
                } else {
                    cyclicListConcat(minimum, degreeVertices[i]);
                }
                if (degreeVertices[i].getPriority() < minimum.getPriority()) {
                    minimum = degreeVertices[i];
                }
            }
        }
    }

    private void cutChildsAndAddToList(Entry<T> entry, Entry<T> curr) {
        do {
            curr.setParent(null);
            curr = curr.getNext();
            if (curr != entry.getChild()) {
                cyclicListConcat(entry, curr);
            }
        } while (curr != entry.getChild());
    }

    public void cutConnection(Entry<T> entry) {
        entry.getNext().setPrevious(entry.getPrevious());
        entry.getPrevious().setNext(entry.getNext());
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

            x.setNext(y.getNext());
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
        StringBuilder builder = new StringBuilder();
        return "FibonacciHeap{" +
                "minimum=" + minimum +
                ", size=" + size +
                "\n, elems=" + rootListToString(builder) +
                '}';
    }

    private String rootListToString(StringBuilder builder) {
        addMemberToStream(Stream.builder(), minimum).build().map(x -> x.toString())
                .forEach(x -> builder.append(x));
        return builder.toString();
    }

    private Stream.Builder<Entry<T>> addMemberToStream(Stream.Builder<Entry<T>> builder, Entry<T> entry) {
        builder.add(entry);
        for (Entry<T> current = entry.getNext(); current != entry; current.getNext()) {
            builder.add(current);
        }
        return builder;
    }
}
