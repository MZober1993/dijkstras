package datastructure;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 23:00
 */
public class AbstractEdge<T extends Element> implements Edge<T> {

    private final T connected;
    private final Double distance;

    public AbstractEdge(T connected, Double distance) {
        this.connected = connected;
        this.distance = distance;
    }

    @Override
    public boolean contains(T element) {
        return connected.equals(element);
    }

    @Override
    public T getConnected() {
        return connected;
    }

    @Override
    public Double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "[" + distance + "]->" + connected.getId();
    }

}
