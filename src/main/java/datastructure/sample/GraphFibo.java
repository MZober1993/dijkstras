package datastructure.sample;

import com.google.common.collect.ImmutableList;
import datastructure.Edge;
import datastructure.Element;
import datastructure.Graph;
import datastructure.VertexImpl;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:40
 */
public class GraphFibo implements Graph<FiboHeap.Node<Element>> {

    private final Map<Integer, List<Edge<FiboHeap.Node<Element>>>> outgoingEdges;
    private final List<Edge<FiboHeap.Node<Element>>> allEdges;
    private final Map<Integer, Element> vertices;
    private final Map<Integer, FiboHeap.Node<Element>> entryVertices;

    public GraphFibo() {
        outgoingEdges = new LinkedHashMap<>();
        allEdges = new ArrayList<>();
        vertices = new LinkedHashMap<>();
        entryVertices = new LinkedHashMap<>();
    }

    public GraphFibo(Integer... identifier) {
        checkNotNull(identifier);
        outgoingEdges = new LinkedHashMap<>();
        allEdges = new ArrayList<>();
        vertices = new LinkedHashMap<>();
        entryVertices = new LinkedHashMap<>();
        for (Integer id : identifier) {
            VertexImpl v = new VertexImpl(id);
            this.vertices.put(id, v);
            this.entryVertices.put(id, new FiboHeap.Node<>(v, Double.MAX_VALUE));
        }
    }

    @Override
    public void addConnection(Integer signOne, Integer signTwo, Double distance) {
        FiboHeap.Node<Element> one = getElementOrCreateOne(signOne);
        FiboHeap.Node<Element> two = getElementOrCreateOne(signTwo);
        EdgeFibo edge = new EdgeFibo(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<Edge<FiboHeap.Node<Element>>>> edges = Optional.ofNullable(outgoingEdges.get(
                one.getValue().getId()));
        if (edges.isPresent()) {
            List<Edge<FiboHeap.Node<Element>>> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getValue().getId(), ImmutableList.<Edge<FiboHeap.Node<Element>>>builder()
                        .addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getValue().getId(), ImmutableList.of(edge));
        }
    }

    @Override
    public List<Edge<FiboHeap.Node<Element>>> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        return edgesFromNode(entryVertices.get(identifier));
    }

    @Override
    public List<Edge<FiboHeap.Node<Element>>> getEdgesFromNode(FiboHeap.Node<Element> entry) {
        checkNotNull(entry);
        return edgesFromNode(entry);
    }

    private List<Edge<FiboHeap.Node<Element>>> edgesFromNode(FiboHeap.Node<Element> element) {
        ImmutableList.Builder<Edge<FiboHeap.Node<Element>>> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<Edge<FiboHeap.Node<Element>>> edges : outgoingEdges.values()) {
            edges.stream().filter(currentEdge -> currentEdge.contains(element)).forEach(edgesFromNodeBuilder::add);
        }
        return edgesFromNodeBuilder.build();
    }

    public List<Edge<FiboHeap.Node<Element>>> getEdges() {
        return allEdges;
    }

    public Map<Integer, Element> getVertices() {
        return vertices;
    }

    @Override
    public FiboHeap.Node<Element> getElementOrCreateOne(int id) {
        if (getElements().containsKey(id)) {
            return getElements().get(id);
        } else {
            FiboHeap.Node<Element> value = new FiboHeap.Node<>(new VertexImpl(id), Double.MAX_VALUE);
            getElements().put(id, value);
            return value;
        }
    }

    @Override
    public Map<Integer, FiboHeap.Node<Element>> getElements() {
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
