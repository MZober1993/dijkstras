package datastructure.fibo;

import datastructure.Vertex;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 11:55
 */
public class EdgeImplFibo implements FiboEdge {

    private final Entry<Vertex> entryOne;
    private final Entry<Vertex> entryTwo;
    private Double distance;

    public EdgeImplFibo(Entry<Vertex> nodeOne, Entry<Vertex> nodeTwo, Double distance) {
        checkNotNull(nodeOne);
        checkNotNull(nodeTwo);
        checkNotNull(distance);

        entryOne = nodeOne;
        entryTwo = nodeTwo;
        this.distance = distance;
    }

    @Override
    public Entry<Vertex> getFirstEntry() {
        return entryOne;
    }

    @Override
    public Entry<Vertex> getSecondEntry() {
        return entryTwo;
    }

    @Override
    public boolean contains(Vertex vertex) {
        return entryOne.getValue().equals(vertex) || entryTwo.getValue().equals(vertex);
    }

    @Override
    public boolean contains(Entry<Vertex> entry) {
        return entryOne.equals(entry) || entryTwo.equals(entry);
    }

    @Override
    public Double getDistance() {
        return distance;
    }


    @Override
    public String toString() {
        return entryOne.getValue().getId() + "-[" + distance + "]->" + entryTwo.getValue().getId();
    }
}