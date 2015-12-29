package datastructure.fibo;

import datastructure.fibo.Entry;

import java.util.stream.Stream;

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
     * @return minimum
     */
    public static <T> Entry<T> cyclicListConcat(Entry<T> x, Entry<T> y) {
        if (checkEntriesWithCorrectlyOverride(x, y)) {
            Entry<T> tmpNext = x.getNext();

            x.setNext(y.getNext());
            linkNextNeighborToEntry(x);

            y.setNext(tmpNext);
            linkNextNeighborToEntry(y);

            return x.getPriority() < y.getPriority() ? x : y;
        } else {
            return x;
        }
    }

    public static <T> void swap(Entry<T> entryOne, Entry<T> entryTwo) {
        Entry<T> tmp = entryOne;
        entryOne = entryTwo;
        entryTwo = tmp;
    }

    public static <T> void cutConnection(Entry<T> entry) {
        entry.getNext().setPrevious(entry.getPrevious());
        entry.getPrevious().setNext(entry.getNext());
    }

    public static <T> void selfLink(Entry<T> entry){
        entry.setNext(entry);
        entry.setPrevious(entry);
    }

    public static <T> void linkNeighborsToEntry(Entry<T> entry){
        linkNextNeighborToEntry(entry);
        linkPreviousNeighborToEntry(entry);
    }

    public static <T> void linkNextNeighborToEntry(Entry<T> entry){
        entry.getNext().setPrevious(entry);
    }

    public static <T> void linkPreviousNeighborToEntry(Entry<T> entry){
        entry.getPrevious().setNext(entry);
    }

    public static <T> boolean checkEntriesWithCorrectlyOverride(Entry<T> x, Entry<T> y) {
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

    public static <T> boolean checkEntries(Entry<T> x, Entry<T> y) {
        if (x == null && y == null) {
            return false;
        } else if (y == null) {
            return false;
        } else if (x == null) {
            return false;
        }
        return true;
    }

    public static <T> String rootListToString(StringBuilder builder, Entry<T> minimum) {
        addMemberToStream(Stream.builder(), minimum).build().map(x -> x.toString())
                .forEach(x -> builder.append(x));
        return builder.toString();
    }

    public static <T> Stream.Builder<Entry<T>> addMemberToStream(Stream.Builder<Entry<T>> builder
            , Entry<T> entry) {
        builder.add(entry);
        for (Entry<T> current = entry.getNext(); current != entry; current.getNext()) {
            builder.add(current);
        }
        return builder;
    }

    static <T> void becomesChildOfEntry(Entry<T> child, Entry<T> entry) {
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
}
