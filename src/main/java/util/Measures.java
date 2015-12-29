package util;

import algorithm.Dijkstra;
import com.google.common.collect.ImmutableList;
import datastructure.GraphHelper;
import datastructure.standard.StandardGraph;
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

    public static void prepareMeasure(GraphImporter graphImporter, Dijkstra algorithm) {
        for (int i = 0; i < 10; i++) {
            GraphImpl graphSeq = graphImporter.importNVerticesAndGetSequentialGraph(TEN_THOUSAND);
            System.out.println(algorithm.shortestPath(graphSeq, graphSeq.getVertex(GraphHelper.FIRST),
                    graphSeq.getLastRandomVertex()));
        }
    }

    public static void prepareMeasure(StandardGraph graph, Dijkstra algorithm) {
        for (int i = 0; i < 10; i++) {
            algorithm.shortestPath(graph, graph.getVertex(GraphHelper.FIRST), graph.getLastRandomVertex());
        }
    }
}
