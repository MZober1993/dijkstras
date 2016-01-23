package datastructure.binary;

import datastructure.AbstractGraph;
import datastructure.Graph;
import datastructure.standard.Vertex;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphBinary extends AbstractGraph<VertexBinary, EdgeImplBinary> {

    public GraphBinary(Integer... identifier) {
        super(identifier);
        for (Integer id : identifier) {
            Vertex v = new Vertex(id);
            this.vertices.put(id, new VertexBinary(v, Double.MAX_VALUE));
        }
    }

    public GraphBinary(List<Integer> identifiers) {
        super(identifiers);
        for (Integer id : identifiers) {
            Vertex v = new Vertex(id);
            this.vertices.put(id, new VertexBinary(v, Double.MAX_VALUE));
        }
    }

    @Override
    public void addConnection(Integer signOne, Integer signTwo, Double distance) {
        VertexBinary one = getElementOrCreateOne(signOne);
        VertexBinary two = getElementOrCreateOne(signTwo);

        if (!one.hasConnectionTo(signTwo)) {
            adjacencyGraph.get(signOne).add(new EdgeImplBinary(two, distance));
            adjacencyGraph.get(signTwo).add(new EdgeImplBinary(one, distance));
            one.isConnectionTo(signTwo);
            two.isConnectionTo(signOne);
            edgeSize++;
        }
    }

    @Override
    public Graph<VertexBinary, EdgeImplBinary> refreshGraph() {
        vertices.forEach((Integer id, VertexBinary entry) -> {
            entry.setClosed(false);
            entry.setKey(Double.MAX_VALUE);
            entry.setAnchor(null);
            entry.setKey(Double.MAX_VALUE);
            entry.setPosition(null);
        });
        return this;
    }

    @Override
    public VertexBinary getElementOrCreateOne(int id) {
        if (getVs().containsKey(id)) {
            return getVs().get(id);
        } else {
            VertexBinary value = new VertexBinary(new Vertex(id), Double.MAX_VALUE);
            getVs().put(id, value);
            return value;
        }
    }
}