package datastructure.fibo;

import datastructure.Vertex;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 11:55
 */
public class EdgeImplFibo implements FiboEdge {

    private final FibonacciHeapBig.Entry<Vertex> entryOne;
    private final FibonacciHeapBig.Entry<Vertex> entryTwo;
    private Double distance;

    public EdgeImplFibo(Vertex nodeOne, Vertex nodeTwo, Double distance) {
        checkNotNull(nodeOne);
        checkNotNull(nodeTwo);
        checkNotNull(distance);

        entryOne= new FibonacciHeapBig.Entry<>(nodeOne, Integer.MAX_VALUE);
        entryTwo= new FibonacciHeapBig.Entry<>(nodeTwo, Integer.MAX_VALUE);
        this.distance = distance;
    }

    @Override
    public FibonacciHeapBig.Entry<Vertex> getFirstEntry() {
        return entryOne;
    }

    @Override
    public FibonacciHeapBig.Entry<Vertex> getSecondEntry() {
        return entryTwo;
    }

    @Override
    public boolean contains(Vertex vertex) {
        return entryOne.getValue().equals(vertex) || entryTwo.getValue().equals(vertex);
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