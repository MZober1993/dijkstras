package datastructure.fibo;

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
public class GraphImplFibo implements Graph<Entry<Element>> {

    private Map<Integer, List<Edge<Entry<Element>>>> outgoingEdges;
    private List<Edge<Entry<Element>>> allEdges;
    private Map<Integer, Element> vertices;
    private Map<Integer, Entry<Element>> entryVertices;

    public GraphImplFibo() {
        init();
    }

    public GraphImplFibo(Integer... identifier) {
        checkNotNull(identifier);
        init();
        for (Integer id : identifier) {
            VertexImpl v = new VertexImpl(id);
            this.vertices.put(id, v);
            this.entryVertices.put(id, new Entry<>(v, Double.MAX_VALUE));
        }
    }

    public GraphImplFibo(List<Integer> identifiers) {
        checkNotNull(identifiers);
        init();
        for (Integer id : identifiers) {
            VertexImpl v = new VertexImpl(id);
            this.vertices.put(id, v);
            this.entryVertices.put(id, new Entry<>(v, Double.MAX_VALUE));
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
        Entry<Element> one = getElementOrCreateOne(signOne);
        Entry<Element> two = getElementOrCreateOne(signTwo);
        EdgeImplFibo edge = new EdgeImplFibo(one, two, distance);
        if (!allEdges.contains(edge)) {
            allEdges.add(edge);
        }
        Optional<List<Edge<Entry<Element>>>> edges = Optional.ofNullable(outgoingEdges.get(
                one.getValue().getId()));
        if (edges.isPresent()) {
            List<Edge<Entry<Element>>> value = edges.get();
            if (!value.contains(edge)) {
                outgoingEdges.put(one.getValue().getId(), ImmutableList.<Edge<Entry<Element>>>builder()
                        .addAll(value).add(edge).build());
            }
        } else {
            outgoingEdges.put(one.getValue().getId(), ImmutableList.of(edge));
        }
    }

    @Override
    public List<Edge<Entry<Element>>> getEdgesFromNode(Integer identifier) {
        checkNotNull(identifier);
        return edgesFromNode(entryVertices.get(identifier));
    }

    @Override
    public List<Edge<Entry<Element>>> getEdgesFromNode(Entry<Element> entry) {
        checkNotNull(entry);
        return edgesFromNode(entry);
    }

    private List<Edge<Entry<Element>>> edgesFromNode(Entry<Element> element) {
        ImmutableList.Builder<Edge<Entry<Element>>> edgesFromNodeBuilder = new ImmutableList.Builder<>();
        for (List<Edge<Entry<Element>>> edges : outgoingEdges.values()) {
            edges.stream().filter(currentEdge -> currentEdge.contains(element)).forEach(edgesFromNodeBuilder::add);
        }
        return edgesFromNodeBuilder.build();
    }

    public List<Edge<Entry<Element>>> getEdges() {
        return allEdges;
    }

    @Override
    public Graph<Entry<Element>> refreshGraph() {
        entryVertices.forEach((Integer id, Entry<Element> entry) -> {
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
    public Entry<Element> getElementOrCreateOne(int id) {
        if (getElements().containsKey(id)) {
            return getElements().get(id);
        } else {
            Entry<Element> value = new Entry<>(new VertexImpl(id), Double.MAX_VALUE);
            getElements().put(id, value);
            return value;
        }
    }

    @Override
    public Map<Integer, Entry<Element>> getElements() {
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
