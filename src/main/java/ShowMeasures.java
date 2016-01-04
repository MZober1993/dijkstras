import algorithm.fibo.DijkstraImplFibo;
import algorithm.standard.DijkstraImpl;
import datastructure.Element;
import datastructure.fibo.Entry;
import datastructure.fibo.GraphImplFibo;
import datastructure.standard.GraphImpl;
import util.Measures;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 02.01.16 - 20:29
 */
public class ShowMeasures {
    public static void main(String[] args) {
        MeasureAlgorithm<GraphImplFibo, DijkstraImplFibo, Entry<Element>> fiboMeasures
                = new MeasureAlgorithm<>();
        /*fiboMeasures.measures(new DijkstraImplFibo(), fiboMeasures.CREATED_IMPORTER,
                new GraphFactory<>(GraphImplFibo.class));*/

        //TODO: check this

        //measuresForNY();
        MeasureAlgorithm<GraphImpl, DijkstraImpl, Element> stdMeasures = new MeasureAlgorithm<>();
        GraphImpl graph = stdMeasures.CREATED_IMPORTER.importNVerticesAndGetGraph(50L, new GraphImpl());
        GraphImplFibo fgraph = fiboMeasures.CREATED_IMPORTER.importNVerticesAndGetGraph(50L, new GraphImplFibo());
        if (fgraph.isEmpty()) {
            System.out.println("empty");
        } else {
            System.out.println(fgraph);
        }

//        fibo(fiboMeasures);
        Measures.prepareMeasure(graph, new DijkstraImpl());
        /*stdMeasures.measures(new DijkstraImpl(), stdMeasures.CREATED_IMPORTER,
                new GraphFactory<>(GraphImpl.class));*/
    }

    private static void fibo(MeasureAlgorithm<GraphImplFibo, DijkstraImplFibo, Entry<Element>> fiboMeasures) {
        Measures.prepareMeasure(fiboMeasures.CREATED_IMPORTER.importNVerticesAndGetGraph(100L
                , new GraphImplFibo())
                , new DijkstraImplFibo());
    }
}
