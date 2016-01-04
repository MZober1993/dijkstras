package util;

import algorithm.Dijkstra;
import com.google.common.collect.ImmutableList;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphFactory;
import datastructure.GraphHelper;

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

    public static <G extends Graph<T>, T extends Element> void prepareMeasure(GraphImporter<G> graphImporter,
                                                                              Dijkstra<T> algorithm,
                                                                              GraphFactory<G, T> factory) {
        for (int i = 0; i < 5; i++) {
            G graph = graphImporter.importNVerticesAndGetGraph(TEN_THOUSAND, factory.create());
            System.out.println(algorithm.shortestPath(graph, graph.getElement(GraphHelper.FIRST),
                    graph.getLastRandomElement()));
        }
    }

    public static <G extends Graph<T>, T extends Element> void prepareMeasure(G graph, Dijkstra<T> algorithm) {
        for (int i = 0; i < 10; i++) {
            int id = 40 + i;
            List<Integer> path = algorithm.shortestPath(graph, graph.getElement(GraphHelper.FIRST),
                    graph.getElement(id));
            System.out.println(path + " Last: " + id);
        }
    }
}
