package datastructure;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 16.12.15 - 20:18
 */
public interface Edge {

    boolean contains(datastructure.Vertex vertex);

    Double getDistance();
}
