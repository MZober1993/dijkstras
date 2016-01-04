package datastructure;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         04.11.15 - 13:39
 */
public class EdgeBuilder<G extends Graph<T>, E extends Edge<T>, T extends Element> {

    private Integer currentId;
    private G graph;

    public EdgeBuilder(G graph) {
        this.graph = graph;
    }

    public EdgeBuilder<G, E, T> current(Integer currentId) {
        if (!graph.getElements().containsKey(currentId)) {
            throw new RuntimeException("Unknown identifiers:" + currentId);
        }
        this.currentId = currentId;
        return this;
    }

    public EdgeBuilder<G, E, T> to(Integer id, Double dist) {
        if (graph.getElements().containsKey(currentId) && graph.getElements().containsKey(id)) {
            graph.addConnection(currentId, id, dist);
            return this;
        } else {
            throw new RuntimeException("Unknown pair of identifiers:" + currentId + "," + id);
        }
    }

    public Integer getCurrentId() {
        return currentId;
    }
}
