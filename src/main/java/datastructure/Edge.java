package datastructure;

import datastructure.fibo.Entry;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 16.12.15 - 20:18
 */
public interface Edge<T> {

    boolean contains(datastructure.Vertex vertex);

    boolean contains(Entry<datastructure.Vertex> entry);

    default T getConnected(T element) {
        if (element.equals(getFirst())) {
            return getSecond();
        } else if (element.equals(getSecond())) {
            return getFirst();
        } else {
            return null;
        }
    }

    T getFirst();

    T getSecond();

    Double getDistance();

    void initEdge(T first, T second, Double distance);
}
