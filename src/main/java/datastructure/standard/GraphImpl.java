package datastructure.standard;

import com.google.common.collect.ImmutableList;
import datastructure.Edge;
import datastructure.Graph;
import datastructure.Vertex;
import datastructure.VertexImpl;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphImpl implements Graph<Vertex> {

    private final Map<Integer, List<Edge<Vertex>>> outgoingEdges;
    private final List<Edge<Vertex>> allEdges;
    private final Map<Integer, Vertex> vertices;

    private GraphImpl() {
        outgoingEdges = new LinkedHashMap<>();
        allEdges = new ArrayList<>();
        vertices = new LinkedHashMap<>();
    }

    public GraphImpl(Integer... identifier) {
        checkNotNull(identifier);
        outgoingEdges = new LinkedHashMap<>();
        allEdges = new ArrayList<>();
        vertices = new LinkedHashMap<>();
        for (Integer id : identifier) {
            this.vertices.put(id, new VertexImpl(id));
        }
    }

    @Override
    public void addConnection(Integer signOne, Integer signTwo, Double distance) {
        Vertex one = getElementOrCreateOne(signOne);
        Vertex two = getElementOrCreateOne(signTwo);
        EdgeImpl edge = new EdgeImpl(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<Edge<Vertex>>> edges = Optional.ofNullable(outgoingEdges.get(one.getId()));
        if (edges.isPresent()) {
            List<Edge<Vertex>> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getId(),
                        ImmutableList.<Edge<Vertex>>builder().addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getId(), ImmutableList.of(edge));
        }
    }

    public List<Edge<Vertex>> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        ImmutableList.Builder<Edge<Vertex>> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<Edge<Vertex>> edges : outgoingEdges.values()) {
            edges.stream().filter(currentEdge -> currentEdge.contains(vertices.get(identifier))).forEach(edgesFromNodeBuilder::add);
        }
        return edgesFromNodeBuilder.build();
    }

    public Map<Integer, List<Edge<Vertex>>> getOutgoingEdges() {
        return outgoingEdges;
    }

    public List<Edge<Vertex>> getEdges() {
        return allEdges;
    }

    @Override
    public Map<Integer, Vertex> getElements() {
        return vertices;
    }

    @Override
    public Vertex getElementOrCreateOne(int id) {
        if (getElements().containsKey(id)) {
            return getElements().get(id);
        } else {
            Vertex value = new VertexImpl(id);
            getElements().put(id, value);
            return value;
        }
    }

    @Override
    public String toString() {
        return "StandardGraph{" +
                "\nvertices=" + vertices +
                ",\noutgoingEdges=" + outgoingEdges +
                "\n}";
    }
}
