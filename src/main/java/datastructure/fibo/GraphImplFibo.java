package datastructure.fibo;

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
public class GraphImplFibo implements Graph<Entry<Vertex>> {

    private final Map<Integer, List<Edge<Entry<Vertex>>>> outgoingEdges;
    private final List<Edge<Entry<Vertex>>> allEdges;
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
        Entry<Vertex> one = getElementOrCreateOne(signOne);
        Entry<Vertex> two = getElementOrCreateOne(signTwo);
        EdgeImplFibo edge = new EdgeImplFibo(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<Edge<Entry<Vertex>>>> edges = Optional.ofNullable(outgoingEdges.get(
                one.getValue().getId()));
        if (edges.isPresent()) {
            List<Edge<Entry<Vertex>>> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getValue().getId(), ImmutableList.<Edge<Entry<Vertex>>>builder()
                        .addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getValue().getId(), ImmutableList.of(edge));
        }
    }

    @Override
    public List<Edge<Entry<Vertex>>> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        ImmutableList.Builder<Edge<Entry<Vertex>>> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<Edge<Entry<Vertex>>> edges : outgoingEdges.values()) {
            edges.stream().filter(currentEdge -> currentEdge.contains(entryVertices.get(identifier))).forEach(edgesFromNodeBuilder::add);
        }
        return edgesFromNodeBuilder.build();
    }

    public List<Edge<Entry<Vertex>>> getEdges() {
        return allEdges;
    }

    public Map<Integer, Vertex> getVertices() {
        return vertices;
    }

    @Override
    public Entry<Vertex> getElementOrCreateOne(int id) {
        if (getElements().containsKey(id)) {
            return getElements().get(id);
        } else {
            Entry<Vertex> value = new Entry<>(new VertexImpl(id), Double.MAX_VALUE);
            getElements().put(id, value);
            return value;
        }
    }

    @Override
    public Map<Integer, Entry<Vertex>> getElements() {
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
