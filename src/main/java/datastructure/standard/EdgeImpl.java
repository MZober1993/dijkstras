package datastructure.standard;

import datastructure.Edge;
import datastructure.Element;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 11:55
 */
public class EdgeImpl implements Edge<Element> {

    private Element first;
    private Element second;
    private Double distance;

    public EdgeImpl(Element first, Element second, Double distance) {
        initEdge(first, second, distance);
    }

    @Override
    public boolean contains(Element vertex) {
        return first.equals(vertex) || second.equals(vertex);
    }

    @Override
    public Element getFirst() {
        return first;
    }

    @Override
    public Element getSecond() {
        return second;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public void initEdge(Element first, Element second, Double distance) {
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