package datastructure.fibo;

import datastructure.Edge;
import datastructure.Vertex;


/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 16.12.15 - 18:55
 */
public interface FiboEdge extends Edge{

    default FibonacciHeap.Entry<Vertex> getConnectedEntry(FibonacciHeap.Entry<Vertex> entry) {
        if (entry.equals(getFirstEntry())) {
            return getSecondEntry();
        } else if (entry.equals(getSecondEntry())) {
            return getFirstEntry();
        } else {
            return null;
        }
    }

    FibonacciHeap.Entry<Vertex> getFirstEntry();

    FibonacciHeap.Entry<Vertex> getSecondEntry();
}
