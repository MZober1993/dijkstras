package datastructure;

import com.google.common.collect.ImmutableList;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 13:39
 */
public class EdgeBuilder<G extends Graph, E extends Edge> {

    private ImmutableList.Builder<Edge> edgeBuilder;
    private Integer currentId;
    private Graph graph;
    private EdgeFactory<E> edgeFactory;

    public EdgeBuilder(G graph, Class<E> clazz) {
        this.graph = graph;
        edgeFactory = new EdgeFactory<>(clazz);

        edgeBuilder = new ImmutableList.Builder<>();
    }

    public EdgeBuilder<G, E> current(Integer currentId) {
        if (!graph.getVertices().containsKey(currentId)) {
            throw new RuntimeException("Unknown identifiers:" + currentId);
        }
        this.currentId = currentId;
        edgeBuilder = new ImmutableList.Builder<>();
        return this;
    }

    public EdgeBuilder<G, E> to(Integer id, Double dist) {
        if (graph.getVertices().containsKey(currentId) && graph.getVertices().containsKey(id)) {
            edgeBuilder.add(edgeFactory.newInstance(
                    graph.getVertices().get(currentId), graph.getVertices().get(id), dist));
            return this;
        } else {
            throw new RuntimeException("Unknown pair of identifiers:" + currentId + "," + id);
        }
    }

    public List<Edge> build() {
        ImmutableList<Edge> build = edgeBuilder.build();
        edgeBuilder = null;
        return build;
    }

    public Integer getCurrentId() {
        return currentId;
    }
}
