package datastructure.fibo;

import datastructure.Element;

import java.util.ArrayList;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 07.01.16 - 21:51
 */
public class FHeap<T extends Element> {
    // For Fibonacci heap
    private Entry<T> min;
    private int count;
    private int maxRank;

    public FHeap() {
        min = null;
        maxRank = 0;
    }

    public FHeap(Entry<T> newMin) {
        min = newMin;
        min.setParent(null);
        min.setChild(null);
        min.setPrevious(null);
        min.setNext(null);
        min.setDeg(0);
        maxRank = 0;
    }

    // method for Fibonacci heap
    public boolean isEmpty() {
        return (min == null) || count == 0;
    }

    public boolean insert(Entry<T> node) {
        boolean success = listConcat(node);
        if (success) {
            count++;
        }
        return success;
    }

    private boolean listConcat(Entry<T> node) {
        if (node == null) {
            return false;
        }
        if (min == null) {
            // Have no root
            min = node;
            min.setParent(null);
        } else {
            min.addSibling(node);
            if (min.getKey() > node.getKey()) {
                min = node;
            }
        }
        return true;
    }

    public void decreaseKey(Entry<T> vertex, Double delta) {
        vertex.setKey(delta);
        // check position of vertex
        Entry<T> parent = vertex.getParent();
        if (parent == null) {
            // right position check new min root
            if (vertex.getKey() < min.getKey())
                min = vertex;
            return;
        } else if (parent.getKey() < delta)
            // still right position
            return;

        Entry<T> node = vertex;
        while (true) {
            node.remove();
            insert(node);
            if (parent.getParent() == null)
                // parent is root
                break;
            else if (!parent.isMarked()) {
                // parent is not mark
                parent.setMarked(true);
                break;
            } else {
                // parent is mark
                node = parent;
                parent = parent.getParent();
            }
        }
    }

    public Entry<T> getMin() {
        return min;
    }

    public Entry<T> extractMin() {
        if (min != null)
            count--;
        else
            return null;
        // Make children of min new roots
        if (min.getChild() != null) {
            Entry<T> temp = min.getChild();
            while (temp != null) {
                temp.remove();
                this.insert(temp);
                temp = min.getChild();
            }
        }

        Entry<T> entry = min;
        // Case: delete last node
        if (min.getNext() == min) {
            this.count = 0;
            maxRank = 0;
            min.remove();
            min = null;
            return entry;
        }

        // Merge root with same rank
        ArrayList<Entry<T>> rankRoots = new ArrayList<>(maxRank + 1);
        for (int i = 0; i < maxRank + 1; i++)
            rankRoots.add(null);
        maxRank = 0;
        Entry<T> curEntry = min.getNext();
        int curRank;
        do {
            curRank = curEntry.getDeg();
            Entry<T> cur = curEntry;
            curEntry = curEntry.getNext();
            while (rankRoots.get(curRank) != null) {
                // have root with same rank
                Entry<T> add = rankRoots.get(curRank);
                if (cur.getKey() > add.getKey()) {
                    Entry<T> temp = cur;
                    cur = add;
                    add = temp;
                }
                add.removeSibling();
                cur.addChild(add);
                rankRoots.set(curRank, null);
                curRank++;
                if (curRank >= rankRoots.size()) {
                    rankRoots.add(null);
                }
            }
            rankRoots.set(curRank, cur);
        } while (curEntry != min);

        // Remove min and find new min
        min.remove();
        min = null;
        for (int i = 0; i < rankRoots.size(); i++) {
            Entry<T> temp = rankRoots.get(i);
            if (temp != null) {
                temp.setPrevious(temp);
                temp.setNext(temp);
                insert(temp);
                if (i > maxRank) {
                    maxRank = i;
                }
            }
        }
        return entry;
    }

}
