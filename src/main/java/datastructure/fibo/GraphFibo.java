package datastructure.fibo;

import com.google.common.collect.ImmutableList;
import datastructure.Element;
import datastructure.Graph;
import datastructure.standard.Vertex;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphFibo implements Graph<VertexFibo> {

    private Map<Integer, List<EdgeFibo>> outgoingEdges;
    private List<EdgeFibo> allEdges;
    private Map<Integer, Element> vertices;
    private Map<Integer, VertexFibo> entryVertices;

    public GraphFibo() {
        init();
    }

    public GraphFibo(Integer... identifier) {
        checkNotNull(identifier);
        init();
        for (Integer id : identifier) {
            Vertex v = new Vertex(id);
            this.vertices.put(id, v);
            this.entryVertices.put(id, new VertexFibo(v, Double.MAX_VALUE));
        }
    }

    public GraphFibo(List<Integer> identifiers) {
        checkNotNull(identifiers);
        init();
        for (Integer id : identifiers) {
            Vertex v = new Vertex(id);
            this.vertices.put(id, v);
            this.entryVertices.put(id, new VertexFibo(v, Double.MAX_VALUE));
        }
    }

    private void init() {
        outgoingEdges = new LinkedHashMap<>();
        allEdges = new ArrayList<>();
        vertices = new LinkedHashMap<>();
        entryVertices = new LinkedHashMap<>();
    }

    @Override
    public void addConnection(Integer signOne, Integer signTwo, Double distance) {
        VertexFibo one = getElementOrCreateOne(signOne);
        VertexFibo two = getElementOrCreateOne(signTwo);
        EdgeFibo edge = new EdgeFibo(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<EdgeFibo>> edges = Optional.ofNullable(outgoingEdges.get(
                one.getValue().getId()));
        if (edges.isPresent()) {
            List<EdgeFibo> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getValue().getId(), ImmutableList.<EdgeFibo>builder()
                        .addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getValue().getId(), ImmutableList.of(edge));
        }
    }

    @Override
    public List<EdgeFibo> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        return edgesFromNode(entryVertices.get(identifier));
    }

    @Override
    public List<EdgeFibo> getEdgesFromNode(VertexFibo entry) {
        checkNotNull(entry);
        return edgesFromNode(entry);
    }

    private List<EdgeFibo> edgesFromNode(VertexFibo element) {
        ImmutableList.Builder<EdgeFibo> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<EdgeFibo> edges : outgoingEdges.values()) {
            edges.stream().filter(currentEdge -> currentEdge.contains(element)).forEach(edgesFromNodeBuilder::add);
        }
        return edgesFromNodeBuilder.build();
    }

    public List<EdgeFibo> getEdges() {
        return allEdges;
    }

    @Override
    public Graph<VertexFibo> refreshGraph() {
        entryVertices.forEach((Integer id, VertexFibo entry) -> {
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

    public Map<Integer, Element> getVertices() {
        return vertices;
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

    @Override
    public Map<Integer, VertexFibo> getElements() {
        return entryVertices;
    }

    @Override
    public String toString() {
        return "FiboGraph{" +
                "\nvertices=" + vertices +
                ",\nelements=" + entryVertices +
                ",\noutgoingEdges=" + outgoingEdges +
                "\n}";
    }
}
