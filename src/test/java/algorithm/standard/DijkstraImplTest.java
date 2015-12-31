package algorithm.standard;

import com.google.common.truth.Truth;
import datastructure.GraphHelper;
import datastructure.Vertex;
import datastructure.standard.EdgeImpl;
import datastructure.standard.GraphImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:41
 */
public class DijkstraImplTest {

    GraphImpl graph;
    DijkstraImpl dijkstra;

    @Before
    public void setUp() {
        graph = GraphHelper.buildSampleGraph(new GraphImpl(0, 1, 2, 3, 4, 5, 6, 7)
                , EdgeImpl.class);
        dijkstra = new DijkstraImpl();
    }

    @Test
    public void testMinimumShortestPath() throws Exception {
        Vertex start = graph.getVertex(0);
        Vertex end = graph.getVertex(7);
        List<Integer> actual = dijkstra.shortestPath(graph, start, end);
        Truth.assertThat(actual).containsAllOf(1, 5, 7).inOrder();
    }
}