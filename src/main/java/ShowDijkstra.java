import algorithm.Dijkstra;
import datastructure.Element;
import datastructure.Graph;
import datastructure.GraphHelper;

import static util.MathHelper.A_MILLION;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:41
 */
public class ShowDijkstra {

    public static final int FIRST = 1;

    public static void main(String[] args) {
        //todo: show dijkstra
    }

    private static <G extends Graph<T>, T extends Element> void testDijkstras(Dijkstra<T> dijkstra, G graph) {
        int size = graph.getElements().size();
        T start = graph.getElement(FIRST);
        for (int i = 0; i < 100; i++) {
            int id = size * (1 + i) / (800 + i);
            T end = graph.getElement(id);

            System.out.println("id: " + id + ",T(10000):" +
                    GraphHelper.calculateTimeWithLastRandom(graph, dijkstra) / A_MILLION + " ms");
            System.out.println("id: " + id + ",Path:" + dijkstra.shortestPath(graph, start, end));
        }
    }
}
