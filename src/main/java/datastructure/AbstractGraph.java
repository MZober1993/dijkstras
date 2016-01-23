package datastructure;

import java.util.*;
import java.util.stream.IntStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 20.01.16 - 22:25
 */
public abstract class AbstractGraph<T extends Element, H extends Edge<T>> implements Graph<T, H> {

    protected int edgeSize = 0;
    protected ArrayList<Set<H>> adjacencyGraph;
    protected Map<Integer, T> vertices;

    public AbstractGraph() {
        init(0);
    }

    public AbstractGraph(Integer... identifier) {
        checkNotNull(identifier);
        init(identifier.length);
    }

    public AbstractGraph(List<Integer> identifiers) {
        checkNotNull(identifiers);
        init(identifiers.size());
    }

    public void init(int size) {
        vertices = new LinkedHashMap<>();
        adjacencyGraph = new ArrayList<>(size + 1);
        IntStream.range(0, size + 1).forEach(x -> adjacencyGraph.add(new HashSet<>()));
    }

    @Override
    public Set<H> getConnectedElements(Element vertex) {
        return adjacencyGraph.get(vertex.getId());
    }

    public List<Set<H>> getAdjacencyGraph() {
        return adjacencyGraph;
    }

    @Override
    public Map<Integer, T> getVs() {
        return vertices;
    }

    @Override
    public int getEdgeSize() {
        return edgeSize;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "\nvertices=" + vertices +
                "\nedges=" + adjacencyGraph +
                "\n}";
    }
}
