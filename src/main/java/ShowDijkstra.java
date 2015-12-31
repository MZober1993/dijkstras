import algorithm.Dijkstra;
import algorithm.standard.DijkstraImpl;
import datastructure.GraphHelper;
import datastructure.Vertex;
import datastructure.standard.StandardGraph;
import util.GraphImporter;
import util.ImportFile;
import util.Measures;

import static util.MathHelper.A_MILLION;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         01.11.15 - 18:41
 */
public class ShowDijkstra {

    public static final int FIRST = 1;

    public static void main(String[] args) {
        Dijkstra dijkstra = new DijkstraImpl();
        StandardGraph graph = new GraphImporter<StandardGraph>(ImportFile.CREATED).importNVerticesAndGetSequentialGraph(10000L);
        Measures.prepareMeasure(graph, dijkstra);

        Vertex start = graph.getVertex(FIRST);
        int size = graph.getVertices().size();

        for (int i = 0; i < 500; i++) {
            int id = size * (1 + i) / (800 + i);
            Vertex end = graph.getVertex(id);

            System.out.println("id: " + id + ",T(10000):" +
                    GraphHelper.calculateTimeWithLastRandom(graph, dijkstra) / A_MILLION + " ms");
            System.out.println("id: " + id + ",Path:" + dijkstra.shortestPath(graph, start, end));
        }
    }
}
