package datastructure.standard;

import datastructure.Edge;
import datastructure.Vertex;
import datastructure.fibo.Entry;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 11:55
 */
public class EdgeImpl implements Edge<Vertex> {

    private Vertex first;
    private Vertex second;
    private Double distance;

    public EdgeImpl(Vertex first, Vertex second, Double distance) {
        initEdge(first, second, distance);
    }

    @Override
    public boolean contains(Vertex vertex) {
        return first.equals(vertex) || second.equals(vertex);
    }

    @Override
    public boolean contains(Entry<Vertex> entry) {
        return false;
    }

    @Override
    public Vertex getFirst() {
        return first;
    }

    @Override
    public Vertex getSecond() {
        return null;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public void initEdge(Vertex first, Vertex second, Double distance) {
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