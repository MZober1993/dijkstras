package datastructure.fibo;

import datastructure.Edge;
import datastructure.Vertex;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 11:55
 */
public class EdgeImplFibo implements Edge<Entry<Vertex>> {

    private Entry<Vertex> first;
    private Entry<Vertex> second;
    private Double distance;

    public EdgeImplFibo() {
    }

    public EdgeImplFibo(Entry<Vertex> first, Entry<Vertex> second, Double distance) {
        initEdge(first, second, distance);
    }

    @Override
    public boolean contains(Vertex vertex) {
        return first.getValue().equals(vertex) || second.getValue().equals(vertex);
    }

    @Override
    public boolean contains(Entry<Vertex> entry) {
        return first.equals(entry) || second.equals(entry);
    }

    @Override
    public Entry<Vertex> getFirst() {
        return null;
    }

    @Override
    public Entry<Vertex> getSecond() {
        return null;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public void initEdge(Entry<Vertex> first, Entry<Vertex> second
            , Double distance) {
        checkNotNull(first);
        checkNotNull(second);
        checkNotNull(distance);
        this.first = first;
        this.second = second;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return first.getValue().getId() + "-[" + distance + "]->" + second.getValue().getId();
    }
}