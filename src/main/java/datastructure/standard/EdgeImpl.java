package datastructure.standard;

import datastructure.Edge;
import datastructure.Vertex;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 2015 - 04.11.15 - 11:55
 */
public class EdgeImpl implements Edge {

    private final Vertex nodeOne;
    private final Vertex nodeTwo;
    private Double distance;

    public EdgeImpl(Vertex nodeOne, Vertex nodeTwo, Double distance) {
        checkNotNull(nodeOne);
        checkNotNull(nodeTwo);
        checkNotNull(distance);
        this.nodeOne = nodeOne;
        this.nodeTwo = nodeTwo;
        this.distance = distance;
    }

    @Override
    public boolean contains(Vertex vertex) {
        return nodeOne.equals(vertex) || nodeTwo.equals(vertex);
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public Vertex getFirstVertex() {
        return nodeOne;
    }

    @Override
    public Vertex getSecondVertex() {
        return nodeTwo;
    }

    @Override
    public String toString() {
        return nodeOne.getId() + "-[" + distance + "]->" + nodeTwo.getId();
    }

}