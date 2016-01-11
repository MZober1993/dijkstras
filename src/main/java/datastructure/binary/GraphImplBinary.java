package datastructure.binary;

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
public class GraphImplBinary implements Graph<BEntry<Element>> {

    private Map<Integer, List<Edge<BEntry<Element>>>> outgoingEdges;
    private List<Edge<BEntry<Element>>> allEdges;
    private Map<Integer, Element> vertices;
    private Map<Integer, BEntry<Element>> entryVertices;

    public GraphImplBinary() {
        init();
    }

    public GraphImplBinary(Integer... identifier) {
        checkNotNull(identifier);
        init();
        for (Integer id : identifier) {
            VertexImpl v = new VertexImpl(id);
            this.vertices.put(id, v);
            this.entryVertices.put(id, new BEntry<>(v, Double.MAX_VALUE));
        }
    }

    public GraphImplBinary(List<Integer> identifiers) {
        checkNotNull(identifiers);
        init();
        for (Integer id : identifiers) {
            VertexImpl v = new VertexImpl(id);
            this.vertices.put(id, v);
            this.entryVertices.put(id, new BEntry<>(v, Double.MAX_VALUE));
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
        BEntry<Element> one = getElementOrCreateOne(signOne);
        BEntry<Element> two = getElementOrCreateOne(signTwo);
        EdgeImplBinary edge = new EdgeImplBinary(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<Edge<BEntry<Element>>>> edges = Optional.ofNullable(outgoingEdges.get(
                one.getValue().getId()));
        if (edges.isPresent()) {
            List<Edge<BEntry<Element>>> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getValue().getId(), ImmutableList.<Edge<BEntry<Element>>>builder()
                        .addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getValue().getId(), ImmutableList.of(edge));
        }
    }

    @Override
    public List<Edge<BEntry<Element>>> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        return edgesFromNode(entryVertices.get(identifier));
    }

    @Override
    public List<Edge<BEntry<Element>>> getEdgesFromNode(BEntry<Element> entry) {
        checkNotNull(entry);
        return edgesFromNode(entry);
    }

    private List<Edge<BEntry<Element>>> edgesFromNode(BEntry<Element> element) {
        ImmutableList.Builder<Edge<BEntry<Element>>> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<Edge<BEntry<Element>>> edges : outgoingEdges.values()) {
            edges.stream().filter(currentEdge -> currentEdge.contains(element)).forEach(edgesFromNodeBuilder::add);
        }
        return edgesFromNodeBuilder.build();
    }

    public List<Edge<BEntry<Element>>> getEdges() {
        return allEdges;
    }

    @Override
    public Graph<BEntry<Element>> refreshGraph() {
        entryVertices.forEach((Integer id, BEntry<Element> entry) -> {
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
    public BEntry<Element> getElementOrCreateOne(int id) {
        if (getElements().containsKey(id)) {
            return getElements().get(id);
        } else {
            BEntry<Element> value = new BEntry<>(new VertexImpl(id), Double.MAX_VALUE);
            getElements().put(id, value);
            return value;
        }
    }

    @Override
    public Map<Integer, BEntry<Element>> getElements() {
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
