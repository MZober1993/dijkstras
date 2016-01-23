import algorithm.Dijkstra;
import algorithm.binary.DijkstraImplBinary;
import algorithm.fibo.DijkstraImplFibo;
import algorithm.standard.DijkstraImpl;
import datastructure.*;
import util.GraphImporter;
import util.ImportFile;

import java.util.List;

import static util.MathHelper.A_MILLION;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:41
 */
public class ShowDijkstra {

    public static final int FIRST = 1;

    public static void main(String[] args) {
        GraphImporter<Element> importer = new GraphImporter<>(ImportFile.CREATED);
        long limit = 10000L;
        testDijkstras(new DijkstraImpl(), importer.importElementGraph(limit), limit);
        testDijkstras(new DijkstraImplFibo(), importer.importEntryGraph(limit), limit);
        testDijkstras(new DijkstraImplBinary(), importer.importBinaryGraph(limit), limit);
    }

    private static <G extends Graph<T, ? extends Edge>, T extends Element> void testDijkstras(Dijkstra<T> dijkstra,
                                                                                              G graph, Long limit) {
        int size = graph.getVs().size();
        for (int i = 0; i < 100; i++) {
            int id = size * (1 + i) / (800 + i);
            graph.refreshGraph();
            Pair<Long, List<Integer>> pair = GraphHelper.calculateTimeAndPath(graph, dijkstra, graph.getV(id));
            System.out.println("id: " + id + ",T(" + limit + "):" + pair.getFirst() / A_MILLION + " ms\n" +
                    ",Path:" + pair.getSecond());
        }
    }
}
