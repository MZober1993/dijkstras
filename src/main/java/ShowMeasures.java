import algorithm.standard.DijkstraImpl;
import datastructure.Element;
import datastructure.standard.GraphImpl;
import util.GraphImporter;
import util.Measures;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 02.01.16 - 20:29
 */
public class ShowMeasures {
    public static void main(String[] args) {
        MeasureAlgorithm<GraphImpl, DijkstraImpl, Element> measure = new MeasureAlgorithm<>();
        long limit = 100L;
        int endBegin = 1;
        //GraphImporter<Element> importer = measure.CREATED_IMPORTER;
        GraphImporter<Element> importer = measure.COMPLETE_IMPORTER;


        /*System.out.println("std");
        Measures.prepareStd(importer.importElementGraph(limit), limit, endBegin);
        */
        System.out.println("fibo");
        Measures.prepareFibo(importer.importEntryGraph(limit), limit, endBegin);
    }
}
