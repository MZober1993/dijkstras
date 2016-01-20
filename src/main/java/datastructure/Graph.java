package datastructure;

import util.MathHelper;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 16.12.15 - 20:09
 */
public interface Graph<T extends Element> {

    T getElementOrCreateOne(int id);

    default T getElement(int id) {
        T entry = getElements().get(id);
        if (entry == null) {
            throw new RuntimeException("Element with id: " + id + " does not exist.");
        }
        return entry;
    }

    default T getRandomElement() {
        return getElement(MathHelper.calculateRandomNodeId(1, getElements().size()));
    }

    Map<Integer, T> getElements();

    default T getOne() {
        return getElements().entrySet().stream().findAny().get().getValue();
    }

    List<? extends Edge<T>> getEdgesFromNode(Integer identifier);

    List<? extends Edge<T>> getEdgesFromNode(T element);

    void addConnection(Integer signOne, Integer signTwo, Double distance);

    default T getLastRandomElement() {
        int size = getElements().size();
        return getElement(MathHelper.calculateRandomNodeId((size * 3) / 4, size));
    }

    List<? extends Edge<T>> getEdges();

    default boolean isEmpty() {
        return getElements().size() == 0;
    }

    Graph<T> refreshGraph();
}
