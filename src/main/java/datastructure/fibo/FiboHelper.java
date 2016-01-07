package datastructure.fibo;

import datastructure.Element;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 29.12.15 - 10:15
 */
public final class FiboHelper {

    public static <T extends Element> void cutConnection(Entry<T> entry) {
        entry.getNext().setPrevious(entry.getPrevious());
        entry.getPrevious().setNext(entry.getNext());
    }

    public static <T extends Element> void selfLink(Entry<T> entry) {
        entry.setNext(entry);
        entry.setPrevious(entry);
    }

    public static <T extends Element> void linkNeighborsToEntry(Entry<T> entry) {
        entry.getNext().setPrevious(entry);
        entry.getPrevious().setNext(entry);
    }

    public static <T extends Element> void becomesChildOfEntry(Entry<T> child, Entry<T> entry) {
        if (entry.getChild() == null) {
            entry.setChild(child);
            child.setParent(entry);
        } else {
            child.setParent(entry);
            child.setNext(entry.getChild());
            child.setPrevious(entry.getChild().getPrevious());
            linkNeighborsToEntry(child);
        }
    }

    public static <T extends Element> void addElementLeftToEntry(Entry<T> element, Entry<T> entry) {
        Entry<T> endHeap = entry.getPrevious();
        entry.setPrevious(element);
        element.setPrevious(endHeap);
        endHeap.setNext(element);
        element.setNext(entry);
    }

    static <T extends Element> void moveChildsToRootList(Entry<T> entry) {
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
    }

    public static <T extends Element> void removeChildFromChildListOfParent(Entry<T> child
            , Entry<T> parent) {
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
    }
}
