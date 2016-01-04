package datastructure.standard;

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
public class GraphImpl implements Graph<Element> {

    private final Map<Integer, List<Edge<Element>>> outgoingEdges;
    private final List<Edge<Element>> allEdges;
    private final Map<Integer, Element> vertices;

    public GraphImpl() {
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
        Element one = getElementOrCreateOne(signOne);
        Element two = getElementOrCreateOne(signTwo);
        EdgeImpl edge = new EdgeImpl(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<Edge<Element>>> edges = Optional.ofNullable(outgoingEdges.get(one.getId()));
        if (edges.isPresent()) {
            List<Edge<Element>> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getId(),
                        ImmutableList.<Edge<Element>>builder().addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getId(), ImmutableList.of(edge));
        }
    }

    @Override
    public List<Edge<Element>> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        return edgesFromNodes(vertices.get(identifier));
    }

    @Override
    public List<Edge<Element>> getEdgesFromNode(Element vertex) {
        checkNotNull(vertex);
        return edgesFromNodes(vertex);
    }

    private List<Edge<Element>> edgesFromNodes(Element element) {
        ImmutableList.Builder<Edge<Element>> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<Edge<Element>> edges : outgoingEdges.values()) {
            edges.stream().filter(currentEdge -> currentEdge.contains(element)).forEach(edgesFromNodeBuilder::add);
        }
        return edgesFromNodeBuilder.build();
    }

    public Map<Integer, List<Edge<Element>>> getOutgoingEdges() {
        return outgoingEdges;
    }

    @Override
    public List<Edge<Element>> getEdges() {
        return allEdges;
    }

    @Override
    public Map<Integer, Element> getElements() {
        return vertices;
    }

    @Override
    public Element getElementOrCreateOne(int id) {
        if (getElements().containsKey(id)) {
            return getElements().get(id);
        } else {
            Element value = new VertexImpl(id);
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
