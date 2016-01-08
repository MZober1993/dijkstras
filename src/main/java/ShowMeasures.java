import algorithm.standard.DijkstraImpl;
import datastructure.Element;
import datastructure.standard.GraphImpl;
import util.Measures;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 02.01.16 - 20:29
 */
public class ShowMeasures {
    public static void main(String[] args) {
        MeasureAlgorithm<GraphImpl, DijkstraImpl, Element> measure = new MeasureAlgorithm<>();
        Measures.prepareFibo(measure.CREATED_IMPORTER);
    }
}
