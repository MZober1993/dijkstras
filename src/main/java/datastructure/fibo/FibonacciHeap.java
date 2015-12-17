package datastructure.fibo; /**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 * On 14.12.15 - 18:00
 */

import util.LoggingHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import static com.google.common.base.Preconditions.checkNotNull;
import static util.LoggingHelper.logWarningIfNull;

public final class FibonacciHeap<T> {

    /* Pointer to the minimum element in the heap. */
    private Entry<T> minimum = null;

    /* Cached size of the heap, so we don't have to recompute this explicitly. */
    private int size = 0;

    private static final Logger LOGGER = LoggingHelper.buildLoggerWithStandardOutputConfig(FibonacciHeap.class);

    /**
     * Inserts the specified element into the Fibonacci heap with the specified
     * priority.  Its priority must be a valid double, so you cannot set the
     * priority to NaN.
     *
     * @param value    The value to insert.
     * @param priority Its priority, which must be valid.
     * @return An Entry representing that element in the tree.
     */
    public Entry<T> enqueue(T value, double priority) {
        checkPriority(priority);

        /* Create the entry object, which is a circularly-linked list of length
         * one.
         */
        Entry<T> result = new Entry<>(value, priority);
        if (size == 0) {
            minimum = result;
        } else {
        /* Merge this singleton list with the tree list. */
            minimum = mergeLists(minimum, result);
        }

        ++size;

        return result;
    }

    /**
     * Returns an Entry object corresponding to the minimum element of the
     * Fibonacci heap, throwing a NoSuchElementException if the heap is
     * empty.
     *
     * @return The smallest element of the heap.
     * @throws NoSuchElementException If the heap is empty.
     */
    public Entry<T> min() {
        if (isEmpty())
            throw new NoSuchElementException("Heap is empty.");
        return minimum;
    }

    public boolean isEmpty() {
        return minimum == null;
    }

    public int size() {
        return size;
    }

    /**
     * Given two Fibonacci heaps, returns a new Fibonacci heap that contains
     * all of the elements of the two heaps.  Each of the input heaps is
     * destructively modified by having all its elements removed.  You can
     * continue to use those heaps, but be aware that they will be empty
     * after this call completes.
     *
     * @param one The first Fibonacci heap to merge.
     * @param two The second Fibonacci heap to merge.
     * @return A new FibonacciHeap containing all of the elements of both
     * heaps.
     */
    public static <T> FibonacciHeap<T> merge(FibonacciHeap<T> one, FibonacciHeap<T> two) {
        FibonacciHeap<T> result = new FibonacciHeap<>();

        /* computes the min of the two lists, so we can store the result in
         * the minimum field of the new heap.
         */
        result.minimum = mergeLists(one.minimum, two.minimum);

        result.size = one.size + two.size;

        one.size = two.size = 0;
        one.minimum = null;
        two.minimum = null;

        return result;
    }

