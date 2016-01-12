package datastructure.fibo;

import datastructure.Element;
import datastructure.PrintHelper;
import util.MathHelper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.12.15 - 17:03
 */
public final class FibonacciHeap<T extends Element> {

    private Entry<T> min = null;
    private Integer size = 0;

    public Entry<T> insert(Entry<T> entry) {
        listConcat(entry);
        size++;
        return entry;
    }

    public void listConcat(Entry<T> element) {
        if (min == null) {
            element.setNext(element);
            element.setPrevious(element);
            min = element;
        } else {
            Entry<T> endHeap = min.getPrevious();
            min.setPrevious(element);
            element.setPrevious(endHeap);
            endHeap.setNext(element);
            element.setNext(min);

            if (min.getKey() > element.getKey()) {
                min = element;
            }
        }
    }

    public Entry<T> extractMin() {
        Entry<T> entry = min;
        if (entry != null) {
            moveChildsToRootList(entry);

            entry.getNext().setPrevious(entry.getPrevious());
            entry.getPrevious().setNext(entry.getNext());
            if (entry == entry.getNext()) {
                min = null;
            } else {
                min = entry.getNext();
                consolidate();
            }
            size--;

            return entry;
        }
        return null;
    }

    private void consolidate() {
        int degreeSize = MathHelper.log2(size) + 1;
        Entry<T>[] degree = new Entry[degreeSize];
        for (int i = 0; i < degreeSize; i++) {
            degree[i] = null;
        }

        cutAndSummarize(degree);

        updateMin(degreeSize, degree);
    }

    private void updateMin(int degreeSize, Entry<T>[] degree) {
        min = null;
        for (int i = 0; i < degreeSize; i++) {
            if (degree[i] != null) {
                listConcat(degree[i]);
            }
        }
    }

    private void cutAndSummarize(Entry<T>[] degree) {
        if (min != null) {
            Entry<T> element = min;
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
                    if (element.getKey() > degree[currentDegree].getKey()) {
                        //swap
                        Entry<T> tmp = degree[currentDegree];
                        degree[currentDegree] = element;
                        element = tmp;
                    }
                    degree[currentDegree].setMarked(false);
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
                    degree[currentDegree] = null;
                    currentDegree++;
                }

                degree[currentDegree] = element;
                element = next;
            } while (element != null);
        }
    }

    public void decreaseKey(Entry<T> element, Double key) {
        if (element != null && element.getKey() > key) {
            element.setKey(key);
            Entry<T> parent = element.getParent();
            if (parent != null && element.getKey() < parent.getKey()) {
                cut(element, parent);
                cascadingCut(parent);
            } else if (element.getKey() < min.getKey()) {
                min = element;
            }
        }
    }

    private static <T extends Element> void removeChildFromChildListOfParent(Entry<T> child
            , Entry<T> parent) {
        checkNotNull(child);
        checkNotNull(parent);
        if (child.getNext() == child) {
            parent.setChild(null);
        } else {
            child.getNext().setPrevious(child.getPrevious());
            child.getPrevious().setNext(child.getNext());
            if (parent.getChild() == child) {
                parent.setChild(child.getNext());
            }
        }
        parent.setDeg(parent.getDeg() - 1);
    }

    private void cut(Entry<T> child, Entry<T> parent) {
        removeChildFromChildListOfParent(child, parent);
        listConcat(child);
        child.setParent(null);
        child.setMarked(false);
    }

    private void cascadingCut(Entry<T> k) {
        checkNotNull(k);
        Entry<T> parent = k.getParent();
        if (parent != null) {
            if (!k.isMarked()) {
                k.setMarked(true);
            } else {
                cut(k, parent);
                cascadingCut(parent);
            }
        }
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    private static <T extends Element> void moveChildsToRootList(Entry<T> entry) {
        for (int i = 0; i < entry.getDeg(); i++) {
            Entry<T> child = entry.getChild();
            if (child != null) {
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
        }
        entry.setDeg(0);
    }

    public Entry<T> getMin() {
        return min;
    }

    public Integer getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "FibonacciHeap{" +
                "\nmin=\n" + min +
                "\n, size=" + size +
                "\n, elem=\n" + PrintHelper.printFibonacciHeap(this, min) + '}';
    }
}
