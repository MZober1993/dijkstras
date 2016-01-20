package algorithm.binary;

import com.google.common.truth.Truth;
import datastructure.GraphHelper;
import datastructure.binary.GraphBinary;
import datastructure.binary.VertexBinary;
import datastructure.standard.GraphImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 14:03
 */
public class DijkstraImplBinaryTest {

    GraphBinary graph;
    GraphImpl graphStd;
    DijkstraImplBinary dijkstra;

    @Before
    public void setUp() {
        graph = GraphHelper.buildSampleGraph(
                new GraphBinary(0, 1, 2, 3, 4, 5, 6, 7));
        graphStd = GraphHelper.buildSampleGraph(
                new GraphImpl(0, 1, 2, 3, 4, 5, 6, 7));
        dijkstra = new DijkstraImplBinary();
    }

    @Test
    public void testMinimumShortestPath() throws Exception {
        VertexBinary start = graph.getElement(0);
        VertexBinary end = graph.getElement(7);
        List<Integer> actual = dijkstra.shortestPath(graph, start, end);

        Truth.assertThat(actual).containsAllOf(0, 1, 5, 7).inOrder();
    }
}
