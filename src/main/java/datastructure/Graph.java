package datastructure;

import util.MathHelper;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 16.12.15 - 20:09
 */
public interface Graph<T> {

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

    List<Edge<T>> getEdgesFromNode(Integer identifier);

    void addConnection(Integer signOne, Integer signTwo, Double distance);

    default T getElementWithIndex(int index) {
        T element = (T) getElements().values().toArray()[index];
        if (element == null) {
            throw new RuntimeException("Element with index: " + index + " does not exist.");
        }
        return element;
    }

    default T getLastRandomElement() {
        int size = getElements().size();
        return getElement(MathHelper.calculateRandomNodeId((size * 3) / 4, size));
    }
}
