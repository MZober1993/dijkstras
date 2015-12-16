package algorithm.fibo;

import com.google.common.truth.Truth;
import datastructure.Vertex;
import datastructure.fibo.EdgeImplFibo;
import datastructure.fibo.GraphImplFibo;
import datastructure.standard.EdgeImpl;
import datastructure.standard.GraphImpl;
import org.junit.Before;
import org.junit.Test;
import util.GraphHelper;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:41
 */
public class DijkstraTest {

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
        Vertex start = graph.getVertex(0);
        Vertex end = graph.getVertex(7);
        List<Integer> actual = dijkstra.shortestPath(graph, start, end);

        Truth.assertThat(actual).containsAllOf(1, 5, 7).inOrder();
        //TODO: resolve Test
    }

    @Test
    public void testSameGraph() throws Exception {
        Truth.assertThat(graph.getEdges()).containsAllIn(graphStd.getEdges());
        Truth.assertThat(graph.getVertices().toString()).isEqualTo(graphStd.getVertices().toString());
    }
}