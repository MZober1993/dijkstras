package datastructure.standard;

import com.google.common.collect.ImmutableList;
import datastructure.Edge;
import datastructure.EdgeBuilder;
import datastructure.Graph;
import datastructure.Vertex;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphImpl implements Graph {

    private final Map<Integer, List<Edge>> outgoingEdges;
    private final List<Edge> allEdges;
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
    public void initXY(Integer identifier, Double x, Double y) {
        checkNotNull(identifier);
        checkNotNull(x);
        checkNotNull(y);

        Vertex current = vertices.get(identifier);
        if (current != null) {
            current.setX(x);
            current.setY(y);
        }
    }

    @Override
    public void addConnection(Integer signOne, Integer signTwo, Double distance) {
        Vertex one = getVertexOrCreateOne(signOne);
        Vertex two = getVertexOrCreateOne(signTwo);
        EdgeImpl edge = new EdgeImpl(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<Edge>> edges = Optional.ofNullable(outgoingEdges.get(one.getId()));
        if (edges.isPresent()) {
            List<Edge> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getId(), ImmutableList.<Edge>builder().addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getId(), ImmutableList.of(edge));
        }
    }

    @Override
    public void linkVertex(EdgeBuilder builder) {
        checkNotNull(builder);
        Integer id = builder.getCurrentId();
        List<Edge> edges = builder.build();
        if (vertices.get(id) != null) {
            checkNotNull(edges);
            this.outgoingEdges.put(id, edges);
        } else {
            throw new RuntimeException("Unknown Vertex with id:" + id);
        }
    }

    @Override
    public List<Edge> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        ImmutableList.Builder<Edge> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<Edge> edges : outgoingEdges.values()) {
            for (Edge currentEdge : edges) {
                if (currentEdge.contains(vertices.get(identifier))) {
                    edgesFromNodeBuilder.add(currentEdge);
                }
            }
        }
        return edgesFromNodeBuilder.build();
    }

    @Override
    public Vertex getVertexOrCreateOne(int id) {
        if (vertices.containsKey(id)) {
            return vertices.get(id);
        } else {
            Vertex value = new VertexImpl(id);
            vertices.put(id, value);
            return value;
        }
    }

    public static List<Integer> reconstructPath(Vertex vertex) {
        List<Integer> path = new ArrayList<>();
        Vertex current = vertex;
        while (current.getPrevious() != null) {
            path.add(0, current.getId());
            current = current.getPrevious();
        }
        path.add(0, current.getId());
        return path;
    }

    @Override
    public Map<Integer, List<Edge>> getOutgoingEdges() {
        return outgoingEdges;
    }

    @Override
    public List<Edge> getEdges() {
        return allEdges;
    }

    @Override
    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "\nvertices=" + vertices +
                ",\noutgoingEdges=" + outgoingEdges +
                "\n}";
    }
}
