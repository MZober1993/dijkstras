import algorithm.fibo.DijkstraImplFibo;
import algorithm.standard.DijkstraImpl;
import datastructure.fibo.GraphImplFibo;
import datastructure.standard.StandardGraph;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 02.01.16 - 20:29
 */
public class ShowMeasures {
    public static void main(String[] args) {
        MeasureAlgorithm<GraphImplFibo, DijkstraImplFibo> fiboMeasures = new MeasureAlgorithm<>();
        fiboMeasures.measures(new DijkstraImplFibo(), fiboMeasures.CREATED_IMPORTER);
        //measuresForNY();
        MeasureAlgorithm<StandardGraph, DijkstraImpl> stdMeasures = new MeasureAlgorithm<>();
        stdMeasures.measures(new DijkstraImpl(), stdMeasures.CREATED_IMPORTER);
    }
}
