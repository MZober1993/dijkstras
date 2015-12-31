package datastructure.fibo;

import com.google.common.collect.ImmutableList;
import datastructure.EdgeBuilder;
import datastructure.Vertex;
import datastructure.VertexImpl;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphImplFibo implements FiboGraph {

    private final Map<Integer, List<FiboEdge>> outgoingEdges;
    private final List<FiboEdge> allEdges;
    private final Map<Integer, Vertex> vertices;
    private final Map<Integer, Entry<Vertex>> entryVertices;

    public GraphImplFibo(Integer... identifier) {
        checkNotNull(identifier);
        outgoingEdges = new LinkedHashMap<>();
        allEdges = new ArrayList<>();
        vertices = new LinkedHashMap<>();
        entryVertices = new LinkedHashMap<>();
        for (Integer id : identifier) {
            VertexImpl v = new VertexImpl(id);
            this.vertices.put(id, v);
            this.entryVertices.put(id, new Entry<>(v, Double.MAX_VALUE));
        }
    }

    @Override
    public void addConnection(Integer signOne, Integer signTwo, Double distance) {
        Entry<Vertex> one = getEntryOrCreateOne(signOne);
        Entry<Vertex> two = getEntryOrCreateOne(signTwo);
        EdgeImplFibo edge = new EdgeImplFibo(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<FiboEdge>> edges = Optional.ofNullable(outgoingEdges.get(
                one.getValue().getId()));
        if (edges.isPresent()) {
            List<FiboEdge> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getValue().getId(), ImmutableList.<FiboEdge>builder()
                        .addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getValue().getId(), ImmutableList.of(edge));
        }
    }

    @Override
    public void linkVertex(EdgeBuilder builder) {
        checkNotNull(builder);
        Integer id = builder.getCurrentId();
        List<FiboEdge> edges = builder.build();
        if (entryVertices.get(id) != null) {
            checkNotNull(edges);
            this.outgoingEdges.put(id, edges);
        } else {
            throw new RuntimeException("Unknown Vertex with id:" + id);
        }
    }

    @Override
    public List<FiboEdge> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        ImmutableList.Builder<FiboEdge> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<FiboEdge> edges : outgoingEdges.values()) {
            for (FiboEdge currentEdge : edges) {
                if (currentEdge.contains(entryVertices.get(identifier))) {
                    edgesFromNodeBuilder.add(currentEdge);
                }
            }
        }
        return edgesFromNodeBuilder.build();
    }

    @Override
    public Map<Integer, List<FiboEdge>> getOutgoingEdges() {
        return outgoingEdges;
    }

    @Override
    public List<FiboEdge> getEdges() {
        return allEdges;
    }

    @Override
    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    @Override
    public Map<Integer, Entry<Vertex>> getEntryVertices() {
        return entryVertices;
    }

    @Override
    public String toString() {
        return "FiboGraph{" +
                "\nvertices=" + vertices +
                ",\noutgoingEdges=" + outgoingEdges +
                "\n}";
    }
}
