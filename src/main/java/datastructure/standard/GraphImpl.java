package datastructure.standard;

import datastructure.AbstractGraph;
import datastructure.Element;
import datastructure.Graph;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphImpl extends AbstractGraph<Element, EdgeImpl> {

    public GraphImpl() {
        super();
    }

    public GraphImpl(Integer... identifier) {
        super(identifier);
        for (Integer id : identifier) {
            this.vertices.put(id, new Vertex(id));
        }
    }

    public GraphImpl(List<Integer> identifiers) {
        super(identifiers);
        for (Integer id : identifiers) {
            this.vertices.put(id, new Vertex(id));
        }
    }

    @Override
    public void addConnection(Integer signOne, Integer signTwo, Double distance) {
        Element one = getElementOrCreateOne(signOne);
        Element two = getElementOrCreateOne(signTwo);

        if (!one.hasConnectionTo(signTwo)) {
            adjacencyGraph.get(signOne).add(new EdgeImpl(two, distance));
            adjacencyGraph.get(signTwo).add(new EdgeImpl(one, distance));
            one.isConnectionTo(signTwo);
            two.isConnectionTo(signOne);
            size++;
        }
    }

    @Override
    public Graph<Element, EdgeImpl> refreshGraph() {
        vertices.forEach((Integer id, Element entry) -> {
            entry.setClosed(false);
            entry.setAnchor(null);
            entry.setG(Double.MAX_VALUE);
        });

        return this;
    }

    @Override
    public Element getElementOrCreateOne(int id) {
        if (getElements().containsKey(id)) {
            return getElements().get(id);
        } else {
            Element value = new Vertex(id);
            getElements().put(id, value);
            return value;
        }
    }
}
