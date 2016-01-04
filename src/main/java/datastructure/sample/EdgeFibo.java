package datastructure.sample;

import datastructure.Edge;
import datastructure.Element;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 11:55
 */
public class EdgeFibo implements Edge<FiboHeap.Node<Element>> {

    private FiboHeap.Node<Element> first;
    private FiboHeap.Node<Element> second;
    private Double distance;

    public EdgeFibo(FiboHeap.Node<Element> first, FiboHeap.Node<Element> second, Double distance) {
        initEdge(first, second, distance);
    }

    @Override
    public boolean contains(FiboHeap.Node<Element> entry) {
        return first.equals(entry) || second.equals(entry);
    }

    @Override
    public FiboHeap.Node<Element> getFirst() {
        return first;
    }

    @Override
    public FiboHeap.Node<Element> getSecond() {
        return second;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public void initEdge(FiboHeap.Node<Element> first, FiboHeap.Node<Element> second, Double distance) {
        checkNotNull(first);
        checkNotNull(second);
        checkNotNull(distance);
        this.first = first;
        this.second = second;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return first.getId() + "-[" + distance + "]->" + second.getId();
    }
}