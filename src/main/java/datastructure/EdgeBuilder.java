package datastructure;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 13:39
 */
public class EdgeBuilder<G extends Graph, E extends Edge<T>, T> {

    private ImmutableList.Builder<E> edgeBuilder;
    private Integer currentId;
    private G graph;
    private EdgeFactory<E, T> edgeFactory;

    public EdgeBuilder(G graph, Class<E> clazz) {
        this.graph = graph;
        edgeFactory = new EdgeFactory<>(clazz);

        edgeBuilder = new ImmutableList.Builder<>();
    }

    public EdgeBuilder<G, E, T> current(Integer currentId) {
        if (!graph.getElements().containsKey(currentId)) {
            throw new RuntimeException("Unknown identifiers:" + currentId);
        }
        this.currentId = currentId;
        edgeBuilder = new ImmutableList.Builder<>();
        return this;
    }

    public EdgeBuilder<G, E, T> to(Integer id, Double dist) {
        if (graph.getElements().containsKey(currentId) && graph.getElements().containsKey(id)) {
            edgeBuilder.add(edgeFactory.newInstance(
                    (T) graph.getElements().get(currentId)
                    , (T) graph.getElements().get(id), dist));
            return this;
        } else {
            throw new RuntimeException("Unknown pair of identifiers:" + currentId + "," + id);
        }
    }

    public List<E> build() {
        ImmutableList<E> build = edgeBuilder.build();
        edgeBuilder = null;
        return build;
    }

    public Integer getCurrentId() {
        return currentId;
    }
}
