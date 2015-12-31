package datastructure.fibo;

import datastructure.PrintHelper;
import util.LoggingHelper;
import util.MathHelper;

import java.util.logging.Logger;

import static datastructure.fibo.FiboHelper.*;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.12.15 - 17:03
 */
public final class FibonacciHeap<T> {

    public static final Logger LOGGER = LoggingHelper.buildLoggerWithStandardOutputConfig(FibonacciHeap.class);
    private Entry<T> minimum = null;
    private Integer size = 0;

    public Entry<T> insert(T value, Double priority) {
        return insert(new Entry<>(value, priority));
    }

    public Entry<T> insert(Entry<T> entry) {
        mainListConcat(entry);
        size++;
        return entry;
    }

    public void mainListConcat(Entry<T> element) {
        cyclicListConcat(minimum, element);
        if (minimum == null || element.getKey() < minimum.getKey()) {
            minimum = element;
        }
    }

    public void listConcat(Entry<T> element) {
        if (minimum == null) {
            minimum = element;
        } else {
            Entry<T> endHeap = minimum.getPrevious();
            minimum.setPrevious(element);
            element.setPrevious(endHeap);
            endHeap.setNext(element);
            element.setNext(minimum);

            // set new minimum
            if (minimum.getKey() > element.getKey()) {
                minimum = element;
            }
        }
    }

    public FibonacciHeap<T> merge(FibonacciHeap<T> heap1, FibonacciHeap<T> heap2) {
        if (heap2.minimum != null) {
            if (heap1.minimum == null) {
                heap1.minimum = heap2.minimum;
            } else {
                cyclicListConcat(heap1.minimum, heap2.minimum);
                if (heap2.minimum.getKey() < heap1.minimum.getKey()) {
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
            //add all children of minimum to the parent list
            for (int i = 0; i < entry.getDeg(); i++) {
                Entry<T> child = entry.getChild();
                if (child == child.getNext()) {
                    entry.setChild(null);
                } else {
                    entry.setChild(child.getNext());
                    cutConnection(child);
                }
                child.setParent(null);
                child.setNext(entry);
                child.setPrevious(entry.getPrevious());
                entry.getPrevious().setNext(child);
                entry.setPrevious(child);
            }

            //remove the minimum
            if (entry == entry.getNext()) {
                minimum = null;
            } else {
                cutConnection(entry);
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
        int degreeSize = 2 * MathHelper.log2(size) + 1;
        Entry<T>[] degree = new Entry[degreeSize];
        for (int i = 0; i < degreeSize; i++) {
            degree[i] = null;
        }

        cutAndSummarizeTrees(degree);

        updateMinimum(degreeSize, degree);
    }

    private void cutAndSummarizeTrees(Entry<T>[] degree) {
        Entry<T> element = minimum;
        Entry<T> next;
        do {
            if (element == element.getNext()) {
                next = null;
            } else {
                next = element.getNext();
            }
            cutConnection(element);
            selfLink(element);

            int currentDegree = element.getDeg();
            while (degree[currentDegree] != null) {
                if (element.getKey() > degree[currentDegree].getKey()) {
                    swap(element, degree[currentDegree]);
                }
                becomesChildOfEntry(degree[currentDegree], element);

                element.setDeg(element.getDeg() + 1);
                degree[currentDegree].setMarked(false);
                degree[currentDegree] = null;
                currentDegree++;
            }
            degree[currentDegree] = element;
            element = next;
        } while (element != null);
    }

    private void updateMinimum(int degreeSize, Entry<T>[] degree) {
        minimum = null;
        for (int i = 0; i < degreeSize; i++) {
            if (degree[i] != null) {
                if (minimum == null) {
                    minimum = degree[i];
                    selfLink(degree[i]);
                } else {
                    degree[i].setNext(minimum);
                    degree[i].setPrevious(minimum.getPrevious());
                    minimum.getPrevious().setNext(degree[i]);
                    minimum.setPrevious(degree[i]);
                    if (degree[i].getKey() < minimum.getKey()) {
                        minimum = degree[i];
                    }
                }
            }
        }
    }

    public void decreaseKey(Entry<T> element, Double key) {
        if (element != null && element.getKey() > key) {
            element.setKey(key);
            if (element.getParent() == null) {
                if (element.getKey() < minimum.getKey()) {
                    minimum = element;
                }
            } else if (key < element.getParent().getKey()) {
                cutHeap(element);
            }
        }
    }

    public void insertIfNotExist(Entry<T> element) {
        if (minimum != element && FiboHelper.selfLinked(element)) {
            insert(element);
        }
    }

    private void cutHeap(Entry<T> element) {
        if (element == minimum) {
            LOGGER.warning("element==minimum in cutHeap");
        }
        Entry<T> parent = element.getParent();
        if (parent != null) {
            parent.setDeg(parent.getDeg() - 1);
        }
        if (element.getNext() == element) {
            parent.setChild(null);
        } else {
            cutConnection(element);
            if (parent.getChild() == element) {
                parent.setChild(element.getNext());
            }
        }

        listConcat(element);
        LOGGER.info(this.toString());
        element.setParent(null);

        if (parent.getParent() != null) {
            if (parent.isMarked()) {
                cutHeap(parent);
                parent.setMarked(false);
            } else {
                parent.setMarked(true);
            }
        }
    }

    public boolean isEmpty() {
        return getSize() == 0;
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
                "\nminimum=\n" + minimum +
                "\n, size=" + size +
                "\n, elem=\n" + PrintHelper.printFibonacciHeap(this, minimum) + '}';
    }
}
