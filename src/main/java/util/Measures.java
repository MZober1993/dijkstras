package util;

import algorithm.Dijkstra;
import com.google.common.collect.ImmutableList;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.standard.GraphImpl;

import java.util.List;
import java.util.stream.Collectors;

import static util.MathHelper.TEN_THOUSAND;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         23.11.15 - 12:44
 */
public enum Measures {
    MEMBER, MEASURE, BOXPLOT, THEORETICAL;

    public static final List<Integer> SAMPLE_FOR_MEASURE = ImmutableList.of(2, 10, 18, 26, 34, 42, 50, 58, 66, 74, 82,
            90, 98, 106, 114, 122, 130);

    public static List<Long> scaleMeasureSample(long i) {
        return SAMPLE_FOR_MEASURE.stream().map((Integer x) -> x * i)
                .collect(Collectors.toList());
    }

    public static <T extends Graph> void prepareMeasure(GraphImporter<T> graphImporter, Dijkstra algorithm) {
        for (int i = 0; i < 5; i++) {
            T graphSeq = graphImporter.importNVerticesAndGetSequentialGraph(TEN_THOUSAND);
            System.out.println(algorithm.shortestPath(graphSeq, graphSeq.getElement(GraphHelper.FIRST),
                    graphSeq.getLastRandomElement()));
        }
    }

    public static void prepareMeasure(GraphImpl graph, Dijkstra algorithm) {
        for (int i = 0; i < 10; i++) {
            algorithm.shortestPath(graph, graph.getElement(GraphHelper.FIRST), graph.getLastRandomElement());
        }
    }
}
