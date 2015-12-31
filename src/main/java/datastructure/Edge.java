package datastructure;

import datastructure.fibo.Entry;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 16.12.15 - 20:18
 */
public interface Edge {

    boolean contains(datastructure.Vertex vertex);

    boolean contains(Entry<datastructure.Vertex> entry);

    Double getDistance();
}
