package datastructure.standard;

import datastructure.AbstractGraph;
import datastructure.Graph;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphImpl extends AbstractGraph<Vertex, EdgeImpl> {

    public GraphImpl() {
        super();
    }

    public GraphImpl(Integer... identifier) {
        super(identifier);
        for (Integer id : identifier) {
            this.vertices.put(id, new Vertex(id, Double.MAX_VALUE));
        }
    }

    public GraphImpl(List<Integer> identifiers) {
        super(identifiers);
        for (Integer id : identifiers) {
            this.vertices.put(id, new Vertex(id, Double.MAX_VALUE));
        }
    }

    @Override
    public void addConnection(Integer signOne, Integer signTwo, Double distance) {
        Vertex one = getElementOrCreateOne(signOne);
        Vertex two = getElementOrCreateOne(signTwo);

        if (!one.hasConnectionTo(signTwo)) {
            adjacencyGraph.get(signOne).add(new EdgeImpl(two, distance));
            adjacencyGraph.get(signTwo).add(new EdgeImpl(one, distance));
            one.isConnectedTo(signTwo);
            two.isConnectedTo(signOne);
            edgeSize++;
        }
    }

    @Override
    public Graph<Vertex, EdgeImpl> refreshGraph() {
        vertices.forEach((Integer id, Vertex entry) -> {
            entry.setClosed(false);
            entry.setAnchor(null);
            entry.setKey(Double.MAX_VALUE);
        });

        return this;
    }

    @Override
    public Vertex getElementOrCreateOne(int id) {
        if (getVs().containsKey(id)) {
            return getVs().get(id);
        } else {
            Vertex value = new Vertex(id, Double.MAX_VALUE);
            getVs().put(id, value);
            return value;
        }
    }
}
