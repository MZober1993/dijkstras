package util;

import algorithm.fibo.DijkstraImplFibo;
import algorithm.standard.DijkstraImpl;
import com.google.common.collect.ImmutableList;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.fibo.Entry;
import datastructure.standard.GraphImpl;

import java.util.List;
import java.util.stream.Collectors;

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

    public static void prepareStd(GraphImpl graph) {
        DijkstraImpl algo = new DijkstraImpl();
        for (int i = 0; i < 10; i++) {
            int id = 40 + i;
            List<Integer> path = algo.shortestPath(graph, graph.getElement(GraphHelper.FIRST), graph.getElement(id));
            System.out.println(path + " Last: " + id);
        }
    }

    public static void prepareFibo(GraphImporter<Element> importer) {
        DijkstraImplFibo algo = new DijkstraImplFibo();
        long limit = 1000L;
        for (int i = 0; i < limit - 2; i++) {
            int id = 2 + i;
            Graph<Entry<Element>> fibo = importer.importEntryGraph(limit);
            List<Integer> path = algo.shortestPath(fibo, fibo.getElement(GraphHelper.FIRST),
                    fibo.getElement(id));
            System.out.println(path + " Last: " + id);
        }
    }
}
