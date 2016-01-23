package datastructure.fibo;

import datastructure.PrintHelper;
import util.MathHelper;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.12.15 - 17:03
 */
public final class FibonacciHeap {

    private Integer min = null;
    private Integer size = 0;
    private Integer[] nexts;
    private Integer[] previous;
    private Integer[] childs;
    private Integer[] parents;
    private GraphFibo graph;

    public FibonacciHeap(GraphFibo graph) {
        this.graph = graph;
        int size = graph.getAdjacencyGraph().size();
        nexts = new Integer[size];
        previous = new Integer[size];
        childs = new Integer[size];
        parents = new Integer[size];
    }

    public VertexFibo insert(VertexFibo entry) {
        checkNotNull(entry);
        listConcat(entry.getId());
        size++;
        return entry;
    }

    public void listConcat(Integer element) {
        checkNotNull(element);
        if (min == null) {
            selfLink(element);
            min = element;
        } else {
            Integer endHeap = previous[min];
            previous[min] = element;
            previous[element] = endHeap;
            nexts[endHeap] = element;
            nexts[element] = min;

            if (graph.getV(min).getKey() > graph.getV(element).getKey()) {
                min = element;
            }
        }
    }

    public VertexFibo extractMin() {
        Integer entry = min;
        if (entry != null) {
            moveChildsToRootList(entry);
            cutConnection(entry);
            if (entry.equals(nexts[entry])) {
                min = null;
            } else {
                min = nexts[entry];
                consolidate();
            }
            size--;

            return graph.getV(entry);
        }
        return null;
    }

    private void consolidate() {
        int degreeSize = MathHelper.log2(size) + 1;
        Integer[] degree = new Integer[degreeSize];
        for (int i = 0; i < degreeSize; i++) {
            degree[i] = null;
        }

        cutAndSummarize(degree);

        updateMin(degreeSize, degree);
    }

    private void updateMin(int degreeSize, Integer[] degree) {
        min = null;
        for (int i = 0; i < degreeSize; i++) {
            if (degree[i] != null) {
                listConcat(degree[i]);
            }
        }
    }

    private void cutAndSummarize(Integer[] degree) {
        if (min != null) {
            Integer element = min;
            Integer next;
            do {
                if (element.equals(nexts[element])) {
                    next = null;
                } else {
                    next = nexts[element];
                }

                cutConnection(element);
                selfLink(element);
                int currentDegree = graph.getV(element).getDeg();
                while (degree[currentDegree] != null) {
                    if (graph.getV(element).getKey() > graph.getV(degree[currentDegree]).getKey()) {
                        //swap
                        Integer tmp = degree[currentDegree];
                        degree[currentDegree] = element;
                        element = tmp;
                    }
                    graph.getV(degree[currentDegree]).setMarked(false);
                    if (childs[element] == null) {
                        childs[element] = degree[currentDegree];
                        parents[degree[currentDegree]] = element;
                    } else {
                        parents[degree[currentDegree]] = element;
                        nexts[degree[currentDegree]] = childs[element];
                        previous[degree[currentDegree]] = previous[childs[element]];
                        neighborLink(degree[currentDegree]);
                    }
                    graph.getV(element).incrementDeg();
                    degree[currentDegree] = null;
                    currentDegree++;
                }

                degree[currentDegree] = element;
                element = next;
            } while (element != null);
        }
    }

    public void decreaseKey(VertexFibo element, Double key) {
        if (element != null && element.getKey() > key) {
            element.setKey(key);
            Integer parent = parents[element.getId()];
            if (parent != null && element.getKey() < graph.getV(parent).getKey()) {
                cut(element.getId(), parent);
                cascadingCut(parent);
            } else if (element.getKey() < graph.getV(min).getKey()) {
                min = element.getId();
            }
        }
    }

    private void removeChildFromChildListOfParent(Integer child, Integer parent) {
        checkNotNull(child);
        checkNotNull(parent);
        if (nexts[child].equals(child)) {
            childs[parent] = null;
        } else {
            cutConnection(child);
            if (childs[parent].equals(child)) {
                childs[parent] = nexts[child];
            }
        }
        graph.getV(parent).decrementDeg();
    }

    private void cut(Integer child, Integer parent) {
        removeChildFromChildListOfParent(child, parent);
        listConcat(child);
        parents[child] = null;
        graph.getV(child).setMarked(false);
    }

    private void cascadingCut(Integer k) {
        checkNotNull(k);
        Integer parent = parents[k];
        if (parent != null) {
            VertexFibo vertex = graph.getV(k);
            if (!vertex.isMarked()) {
                vertex.setMarked(true);
            } else {
                cut(k, parent);
                cascadingCut(parent);
            }
        }
    }

    private void moveChildsToRootList(Integer entry) {
        for (int i = 0; i < graph.getV(entry).getDeg(); i++) {
            Integer child = childs[entry];
            if (child != null) {
                if (child.equals(nexts[child])) {
                    childs[entry] = null;
                } else {
                    childs[entry] = nexts[child];
                    previous[nexts[child]] = previous[child];
                    cutConnection(child);
                }
                parents[child] = null;
                nexts[child] = entry;
                previous[child] = previous[entry];
                nexts[previous[entry]] = child;
                previous[entry] = child;
            }
        }
        graph.getV(entry).setDeg(0);
    }

    private void cutConnection(Integer entry) {
        previous[nexts[entry]] = previous[entry];
        nexts[previous[entry]] = nexts[entry];
    }

    private void neighborLink(Integer i) {
        previous[nexts[i]] = i;
        nexts[previous[i]] = i;
    }

    private void selfLink(Integer element) {
        nexts[element] = element;
        previous[element] = element;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public VertexFibo getMin() {
        return graph.getV(min);
    }

    public Integer getSize() {
        return size;
    }

    public Integer[] getNexts() {
        return nexts;
    }

    public Integer[] getPrevious() {
        return previous;
    }

    public Integer[] getChilds() {
        return childs;
    }

    public Integer[] getParents() {
        return parents;
    }

    public GraphFibo getGraph() {
        return graph;
    }

    @Override
    public String toString() {
        return "FibonacciHeap{" +
                "\nmin=\n" + min +
                "\n, edgeSize=" + size +
                "\n, elem=\n" + PrintHelper.printFibonacciHeap(this, getMin()) + '}';
    }
}
