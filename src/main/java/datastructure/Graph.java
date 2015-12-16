package datastructure;

import util.MathHelper;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 2015 - 17.11.15 - 14:22
 */
public interface Graph {

    public void initXY(Integer identifier, Double x, Double y);

    public void addConnection(Integer signOne, Integer signTwo, Double distance);

    public void linkVertex(EdgeBuilder builder);

    public List<Edge> getEdgesFromNode(Integer identifier);

    public default Vertex getVertex(int id) {
        Vertex vertex = getVertices().get(id);
        if (vertex == null) {
            throw new RuntimeException("Vertex with id: " + id + " does not exist.");
        }
        return vertex;
    }

    public default Vertex getVertexWithIndex(int index) {
        Vertex vertex = (Vertex) getVertices().values().toArray()[index];
        if (vertex == null) {
            throw new RuntimeException("Vertex with index: " + index + " does not exist.");
        }
        return vertex;
    }

    public Vertex getVertexOrCreateOne(int id);

    public default Vertex getLastRandomVertex() {
        int size = getVertices().size();
        return getVertex(MathHelper.calculateRandomNodeId((size * 3) / 4, size));
    }

    public default Vertex getRandomVertex() {
        return getVertex(MathHelper.calculateRandomNodeId(1, getVertices().size()));
    }

    public default List<Edge> getEdgesFromNode(Vertex vertex) {
        return getEdgesFromNode(vertex.getId());
    }

    public List<Edge> getEdges();

    public Map<Integer, List<Edge>> getOutgoingEdges();

    public Map<Integer, Vertex> getVertices();

    public default Vertex getOne() {
        return getVertices().entrySet().stream().findAny().get().getValue();
    }
}
