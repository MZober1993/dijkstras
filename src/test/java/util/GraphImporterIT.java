package util;

import algorithm.Dijkstra;
import algorithm.fibo.DijkstraImplFibo;
import algorithm.standard.DijkstraImpl;
import com.google.common.truth.Truth;
import datastructure.Edge;
import datastructure.Element;
import datastructure.Graph;
import datastructure.fibo.Entry;
import datastructure.fibo.GraphImplFibo;
import datastructure.standard.GraphImpl;
import org.junit.Test;

import java.util.List;

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
    public static final int FIRST = 0;
    public static final long EMPTY = 0;
    public static final long SMALL_LIMIT_NUMBER = 20;
    public static final GraphImporter<Element> STD_GRAPH_IMPORTER = new GraphImporter<>(ImportFile.CREATED);
    public static final Dijkstra<Element> STD_DIJKSTRA = new DijkstraImpl();
    public static final GraphImporter<Entry<Element>> FIBO_GRAPH_IMPORTER = new GraphImporter<>(ImportFile.CREATED);
    public static final Dijkstra<Entry<Element>> FIBO_DIJKSTRA = new DijkstraImplFibo();

    @Test
    public void testImportStdGraph() {
        GraphImpl graph = STD_GRAPH_IMPORTER
                .importNVerticesAndGetGraph(SMALL_LIMIT_NUMBER, new GraphImpl());
        importGraphTest(graph);
    }

    @Test
    public void testImportStdGraphAndUseDijkstra() {
        GraphImpl graph = STD_GRAPH_IMPORTER
                .importNVerticesAndGetGraph(SMALL_LIMIT_NUMBER, new GraphImpl());
        Element start = graph.getElement(1);
        Element end = graph.getElement(15);
        List<Integer> shortestPath = STD_DIJKSTRA.shortestPath(graph, start, end);

        checkShortestPath(start.getId(), end.getId(), shortestPath);
    }

    @Test
    public void testImportFiboGraph() {
        GraphImplFibo graph = FIBO_GRAPH_IMPORTER
                .importNVerticesAndGetGraph(SMALL_LIMIT_NUMBER, new GraphImplFibo());
        Entry<Element> element = graph.getOne();
        List<Edge<Entry<Element>>> edgesFromNode = graph.getEdgesFromNode(element);
        Edge<Entry<Element>> edge = edgesFromNode.get(FIRST);

        checkGraphContainsVerticesAndEdges(graph, edgesFromNode);
        checkEdgeContainsDistanceAndTwoVertices(edge);
    }

    @Test
    public void testImportFiboGraphAndUseDijkstra() {
        GraphImplFibo graph = FIBO_GRAPH_IMPORTER
                .importNVerticesAndGetGraph(SMALL_LIMIT_NUMBER, new GraphImplFibo());
        Entry<Element> start = graph.getElement(1);
        Entry<Element> end = graph.getElement(15);
        List<Integer> shortestPath = FIBO_DIJKSTRA.shortestPath(graph, start, end);

        checkShortestPath(start.getId(), end.getId(), shortestPath);
    }

    @Test
    public void testImportFiboGraphAndUseMultipleDijkstra() {
        multipleDijkstra(FIBO_GRAPH_IMPORTER, SMALL_LIMIT_NUMBER);
    }

    private void multipleDijkstra(GraphImporter<Entry<Element>> importer, Long limit) {
        for (int i = 2; i < limit; i++) {
            try {
                GraphImplFibo graph = importer
                        .importNVerticesAndGetGraph(SMALL_LIMIT_NUMBER, new GraphImplFibo());
                Entry<Element> start = graph.getElement(1);
                Entry<Element> end = graph.getElement(i);
                checkNotNull(start);
                checkNotNull(end);
                List<Integer> shortestPath = FIBO_DIJKSTRA.shortestPath(graph, start, end);
                checkShortestPath(start.getId(), end.getId(), shortestPath);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failure at Entry<Element> with the id:" + i);
            }
        }
    }

    @Test
    public void testImportSimpleFiboGraphAndUseMultipleDijkstra() {
        multipleDijkstra(FIBO_GRAPH_IMPORTER, 7L);
    }

    private <T> void importGraphTest(Graph<T> graph) {
        T element = graph.getOne();
        List<Edge<T>> edgesFromNode = graph.getEdgesFromNode(element);
        Edge<T> edge = edgesFromNode.get(FIRST);

        checkGraphContainsVerticesAndEdges(graph, edgesFromNode);
        checkEdgeContainsDistanceAndTwoVertices(edge);
    }

    private static <T> void checkEdgeContainsDistanceAndTwoVertices(Edge<T> edge) {
        assertNotNull(edge.getDistance());
        assertNotNull(edge.getFirst());
        assertNotNull(edge.getSecond());
    }

    private <T> void checkGraphContainsVerticesAndEdges(Graph<T> graph, List<Edge<T>> edgesFromNode) {
        Truth.assertThat(graph.getElements().size() > MINIMUM_COUNT);
        Truth.assertThat(edgesFromNode.size() > EMPTY);
    }

    private void checkShortestPath(Integer start, Integer end, List<Integer> shortestPath) {
        assertFalse("Empty shortestPath", shortestPath.isEmpty());
        assertTrue("ShortestPath contains not the start-vertex.", shortestPath.contains(start));
        assertTrue("ShortestPath contains not the end-vertex.", shortestPath.contains(end));
    }
}
