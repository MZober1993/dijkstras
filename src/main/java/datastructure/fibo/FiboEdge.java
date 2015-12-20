package datastructure.fibo;

import datastructure.Edge;
import datastructure.Vertex;


/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 16.12.15 - 18:55
 */
public interface FiboEdge extends Edge{

    default FibonacciHeapBig.Entry<Vertex> getConnectedEntry(FibonacciHeapBig.Entry<Vertex> entry) {
        if (entry.equals(getFirstEntry())) {
            return getSecondEntry();
        } else if (entry.equals(getSecondEntry())) {
            return getFirstEntry();
        } else {
            return null;
        }
    }

    FibonacciHeapBig.Entry<Vertex> getFirstEntry();

    FibonacciHeapBig.Entry<Vertex> getSecondEntry();
}
