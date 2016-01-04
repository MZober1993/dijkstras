package datastructure.fibo;

import datastructure.Element;
import datastructure.PrintHelper;
import util.MathHelper;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static datastructure.fibo.FiboHelper.*;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.12.15 - 17:03
 */
public final class FibonacciHeap<T extends Element> {

    private Entry<T> minimum = null;
    private Integer size = 0;
    private List<Entry<T>> elements = new ArrayList<>();

    public Entry<T> insert(T value, Double priority) {
        return insert(new Entry<>(value, priority));
    }

    public Entry<T> insert(Entry<T> entry) {
        listConcat(entry);
        elements.add(entry);
        size++;
        return entry;
    }

    public void listConcat(Entry<T> element) {
        if (minimum == null) {
            selfLink(element);
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

    public Entry<T> extractMin() {
        //TODO: fix this nullPointerException
        Entry<T> entry = minimum;
        if (entry != null) {
            //add all children of minimum to the parent list
            for (int i = 0; i < entry.getDeg(); i++) {
                Entry<T> child = entry.getChild();
                if (child != null) {
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
            }
            entry.setDeg(0);

            //remove the minimum TODO: check here the child-connection
            if (entry == entry.getNext()) {
                System.out.println("entry selfpointed:\n" + entry);
                System.out.println("child of entry:\n" + entry.getChild());
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
        elements.stream().sorted((entry1, t1) -> entry1.getId() < t1.getId()
                ? -1 : entry1.getId() == t1.getId()
                ? 0 : 1).forEach(System.out::println);
        minimumCheckPrint();
        return null;
    }

    private void consolidate() {
        int degreeSize = MathHelper.log2(size) + 1;
        Entry<T>[] degree = new Entry[degreeSize];
        for (int i = 0; i < degreeSize; i++) {
            degree[i] = null;
        }

        if (minimum != null) {
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

        minimum = null;
        for (int i = 0; i < degreeSize; i++) {
            if (degree[i] != null) {
                listConcat(degree[i]);
            }
        }
    }

    public void decreaseKey(Entry<T> element, Double key) {
        if (element != null && element.getKey() > key) {
            element.setKey(key);
            Entry<T> parent = element.getParent();
            if (parent != null && element.getKey() < parent.getKey()) {
                cut(element, parent);
                cascadingCut(parent);
            } else if (element.getKey() < minimum.getKey()) {
                minimum = element;
            }
        }
    }

    private void minimumCheckPrint() {
        if (minimum == null) {
            System.out.println("minimum is null and size is:" + size);
        }
    }

    private void cut(Entry<T> child, Entry<T> parent) {
        checkNotNull(child);
        checkNotNull(parent);
        if (child.getNext() == child) {
            parent.setChild(null);
        } else {
            cutConnection(child);
            if (parent.getChild() == child) {
                parent.setChild(child.getNext());
            }
        }
        parent.setDeg(parent.getDeg() - 1);
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
