package datastructure.binary;

import datastructure.Edge;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 11:55
 */
public class EdgeBinary implements Edge<VertexBinary> {

    private VertexBinary first;
    private VertexBinary second;
    private Double distance;

    public EdgeBinary(VertexBinary first, VertexBinary second, Double distance) {
        initEdge(first, second, distance);
    }

    @Override
    public boolean contains(VertexBinary entry) {
        return first.equals(entry) || second.equals(entry);
    }

    @Override
    public VertexBinary getFirst() {
        return first;
    }

    @Override
    public VertexBinary getSecond() {
        return second;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public void initEdge(VertexBinary first, VertexBinary second, Double distance) {
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