    /**
     * Dequeues and returns the minimum element of the Fibonacci heap.  If the
     * heap is empty, this throws a NoSuchElementException.
     *
     * @return The smallest element of the Fibonacci heap.
     * @throws NoSuchElementException If the heap is empty.
     */
    public Entry<T> dequeueMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }

        --size;

        /* Grab the minimum element so we know what to return. */
        Entry<T> minElem = minimum;

        /* Now, we need to get rid of this element from the list of roots.  There
         * are two cases to consider.  First, if this is the only element in the
         * list of roots, we set the list of roots to be null by clearing minimum.
         * Otherwise, if it's not null, then we write the elements next to the
         * min element around the min element to remove it, then arbitrarily
         * reassign the min.
         */
        skipEntry(minimum);
        if (minimum.next == minimum) { // Case one
            minimum = null;
        } else { // Case two
            minimum = minimum.next; // Arbitrary element of the root list.
        }
        /*if (consolidate(minElem)) {
            LOGGER.info("Completely Inspection");
        } else {
            LOGGER.info("No Completely Inspection");
        }*/
        myconsolidate(minElem);

        return minElem;
    }

    /**
     * @param minElem - temporary minimum element
     * @return completely inspection of all entries?
     */
    private boolean consolidate(Entry<T> minElem) {
        cutConnection(minElem);

        /* Next, splice the children of the root node into the topmost list,
         * then set minimum to point somewhere in that list.
         */
        minimum = mergeLists(minimum, minElem.child);

        /* If there are no entries left, we're done. */
        if (minimum == null) return false;

        /* Next, we need to coalsce all of the roots so that there is only one
         * tree of each degree.  To track trees of each size, we allocate an
         * ArrayList where the entry at position i is either null or the
         * unique tree of degree i.
         */
        List<Entry<T>> treeTable = new ArrayList<>();

        /* We need to traverse the entire list, but since we're going to be
         * messing around with it we have to be careful not to break our
         * traversal order mid-stream.  One major challenge is how to detect
         * whether we're visiting the same node twice.  To do this, we'll
         * spent a bit of overhead adding all of the nodes to a list, and
         * then will visit each element of this list in order.
         */
        List<Entry<T>> toVisit = new ArrayList<>();

        /* To add everything, we'll iterate across the elements until we
         * find the first element twice.  We check this by looping while the
         * list is empty or while the current element isn't the first element
         * of that list.
         */
        for (Entry<T> curr = minimum; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.next)
            toVisit.add(curr);

        /* Traverse this list and perform the appropriate unioning steps. */
        for (Entry<T> curr : toVisit) {
            /* Keep merging until a match arises. */
            while (true) {
                /* Ensure that the list is long enough to hold an element of this
                 * degree.
                 */
                while (curr.deg >= treeTable.size())
                    treeTable.add(null);

                /* If nothing's here, we're can record that this tree has this size
                 * and are done processing.
                 */
                if (treeTable.get(curr.deg) == null) {
                    treeTable.set(curr.deg, curr);
                    break;
                }

                /* Otherwise, merge with what's there. */
                Entry<T> other = treeTable.get(curr.deg);
                treeTable.set(curr.deg, null); // Clear the slot

                /* Determine which of the two trees has the smaller root, storing
                 * the two tree accordingly.
                 */
                Entry<T> min = (other.priority < curr.priority) ? other : curr;
                Entry<T> max = (other.priority < curr.priority) ? curr : other;

                /* Break max out of the root list, then merge it into min's child
                 * list.
                 */
                skipEntry(max);

                /* Make it a singleton so that we can merge it. */
                max.next = max.previous = max;
                min.child = mergeLists(min.child, max);

                /* Reparent max appropriately. */
                max.parent = min;

                /* Clear max's mark, since it can now lose another child. */
                max.isMarked = false;

                /* Increase min's degree; it now has another child. */
                ++min.deg;

                /* Continue merging this tree. */
                curr = min;
            }

            /* Update the global min based on this node.  Note that we compare
             * for <= instead of < here.  That's because if we just did a
             * reparent operation that merged two different trees of equal
             * priority, we need to make sure that the min pointer points to
             * the root-level one.
             */
            if (curr.priority <= minimum.priority) minimum = curr;
        }
        return true;
    }

    private void skipEntry(Entry<T> entry) {
        entry.next.previous = entry.previous;
        entry.previous.next = entry.next;
    }

    private void myconsolidate(Entry<T> minElem) {
        cutConnection(minElem);

        Entry<T>[] a = new Entry[size];
        while (minimum != null) {
            Entry<T> x = minimum;
            int d = minimum.deg;
            if (x.next == x) {
                minimum = null;
            } else {
                skipEntry(minimum);
                minimum = x.next;
                connectEntryToItSelf(x);
            }
            while (a[d] != null) {
                Entry<T> y = a[d];
                if (x.priority > y.priority) {
                    swap(x, y);
                }
                mergeLists(y, x);
                a[d] = null;
                d++;
            }
            a[d] = x;
        }

        buildHeap(a);
    }

    private void buildHeap(Entry<T>[] a) {
        minimum = null;
        for (int i = 0; i < size; i++) {
            if (a[i] != null) {
                if (minimum == null) {
                    minimum = a[i];
                } else {
                    cyclicListConcat(a[i]);
                }
                if (a[i].priority < minimum.priority) {
                    minimum = a[i];
                }
            }
        }
    }

    private void swap(Entry<T> x, Entry<T> y) {
        Entry<T> tmp = x;
        x = y;
        y = tmp;
    }

    private void connectEntryToItSelf(Entry<T> entry) {
        entry.next = entry;
        entry.previous = entry;
    }

    private void cutConnection(Entry<T> minElem) {
        if (minElem.child != null) {
            Entry<?> curr = minElem.child;
            do {
                curr.parent = null;
                curr = curr.next;
            } while (curr != minElem.child);
        }
    }

    /**
     * Decreases the key of the specified element to the new priority.  If the
     * new priority is greater than the old priority, this function throws an
     * IllegalArgumentException.  The new priority must be a finite double,
     * so you cannot set the priority to be NaN, or +/- infinity.  Doing
     * so also throws an IllegalArgumentException.
     * <p>
     * It is assumed that the entry belongs in this heap.  For efficiency
     * reasons, this is not checked at runtime.
     *
     * @param entry       The element whose priority should be decreased.
     * @param newPriority The new priority to associate with this entry.
     * @throws IllegalArgumentException If the new priority exceeds the old
     *                                  priority, or if the argument is not a finite double.
     */
    public void decreaseKey(Entry<T> entry, double newPriority) {
        checkPriority(newPriority);
        if (newPriority > entry.priority)
            throw new IllegalArgumentException("New priority: " + newPriority + ",exceeds old:" + entry.priority);

        /* Forward this to a helper function. */
        decreaseKeyUnchecked(entry, newPriority);
    }

    /**
     * Deletes this Entry from the Fibonacci heap that contains it.
     * <p>
     * It is assumed that the entry belongs in this heap.  For efficiency
     * reasons, this is not checked at runtime.
     *
     * @param entry The entry to delete.
     */
    public void delete(Entry<T> entry) {
        /* Use decreaseKey to drop the entry's key to -infinity.  This will
         * guarantee that the node is cut and set to the global minimum.
         */
        decreaseKeyUnchecked(entry, Double.NEGATIVE_INFINITY);

        /* Call dequeueMin to remove it. */
        dequeueMin();
    }

    /**
     * Utility function which, given a user-specified priority, checks whether
     * it's a valid double and throws an IllegalArgumentException otherwise.
     *
     * @param priority The user's specified priority.
     * @throws IllegalArgumentException If it is not valid.
     */
    private void checkPriority(double priority) {
        if (Double.isNaN(priority))
            throw new IllegalArgumentException(priority + " is invalid.");
    }

    /**
     * Utility function which, given two pointers into disjoint circularly-
     * linked lists, merges the two lists together into one circularly-linked
     * list in O(1) time.  Because the lists may be empty, the return value
     * is the only pointer that's guaranteed to be to an element of the
     * resulting list.
     * <p>
     * This function assumes that one and two are the minimum elements of the
     * lists they are in, and returns a pointer to whichever is smaller.  If
     * this condition does not hold, the return value is some arbitrary pointer
     * into the doubly-linked list.
     *
     * @param one A pointer into one of the two linked lists.
     * @param two A pointer into the other of the two linked lists.
     * @return A pointer to the smallest element of the resulting list.
     */
    private static <T> Entry<T> mergeLists(Entry<T> one, Entry<T> two) {
        /* There are four cases depending on whether the lists are null or not.
         * We consider each separately.
         */
        if (one == null && two == null) { // Both null, resulting list is null.
            return null;
        } else if (one != null && two == null) { // Two is null, result is one.
            return one;
        } else if (one == null) { // One is null, result is two.
            return two;
        } else {
            Entry<T> oneNext = one.next; // Cache this since we're about to overwrite it.
            one.next = two.next;
            one.next.previous = one;
            two.next = oneNext;
            two.next.previous = two;

            /* Return a pointer to whichever's smaller. */
            return one.priority < two.priority ? one : two;
        }
    }

    private void cyclicListConcat(Entry<T> y) {Entry<T> x = minimum;
        checkNotNull(y);
        if (x == null) {
            x = y;
        } else {
            x.next = y;
            y.previous = x;
            x.next.previous = y.previous;
            y.previous.next = x.next;
        }
    }

    /**
     * Decreases the key of a node in the tree without doing any checking to ensure
     * that the new priority is valid.
     *
     * @param entry    The node whose key should be decreased.
     * @param priority The node's new priority.
     */
    private void decreaseKeyUnchecked(Entry<T> entry, double priority) {
        /* First, change the node's priority. */
        entry.priority = priority;

        /* If the node no longer has a higher priority than its parent, cut it.
         * Note that this also means that if we try to run a delete operation
         * that decreases the key to -infinity, it's guaranteed to cut the node
         * from its parent.
         */
        logWarningIfNull("minimum", minimum, LOGGER);
        logWarningIfNull("entry", entry, LOGGER);
        if (entry.parent != null && entry.priority <= entry.parent.priority) {
            cutNode(entry);
        }

        /* If our new value is the new min, mark it as such.  Note that if we
         * ended up decreasing the key in a way that ties the current minimum
         * priority, this will change the min accordingly.
         */
        logWarningIfNull("minimum", minimum, LOGGER);
        logWarningIfNull("entry", entry, LOGGER);
        if (entry.priority <= minimum.priority)
            minimum = entry;
    }

    /**
     * Cuts a node from its parent.  If the parent was already marked, recursively
     * cuts that node from its parent as well.
     *
     * @param entry The node to cut from its parent.
     */
    private void cutNode(Entry<T> entry) {
        /* Begin by clearing the node's mark, since we just cut it. */
        entry.isMarked = false;

        /* Base case: If the node has no parent, we're done. */
        if (entry.parent == null) return;

        /* Rewire the node's siblings around it, if it has any siblings. */
        if (entry.next != entry) { // Has siblings
            skipEntry(entry);
        }

        /* If the node is the one identified by its parent as its child,
         * we need to rewrite that pointer to point to some arbitrary other
         * child.
         */
        if (entry.parent.child == entry) {
            /* If there are any other children, pick one of them arbitrarily. */
            if (entry.next != entry) {
                entry.parent.child = entry.next;
            }
            /* Otherwise, there aren't any children left and we should clear the
             * pointer and drop the node's degree.
             */
            else {
                entry.parent.child = null;
            }
        }

        /* Decrease the degree of the parent, since it just lost a child. */
        --entry.parent.deg;

        /* Splice this tree into the root list by converting it to a singleton
         * and invoking the merge subroutine.
         */
        entry.previous = entry.next = entry;
        minimum = mergeLists(minimum, entry);
        logWarningIfNull("entry", entry, LOGGER);
        logWarningIfNull("minimum", minimum, LOGGER);

        /* Mark the parent and recursively cut it if it's already been
         * marked.
         */
        if (entry.parent.isMarked)
            cutNode(entry.parent);
        else
            entry.parent.isMarked = true;

        /* Clear the relocated node's parent; it's now a root. */
        entry.parent = null;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("FibonacciHeap{" + "minimum=").append(minimum).append(", size=").append(size).append("[\n");
        Entry<T> start = minimum;
        Entry<T> tmp = minimum.next;
        while (tmp != start) {
            builder.append(", ").append(tmp.toString()).append("\n");
            tmp = tmp.next;
        }
        builder.append(", ").append(start.toString()).append("\n");
        builder.append("]\n}");
        return builder.toString();
    }

    public static final class Entry<T> {
        private int deg = 0;       // Number of children
        private boolean isMarked = false; // Whether this node is marked

        private Entry<T> next;   // Next and previous elements in the list
        private Entry<T> previous;

        private Entry<T> parent; // Parent in the tree, if any.

        private Entry<T> child;  // Child node, if any.

        private T elem;     // Element being stored here
        private double priority; // Its priority

        public T getValue() {
            return elem;
        }

        public void setValue(T value) {
            elem = value;
        }

        public double getPriority() {
            return priority;
        }

        public Entry(T elem, double priority) {
            next = this;
            previous = this;
            this.elem = elem;
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "priority=" + priority +
                    ", elem=" + elem +
                    ", isMarked=" + isMarked +
                    ", deg=" + deg +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry<?> entry = (Entry<?>) o;

            if (deg != entry.deg) return false;
            return isMarked == entry.isMarked && !(elem != null ? !elem.equals(entry.elem) : entry.elem != null);
        }
    }
}
