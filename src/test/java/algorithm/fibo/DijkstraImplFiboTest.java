package algorithm.fibo;

import com.google.common.truth.Truth;
import datastructure.GraphHelper;
import datastructure.Vertex;
import datastructure.fibo.EdgeImplFibo;
import datastructure.fibo.Entry;
import datastructure.fibo.GraphImplFibo;
import datastructure.standard.EdgeImpl;
import datastructure.standard.GraphImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:41
 */
public class DijkstraImplFiboTest {

    GraphImplFibo graph;
    GraphImpl graphStd;
    DijkstraImplFibo dijkstra;

    @Before
    public void setUp() {
        graph = GraphHelper.buildSampleGraph(new GraphImplFibo(0, 1, 2, 3, 4, 5, 6, 7), EdgeImplFibo.class);
        graphStd = GraphHelper.buildSampleGraph(new GraphImpl(0, 1, 2, 3, 4, 5, 6, 7), EdgeImpl.class);
        dijkstra = new DijkstraImplFibo();
    }

    @Test
    public void testMinimumShortestPath() throws Exception {
        Entry<Vertex> start = graph.getElement(0);
        Entry<Vertex> end = graph.getElement(7);
        List<Integer> actual = dijkstra.shortestPath(graph, start, end);

        Truth.assertThat(actual).containsAllOf(0, 1, 5, 7).inOrder();
    }

    @Test
    public void testSameGraph() throws Exception {
        Truth.assertThat(graph.getEdges()).containsAllIn(graphStd.getEdges());
        Truth.assertThat(graph.getVertices().toString()).isEqualTo(graphStd.getElements().toString());
    }
}