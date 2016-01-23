package util;

import algorithm.Dijkstra;
import algorithm.fibo.DijkstraImplFibo;
import algorithm.standard.DijkstraImpl;
import com.google.common.truth.Truth;
import datastructure.Edge;
import datastructure.Element;
import datastructure.Graph;
import datastructure.fibo.EdgeImplFibo;
import datastructure.fibo.GraphFibo;
import datastructure.fibo.VertexFibo;
import datastructure.standard.GraphImpl;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;
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
    public static final int FIRST = 1;
    public static final long EMPTY = 0;
    public static final long SMALL_LIMIT_NUMBER = 20;
    public static final GraphImporter<Element> IMPORTER = new GraphImporter<>(ImportFile.CREATED);
    public static final Dijkstra<Element> STD_DIJKSTRA = new DijkstraImpl();
    public static final Dijkstra<VertexFibo> FIBO_DIJKSTRA = new DijkstraImplFibo();

    @Test
    public void testImportStdGraph() {
        GraphImpl graph = IMPORTER.importElementGraph(SMALL_LIMIT_NUMBER);
        importGraphTest(graph);
    }

    @Test
    public void testImportStdGraphAndUseDijkstra() {
        GraphImpl graph = IMPORTER.importElementGraph(SMALL_LIMIT_NUMBER);
        Element start = graph.getV(1);
        Element end = graph.getV(15);
        List<Integer> shortestPath = STD_DIJKSTRA.shortestPath(graph, start, end);

        checkShortestPath(start.getId(), end.getId(), shortestPath);
    }

    @Test
    public void testImportFiboGraph() {
        GraphFibo graph = IMPORTER.importEntryGraph(SMALL_LIMIT_NUMBER);
        VertexFibo element = graph.getOne();
        Set<EdgeImplFibo> edgesFromNode = graph.getConnectedElements(element);

        checkGraphContainsVerticesAndEdges(graph, edgesFromNode);
        checkEdgeContainsDistanceAndAVertex(edgesFromNode.stream().findFirst().get());
    }

    @Test
    public void testImportFiboGraphAndUseDijkstra() {
        GraphFibo graph = IMPORTER.importEntryGraph(SMALL_LIMIT_NUMBER);
        VertexFibo start = graph.getV(1);
        VertexFibo end = graph.getV(15);
        List<Integer> shortestPath = FIBO_DIJKSTRA.shortestPath(graph, start, end);

        checkShortestPath(start.getId(), end.getId(), shortestPath);
    }

    @Test
    public void testImportFiboGraphAndUseMultipleDijkstra() {
        multipleDijkstra(SMALL_LIMIT_NUMBER);
    }

    private void multipleDijkstra(Long limit) {
        for (int i = 2; i < limit; i++) {
            try {
                GraphFibo graph = IMPORTER.importEntryGraph(SMALL_LIMIT_NUMBER);
                VertexFibo start = graph.getV(1);
                VertexFibo end = graph.getV(i);
                checkNotNull(start);
                checkNotNull(end);
                List<Integer> shortestPath = FIBO_DIJKSTRA.shortestPath(graph, start, end);
                checkShortestPath(start.getId(), end.getId(), shortestPath);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failure at VertexFibo with the id:" + i);
            }
        }
    }

    @Test
    public void testImportSimpleFiboGraphAndUseMultipleDijkstra() {
        multipleDijkstra(7L);
    }

    private <T extends Element> void importGraphTest(Graph<T, ? extends Edge<T>> graph) {
        T element = graph.getOne();
        Set<? extends Edge<T>> edgesFromNode = graph.getConnectedElements(element);
        Edge<T> edge = graph.getAdjacencyGraph().get(FIRST).stream().findFirst().get();

        checkGraphContainsVerticesAndEdges(graph, edgesFromNode);
        checkEdgeContainsDistanceAndAVertex(edge);
    }

    private static <T extends Element, E extends Edge<T>> void
    checkEdgeContainsDistanceAndAVertex(E edge) {
        assertNotNull(edge.getDistance());
        assertNotNull(edge.getConnected());
    }

    private <T extends Element> void checkGraphContainsVerticesAndEdges(Graph<T, ? extends Edge<T>> graph
            , Set<? extends Edge<T>> edgesFromNode) {
        Truth.assertThat(graph.getVs().size() > MINIMUM_COUNT);
        Truth.assertThat(edgesFromNode.size() > EMPTY);
    }

    private void checkShortestPath(Integer start, Integer end, List<Integer> shortestPath) {
        assertFalse("Empty shortestPath", shortestPath.isEmpty());
        assertTrue("ShortestPath contains not the start-vertex.", shortestPath.contains(start));
        assertTrue("ShortestPath contains not the end-vertex.", shortestPath.contains(end));
    }
}
