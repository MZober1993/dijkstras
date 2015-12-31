package datastructure;

import datastructure.fibo.Entry;
import util.MathHelper;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 16.12.15 - 20:09
 */
public interface Graph {

    <T extends Edge> List<T> getEdgesFromNode(Integer identifier);

    void addConnection(Integer signOne, Integer signTwo, Double distance);

    default Vertex getVertexOrCreateOne(int id) {
        if (getVertices().containsKey(id)) {
            return getVertices().get(id);
        } else {
            Vertex value = new VertexImpl(id);
            getVertices().put(id, value);
            return value;
        }
    }

    default Entry<Vertex> getEntryOrCreateOne(int id) {
        if (getEntryVertices().containsKey(id)) {
            return getEntryVertices().get(id);
        } else {
            Entry<Vertex> value = new Entry<>(new VertexImpl(id), Double.MAX_VALUE);
            getEntryVertices().put(id, value);
            return value;
        }
    }

    void linkVertex(EdgeBuilder builder);

    default Vertex getVertex(int id) {
        Vertex vertex = getVertices().get(id);
        if (vertex == null) {
            throw new RuntimeException("Vertex with id: " + id + " does not exist.");
        }
        return vertex;
    }

    Map<Integer, Vertex> getVertices();

    Map<Integer, Entry<Vertex>> getEntryVertices();

    default Vertex getVertexWithIndex(int index) {
        Vertex vertex = (Vertex) getVertices().values().toArray()[index];
        if (vertex == null) {
            throw new RuntimeException("Vertex with index: " + index + " does not exist.");
        }
        return vertex;
    }

    default Vertex getLastRandomVertex() {
        int size = getVertices().size();
        return getVertex(MathHelper.calculateRandomNodeId((size * 3) / 4, size));
    }

    default Vertex getRandomVertex() {
        return getVertex(MathHelper.calculateRandomNodeId(1, getVertices().size()));
    }

    default Vertex getOne() {
        return getVertices().entrySet().stream().findAny().get().getValue();
    }
}
