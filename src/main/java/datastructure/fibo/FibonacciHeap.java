package datastructure.fibo;

import datastructure.PrintHelper;
import util.MathHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.12.15 - 17:03
 */
public final class FibonacciHeap {

    private Integer min = null;
    private Integer size = 0;
    private List<Integer> nexts;
    private List<Integer> previous;
    private List<Integer> childs;
    private List<Integer> parents;
    private GraphFibo graph;

    public FibonacciHeap(GraphFibo graph) {
        this.graph = graph;
        int size = graph.getAdjacencyGraph().size();
        nexts = new ArrayList<>(size);
        previous = new ArrayList<>(size);
        childs = new ArrayList<>(size);
        parents = new ArrayList<>(size);
        IntStream.range(0, size + 1).forEach(x -> {
            nexts.add(null);
            previous.add(null);
            childs.add(null);
            parents.add(null);
        });
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
            Integer endHeap = previous.get(min);
            previous.set(min, element);
            previous.set(element, endHeap);
            nexts.set(endHeap, element);
            nexts.set(element, min);

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
            if (entry.equals(nexts.get(entry))) {
                min = null;
            } else {
                min = nexts.get(entry);
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
                if (element.equals(nexts.get(element))) {
                    next = null;
                } else {
                    next = nexts.get(element);
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
                    if (childs.get(element) == null) {
                        childs.set(element, degree[currentDegree]);
                        parents.set(degree[currentDegree], element);
                    } else {
                        parents.set(degree[currentDegree], element);
                        nexts.set(degree[currentDegree], childs.get(element));
                        previous.set(degree[currentDegree], previous.get(childs.get(element)));
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
            Integer parent = parents.get(element.getId());
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
        if (nexts.get(child).equals(child)) {
            childs.set(parent, null);
        } else {
            cutConnection(child);
            if (childs.get(parent).equals(child)) {
                childs.set(parent, nexts.get(child));
            }
        }
        graph.getV(parent).decrementDeg();
    }

    private void cut(Integer child, Integer parent) {
        removeChildFromChildListOfParent(child, parent);
        listConcat(child);
        parents.set(child, null);
        graph.getV(child).setMarked(false);
    }

    private void cascadingCut(Integer k) {
        checkNotNull(k);
        Integer parent = parents.get(k);
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
            Integer child = childs.get(entry);
            if (child != null) {
                if (child.equals(nexts.get(child))) {
                    childs.set(entry, null);
                } else {
                    childs.set(entry, nexts.get(child));
                    previous.set(nexts.get(child), previous.get(child));
                    cutConnection(child);
                }
                parents.set(child, null);
                nexts.set(child, entry);
                previous.set(child, previous.get(entry));
                nexts.set(previous.get(entry), child);
                previous.set(entry, child);
            }
        }
        graph.getV(entry).setDeg(0);
    }

    private void cutConnection(Integer entry) {
        previous.set(nexts.get(entry), previous.get(entry));
        nexts.set(previous.get(entry), nexts.get(entry));
    }

    private void neighborLink(Integer i) {
        previous.set(nexts.get(i), i);
        nexts.set(previous.get(i), i);
    }

    private void selfLink(Integer element) {
        nexts.set(element, element);
        previous.set(element, element);
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

    public List<Integer> getNexts() {
        return nexts;
    }

    public List<Integer> getPrevious() {
        return previous;
    }

    public List<Integer> getChilds() {
        return childs;
    }

    public List<Integer> getParents() {
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
