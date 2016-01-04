package datastructure.fibo;

import datastructure.Element;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 29.12.15 - 10:15
 */
public final class FiboHelper {

    /**
     * Concat the root-list of y next to x's root-list:
     * -x<=>x.next- & -y.previous<=>y- ==>
     * -x<=>y- & -y.previous<=>x.next-
     *
     * @param x left-heap
     * @param y right-heap
     */
    public static <T extends Element> void cyclicListConcat(Entry<T> x, Entry<T> y) {
        if (checkEntriesWithCorrectlyOverride(x, y)) {
            Entry<T> tmpNext = x.getNext();

            x.setNext(y.getNext());
            linkNextNeighborToEntry(x);

            y.setNext(tmpNext);
            linkNextNeighborToEntry(y);
        }
    }

    public static <T extends Element> boolean selfLinked(Entry<T> x) {
        return x.getPrevious() == x && x.getNext() == x;
    }

    public static <T extends Element> void swap(Entry<T> entryOne, Entry<T> entryTwo) {
        Entry<T> tmp = entryOne;
        entryOne = entryTwo;
        entryTwo = tmp;
    }

    public static <T extends Element> void cutConnection(Entry<T> entry) {
        entry.getNext().setPrevious(entry.getPrevious());
        entry.getPrevious().setNext(entry.getNext());
    }

    public static <T extends Element> void selfLink(Entry<T> entry) {
        entry.setNext(entry);
        entry.setPrevious(entry);
    }

    public static <T extends Element> void linkNeighborsToEntry(Entry<T> entry) {
        linkNextNeighborToEntry(entry);
        linkPreviousNeighborToEntry(entry);
    }

    public static <T extends Element> void linkNextNeighborToEntry(Entry<T> entry) {
        entry.getNext().setPrevious(entry);
    }

    public static <T extends Element> void linkPreviousNeighborToEntry(Entry<T> entry) {
        entry.getPrevious().setNext(entry);
    }

    public static <T extends Element> boolean checkEntriesWithCorrectlyOverride(Entry<T> x, Entry<T> y) {
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

    public static <T extends Element> boolean checkEntries(Entry<T> x, Entry<T> y) {
        if (x == null && y == null) {
            return false;
        } else if (y == null) {
            return false;
        } else if (x == null) {
            return false;
        }
        return true;
    }

    static <T extends Element> void becomesChildOfEntry(Entry<T> child, Entry<T> entry) {
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

    /*public static <T extends Element> void removeChildFromChildListOfParent(Node<T> child, Node<T> parent) {
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
    }*/
}
