package util;

import algorithm.Dijkstra;
import algorithm.fibo.DijkstraImplFibo;
import algorithm.standard.DijkstraImpl;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.fibo.Entry;
import datastructure.fibo.GraphImplFibo;
import datastructure.standard.GraphImpl;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         23.11.15 - 12:44
 */
public enum Measures {
    MEMBER, MEASURE, BOXPLOT, THEORETICAL, PLAIN_T_N;

    public static final List<Integer> SAMPLE_FOR_MEASURE = ImmutableList.of(2, 10, 18, 26, 34, 42, 50, 58, 66, 74, 82,
            90, 98, 106, 114, 122, 130);

    public static List<Long> scaleMeasureSample(long i) {
        return SAMPLE_FOR_MEASURE.stream().map((Integer x) -> x * i)
                .collect(Collectors.toList());
    }

    public static List<Long> measureLimits(int limitOfGraph, int multiplier, int start) {
        return LongStream.range(0, limitOfGraph / multiplier).map(x -> x * multiplier + start)
                .boxed().collect(Collectors.toList());
    }

    public static String prepareStd(GraphImpl graph, long limit, int endBegin) {
        StringBuilder builder = new StringBuilder();
        DijkstraImpl algo = new DijkstraImpl();
        for (int i = 0; i < limit - endBegin; i++) {
            int id = endBegin + i;
            List<Integer> path = calcShortestPathAndPrintTime(graph, algo, id);

            builder.append(path).append(" Last: ").append(id).append("\n");
        }
        return builder.toString();
    }

    public static String prepareFibo(GraphImplFibo graph, long limit, int endBegin) {
        StringBuilder builder = new StringBuilder();
        DijkstraImplFibo algo = new DijkstraImplFibo();
        for (int i = 0; i < limit - endBegin; i++) {
            int id = endBegin + i;
            Graph<Entry<Element>> fibo = graph.refreshGraph();
            List<Integer> path = calcShortestPathAndPrintTime(fibo, algo, id);

            builder.append(path).append(" Last: ").append(id).append("\n");
        }
        return builder.toString();
    }

    private static <G extends Graph<T>, T extends Element>
    List<Integer> calcShortestPathAndPrintTime(G graph, Dijkstra<T> algo, int id) {
        Stopwatch stopwatch = Stopwatch.createStarted();
        List<Integer> path = algo.shortestPath(graph, graph.getElement(GraphHelper.FIRST), graph.getElement(id));
        stopwatch.stop();

        System.out.println(stopwatch.elapsed(TimeUnit.NANOSECONDS));
        return path;
    }
}
