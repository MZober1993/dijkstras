package datastructure;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 20:15
 */
public interface Edge<T extends Element> {

    boolean contains(T element);

    T getConnected();

    Double getDistance();
}
