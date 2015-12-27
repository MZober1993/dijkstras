package datastructure.fibo;

import util.MathHelper;

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

    /*public Entry<T> extractMin() {
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
    }*/

    public Entry<T> extractMin() {
        Entry<T> entry = minimum;
        if (entry != null) {
            //add all childs of minimum to the parent list
            for (int i = 0; i < entry.getDeg(); i++) {
                Entry<T> child = entry.getChild();
                if (child == child.getNext()) {
                    entry.setChild(null);
                } else {
                    entry.setChild(child.getNext());
                    child.getNext().setPrevious(child.getPrevious());
                    child.getPrevious().setNext(child.getNext());
                }
                child.setParent(null);
                child.setNext(entry);
                child.setPrevious(entry.getPrevious());
                entry.getPrevious().setNext(child);
                entry.setPrevious(child);
            }

            //remove the min
            if (entry == entry.getNext()) {
                minimum = null;
            } else {
                entry.getNext().setPrevious(entry.getPrevious());
                entry.getPrevious().setNext(entry.getNext());
                minimum = entry.getNext();
            }
            size--;
            if (size > 0) {
                consolidate();
            }
            return entry;
        }
        return null;
    }

    private void consolidate() {
        System.out.println(size);
        int degreeSize = 2 * MathHelper.log2(size + 1) + 1;
        Entry<T>[] degree = new Entry[degreeSize];
        for (int i = 0; i < degreeSize; i++) {
            degree[i] = null;
        }

        Entry<T> element = minimum;
        Entry<T> next;
        do {
            if (element == element.getNext()) {
                next = null;
            } else {
                next = element.getNext();
            }
            element.getNext().setPrevious(element.getPrevious());
            element.getPrevious().setNext(element.getNext());
            element.setNext(element);
            element.setPrevious(element);

            int currentDegree = element.getDeg();
            while (degree[currentDegree] != null) {
                if (element.getPriority() > degree[currentDegree].getPriority()) {
                    swap(element, degree[currentDegree]);
                }
                //todo:make this easier
                //degree[currentDegree] becomes child of element
                if (element.getChild() == null) {
                    element.setChild(degree[currentDegree]);
                    degree[currentDegree].setParent(element);
                } else {
                    degree[currentDegree].setParent(element);
                    degree[currentDegree].setNext(element.getChild());
                    degree[currentDegree].setPrevious(element.getChild().getPrevious());
                    degree[currentDegree].getNext().setPrevious(degree[currentDegree]);
                    degree[currentDegree].getPrevious().setNext(degree[currentDegree]);
                }

                element.setDeg(element.getDeg() + 1);
                degree[currentDegree].setMarked(false);
                degree[currentDegree] = null;
                currentDegree++;
            }
            degree[currentDegree] = element;
            element = next;
        } while (element != null);

        //update minimum
        minimum = null;
        for (int i = 0; i < degreeSize; i++) {
            if (degree[i] != null) {
                if (minimum == null) {
                    //heap empty
                    minimum = degree[i];
                    degree[i].setNext(degree[i]);
                    degree[i].setPrevious(degree[i]);
                } else {
                    degree[i].setNext(minimum);
                    degree[i].setPrevious(minimum.getPrevious());
                    minimum.getPrevious().setNext(degree[i]);
                    minimum.setPrevious(degree[i]);
                    if (degree[i].getPriority() < minimum.getPriority()) {
                        minimum = degree[i];
                    }
                }
            }
        }
    }

    public void swap(Entry<T> entryOne, Entry<T> entryTwo) {
        Entry<T> tmp = entryOne;
        entryOne = entryTwo;
        entryTwo = tmp;
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
