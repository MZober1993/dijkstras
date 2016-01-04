package datastructure.fibo;

import datastructure.Edge;
import datastructure.Element;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 11:55
 */
public class EdgeImplFibo implements Edge<Entry<Element>> {

    private Entry<Element> first;
    private Entry<Element> second;
    private Double distance;

    public EdgeImplFibo(Entry<Element> first, Entry<Element> second, Double distance) {
        initEdge(first, second, distance);
    }

    @Override
    public boolean contains(Entry<Element> entry) {
        return first.equals(entry) || second.equals(entry);
    }

    @Override
    public Entry<Element> getFirst() {
        return first;
    }

    @Override
    public Entry<Element> getSecond() {
        return second;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public void initEdge(Entry<Element> first, Entry<Element> second, Double distance) {
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