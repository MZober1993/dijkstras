package datastructure;

import algorithm.Dijkstra;
import com.google.common.base.Stopwatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         16.11.15 - 21:39
 */
public class GraphHelper {

    public static final int FIRST = 1;

    public static <G extends Graph, E extends Edge> G buildSampleGraph(G graph, Class<E> clazz) {
        EdgeBuilder builder = new EdgeBuilder<>(graph, clazz);
        linkGraphWithSampleDistances(graph, builder);
        return graph;
    }

    private static <T extends Graph> void linkGraphWithSampleDistances(T graph, EdgeBuilder builder) {
        graph.linkVertex(builder.current(0).to(1, 7.0).to(2, 8.0));
        graph.linkVertex(builder.current(1).to(0, 7.0).to(5, 2.0));
        graph.linkVertex(builder.current(2).to(0, 8.0).to(5, 6.0).to(6, 4.0));
        graph.linkVertex(builder.current(3).to(5, 8.0));
        graph.linkVertex(builder.current(4).to(7, 1.0));
        graph.linkVertex(builder.current(5).to(1, 2.0).to(2, 6.0).to(3, 8.0)
                .to(6, 9.0).to(7, 3.0));
        graph.linkVertex(builder.current(6).to(2, 4.0).to(5, 9.0));
        graph.linkVertex(builder.current(7).to(4, 1.0).to(5, 3.0));
    }

    public static <G extends Graph<T>, T> long calculateTimeWithLastRandom(G graph, Dijkstra algorithm) {
        T start = graph.getElement(FIRST);
        T end = graph.getLastRandomElement();

        return calculateTime(graph, algorithm, start, end);
    }

    private static <G extends Graph<T>, T> long calculateTime(G graph, Dijkstra algorithm, T start, T end) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        algorithm.shortestPath(graph, start, end);
        stopwatch.stop();

        return stopwatch.elapsed(TimeUnit.NANOSECONDS);
    }

    public static List<Integer> reconstructPath(Vertex vertex) {
        List<Integer> path = new ArrayList<>();
        Vertex current = vertex;
        while (current.getPrevious() != null) {
            path.add(0, current.getId());
            current = current.getPrevious();
        }
        path.add(0, current.getId());
        return path;
    }
}
