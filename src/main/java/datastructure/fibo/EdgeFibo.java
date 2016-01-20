package datastructure.fibo;

import datastructure.Edge;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 11:55
 */
public class EdgeFibo implements Edge<VertexFibo> {

    private VertexFibo first;
    private VertexFibo second;
    private Double distance;

    public EdgeFibo(VertexFibo first, VertexFibo second, Double distance) {
        initEdge(first, second, distance);
    }

    @Override
    public boolean contains(VertexFibo entry) {
        return first.equals(entry) || second.equals(entry);
    }

    @Override
    public VertexFibo getFirst() {
        return first;
    }

    @Override
    public VertexFibo getSecond() {
        return second;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public void initEdge(VertexFibo first, VertexFibo second, Double distance) {
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