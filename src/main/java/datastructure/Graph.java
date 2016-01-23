package datastructure;

import util.MathHelper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 16.12.15 - 20:09
 */
public interface Graph<T extends Element, H extends Edge<T>> {

    T getElementOrCreateOne(int id);

    default T getV(int id) {
        T entry = getVs().get(id);
        if (entry == null) {
            throw new RuntimeException("Element with id: " + id + " does not exist.");
        }
        return entry;
    }

    default T getRandomElement() {
        return getV(MathHelper.calculateRandomNodeId(1, getVs().size()));
    }

    Map<Integer, T> getVs();

    default T getOne() {
        return getVs().entrySet().stream().findAny().get().getValue();
    }

    Set<? extends Edge<T>> getConnectedElements(T vertex);

    List<Set<H>> getAdjacencyGraph();

    void addConnection(Integer signOne, Integer signTwo, Double distance);

    default T getLastRandomElement() {
        int size = getVs().size();
        return getV(MathHelper.calculateRandomNodeId((size * 3) / 4, size));
    }

    int getEdgeSize();

    default boolean isEmpty() {
        return getVs().size() == 0;
    }

    Graph<T, H> refreshGraph();
}
