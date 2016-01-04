package algorithm.sample;

import com.google.common.truth.Truth;
import datastructure.Element;
import datastructure.GraphHelper;
import datastructure.sample.EdgeFibo;
import datastructure.sample.FiboHeap;
import datastructure.sample.GraphFibo;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 07.01.16 - 20:03
 */
public class DijkstraFiboTest {
    GraphFibo graph;
    DijkstraFibo dijkstra;

    @Before
    public void setUp() {
        graph = GraphHelper.<GraphFibo, EdgeFibo, FiboHeap.Node<Element>>buildSampleGraph(
                new GraphFibo(0, 1, 2, 3, 4, 5, 6, 7));
        dijkstra = new DijkstraFibo();
    }

    @Test
    public void testMinimumShortestPath() throws Exception {
        System.out.println(graph);
        FiboHeap.Node<Element> start = graph.getElement(0);
        FiboHeap.Node<Element> end = graph.getElement(7);

        List<Integer> actual = dijkstra.shortestPath(graph, start, end);

        Truth.assertThat(actual).containsAllOf(0, 1, 5, 7).inOrder();
    }
}
