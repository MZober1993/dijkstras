package datastructure.standard;

import com.google.common.collect.ImmutableList;
import datastructure.EdgeBuilder;
import datastructure.Vertex;
import datastructure.VertexImpl;
import datastructure.fibo.Entry;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphImpl implements StandardGraph {

    private final Map<Integer, List<StandardEdge>> outgoingEdges;
    private final List<StandardEdge> allEdges;
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
        Vertex one = getVertexOrCreateOne(signOne);
        Vertex two = getVertexOrCreateOne(signTwo);
        EdgeImpl edge = new EdgeImpl(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<StandardEdge>> edges = Optional.ofNullable(outgoingEdges.get(one.getId()));
        if (edges.isPresent()) {
            List<StandardEdge> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getId(), ImmutableList.<StandardEdge>builder().addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getId(), ImmutableList.of(edge));
        }
    }

    @Override
    public void linkVertex(EdgeBuilder builder) {
        checkNotNull(builder);
        Integer id = builder.getCurrentId();
        List<StandardEdge> edges = builder.build();
        if (vertices.get(id) != null) {
            checkNotNull(edges);
            this.outgoingEdges.put(id, edges);
        } else {
            throw new RuntimeException("Unknown Vertex with id:" + id);
        }
    }

    @Override
    public List<StandardEdge> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        ImmutableList.Builder<StandardEdge> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<StandardEdge> edges : outgoingEdges.values()) {
            edges.stream().filter(currentEdge -> currentEdge.contains(vertices.get(identifier))).forEach(edgesFromNodeBuilder::add);
        }
        return edgesFromNodeBuilder.build();
    }

    @Override
    public Map<Integer, List<StandardEdge>> getOutgoingEdges() {
        return outgoingEdges;
    }

    @Override
    public List<StandardEdge> getEdges() {
        return allEdges;
    }

    @Override
    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    @Override
    public Map<Integer, Entry<Vertex>> getEntryVertices() {
        throw new IllegalArgumentException("Standardgraph has no EntryVertices-Map");
    }

    @Override
    public String toString() {
        return "StandardGraph{" +
                "\nvertices=" + vertices +
                ",\noutgoingEdges=" + outgoingEdges +
                "\n}";
    }
}
