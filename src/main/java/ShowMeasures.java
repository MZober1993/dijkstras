import algorithm.fibo.DijkstraImplFibo;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphHelper;
import datastructure.fibo.Entry;
import datastructure.fibo.GraphImplFibo;
import util.GraphImporter;
import util.ImportFile;
import util.Measures;

import java.util.List;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 02.01.16 - 20:29
 */
public class ShowMeasures {
    public static void main(String[] args) {
        DijkstraImplFibo algo = new DijkstraImplFibo();
        long limit = 10000L;
        for (int i = 0; i < limit - 2; i++) {
            int id = 2 + i;
            Graph<Entry<Element>> graph = new GraphImporter<Entry<Element>>(ImportFile.CREATED, limit)
                    .importGraphWithLimit(new GraphImplFibo());
            List<Integer> path = algo.shortestPath(graph, graph.getElement(GraphHelper.FIRST),
                    graph.getElement(id));
            System.out.println(path + " Last: " + id);
        }
    }

    private static void fibo(MeasureAlgorithm<GraphImplFibo, DijkstraImplFibo, Entry<Element>> fiboMeasures) {
        Measures.prepareMeasure(
                fiboMeasures.CREATED_IMPORTER.importNVerticesAndGetGraph(100L
                        , new GraphImplFibo())
                , new DijkstraImplFibo());
    }
}
