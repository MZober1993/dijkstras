package datastructure.binary;

import datastructure.Edge;
import datastructure.Element;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 11:55
 */
public class EdgeImplBinary implements Edge<BEntry<Element>> {

    private BEntry<Element> first;
    private BEntry<Element> second;
    private Double distance;

    public EdgeImplBinary(BEntry<Element> first, BEntry<Element> second, Double distance) {
        initEdge(first, second, distance);
    }

    @Override
    public boolean contains(BEntry<Element> entry) {
        return first.equals(entry) || second.equals(entry);
    }

    @Override
    public BEntry<Element> getFirst() {
        return first;
    }

    @Override
    public BEntry<Element> getSecond() {
        return second;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public void initEdge(BEntry<Element> first, BEntry<Element> second, Double distance) {
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