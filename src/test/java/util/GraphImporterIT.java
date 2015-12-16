package util;

import algorithm.Dijkstra;
import algorithm.standard.DijkstraImpl;
import com.google.common.truth.Truth;
import datastructure.standard.StandardEdge;
import datastructure.standard.StandardGraph;
import datastructure.Vertex;
import datastructure.standard.GraphImpl;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         18.11.15 - 14:24
 *         On the Site <a href="http://www.dis.uniroma1.it/challenge9/download.shtml">Dimacs</a> you can download
 *         realistic graphs of the usa. The downloaded graph is represented in an import-file. All import-files are
 *         listed in the enum: ImportFiles.
 */
public class GraphImporterIT {

    public static final long MINIMUM_COUNT = 2;
    public static final int FIRST = 0;
    public static final long EMPTY = 0;
    public static final long HUGE_LIMIT_NUMBER = 2000000000;
    public static final long SMALL_LIMIT_NUMBER = 20;
    GraphImporter graphImporter;
    Dijkstra dijkstra;

    @Before
    public void setUp() {
        graphImporter = new GraphImporter(ImportFile.NY);
        dijkstra = new DijkstraImpl();

    }

    @Test
    public void testImportGraph() {
        GraphImpl graph = graphImporter.importLinesOfFileAndGetSequentialGraph(HUGE_LIMIT_NUMBER);
        Vertex vertex = graph.getOne();
        List<StandardEdge> edgesFromNode = graph.getEdgesFromNode(vertex);
        StandardEdge edge = edgesFromNode.get(FIRST);

        checkGraphContainsVerticesAndEdges(graph, edgesFromNode);
        checkEdgeContainsDistanceAndTwoVertices(edge);
    }

    @Test
    public void testImportSmallGraphAndUseDijkstra() {
        GraphImpl graph = graphImporter.importLinesOfFileAndGetSequentialGraph(SMALL_LIMIT_NUMBER);
        Vertex start = graph.getVertexWithIndex(0);
        Vertex end = graph.getVertex(13);
        List<Integer> shortestPath = dijkstra.shortestPath(graph, start, end);

        checkShortestPath(start, end, shortestPath);
    }

    @Test
    public void testImportHugeGraphAndUseDijkstra() {
        GraphImpl graph = graphImporter.importLinesOfFileAndGetSequentialGraph(HUGE_LIMIT_NUMBER);
        Vertex start = graph.getVertexWithIndex(0);
        Vertex end = graph.getVertexWithIndex(graph.getVertices().values().size() - 1);
        List<Integer> shortestPath = dijkstra.shortestPath(graph, start, end);
        checkShortestPath(start, end, shortestPath);
    }

    private static void checkEdgeContainsDistanceAndTwoVertices(StandardEdge edge) {
        assertNotNull(edge.getDistance());
        assertNotNull(edge.getFirstVertex());
        assertNotNull(edge.getSecondVertex());
    }

    private void checkGraphContainsVerticesAndEdges(StandardGraph graph, List<StandardEdge> edgesFromNode) {
        Truth.assertThat(graph.getVertices().size() > MINIMUM_COUNT);
        Truth.assertThat(edgesFromNode.size() > EMPTY);
    }

    private void checkShortestPath(Vertex start, Vertex end, List<Integer> shortestPath) {
        assertFalse("Empty shortestPath", shortestPath.isEmpty());
        assertTrue("ShortestPath contains not the start-vertex.", shortestPath.contains(start.getId()));
        assertTrue("ShortestPath contains not the end-vertex.", shortestPath.contains(end.getId()));
    }
}
