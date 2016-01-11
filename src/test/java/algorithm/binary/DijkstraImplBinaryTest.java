package algorithm.binary;

import com.google.common.truth.Truth;
import datastructure.Element;
import datastructure.GraphHelper;
import datastructure.binary.BEntry;
import datastructure.binary.EdgeImplBinary;
import datastructure.binary.GraphImplBinary;
import datastructure.standard.EdgeImpl;
import datastructure.standard.GraphImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 11.01.16 - 14:03
 */
public class DijkstraImplBinaryTest {

    GraphImplBinary graph;
    GraphImpl graphStd;
    DijkstraImplBinary dijkstra;

    @Before
    public void setUp() {
        graph = GraphHelper.<GraphImplBinary, EdgeImplBinary, BEntry<Element>>buildSampleGraph(
                new GraphImplBinary(0, 1, 2, 3, 4, 5, 6, 7));
        graphStd = GraphHelper.<GraphImpl, EdgeImpl, Element>buildSampleGraph(
                new GraphImpl(0, 1, 2, 3, 4, 5, 6, 7));
        dijkstra = new DijkstraImplBinary();
    }

    @Test
    public void testMinimumShortestPath() throws Exception {
        BEntry<Element> start = graph.getElement(0);
        BEntry<Element> end = graph.getElement(7);
        List<Integer> actual = dijkstra.shortestPath(graph, start, end);

        Truth.assertThat(actual).containsAllOf(0, 1, 5, 7).inOrder();
    }

    @Test
    public void testSameGraph() throws Exception {
        Truth.assertThat(graph.getVertices()).isEqualTo(graphStd.getElements());
        Truth.assertThat(graph.getEdges().toString()).isEqualTo(graphStd.getEdges().toString());
    }
}
