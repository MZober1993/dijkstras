package datastructure;

import algorithm.Dijkstra;
import com.google.common.base.Stopwatch;
import datastructure.binary.GraphBinary;
import datastructure.fibo.GraphFibo;
import datastructure.fibo.VertexFibo;
import datastructure.standard.EdgeImpl;
import datastructure.standard.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         16.11.15 - 21:39
 */
public class GraphHelper {

    public static final int FIRST = 1;

    public static <G extends Graph<VertexFibo, ? extends Edge<VertexFibo>>> GraphFibo box(G graph) {
        return (GraphFibo) graph;
    }

    public static <G extends Graph<T, H>, H extends Edge<T>, T extends Element>
    G buildSampleGraph(G graph) {
        EdgeBuilder<G, H, T> builder = new EdgeBuilder<>(graph);
        linkFlatGraphWithSampleDistances(builder);
        return graph;
    }

    private static void linkFlatGraphWithSampleDistances(EdgeBuilder builder) {
        builder.current(0).to(1, 7.0).to(2, 8.0);
        builder.current(1).to(5, 2.0);
        builder.current(2).to(5, 6.0).to(6, 4.0);
        builder.current(3).to(5, 8.0);
        builder.current(4).to(7, 1.0);
        builder.current(5).to(6, 9.0).to(7, 3.0);
    }

    public static <G extends Graph<T, ? extends Edge<T>>, T extends Element> long calculateTimeWithLastRandom(G graph,
                                                                                                              Dijkstra<T> algorithm) {
        T start = graph.getV(FIRST);
        T end = graph.getLastRandomElement();

        return calculateTime(graph, algorithm, start, end);
    }

    public static <G extends Graph<T, ? extends Edge<T>>, T extends Element> Pair<Long, List<Integer>>
    calculateTimeAndPathWithLastRandom(G graph, Dijkstra<T> algo) {
        T end = graph.getLastRandomElement();
        return calculateTimeAndPath(graph, algo, end);
    }

    public static <G extends Graph<T, ? extends Edge<T>>, T extends Element> Pair<Long, List<Integer>>
    calculateTimeAndPath(G graph, Dijkstra<T> algo, T end) {
        T start = graph.getV(FIRST);
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Integer> path = algo.shortestPath(graph, start, end);
        stopwatch.stop();

        return new Pair<>(stopwatch.elapsed(TimeUnit.NANOSECONDS), path);
    }

    public static <G extends Graph<T, ? extends Edge<T>>, T extends Element> Pair<Long, List<Integer>>
    calculateTimeAndPath(G graph, Dijkstra<T> algo, T start, T end) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Integer> path = algo.shortestPath(graph, start, end);
        stopwatch.stop();

        return new Pair<>(stopwatch.elapsed(TimeUnit.NANOSECONDS), path);
    }

    private static <G extends Graph<T, ? extends Edge<T>>, T extends Element> long calculateTime(G graph, Dijkstra<T> algorithm, T start,
                                                                                                 T end) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        algorithm.shortestPath(graph, start, end);
        stopwatch.stop();

        return stopwatch.elapsed(TimeUnit.NANOSECONDS);
    }

    public static List<Integer> reconstructPath(Element element) {
        List<Integer> path = new ArrayList<>();
        Element current = element;
        while (current.getAnchor() != null) {
            path.add(0, current.getId());
            current = current.getAnchor();
        }
        path.add(0, current.getId());
        return path;
    }

    public static GraphFibo transformGraphToFiboGraph(Graph<Vertex, EdgeImpl> graph) {
        GraphFibo entryGraph = new GraphFibo(elementsToIdList(graph));
        List<Set<EdgeImpl>> adjacencyGraph = graph.getAdjacencyGraph();
        IntStream.range(0, adjacencyGraph.size())
                .forEach(i -> adjacencyGraph.get(i).stream().forEach(edgeMapper(entryGraph, i)));
        return entryGraph;
    }

    private static Consumer<EdgeImpl> edgeMapper(GraphFibo entryGraph, int i) {
        return halfEdge -> entryGraph.addConnection(i, halfEdge.getConnected().getId()
                , halfEdge.getDistance());
    }

    private static Consumer<EdgeImpl> edgeMapper(GraphBinary binaryGraph, int i) {
        return halfEdge -> binaryGraph.addConnection(i, halfEdge.getConnected().getId()
                , halfEdge.getDistance());
    }

    public static GraphBinary transformGraphToBinaryGraph(Graph<Vertex, EdgeImpl> graph) {
        GraphBinary binaryGraph = new GraphBinary(elementsToIdList(graph));
        List<Set<EdgeImpl>> adjacencyGraph = graph.getAdjacencyGraph();
        IntStream.range(0, adjacencyGraph.size())
                .forEach(i -> adjacencyGraph.get(i).stream().forEach(edgeMapper(binaryGraph, i)));
        return binaryGraph;
    }

    private static <T extends Element> List<Integer> elementsToIdList(Graph<T, ? extends Edge<T>> entryGraph) {
        return entryGraph.getVs().values()
                .stream().map(T::getId).collect(Collectors.toList());
    }
}
