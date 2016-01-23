import datastructure.Element;
import util.GraphFileCreator;
import util.GraphImporter;
import util.Measures;

/**
 * @author <a href="mailto:mattthias.zober@outlook.de">Matthias Zober</a>
 *         On 02.01.16 - 20:29
 */
public class ShowMeasures {
    public static void main(String[] args) {
        MeasureAlgorithm measure = new MeasureAlgorithm();
        long limit = GraphFileCreator.COMPLETE_LIMIT;
        int endBegin = GraphFileCreator.COMPLETE_LIMIT - 10;
        //GraphImporter<Element> importer = measure.CREATED_IMPORTER;
        GraphImporter<Element> importer = measure.COMPLETE_IMPORTER;

        Measures.prepareStd(importer.importElementGraph(limit), limit, endBegin);
        measure.tNRecordInOneFile();
    }
}
