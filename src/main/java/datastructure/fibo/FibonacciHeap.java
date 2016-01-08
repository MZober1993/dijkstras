package datastructure.fibo;

import datastructure.Element;
import datastructure.PrintHelper;
import util.MathHelper;

import static com.google.common.base.Preconditions.checkNotNull;
import static datastructure.fibo.FiboHelper.*;

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
            selfLink(element);
            min = element;
        } else {
            addElementLeftToEntry(element, min);

            if (min.getKey() > element.getKey()) {
                min = element;
            }
        }
    }

    public Entry<T> extractMin() {
        Entry<T> entry = min;
        if (entry != null) {
            moveChildsToRootList(entry);

            cutConnection(entry);
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

                cutConnection(element);
                selfLink(element);
                int currentDegree = element.getDeg();
                while (degree[currentDegree] != null) {
                    if (element.getKey() > degree[currentDegree].getKey()) {
                        //swap
                        Entry<T> tmp = degree[currentDegree];
                        degree[currentDegree] = element;
                        element = tmp;
                    }
                    degree[currentDegree].setMarked(false);
                    becomesChildOfEntry(degree[currentDegree], element);
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

    public Entry<T> getMin() {
        return min;
    }

    public Integer getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return "FibonacciHeap{" +
                "\nmin=\n" + min +
                "\n, size=" + size +
                "\n, elem=\n" + PrintHelper.printFibonacciHeap(this, min) + '}';
    }
}
