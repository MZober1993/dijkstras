package datastructure.fibo;

import datastructure.AbstractGraph;
import datastructure.Graph;
import datastructure.standard.Vertex;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphFibo extends AbstractGraph<VertexFibo, EdgeImplFibo> {

    public GraphFibo() {
        super();
    }

    public GraphFibo(Integer... identifier) {
        super(identifier);
        for (Integer id : identifier) {
            Vertex v = new Vertex(id);
            this.vertices.put(id, new VertexFibo(v, Double.MAX_VALUE));
        }
    }

    public GraphFibo(List<Integer> identifiers) {
        super(identifiers);
        for (Integer id : identifiers) {
            Vertex v = new Vertex(id);
            this.vertices.put(id, new VertexFibo(v, Double.MAX_VALUE));
        }
    }

    @Override
    public void addConnection(Integer signOne, Integer signTwo, Double distance) {
        VertexFibo one = getElementOrCreateOne(signOne);
        VertexFibo two = getElementOrCreateOne(signTwo);

        if (!one.hasConnectionTo(signTwo)) {
            adjacencyGraph.get(signOne).add(new EdgeImplFibo(two, distance));
            adjacencyGraph.get(signTwo).add(new EdgeImplFibo(one, distance));
            one.isConnectionTo(signTwo);
            two.isConnectionTo(signOne);
            size++;
        }
    }

    @Override
    public Graph<VertexFibo, EdgeImplFibo> refreshGraph() {
        vertices.forEach((Integer id, VertexFibo entry) -> {
            entry.setClosed(false);
            entry.setDeg(0);
            entry.setParent(null);
            entry.setChild(null);
            entry.setNext(entry);
            entry.setPrevious(entry);
            entry.setMarked(false);
            entry.setKey(Double.MAX_VALUE);
            entry.setAnchor(null);
            entry.setG(Double.MAX_VALUE);
        });
        return this;
    }

    @Override
    public VertexFibo getElementOrCreateOne(int id) {
        if (getElements().containsKey(id)) {
            return getElements().get(id);
        } else {
            VertexFibo value = new VertexFibo(new Vertex(id), Double.MAX_VALUE);
            getElements().put(id, value);
            return value;
        }
    }
}
