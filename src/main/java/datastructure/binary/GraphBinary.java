package datastructure.binary;

import com.google.common.collect.ImmutableList;
import datastructure.Edge;
import datastructure.Element;
import datastructure.Graph;
import datastructure.standard.Vertex;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphBinary implements Graph<VertexBinary> {

    private Map<Integer, List<Edge<VertexBinary>>> outgoingEdges;
    private List<Edge<VertexBinary>> allEdges;
    private Map<Integer, Element> vertices;
    private Map<Integer, VertexBinary> entryVertices;

    public GraphBinary(Integer... identifier) {
        checkNotNull(identifier);
        init();
        for (Integer id : identifier) {
            Vertex v = new Vertex(id);
            this.vertices.put(id, v);
            this.entryVertices.put(id, new VertexBinary(v, Double.MAX_VALUE));
        }
    }

    public GraphBinary(List<Integer> identifiers) {
        checkNotNull(identifiers);
        init();
        for (Integer id : identifiers) {
            Vertex v = new Vertex(id);
            this.vertices.put(id, v);
            this.entryVertices.put(id, new VertexBinary(v, Double.MAX_VALUE));
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
        VertexBinary one = getElementOrCreateOne(signOne);
        VertexBinary two = getElementOrCreateOne(signTwo);
        EdgeBinary edge = new EdgeBinary(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<Edge<VertexBinary>>> edges = Optional.ofNullable(outgoingEdges.get(
                one.getValue().getId()));
        if (edges.isPresent()) {
            List<Edge<VertexBinary>> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getValue().getId(), ImmutableList.<Edge<VertexBinary>>builder()
                        .addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getValue().getId(), ImmutableList.of(edge));
        }
    }

    @Override
    public List<Edge<VertexBinary>> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        return edgesFromNode(entryVertices.get(identifier));
    }

    @Override
    public List<Edge<VertexBinary>> getEdgesFromNode(VertexBinary entry) {
        checkNotNull(entry);
        return edgesFromNode(entry);
    }

    private List<Edge<VertexBinary>> edgesFromNode(VertexBinary element) {
        ImmutableList.Builder<Edge<VertexBinary>> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<Edge<VertexBinary>> edges : outgoingEdges.values()) {
            edges.stream().filter(currentEdge -> currentEdge.contains(element)).forEach(edgesFromNodeBuilder::add);
        }
        return edgesFromNodeBuilder.build();
    }

    public List<Edge<VertexBinary>> getEdges() {
        return allEdges;
    }

    @Override
    public Graph<VertexBinary> refreshGraph() {
        entryVertices.forEach((Integer id, VertexBinary entry) -> {
            entry.setClosed(false);
            entry.setKey(Double.MAX_VALUE);
            entry.setAnchor(null);
            entry.setG(Double.MAX_VALUE);
            entry.setPosition(null);
        });
        return this;
    }

    public Map<Integer, Element> getVertices() {
        return vertices;
    }

    @Override
    public VertexBinary getElementOrCreateOne(int id) {
        if (getElements().containsKey(id)) {
            return getElements().get(id);
        } else {
            VertexBinary value = new VertexBinary(new Vertex(id), Double.MAX_VALUE);
            getElements().put(id, value);
            return value;
        }
    }

    @Override
    public Map<Integer, VertexBinary> getElements() {
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
