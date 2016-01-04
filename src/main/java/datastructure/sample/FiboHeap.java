package datastructure.sample; /**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 * On 14.12.15 - 18:00
 */

import datastructure.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public final class FiboHeap<T extends Element> {

    public static final class Node<T extends Element> implements Element {
        private int deg = 0;       // Number of children
        private boolean isMarked = false; // Whether this node is marked

        private Node<T> next;   // Next and previous elements in the list
        private Node<T> previous;

        private Node<T> parent; // Parent in the tree, if any.

        private Node<T> child;  // Child node, if any.

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

        public Node(T elem, double priority) {
            next = previous = this;
            this.elem = elem;
            this.priority = priority;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            FiboHeap.Node<T> entry = (FiboHeap.Node<T>) o;

            return elem.getId().equals(entry.elem.getId());
        }

        @Override
        public Integer getId() {
            return elem.getId();
        }

        @Override
        public void setG(Double g) {
            elem.setG(g);
        }

        @Override
        public Double getG() {
            return elem.getG();
        }

        @Override
        public Element getAnchor() {
            return elem.getAnchor();
        }

        @Override
        public void setAnchor(Element element) {
            elem.setAnchor(element);
        }

        @Override
        public boolean isClosed() {
            return elem.isClosed();
        }

        @Override
        public void setClosed(boolean close) {
            elem.setClosed(close);
        }

        @Override
        public String toString() {
            return "Node{" +
                    "deg=" + deg +
                    ", isMarked=" + isMarked +
                    ", l=" + otherToString(previous) +
                    ", this=" + otherToString(this) +
                    ", r=" + otherToString(next) +
                    ", p=" + otherToString(parent) +
                    ", c=" + otherToString(child) +
                    '}';


        }

        private String otherToString(Node<T> other) {
            if (other != null) {
                String prioString = String.valueOf(other.getPriority());
                if (other.getPriority() == Double.MAX_VALUE) {
                    prioString = "MAX";
                }
                return other.getId() + ",(" + prioString + ")";
            } else
                return "null";
        }
    }

    /* Pointer to the minimum element in the heap. */
    private Node<T> minimum = null;

    /* Cached size of the heap, so we don't have to recompute this explicitly. */
    private int size = 0;

    /**
     * Inserts the specified element into the Fibonacci heap with the specified
     * priority.  Its priority must be a valid double, so you cannot set the
     * priority to NaN.
     *
     * @param value The value to insert.
     * @param priority Its priority, which must be valid.
     * @return An Node representing that element in the tree.
     */
    public Node<T> enqueue(T value, double priority) {
        checkPriority(priority);

        /* Create the entry object, which is a circularly-linked list of length
         * one.
         */
        Node<T> result = new Node<>(value, priority);

        /* Merge this singleton list with the tree list. */
        minimum = mergeLists(minimum, result);

        ++size;

        return result;
    }

    /**
     * Inserts the specified element into the Fibonacci heap with the specified
     * priority.  Its priority must be a valid double, so you cannot set the
     * priority to NaN.
     * @param node
     *
     * @return An Node representing that element in the tree.
     */
    public Node<T> enqueue(Node<T> node) {
        checkPriority(node.getPriority());

        /* Merge this singleton list with the tree list. */
        minimum = mergeLists(minimum, node);

        ++size;

        return node;
    }

    /**
     * Returns an Node object corresponding to the minimum element of the
     * Fibonacci heap, throwing a NoSuchElementException if the heap is
     * empty.
     *
     * @return The smallest element of the heap.
     * @throws NoSuchElementException If the heap is empty.
     */
    public Node<T> min() {
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
     * @return A new FiboHeap containing all of the elements of both
     *         heaps.
     */
    public static <T extends Element> FiboHeap<T> merge(FiboHeap<T> one, FiboHeap<T> two) {
        FiboHeap<T> result = new FiboHeap<T>();

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
    public Node<T> dequeueMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty.");
        }

        --size;

        /* Grab the minimum element so we know what to return. */
        Node<T> minElem = minimum;

        /* Now, we need to get rid of this element from the list of roots.  There
         * are two cases to consider.  First, if this is the only element in the
         * list of roots, we set the list of roots to be null by clearing minimum.
         * Otherwise, if it's not null, then we write the elements next to the
         * min element around the min element to remove it, then arbitrarily
         * reassign the min.
         */
        if (minimum.next == minimum) { // Case one
            minimum = null;
        } else { // Case two
            minimum.previous.next = minimum.next;
            minimum.next.previous = minimum.previous;
            minimum = minimum.next; // Arbitrary element of the root list.
        }

        /* Next, clear the parent fields of all of the min element's children,
         * since they're about to become roots.  Because the elements are
         * stored in a circular list, the traversal is a bit complex.
         */
        if (minElem.child != null) {
            /* Keep track of the first visited node. */
            Node<?> curr = minElem.child;
            do {
                curr.parent = null;

                /* Walk to the next node, then stop if this is the node we
                 * started at.
                 */
                curr = curr.next;
            } while (curr != minElem.child);
        }

        /* Next, splice the children of the root node into the topmost list,
         * then set minimum to point somewhere in that list.
         */
        minimum = mergeLists(minimum, minElem.child);

        /* If there are no entries left, we're done. */
        if (minimum == null) return minElem;

        /* Next, we need to coalsce all of the roots so that there is only one
         * tree of each degree.  To track trees of each size, we allocate an
         * ArrayList where the entry at position i is either null or the
         * unique tree of degree i.
         */
        List<Node<T>> treeTable = new ArrayList<Node<T>>();

        /* We need to traverse the entire list, but since we're going to be
         * messing around with it we have to be careful not to break our
         * traversal order mid-stream.  One major challenge is how to detect
         * whether we're visiting the same node twice.  To do this, we'll
         * spent a bit of overhead adding all of the nodes to a list, and
         * then will visit each element of this list in order.
         */
        List<Node<T>> toVisit = new ArrayList<Node<T>>();

        /* To add everything, we'll iterate across the elements until we
         * find the first element twice.  We check this by looping while the
         * list is empty or while the current element isn't the first element
         * of that list.
         */
        for (Node<T> curr = minimum; toVisit.isEmpty() || toVisit.get(0) != curr; curr = curr.next)
            toVisit.add(curr);

        /* Traverse this list and perform the appropriate unioning steps. */
        for (Node<T> curr : toVisit) {
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
                Node<T> other = treeTable.get(curr.deg);
                treeTable.set(curr.deg, null); // Clear the slot

                /* Determine which of the two trees has the smaller root, storing
                 * the two tree accordingly.
                 */
                Node<T> min = (other.priority < curr.priority) ? other : curr;
                Node<T> max = (other.priority < curr.priority) ? curr : other;

                /* Break max out of the root list, then merge it into min's child
                 * list.
                 */
                max.next.previous = max.previous;
                max.previous.next = max.next;

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
        return minElem;
    }

    /**
     * Decreases the key of the specified element to the new priority.  If the
     * new priority is greater than the old priority, this function throws an
     * IllegalArgumentException.  The new priority must be a finite double,
     * so you cannot set the priority to be NaN, or +/- infinity.  Doing
     * so also throws an IllegalArgumentException.
     *
     * It is assumed that the node belongs in this heap.  For efficiency
     * reasons, this is not checked at runtime.
     *
     * @param node The element whose priority should be decreased.
     * @param newPriority The new priority to associate with this node.
     * @throws IllegalArgumentException If the new priority exceeds the old
     *         priority, or if the argument is not a finite double.
     */
    public void decreaseKey(Node<T> node, double newPriority) {
        checkPriority(newPriority);
        if (newPriority > node.priority)
            throw new IllegalArgumentException("New priority exceeds old.");

        /* Forward this to a helper function. */
        decreaseKeyUnchecked(node, newPriority);
    }

    /**
     * Deletes this Node from the Fibonacci heap that contains it.
     *
     * It is assumed that the node belongs in this heap.  For efficiency
     * reasons, this is not checked at runtime.
     *
     * @param node The node to delete.
     */
    public void delete(Node<T> node) {
        /* Use decreaseKey to drop the node's key to -infinity.  This will
         * guarantee that the node is cut and set to the global minimum.
         */
        decreaseKeyUnchecked(node, Double.NEGATIVE_INFINITY);

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
     *
     * This function assumes that one and two are the minimum elements of the
     * lists they are in, and returns a pointer to whichever is smaller.  If
     * this condition does not hold, the return value is some arbitrary pointer
     * into the doubly-linked list.
     *
     * @param one A pointer into one of the two linked lists.
     * @param two A pointer into the other of the two linked lists.
     * @return A pointer to the smallest element of the resulting list.
     */
    private static <T extends Element> Node<T> mergeLists(Node<T> one, Node<T> two) {
        /* There are four cases depending on whether the lists are null or not.
         * We consider each separately.
         */
        if (one == null && two == null) { // Both null, resulting list is null.
            return null;
        } else if (one != null && two == null) { // Two is null, result is one.
            return one;
        } else if (one == null && two != null) { // One is null, result is two.
            return two;
        } else {
            Node<T> oneNext = one.next; // Cache this since we're about to overwrite it.
            one.next = two.next;
            one.next.previous = one;
            two.next = oneNext;
            two.next.previous = two;

            /* Return a pointer to whichever's smaller. */
            return one.priority < two.priority ? one : two;
        }
    }

    /**
     * Decreases the key of a node in the tree without doing any checking to ensure
     * that the new priority is valid.
     *
     * @param node The node whose key should be decreased.
     * @param priority The node's new priority.
     */
    private void decreaseKeyUnchecked(Node<T> node, double priority) {
        /* First, change the node's priority. */
        node.priority = priority;

        /* If the node no longer has a higher priority than its parent, cut it.
         * Note that this also means that if we try to run a delete operation
         * that decreases the key to -infinity, it's guaranteed to cut the node
         * from its parent.
         */
        if (node.parent != null && node.priority <= node.parent.priority)
            cutNode(node);

        /* If our new value is the new min, mark it as such.  Note that if we
         * ended up decreasing the key in a way that ties the current minimum
         * priority, this will change the min accordingly.
         */
        if (node.priority <= minimum.priority)
            minimum = node;
    }

    /**
     * Cuts a node from its parent.  If the parent was already marked, recursively
     * cuts that node from its parent as well.
     *
     * @param node The node to cut from its parent.
     */
    private void cutNode(Node<T> node) {
        /* Begin by clearing the node's mark, since we just cut it. */
        node.isMarked = false;

        /* Base case: If the node has no parent, we're done. */
        if (node.parent == null) return;

        /* Rewire the node's siblings around it, if it has any siblings. */
        if (node.next != node) { // Has siblings
            node.next.previous = node.previous;
            node.previous.next = node.next;
        }

        /* If the node is the one identified by its parent as its child,
         * we need to rewrite that pointer to point to some arbitrary other
         * child.
         */
        if (node.parent.child == node) {
            /* If there are any other children, pick one of them arbitrarily. */
            if (node.next != node) {
                node.parent.child = node.next;
            }
            /* Otherwise, there aren't any children left and we should clear the
             * pointer and drop the node's degree.
             */
            else {
                node.parent.child = null;
            }
        }

        /* Decrease the degree of the parent, since it just lost a child. */
        --node.parent.deg;

        /* Splice this tree into the root list by converting it to a singleton
         * and invoking the merge subroutine.
         */
        node.previous = node.next = node;
        minimum = mergeLists(minimum, node);

        /* Mark the parent and recursively cut it if it's already been
         * marked.
         */
        if (node.parent.isMarked)
            cutNode(node.parent);
        else
            node.parent.isMarked = true;

        /* Clear the relocated node's parent; it's now a root. */
        node.parent = null;
    }
}